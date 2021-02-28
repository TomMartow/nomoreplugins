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
package plugin.nomore.inventorytagsextended;

import com.google.common.base.Strings;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;
import plugin.nomore.inventorytagsextended.builder.ConfigObject;
import plugin.nomore.inventorytagsextended.builder.HighlightingObject;
import plugin.nomore.inventorytagsextended.utils.StringFormat;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Extension
@PluginDescriptor(
		name = "Ex: Inventory Tags",
		description = "Inventory Tags, but with more options.",
		tags = {"inventory", "tags", "nomore"}
)
@Slf4j
public class InventoryTagsExtendedPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private InventoryTagsExtendedConfig config;

	@Inject
	private InventoryTagsExtendedOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private StringFormat stringFormat;

	@Inject
	private ItemManager itemManager;

	@Provides
	InventoryTagsExtendedConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(InventoryTagsExtendedConfig.class);
	}

	private static final List<HighlightingObject> inventoryItemsToHighlight = new ArrayList<>();
	private final List<ConfigObject> configObjects = new ArrayList<>();
	private int gameTick = 5;
	private boolean initialCheck = false;

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
		getConfigTextField();
		clientThread.invoke(this::getAllInventoryItems);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		inventoryItemsToHighlight.clear();
	}

	@Subscribe
	private void on(GameTick event)
	{
		if (initialCheck)
		{
			return;
		}
		Widget clickHereToPlay = client.getWidget(378,87);
		if (clickHereToPlay != null)
		{
			if (!clickHereToPlay.isHidden())
			{
				initialCheck = false;
				gameTick = 5;
				return;
			}
		}
		if (!initialCheck && clickHereToPlay == null)
		{
			Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
			if (inventory == null)
			{
				return;
			}
			if (inventory.isHidden())
			{
				return;
			}
			if (gameTick != 0)
			{
				gameTick -= 1;
				inventoryItemsToHighlight.clear();
				getConfigTextField();
				getAllInventoryItems();
			}
			else
			{
				initialCheck = true;
			}
		}
	}

	@Subscribe
	private void on(GameStateChanged event)
	{
		switch (event.getGameState())
		{
			case LOGGED_IN:
			case LOADING:
			case LOGIN_SCREEN_AUTHENTICATOR:
			case UNKNOWN:
			case STARTING:
			case CONNECTION_LOST:
			case LOGIN_SCREEN:
			case LOGGING_IN:
			case HOPPING:
				gameTick = 5;
				initialCheck = false;
		}
	}

	@Subscribe
	private void on(ItemContainerChanged event)
	{
		inventoryItemsToHighlight.clear();
		Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
		if (inventory == null || inventory.isHidden())
		{
			return;
		}
		for (WidgetItem item : inventory.getWidgetItems())
		{
			if (item == null)
			{
				continue;
			}
			compareInventoryItem(item);
		}
	}

	private void getAllInventoryItems()
	{
		if (client.getLocalPlayer() == null)
		{
			return;
		}
		assert client.isClientThread();

		Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
		if (inventory == null || inventory.isHidden())
		{
			return;
		}
		for (WidgetItem item : inventory.getWidgetItems())
		{
			if (item == null)
			{
				continue;
			}
			compareInventoryItem(item);
		}
	}

	private void compareInventoryItem(WidgetItem item)
	{
		HighlightingObject highlightingObject = createHighlightingObject(item);
		if (highlightingObject == null)
		{
			return;
		}
		String itemName = highlightingObject.getName();
		int itemId = item.getId();
		int itemQuantity = item.getQuantity();
		for (ConfigObject configObject : configObjects)
		{
			if (!Strings.isNullOrEmpty(configObject.getName()))
			{
				if (configObject.getName()
						.equalsIgnoreCase(stringFormat.rws(itemName))
						&& itemQuantity >= configObject.getQuantity())
				{
					highlightingObject.setColor(configObject.getColor());
					inventoryItemsToHighlight.add(highlightingObject);
				}
			}
			if (itemId == configObject.getId()
					&& itemQuantity >= configObject.getQuantity())
			{
				highlightingObject.setColor(configObject.getColor());
				inventoryItemsToHighlight.add(highlightingObject);
			}
		}
	}

	private HighlightingObject createHighlightingObject(WidgetItem item)
	{
		ItemComposition def = itemManager.getItemComposition(item.getId());
		return HighlightingObject.builder()
				.name(def.getName())
				.id(item.getId())
				.widgetItem(item)
				.index(item.getIndex())
				.quantity(item.getQuantity())
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
		String configTextString = stringFormat.rws(config.inventoryItemConfigTextString());
		if (Strings.isNullOrEmpty(configTextString))
		{
			return;
		}
		String[] stringsSplitByComma = configTextString.split(",");
		for (String commaSplit : stringsSplitByComma)
		{
			if (commaSplit.contains(":"))
			{
				String[] colonSplit = new String[0];
				String[] colonToAdd = commaSplit.split(":");
				if (colonToAdd.length == 0)
				{
					return;
				}
				if (colonToAdd.length == 1)
				{
					colonSplit = new String[]{colonToAdd[0], "", "1"};
				}
				if (colonToAdd.length == 2)
				{
					colonSplit = new String[]{colonToAdd[0], colonToAdd[1], "1"};
				}
				if (colonToAdd.length == 3)
				{
					colonSplit = new String[]{colonToAdd[0], colonToAdd[1], colonToAdd[2]};
				}
				log.info(colonSplit[0] + ", " + colonSplit[1] + ", " + colonSplit[2]);
				if (stringFormat.containsNumbers(colonSplit[0]))
				{
					createConfigObject(null, checkInt(colonSplit[0]), colonSplit[1], checkInt(colonSplit[2]));
				}
				else
				{
					createConfigObject(colonSplit[0], -1, colonSplit[1], checkInt(colonSplit[2]));
				}
			}
			else
			{
				if (stringFormat.containsNumbers(commaSplit))
				{
					createConfigObject(null, -1, null,1);
				}
				else
				{
					createConfigObject(commaSplit, -1, null,1);
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

	private void createConfigObject(String configName, int configId, String configColor, int configQuantity)
	{
		if (Strings.isNullOrEmpty(configName))
		{
			configName = "null";
		}
		if (Strings.isNullOrEmpty(configColor))
		{
			configColor = "null";
		}
		try
		{
			if (configColor.length() != 6)
			{
				configColor = "00FF00";
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			if (Strings.isNullOrEmpty(configColor))
			{
				configColor = "00FF00";
			}
		}
		Color actualConfigColor = config.inventoryItemDefaultHighlightColor();
		try
		{
			actualConfigColor = Color.decode("#" + configColor);
		}
		catch (NumberFormatException nfe)
		{
			System.out.println("Error decoding color for " + configColor);
		}
		ConfigObject configObject = ConfigObject.builder()
				.name(configName)
				.id(configId)
				.quantity(configQuantity)
				.color(actualConfigColor)
				.build();
		configObjects.add(configObject);
	}

	@Subscribe
	private void onConfigChanged(ConfigChanged event)
	{
		if (!event.getGroup().equals("inventorytagsextended"))
		{
			return;
		}
		if (event.getKey().equals("configInventoryItemTextField"))
		{
			inventoryItemsToHighlight.clear();
			configObjects.clear();
			getConfigTextField();
			clientThread.invoke(this::getAllInventoryItems);
		}
	}

	// ██████╗ ███████╗████████╗████████╗███████╗██████╗ ███████╗
	//██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
	//██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝███████╗
	//██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗╚════██║
	//╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║███████║
	//╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

	public List<HighlightingObject> getInventoryItemsToHighlight()
	{
		return inventoryItemsToHighlight;
	}

}