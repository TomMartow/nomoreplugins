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
package plugin.nomore.inventorystateindicators;

import com.google.common.base.Strings;
import com.google.inject.Provides;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;
import plugin.nomore.inventorystateindicators.utils.Utils;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;

@Extension
@PluginDescriptor(
		name = "Inventory State Indicators",
		description = "Displays indicators based on the inventory state.",
		tags = {"tag1", "tag2", "tag3"}
)
@Slf4j
public class InventoryStateIndicatorsPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private InventoryStateIndicatorsOverlay overlay;

	@Inject
	private InventoryStateIndicatorsConfig config;

	@Inject
	private Utils utils;

	@Inject
	private OverlayManager overlayManager;

	@Getter @Setter
	boolean inventoryFull = false;
	@Getter @Setter
	boolean inventoryContains = false;
	@Getter @Setter
	boolean inventoryDoesNotContain = false;

	@Provides
	InventoryStateIndicatorsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(InventoryStateIndicatorsConfig.class);
	}

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	private void on(ItemContainerChanged e)
	{
		if (e.getItemContainer() != client.getItemContainer(InventoryID.INVENTORY))
		{
			return;
		}

		checkInventory();
	}

	@Subscribe
	private void on(ConfigChanged e)
	{
		if (!e.getGroup().equalsIgnoreCase("InventoryStateIndicators"))
		{
			return;
		}

		checkInventory();
	}

	private void checkInventory()
	{
		Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
		if (inventory == null)
		{
			return;
		}

		Collection<WidgetItem> items = inventory.getWidgetItems();

		if (config.enableInventoryFullSlot28Override())
		{
			setInventoryFull(items
					.stream()
					.filter(i -> i != null
							&& i.getIndex() == 27)
					.findFirst()
					.orElse(null) != null || items.size() == 28);
		}
		else
		{
			setInventoryFull(items.size() == 28);
		}

		if (config.enableInventoryContainsIndicator()
				&& !Strings.isNullOrEmpty(config.inventoryContainsItemIDs()))
		{
			String[] configContainItemIDs = utils.rws(config.inventoryContainsItemIDs()).split(",");
			for (String cItemID : configContainItemIDs)
			{
				if (Strings.isNullOrEmpty(cItemID))
				{
					continue;
				}

				for (WidgetItem item : inventory.getWidgetItems())
				{
					if (item == null)
					{
						continue;
					}

					if (item.getId() == Integer.parseInt(cItemID))
					{
						setInventoryContains(true);
						break;
					}
				}

				if (inventoryContains)
				{
					break;
				}
			}
		}

		if (config.enableInventoryDoesNotContainsIndicator()
				&& !Strings.isNullOrEmpty(config.inventoryDoesNotContainsItemIDs()))
		{
			String[] configDoesNotContainItemIDs = utils.rws(config.inventoryDoesNotContainsItemIDs()).split(",");
			for (String cItemID : configDoesNotContainItemIDs)
			{
				if (Strings.isNullOrEmpty(cItemID))
				{
					continue;
				}

				for (WidgetItem item : inventory.getWidgetItems())
				{
					if (item == null)
					{
						continue;
					}

					if (item.getId() == Integer.parseInt(cItemID))
					{
						setInventoryDoesNotContain(false);
						return;
					}
				}
			}
			setInventoryDoesNotContain(true);
		}
	}
}