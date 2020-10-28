/*
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
package plugin.nomore.inventoryitemindicators;

import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.WildcardMatcher;
import org.pf4j.Extension;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

@Extension
@PluginDescriptor(
	name = "Inventory Item Indicators",
	description = "Display an indicator over inventory items.",
	tags = {"ahk", "inventory", "nomore"},
	type = PluginType.UTILITY
)
@Slf4j
public class IIPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private IIConfig config;

	@Inject
	private MouseManager mouseManager;

	@Inject
	private InventoryOverlay inventoryOverlay;

	@Inject
	private SceneOverlay sceneOverlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ItemManager itemManager;

	@Provides
	IIConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(IIConfig.class);
	}

	@Override
	protected void startUp() {
		overlayManager.add(sceneOverlay);
		overlayManager.add(inventoryOverlay);
		checkConfigTextField();
	}

	@Override
	protected void shutDown() {
		overlayManager.remove(sceneOverlay);
		overlayManager.remove(inventoryOverlay);
		configItems.clear();
		inventoryItems.clear();
	}

	@Getter(AccessLevel.PACKAGE)
	boolean inventoryFull = false;
	boolean inventoryContains = false;
	@Getter(AccessLevel.PACKAGE)
	HashSet<ConfigItems> configItems = new HashSet<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Item, Color> inventoryItems = new HashMap<>();

	@Subscribe
	private void on(ConfigChanged event)
	{
		if (!event.getGroup().equals("inventoryitemindicators"))
		{
			return;
		}
		checkConfigTextField();
	}

	@Subscribe
	private void on(GameTick gameTick)
	{
		ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
		if (inventory == null)
		{
			return;
		}
		Item[] items = inventory.getItems();
		if (config.displayFull())
		{
			inventoryFullCheck(items);
		}
		if (config.displayContain())
		{
			inventoryContainsCheck(items);
		}
	}

	private void inventoryFullCheck(Item[] items)
	{
		int i = 0;
		for (Item item : items) {
			if (item == null || item.getId() == -1)
			{
				break;
			}
			i++;
		}
		inventoryFull = i == 28;
	}

	private void inventoryContainsCheck(Item[] items)
	{
		inventoryContains = false;
		for (Item item : items)
		{
			if (item == null || item.getId() == -1)
			{
				continue;
			}
			String itemName = removeCharacters(itemManager.getItemDefinition(item.getId()).getName());
			int itemAmount = item.getQuantity();
			configItems.forEach((configItem) -> {
				String configName = configItem.getName();
				if (itemAmount >= configItem.getAmount()
						&& (itemName.equalsIgnoreCase(configName) || (configName.contains("*") && WildcardMatcher.matches(configName, itemName))))
				{
					inventoryContains = true;
					if (config.highlightItems())
					{
						//System.out.println(itemName + ", " + itemAmount + " matches " + configItem.getName() + ", " + configItem.getAmount());
						inventoryItems.putIfAbsent(item, configItem.getColor());
					}
				}
			});
		}
	}

	private void checkConfigTextField()
	{
		configItems.clear();
		inventoryItems.clear();
		if (config.containName().isEmpty())
		{
			inventoryContains = false;
			System.out.println("The config text field is empty.");
			return;
		}
		String[] splitString = splitString(removeCharacters(config.containName()), ",");
		for (String stringPart : splitString)
		{
			if (stringPart.isEmpty())
			{
				return;
			}
			String[] colonSplit = splitString(stringPart, ":"); // Lol at the name.
			String configName = null;
			int configAmount = 1;
			Color configColor = config.defaultHighlightColor();
			if (colonSplit[0].isEmpty())
			{
				continue;
			}
			else
			{
				configName = colonSplit[0];
			}
			if (colonSplit.length >= 2 && !colonSplit[1].isEmpty())
			{
				String s1 = colonSplit[1];
				String s2 = s1.replaceAll("[^\\d.]", "");
				configAmount = Integer.parseInt(s2);
				if (configAmount < 1)
				{
					configAmount = 1;
				}
			}
			if (colonSplit.length == 3)
			{
				try {
					configColor = Color.decode("#" + colonSplit[2]);
				} catch (NumberFormatException nfe) {
					configColor = config.defaultHighlightColor();
					System.out.println("Error decoding color.");
				}
			}
			configItems.add(new ConfigItems(configName, configAmount, configColor));
		}
	}

	private String removeCharacters(String string)
	{
		string = string.toLowerCase();
		string = string.replaceAll(" ", "");
		string = string.replaceAll("\n", "");
		return string;
	}

	private String[] splitString(String string, String splitChar)
	{
		return string.split(Pattern.quote(splitChar));
	}
}