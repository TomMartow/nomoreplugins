/*
 * Copyright (c) 2018, James Swindle <wilingua@gmail.com>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package plugin.nomore.objectmarkersextended;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.queries.DecorativeObjectQuery;
import net.runelite.api.queries.GameObjectQuery;
import net.runelite.api.queries.GroundObjectQuery;
import net.runelite.api.queries.WallObjectQuery;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.Keybind;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.HotkeyListener;
import org.pf4j.Extension;
import plugin.nomore.objectmarkersextended.utils.StringFormat;
import plugin.nomore.objectmarkersextended.builder.ConfigObject;
import plugin.nomore.objectmarkersextended.builder.HighlightingObject;

import javax.inject.Inject;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

@Extension
@PluginDescriptor(
		name = "Ex: Object Markers",
		description = "Object Markers, but with more options.",
		tags = {"object", "marker", "nomore"}
)
@Slf4j
public class ObjectMarkersExtendedPlugin extends Plugin implements KeyListener
{

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private KeyManager keyManager;

	@Inject
	private ObjectMarkersExtendedConfig config;

	@Inject
	private ObjectMarkersExtendedOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private StringFormat stringFormat;

	@Provides
	ObjectMarkersExtendedConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ObjectMarkersExtendedConfig.class);
	}

	private static final List<HighlightingObject> objectToHighlight = new ArrayList<>();
	private final List<ConfigObject> configObjects = new ArrayList<>();

	private static final String UNMARK = "Un-tag";
	private static final String MARK = "Tag";
	private boolean isShiftKeyPressed = false;

	@Override
	protected void startUp()
	{
		keyManager.registerKeyListener(this);
		overlayManager.add(overlay);
		getConfigTextField();
		if (client.getLocalPlayer() == null)
		{
			return;
		}
		clientThread.invoke(this::getAllObjects);
	}

	@Override
	protected void shutDown()
	{
		keyManager.unregisterKeyListener(this);
		overlayManager.remove(overlay);
		objectToHighlight.clear();
	}

	@Subscribe
	private void on(MenuOpened e)
	{

		if (!isShiftKeyPressed)
		{
			client.setMenuEntries(e.getMenuEntries());
			return;
		}

		MenuEntry objectEntry = Arrays.stream(e.getMenuEntries()).filter(entry -> entry.getMenuAction() == MenuAction.EXAMINE_OBJECT).findFirst().orElse(null);

		if (objectEntry == null)
		{
			return;
		}

		final Tile tile = client.getScene().getTiles()[client.getPlane()][objectEntry.getParam0()][objectEntry.getParam1()];
		final TileObject object = findTileObject(tile, objectEntry.getIdentifier());

		if (object == null)
		{
			return;
		}

		MenuEntry[] menuEntries = e.getMenuEntries();
		menuEntries = Arrays.copyOf(menuEntries, menuEntries.length + 1);
		MenuEntry menuEntry = menuEntries[2] = new MenuEntry();

		menuEntry.setOption(getObjectToHighlight().stream().anyMatch(o -> o.getObject() == object) ? UNMARK : MARK);
		menuEntry.setTarget(objectEntry.getTarget());
		menuEntry.setParam0(objectEntry.getParam0());
		menuEntry.setParam1(objectEntry.getParam1());
		menuEntry.setIdentifier(objectEntry.getIdentifier());
		menuEntry.setOpcode(objectEntry.getMenuAction().getId());
		client.setMenuEntries(menuEntries);
	}

	@Subscribe
	private void on(MenuOptionClicked e)
	{
		if (e.getMenuOption().equalsIgnoreCase(MARK))
		{
			final Tile tile = client.getScene().getTiles()[client.getPlane()][e.getActionParam()][e.getWidgetId()];
			final TileObject object = findTileObject(tile, e.getId());

			if (object == null)
			{
				return;
			}

			compareObject(object, tile, true);
			e.consume();
		}
		if (e.getMenuOption().equalsIgnoreCase(UNMARK))
		{
			final Tile tile = client.getScene().getTiles()[client.getPlane()][e.getActionParam()][e.getWidgetId()];
			final TileObject object = findTileObject(tile, e.getId());

			if (object == null)
			{
				return;
			}

			objectToHighlight.removeIf(hObj -> hObj.getObject() == object);
			e.consume();
		}
	}

	private TileObject findTileObject(Tile tile, int id)
	{
		if (tile == null)
		{
			return null;
		}

		final GameObject[] tileGameObjects = tile.getGameObjects();
		final DecorativeObject tileDecorativeObject = tile.getDecorativeObject();
		final WallObject tileWallObject = tile.getWallObject();
		final GroundObject groundObject = tile.getGroundObject();

		if (objectIdEquals(tileWallObject, id))
		{
			return tileWallObject;
		}

		if (objectIdEquals(tileDecorativeObject, id))
		{
			return tileDecorativeObject;
		}

		if (objectIdEquals(groundObject, id))
		{
			return groundObject;
		}

		for (GameObject object : tileGameObjects)
		{
			if (objectIdEquals(object, id))
			{
				return object;
			}
		}
		return null;
	}

	private boolean objectIdEquals(TileObject tileObject, int id)
	{
		if (tileObject == null)
		{
			return false;
		}

		if (tileObject.getId() == id)
		{
			return true;
		}

		// Menu action EXAMINE_OBJECT sends the transformed object id, not the base id, unlike
		// all of the GAME_OBJECT_OPTION actions, so check the id against the impostor ids
		final ObjectComposition comp = client.getObjectDefinition(tileObject.getId());

		if (comp.getImpostorIds() != null)
		{
			for (int impostorId : comp.getImpostorIds())
			{
				if (impostorId == id)
				{
					return true;
				}
			}
		}

		return false;
	}

	@Subscribe
	private void on(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOADING)
		{
			objectToHighlight.clear();
			getConfigTextField();
		}
	}

	@Subscribe
	private void on(GameObjectSpawned event)
	{
		TileObject object = event.getGameObject();
		Tile tile = event.getTile();
		if (object == null || tile == null)
		{
			return;
		}
		compareObject(object, tile, false);
	}

	@Subscribe
	private void on(GameObjectChanged event)
	{
		removePrevious(event.getGameObject());
		TileObject newObject = event.getGameObject();
		Tile tile = event.getTile();
		if (newObject == null || tile == null)
		{
			return;
		}
		compareObject(newObject, tile, false);
	}

	@Subscribe
	private void on(GameObjectDespawned event)
	{
		TileObject object = event.getGameObject();
		Tile tile = event.getTile();
		if (object == null || tile == null)
		{
			return;
		}
		objectToHighlight.removeIf(HighlightingObject -> HighlightingObject.getObject() == createHighlightingObject(object, tile).getObject());
	}

	@Subscribe
	private void on(GroundObjectSpawned event)
	{
		TileObject object = event.getGroundObject();
		Tile tile = event.getTile();
		if (object == null || tile == null)
		{
			return;
		}
		compareObject(object, tile, false);
	}

	@Subscribe
	private void on(GroundObjectChanged event)
	{
		removePrevious(event.getPrevious());
		TileObject newObject = event.getGroundObject();
		Tile tile = event.getTile();
		if (newObject == null || tile == null)
		{
			return;
		}
		compareObject(newObject, tile, false);
	}

	@Subscribe
	private void on(GroundObjectDespawned event)
	{
		TileObject object = event.getGroundObject();
		Tile tile = event.getTile();
		if (object == null || tile == null)
		{
			return;
		}
		objectToHighlight.removeIf(HighlightingObject -> HighlightingObject.getObject() == createHighlightingObject(object, tile).getObject());
	}

	@Subscribe
	private void on(DecorativeObjectSpawned event)
	{
		TileObject object = event.getDecorativeObject();
		Tile tile = event.getTile();
		if (object == null || tile == null)
		{
			return;
		}
		compareObject(object, tile, false);
	}

	@Subscribe
	private void on(DecorativeObjectChanged event)
	{
		removePrevious(event.getPrevious());
		TileObject newObject = event.getDecorativeObject();
		Tile tile = event.getTile();
		if (newObject == null || tile == null)
		{
			return;
		}
		compareObject(newObject, tile, false);
	}

	@Subscribe
	private void on(DecorativeObjectDespawned event)
	{
		TileObject object = event.getDecorativeObject();
		Tile tile = event.getTile();
		if (object == null || tile == null)
		{
			return;
		}
		objectToHighlight.removeIf(HighlightingObject -> HighlightingObject.getObject() == createHighlightingObject(object, tile).getObject());
	}

	@Subscribe
	private void on(WallObjectSpawned event)
	{
		TileObject object = event.getWallObject();
		Tile tile = event.getTile();
		if (object == null || tile == null)
		{
			return;
		}
		compareObject(object, tile, false);
	}

	@Subscribe
	private void on(WallObjectChanged event)
	{
		removePrevious(event.getPrevious());
		TileObject newObject = event.getWallObject();
		Tile tile = event.getTile();
		if (newObject == null || tile == null)
		{
			return;
		}
		compareObject(newObject, tile, false);
	}

	@Subscribe
	private void on(WallObjectDespawned event)
	{
		TileObject object = event.getWallObject();
		Tile tile = event.getTile();
		if (object == null || tile == null)
		{
			return;
		}
		objectToHighlight.removeIf(HighlightingObject -> HighlightingObject.getObject() == createHighlightingObject(object, tile).getObject());
	}

	private void removePrevious(TileObject previousObject)
	{
		if (previousObject == null)
		{
			return;
		}
		HighlightingObject previousHighlightingObject = createHighlightingObject(previousObject, null);
		if (previousHighlightingObject != null)
		{
			objectToHighlight.removeIf(HighlightingObject
					-> HighlightingObject.getObject() == previousHighlightingObject.getObject()
					&& HighlightingObject.getTile() == previousHighlightingObject.getTile());
		}
	}

	public void compareObject(TileObject object, Tile tile, boolean forceTag)
	{
		HighlightingObject highlightingObject = createHighlightingObject(object, tile);
		if (highlightingObject == null)
		{
			return;
		}
		if (forceTag)
		{
			objectToHighlight.add(highlightingObject);
		}
		String objectName = highlightingObject.getName();
		int objectId = highlightingObject.getId();
		for (ConfigObject configObject : configObjects)
		{
			// configObject.getName() may literally equal "null" rather than being null.
			if (!configObject.getName().equalsIgnoreCase("null"))
			{
				if (configObject.getName()
						.equalsIgnoreCase(stringFormat.rws(objectName)))
				{
					highlightingObject.setColor(configObject.getColor());
					objectToHighlight.add(highlightingObject);
				}
			}
			if (configObject.getId() == objectId)
			{
				highlightingObject.setColor(configObject.getColor());
				objectToHighlight.add(highlightingObject);
			}
		}
	}

	public void getAllObjects()
	{
		if (client.getLocalPlayer() == null)
		{
			return;
		}
		assert client.isClientThread();

		for (GameObject object : new GameObjectQuery().result(client).list)
		{
			if (object == null)
			{
				continue;
			}
			compareObject(object, null, false);
		}
		for (DecorativeObject object : new DecorativeObjectQuery().result(client).list)
		{
			if (object == null)
			{
				continue;
			}
			compareObject(object, null, false);
		}
		for (WallObject object : new WallObjectQuery().result(client).list)
		{
			if (object == null)
			{
				continue;
			}
			compareObject(object, null, false);
		}
		for (GroundObject object : new GroundObjectQuery().result(client).list)
		{
			if (object == null)
			{
				continue;
			}
			compareObject(object, null, false);
		}
	}

	private HighlightingObject createHighlightingObject(TileObject object, Tile tile)
	{
		ObjectComposition cDef = client.getObjectDefinition(object.getId());
		if (cDef == null)
		{
			return null;
		}
		return HighlightingObject.builder()
				.name(cDef.getName())
				.id(object.getId())
				.object(object)
				.tile(tile)
				.color(config.objectDefaultHighlightColor() != null ? config.objectDefaultHighlightColor() : Color.GREEN)
				.plane(object.getPlane())
				.build();
	}

	// ██████╗ ██████╗ ███╗   ██╗███████╗██╗ ██████╗
	//██╔════╝██╔═══██╗████╗  ██║██╔════╝██║██╔════╝
	//██║     ██║   ██║██╔██╗ ██║█████╗  ██║██║  ███╗
	//██║     ██║   ██║██║╚██╗██║██╔══╝  ██║██║   ██║
	//╚██████╗╚██████╔╝██║ ╚████║██║     ██║╚██████╔╝
	// ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝     ╚═╝ ╚═════╝

	//██████╗ ███████╗██╗      █████╗ ████████╗███████╗██████╗
	//██╔══██╗██╔════╝██║     ██╔══██╗╚══██╔══╝██╔════╝██╔══██╗
	//██████╔╝█████╗  ██║     ███████║   ██║   █████╗  ██║  ██║
	//██╔══██╗██╔══╝  ██║     ██╔══██║   ██║   ██╔══╝  ██║  ██║
	//██║  ██║███████╗███████╗██║  ██║   ██║   ███████╗██████╔╝
	//╚═╝  ╚═╝╚══════╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═════╝

	private void getConfigTextField()
	{
		String configTextString = stringFormat.rws(config.configObjectTextField());
		if (Strings.isNullOrEmpty(configTextString))
		{
			return;
		}
		String[] stringsSplitByComma = configTextString.split(",");
		for (String commaSplit : stringsSplitByComma)
		{
			if (commaSplit.contains(":"))
			{
				String[] colonSplit;
				String[] colonToAdd = commaSplit.split(":");
				if (colonToAdd.length == 0)
				{
					return;
				}
				if (colonToAdd.length == 1)
				{
					colonSplit = new String[]{colonToAdd[0], ""};
				}
				else
				{
					colonSplit = new String[]{colonToAdd[0], colonToAdd[1]};
				}
				if (stringFormat.containsNumbers(colonSplit[0]))
				{
					createConfigObject(null, checkInt(colonSplit[0]), colonSplit[1]);
				}
				else
				{
					createConfigObject(colonSplit[0], -1, colonSplit[1]);
				}
			}
			else
			{
				String[] fakeSplit = {commaSplit, null, null};
				if (stringFormat.containsNumbers(fakeSplit[0]))
				{
					createConfigObject(null, checkInt(fakeSplit[0]), null);
				}
				else
				{
					createConfigObject(fakeSplit[0], -1, null);
				}
			}
		}
	}

	private int checkInt(String stringNum)
	{
		String charRemoved = stringFormat.removeCharactersFromString(stringNum);
		if (Strings.isNullOrEmpty(charRemoved))
		{
			return -1;
		}
		int number;
		if (charRemoved.length() > 8)
		{
			number = Integer.parseInt(charRemoved.substring(0, 8));
		}
		else
		{
			number = Integer.parseInt(charRemoved);
		}
		return number;
	}

	private void createConfigObject(String configObjectName, int configObjectId, String configObjectColor)
	{
		if (Strings.isNullOrEmpty(configObjectName))
		{
			configObjectName = "null";
		}
		Color actualConfigColor;
		try
		{
			actualConfigColor = Color.decode("#" + configObjectColor);
		}
		catch (NumberFormatException nfe)
		{
			actualConfigColor = config.objectDefaultHighlightColor();
		}
		ConfigObject configObject = ConfigObject.builder()
				.name(configObjectName)
				.id(configObjectId)
				.color(actualConfigColor)
				.build();
		configObjects.add(configObject);
	}

	@Subscribe
	public void on(ConfigChanged event)
	{
		if (!event.getGroup().equals("objectmarkersextended"))
		{
			return;
		}
		if (event.getKey().equals("configObjectTextField"))
		{
			objectToHighlight.clear();
			configObjects.clear();
			getConfigTextField();
			clientThread.invoke(this::getAllObjects);
		}
	}

	// ██████╗ ███████╗████████╗████████╗███████╗██████╗ ███████╗
	//██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
	//██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝███████╗
	//██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗╚════██║
	//╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║███████║
	//╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

	public List<HighlightingObject> getObjectToHighlight()
	{
		return objectToHighlight;
	}

	//███╗   ███╗██╗███████╗ ██████╗
	//████╗ ████║██║██╔════╝██╔════╝
	//██╔████╔██║██║███████╗██║
	//██║╚██╔╝██║██║╚════██║██║
	//██║ ╚═╝ ██║██║███████║╚██████╗
	//╚═╝     ╚═╝╚═╝╚══════╝ ╚═════╝

	public boolean doesPlayerHaveALineOfSightToObject(Player player, HighlightingObject object)
	{
		if (player == null)
		{
			return false;
		}
		if (object == null)
		{
			return false;
		}
		return player.getWorldArea().hasLineOfSightTo(client, object.getObject().getWorldLocation());
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 16)
		{
			isShiftKeyPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 16)
		{
			isShiftKeyPressed = false;
		}
	}
}