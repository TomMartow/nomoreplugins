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
package plugin.nomore.grounditemsextended;

import com.google.common.base.Strings;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;
import plugin.nomore.grounditemsextended.builder.ConfigObject;
import plugin.nomore.grounditemsextended.builder.HighlightingObject;
import plugin.nomore.grounditemsextended.utils.StringFormat;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Extension
@PluginDescriptor(
		name = "Ex: Ground Items",
		description = "Ground Items, but with more options.",
		tags = {"ground", "items", "loot", "nomore"}
)
@Slf4j
public class GroundMarkersExtendedPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private GroundMarkersExtendedConfig config;

	@Inject
	private GroundMarkersExtendedOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private StringFormat stringFormat;

	@Inject
	private ItemManager itemManager;

	@Provides
	GroundMarkersExtendedConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(GroundMarkersExtendedConfig.class);
	}

	private static final List<HighlightingObject> groundItemsToHighlight = new ArrayList<>();
	private final List<ConfigObject> configObjects = new ArrayList<>();
	
	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
		getConfigTextField();
	}
	
	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		groundItemsToHighlight.clear();
	}

	@Subscribe
	private void on(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOADING) {
			groundItemsToHighlight.clear();
			getConfigTextField();
		}
	}

	@Subscribe
	private void on(ItemSpawned event)
	{
		TileItem item = event.getItem();
		Tile tile = event.getTile();
		if (item == null || tile == null)
		{
			return;
		}
		compareGroundItem(item, tile);
	}

	@Subscribe
	private void on(ItemDespawned event)
	{
		TileItem item = event.getItem();
		Tile tile = event.getTile();
		if (item == null || tile == null)
		{
			return;
		}
		groundItemsToHighlight.removeIf(highlightingObject
				-> highlightingObject.getTileItem() == createHighlightingObject(item, null).getTileItem());
	}

	private void compareGroundItem(TileItem item, Tile tile)
	{
		HighlightingObject highlightingObject = createHighlightingObject(item, tile);
		if (highlightingObject == null)
		{
			return;
		}
		String itemName = highlightingObject.getName();
		int itemId = item.getId();
		for (ConfigObject configObject : configObjects)
		{
			if (!Strings.isNullOrEmpty(configObject.getName()))
			{
				if (configObject.getName()
						.equalsIgnoreCase(stringFormat.rws(itemName))
						|| itemId == configObject.getId())
				{
					highlightingObject.setColor(configObject.getColor());
					groundItemsToHighlight.add(highlightingObject);
				}
			}
			if (itemId == configObject.getId())
			{
				highlightingObject.setColor(configObject.getColor());
				groundItemsToHighlight.add(highlightingObject);
			}
		}
	}

	private HighlightingObject createHighlightingObject(TileItem item, Tile tile)
	{
		ItemComposition def = itemManager.getItemComposition(item.getId());
		return HighlightingObject.builder()
				.name(def.getName())
				.id(item.getId())
				.tileItem(item)
				.tile(tile)
				.quantity(item.getQuantity())
				.gePrice(def.getPrice())
				.haPrice(def.getHaPrice())
				.plane(item.getTile().getWorldLocation().getPlane())
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
		String configTextString = stringFormat.rws(config.groundItemConfigTextString());
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

	private void createConfigObject(String configItemName, int configItemId, String configItemColor)
	{
		if (Strings.isNullOrEmpty(configItemName))
		{
			configItemName = "null";
		}
		if (Strings.isNullOrEmpty(configItemColor))
		{
			configItemColor = "null";
		}
		try
		{
			if (configItemColor.length() != 6)
			{
				configItemColor = "00FF00";
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			if (Strings.isNullOrEmpty(configItemColor))
			{
				configItemColor = "00FF00";
			}
		}
		Color actualConfigColor = config.groundItemDefaultHighlightColor();
		try
		{
			actualConfigColor = Color.decode("#" + configItemColor);
		}
		catch (NumberFormatException nfe)
		{
			System.out.println("Error decoding color for " + configItemColor);
		}
		ConfigObject configObject = ConfigObject.builder()
				.name(configItemName)
				.id(configItemId)
				.color(actualConfigColor)
				.build();
		configObjects.add(configObject);
	}

	@Subscribe
	private void on(ConfigChanged event)
	{
		if (!event.getGroup().equals("grounditemsextended"))
		{
			return;
		}
		if (event.getKey().equals("configGroundItemTextField"))
		{
			groundItemsToHighlight.clear();
			configObjects.clear();
			getConfigTextField();
		}
	}

	// ██████╗ ███████╗████████╗████████╗███████╗██████╗ ███████╗
	//██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
	//██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝███████╗
	//██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗╚════██║
	//╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║███████║
	//╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

	public List<HighlightingObject> getGroundItemsToHighlight()
	{
		return groundItemsToHighlight;
	}

	//███╗   ███╗██╗███████╗ ██████╗
	//████╗ ████║██║██╔════╝██╔════╝
	//██╔████╔██║██║███████╗██║
	//██║╚██╔╝██║██║╚════██║██║
	//██║ ╚═╝ ██║██║███████║╚██████╗
	//╚═╝     ╚═╝╚═╝╚══════╝ ╚═════╝

	public boolean doesPlayerHaveALineOfSightToItem(Player player, TileItem item)
	{
		if (player == null)
		{
			return false;
		}
		if (item == null)
		{
			return false;
		}
		return player.getWorldArea().hasLineOfSightTo(client, item.getTile().getWorldLocation().toWorldArea());
	}
}