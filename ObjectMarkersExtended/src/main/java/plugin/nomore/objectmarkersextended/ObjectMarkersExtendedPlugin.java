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
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.queries.DecorativeObjectQuery;
import net.runelite.api.queries.GameObjectQuery;
import net.runelite.api.queries.GroundObjectQuery;
import net.runelite.api.queries.WallObjectQuery;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;
import plugin.nomore.objectmarkersextended.utils.StringFormat;
import plugin.nomore.objectmarkersextended.builder.ConfigObject;
import plugin.nomore.objectmarkersextended.builder.HighlightingObject;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

@Extension
@PluginDescriptor(
		name = "Ex: Object Markers",
		description = "Object Markers, but with more options.",
		tags = {"object", "marker", "nomore"}
)
@Slf4j
public class ObjectMarkersExtendedPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

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

	@Override
	protected void startUp()
	{
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
		overlayManager.remove(overlay);
		objectToHighlight.clear();
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
		compareObject(object, tile);
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
		compareObject(newObject, tile);
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
		objectToHighlight.removeIf(HighlightingObject -> HighlightingObject.equals(createHighlightingObject(object, tile)));
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
		compareObject(object, tile);
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
		compareObject(newObject, tile);
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
		objectToHighlight.removeIf(HighlightingObject -> HighlightingObject.equals(createHighlightingObject(object, tile)));
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
		compareObject(object, tile);
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
		compareObject(newObject, tile);
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
		objectToHighlight.removeIf(HighlightingObject -> HighlightingObject.equals(createHighlightingObject(object, tile)));
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
		compareObject(object, tile);
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
		compareObject(newObject, tile);
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
		objectToHighlight.removeIf(HighlightingObject -> HighlightingObject.equals(createHighlightingObject(object, tile)));
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
					-> HighlightingObject.getObject() == previousHighlightingObject.getObject());
		}
	}

	public void compareObject(TileObject object, Tile tile)
	{
		HighlightingObject highlightingObject = createHighlightingObject(object, tile);
		if (highlightingObject == null)
		{
			return;
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
			compareObject(object, null);
		}
		for (DecorativeObject object : new DecorativeObjectQuery().result(client).list)
		{
			if (object == null)
			{
				continue;
			}
			compareObject(object, null);
		}
		for (WallObject object : new WallObjectQuery().result(client).list)
		{
			if (object == null)
			{
				continue;
			}
			compareObject(object, null);
		}
		for (GroundObject object : new GroundObjectQuery().result(client).list)
		{
			if (object == null)
			{
				continue;
			}
			compareObject(object, null);
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
		if (Strings.isNullOrEmpty(configObjectColor))
		{
			configObjectColor = "null";
		}
		try
		{
			if (configObjectColor.length() != 6)
			{
				configObjectColor = "00FF00";
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			if (Strings.isNullOrEmpty(configObjectColor))
			{
				configObjectColor = "00FF00";
			}
		}
		Color actualConfigColor = config.objectDefaultHighlightColor();
		try
		{
			actualConfigColor = Color.decode("#" + configObjectColor);
		}
		catch (NumberFormatException nfe)
		{
			System.out.println("Error decoding color for " + configObjectColor);
		}
		ConfigObject configObject = ConfigObject.builder()
				.name(configObjectName)
				.id(configObjectId)
				.color(actualConfigColor)
				.build();
		configObjects.add(configObject);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
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

}