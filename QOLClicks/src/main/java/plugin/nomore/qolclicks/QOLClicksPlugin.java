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
package plugin.nomore.qolclicks;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.events.*;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import org.jetbrains.annotations.NotNull;
import org.pf4j.Extension;
import plugin.nomore.qolclicks.utils.*;
import plugin.nomore.qolclicks.utils.Cursor;
import plugin.nomore.qolclicks.utils.builds.GameObjectItem;
import plugin.nomore.qolclicks.utils.builds.InventoryItem;
import plugin.nomore.qolclicks.utils.scene.Inventory;
import plugin.nomore.qolclicks.utils.scene.Npcs;
import plugin.nomore.qolclicks.utils.scene.Objects;
import plugin.nomore.qolclicks.utils.TargetMenues;

import javax.inject.Inject;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Extension
@PluginDescriptor(
		name = "QOL Clicks",
		description = "QOL fixes that should be implemented.",
		tags = {"click", "nomore", "qol"},
		type = PluginType.UTILITY
)
@Slf4j
public class QOLClicksPlugin extends Plugin
{

	@Inject private Client client;
	@Inject private QOLClicksConfig config;
	@Inject private MenuClicked menuClicked;
	@Inject private MenuAdded menuAdded;
	@Inject private Cursor cursor;
	@Inject private Inventory inventory;
	@Inject private Objects objects;
	@Inject private Npcs npcs;
	@Inject private StringUtils string;
	@Inject private TargetMenues targetMenues;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	public Point pointClicked = new Point(0,0);
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	public boolean iterating = false;
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	public MenuEntry targetMenu = null;

	@Provides
	QOLClicksConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(QOLClicksConfig.class);
	}

	@Override
	protected void startUp()
	{
		log.info("QOL Clicks: started.");
		if (client.getLocalPlayer() == null)
		{
			return;
		}
		client.getNpcs().forEach(npc ->
		{
			if (npc != null)
			{
				npcList.add(npc);
			}
		});
	}

	@Override
	protected void shutDown()
	{
		log.info("QOL Clicks: finished.");
	}

	@Subscribe
	private void on(MenuOpened e)
	{
		if (e.getFirstEntry().getParam1() == WidgetInfo.INVENTORY.getId())
		{
			if (config.enableDropMatching()
					|| config.enableDropExcept())
			{
				addDropMenu(e);
			}
		}

		if (e.getFirstEntry().getMenuOpcode() == MenuOpcode.CC_OP
				&& e.getFirstEntry().getOption().equalsIgnoreCase("Inventory"))
		{
			addBankMenu(e);
		}
	}

	@Subscribe
	private void on(MenuOptionClicked e)
	{
		MenuEntry originalEntry = e.clone();

		//  ███████╗██╗██████╗ ███████╗███╗   ███╗ █████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗
		//  ██╔════╝██║██╔══██╗██╔════╝████╗ ████║██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝
		//  █████╗  ██║██████╔╝█████╗  ██╔████╔██║███████║█████╔╝ ██║██╔██╗ ██║██║  ███╗
		//  ██╔══╝  ██║██╔══██╗██╔══╝  ██║╚██╔╝██║██╔══██║██╔═██╗ ██║██║╚██╗██║██║   ██║
		//  ██║     ██║██║  ██║███████╗██║ ╚═╝ ██║██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
		//  ╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝
		//

		if (e.getMenuOpcode() == MenuOpcode.ITEM_USE
				&& e.getOption().equals("Burn"))
		{
			menuClicked.useItemOnItem(
					inventory.getItemInSlotIfMatches(e.getIdentifier(), e.getParam0()),
					inventory.getItem("Tinderbox"),
					e);
		}

		//   ██████╗ ██████╗  ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗
		//  ██╔════╝██╔═══██╗██╔═══██╗██║ ██╔╝██║████╗  ██║██╔════╝
		//  ██║     ██║   ██║██║   ██║█████╔╝ ██║██╔██╗ ██║██║  ███╗
		//  ██║     ██║   ██║██║   ██║██╔═██╗ ██║██║╚██╗██║██║   ██║
		//  ╚██████╗╚██████╔╝╚██████╔╝██║  ██╗██║██║ ╚████║╚██████╔╝
		//   ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝
		//

		if (e.getMenuOpcode() == MenuOpcode.ITEM_USE
				&& e.getOption().equals("Cook"))
		{
			if (!config.enableRange() && config.enableFire())
			{
				menuClicked.useItemOnGameObject(
						inventory.getItem(e.getIdentifier()),
						objects.getClosestGameObjectMatching("Fire"),
						e);
			}
			else if (config.enableRange() && !config.enableFire())
			{
				menuClicked.useItemOnGameObject(
						inventory.getItem(e.getIdentifier()),
						objects.getClosestGameObjectMatching("Range"),
						e);
			}
			else if (config.enableRange() && config.enableFire())
			{
				menuClicked.useItemOnGameObject(
						inventory.getItem(e.getIdentifier()),
						objects.getMatchingGameObjectsSortedByClosest("Fire", "Range").get(0),
						e);
			}
		}

		//  ███████╗██╗███████╗██╗  ██╗██╗███╗   ██╗ ██████╗
		//  ██╔════╝██║██╔════╝██║  ██║██║████╗  ██║██╔════╝
		//  █████╗  ██║███████╗███████║██║██╔██╗ ██║██║  ███╗
		//  ██╔══╝  ██║╚════██║██╔══██║██║██║╚██╗██║██║   ██║
		//  ██║     ██║███████║██║  ██║██║██║ ╚████║╚██████╔╝
		//  ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝
		//

		if (e.getMenuOpcode() == MenuOpcode.ITEM_USE
				&& e.getOption().equals("Use-rod"))
		{
			List<NPC> npcList = npcs.getNpcsMatchingId(1542);
			if (npcList.size() == 0)
			{
				return;
			}
			NPC npc = npcList.size() > 2
					? npcList.get(new Random().nextInt(2))
					: npcList.get(new Random().nextInt(npcList.size()));
			if (npc == null)
			{
				return;
			}
			String name = client.getNpcDefinition(npc.getId()).getName();
			new Thread(() ->
			{
				setTargetMenu(new MenuEntry(
						"Use-rod",
						"<col=ffff00>" + name,
						npc.getIndex(),
						MenuOpcode.NPC_FIRST_OPTION.getId(),
						0,
						0,
						false));
				click(client.getCanvas().getBounds());
			}).start();
			e.consume();
			return;
		}

		if (e.getMenuOpcode() == MenuOpcode.ITEM_USE
				&& e.getOption().equals("Cut"))
		{
			WidgetItem fish = inventory.getLastItem(11330, 11328);
			if (fish == null)
			{
				return;
			}
			WidgetItem knife = inventory.getItem("Knife");
			if (knife == null)
			{
				return;
			}
			if (config.enableCutOffcutsAutoCut() && !isIterating())
			{
				List<WidgetItem> items = inventory.getItems(11330, 11328);
				setIterating(true);
				new Thread(() ->
				{
					for (WidgetItem item : items)
					{
						if (item == null)
						{
							continue;
						}
						click(knife.getCanvasBounds());
						try
						{
							Thread.sleep(getRandomIntBetweenRange(config.dropMinTime(), config.dropMaxTime()));
						}
						catch (InterruptedException exc)
						{
							setIterating(false);
							exc.printStackTrace();
						}
					}
					if (config.enableCutOffcutsAutoDrop())
					{
						try
						{
							Thread.sleep(getRandomIntBetweenRange(600, 1200));
						}
						catch (InterruptedException exc)
						{
							setIterating(false);
							exc.printStackTrace();
						}
						List<InventoryItem> dropList = new ArrayList<>();
						for (WidgetItem item : inventory.getItems())
						{
							if (item == null)
							{
								continue;
							}
							if (item.getId() == 11324)
							{
								dropList.add(InventoryItem.builder()
										.item(item)
										.name("Roe")
										.build());
							}
						}
						inventory.dropItems(dropList);
					}
					setIterating(false);
				}).start();
				e.consume();
				return;
			}
			if (inventory.getItems().size() == 28
					&& !inventory.contains("Fish offcuts"))
			{
				menuClicked.dropItem(
						fish,
						e);
			}
			else
			{
				menuClicked.useItemOnItem(
						knife,
						fish,
						e
				);
			}
		}

		if (e.getMenuOpcode() == MenuOpcode.ITEM_USE
				&& e.getOption().equals("Cage"))
		{
			List<NPC> npcList = npcs.getNpcsWithMenuAction("Cage");
			if (npcList.size() == 0)
			{
				return;
			}
			NPC npc = npcList.size() > 2
					? npcList.get(new Random().nextInt(2))
					: npcList.get(new Random().nextInt(npcList.size()));
			if (npc == null)
			{
				return;
			}
			String name = client.getNpcDefinition(npc.getId()).getName();
			new Thread(() ->
			{
				setTargetMenu(new MenuEntry(
						"Cage",
						"<col=ffff00>" + name,
						npc.getIndex(),
						MenuOpcode.NPC_FIRST_OPTION.getId(),
						0,
						0,
						false));
				click(client.getCanvas().getBounds());
			}).start();
			e.consume();
			return;
		}

		if (e.getMenuOpcode() == MenuOpcode.ITEM_USE
				&& e.getOption().equals("Lure"))
		{
			List<NPC> npcList = npcs.getNpcsWithMenuAction("Lure");
			if (npcList.size() == 0)
			{
				return;
			}
			NPC npc = npcList.size() > 2
					? npcList.get(new Random().nextInt(2))
					: npcList.get(new Random().nextInt(npcList.size()));
			if (npc == null)
			{
				return;
			}
			String name = client.getNpcDefinition(npc.getId()).getName();
			new Thread(() ->
			{
				setTargetMenu(new MenuEntry(
						"Lure",
						"<col=ffff00>" + name,
						npc.getIndex(),
						MenuOpcode.NPC_FIRST_OPTION.getId(),
						0,
						0,
						false));
				click(client.getCanvas().getBounds());
			}).start();
			e.consume();
			return;
		}

		//  ██████╗ ██████╗  █████╗ ██╗   ██╗███████╗██████╗
		//  ██╔══██╗██╔══██╗██╔══██╗╚██╗ ██╔╝██╔════╝██╔══██╗
		//  ██████╔╝██████╔╝███████║ ╚████╔╝ █████╗  ██████╔╝
		//  ██╔═══╝ ██╔══██╗██╔══██║  ╚██╔╝  ██╔══╝  ██╔══██╗
		//  ██║     ██║  ██║██║  ██║   ██║   ███████╗██║  ██║
		//  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
		//

		if (config.enableUnnoteBones())
		{
			ItemDefinition def = client.getItemDefinition(e.getIdentifier());
			if (def.isStackable()
					&& def.getName()
					.toLowerCase()
					.contains("bones"))
			{
				menuClicked.useItemOnNPC(
						"Unnote",
						inventory.getItem(e.getIdentifier()),
						npcs.getClosestMatchingName("Phials"),
						e);
			}
		}

		//  ██████╗ ██████╗  ██████╗ ██████╗ ██████╗ ██╗███╗   ██╗ ██████╗
		//  ██╔══██╗██╔══██╗██╔═══██╗██╔══██╗██╔══██╗██║████╗  ██║██╔════╝
		//  ██║  ██║██████╔╝██║   ██║██████╔╝██████╔╝██║██╔██╗ ██║██║  ███╗
		//  ██║  ██║██╔══██╗██║   ██║██╔═══╝ ██╔═══╝ ██║██║╚██╗██║██║   ██║
		//  ██████╔╝██║  ██║╚██████╔╝██║     ██║     ██║██║ ╚████║╚██████╔╝
		//  ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ ╚═╝     ╚═╝     ╚═╝╚═╝  ╚═══╝ ╚═════╝
		//

		if (e.getMenuOpcode() == MenuOpcode.ITEM_USE
				&& e.getOption().equals("Drop-Matching"))
		{
			List<InventoryItem> dropList = new ArrayList<>();
			String[] configItemNames = string.removeWhiteSpaces(config.dropMatchingTextBox()).split(",");
			for (WidgetItem item : inventory.getItems())
			{
				if (item == null)
				{
					continue;
				}
				InventoryItem inventoryItem = InventoryItem.builder()
						.item(item)
						.name(client.getItemDefinition(item.getId()).getName())
						.build();
				if (Arrays.stream(configItemNames)
						.anyMatch(cIN
								-> string.removeWhiteSpaces(inventoryItem.getName())
								.equalsIgnoreCase(cIN)))
				{
					dropList.add(inventoryItem);
				}
			}
			inventory.dropItems(dropList);
			e.consume();
			return;
		}

		if (e.getMenuOpcode() == MenuOpcode.ITEM_USE
				&& e.getOption().equals("Drop-Except"))
		{
			List<InventoryItem> dropList = new ArrayList<>();
			String[] configItemNames = string.removeWhiteSpaces(config.dropExceptTextBox()).split(",");
			for (WidgetItem item : inventory.getItems())
			{
				if (item == null)
				{
					continue;
				}
				InventoryItem inventoryItem = InventoryItem.builder()
						.item(item)
						.name(client.getItemDefinition(item.getId()).getName())
						.build();
				if (Arrays.stream(configItemNames)
						.noneMatch(cIN
								-> string.removeWhiteSpaces(inventoryItem.getName())
								.equalsIgnoreCase(cIN)))
				{
					dropList.add(inventoryItem);
				}
			}
			inventory.dropItems(dropList);
			e.consume();
			return;
		}

		//  ██████╗  █████╗ ███╗   ██╗██╗  ██╗██╗███╗   ██╗ ██████╗
		//  ██╔══██╗██╔══██╗████╗  ██║██║ ██╔╝██║████╗  ██║██╔════╝
		//  ██████╔╝███████║██╔██╗ ██║█████╔╝ ██║██╔██╗ ██║██║  ███╗
		//  ██╔══██╗██╔══██║██║╚██╗██║██╔═██╗ ██║██║╚██╗██║██║   ██║
		//  ██████╔╝██║  ██║██║ ╚████║██║  ██╗██║██║ ╚████║╚██████╔╝
		//  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝
		//

		if (e.getMenuOpcode() == MenuOpcode.GAME_OBJECT_FIRST_OPTION
				&& e.getTarget().toLowerCase().contains("bank"))
		{
			List<GameObject> gameObjectList = objects.getGameObjectsWithNameContaining("Bank chest", "Bank booth");
			if (gameObjectList.size() == 0)
			{
				return;
			}
			GameObject object = gameObjectList.size() > 2
					? gameObjectList.get(new Random().nextInt(2))
					: gameObjectList.get(new Random().nextInt(gameObjectList.size()));
			if (object == null)
			{
				return;
			}
			GameObjectItem gameObjectItem = GameObjectItem.builder()
					.object(object)
					.name(client.getObjectDefinition(object.getId()).getName())
					.actions(client.getObjectDefinition(object.getId()).getActions())
					.build();
			new Thread(() ->
			{
				setTargetMenu(new MenuEntry(
						Arrays.stream(gameObjectItem.getActions()).findFirst().orElse("Bank"),
						"<col=ffff>" + gameObjectItem.getName(),
						object.getId(),
						MenuOpcode.GAME_OBJECT_FIRST_OPTION.getId(),
						object.getSceneMinLocation().getX(),
						object.getSceneMinLocation().getY(),
						false));
				click(client.getCanvas().getBounds());
			}).start();
			e.consume();
			return;
		}



		if (getTargetMenu() != null)
		{
			e.setMenuEntry(getTargetMenu());
			setTargetMenu(null);
		}

		debugOption(originalEntry, e);
	}

	@Subscribe
	private void on(MenuEntryAdded event)
	{
		if (event.getOpcode() == MenuOpcode.ITEM_USE.getId())
		{

			//  ███████╗██╗██████╗ ███████╗███╗   ███╗ █████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗
			//  ██╔════╝██║██╔══██╗██╔════╝████╗ ████║██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝
			//  █████╗  ██║██████╔╝█████╗  ██╔████╔██║███████║█████╔╝ ██║██╔██╗ ██║██║  ███╗
			//  ██╔══╝  ██║██╔══██╗██╔══╝  ██║╚██╔╝██║██╔══██║██╔═██╗ ██║██║╚██╗██║██║   ██║
			//  ██║     ██║██║  ██║███████╗██║ ╚═╝ ██║██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝
			//  ╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝
			//

			if (config.enableFiremaking()
					&& client.getItemDefinition(event.getIdentifier())
					.getName()
					.toLowerCase()
					.contains("logs"))
			{
				menuAdded.useItemOnItem(
						"Burn",
						inventory.getItem(event.getIdentifier()),
						inventory.getItem("Tinderbox"),
						event);
			}

			//   ██████╗ ██████╗  ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗
			//  ██╔════╝██╔═══██╗██╔═══██╗██║ ██╔╝██║████╗  ██║██╔════╝
			//  ██║     ██║   ██║██║   ██║█████╔╝ ██║██╔██╗ ██║██║  ███╗
			//  ██║     ██║   ██║██║   ██║██╔═██╗ ██║██║╚██╗██║██║   ██║
			//  ╚██████╗╚██████╔╝╚██████╔╝██║  ██╗██║██║ ╚████║╚██████╔╝
			//   ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝
			//

			if (config.enableFire()
					|| config.enableRange()
					&& client.getItemDefinition(event.getIdentifier())
					.getName()
					.toLowerCase()
					.contains("raw"))
			{
				menuAdded.interactWithNPC(
						"Lure",
						npcs.getClosestNpcWithMenuAction("Lure"),
						event);
			}

			//  ███████╗██╗███████╗██╗  ██╗██╗███╗   ██╗ ██████╗
			//  ██╔════╝██║██╔════╝██║  ██║██║████╗  ██║██╔════╝
			//  █████╗  ██║███████╗███████║██║██╔██╗ ██║██║  ███╗
			//  ██╔══╝  ██║╚════██║██╔══██║██║██║╚██╗██║██║   ██║
			//  ██║     ██║███████║██║  ██║██║██║ ╚████║╚██████╔╝
			//  ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝
			//

			if (config.enableBarbarianRod()
					&& client.getItemDefinition(event.getIdentifier())
					.getName()
					.equalsIgnoreCase("Barbarian rod"))
			{
				menuAdded.interactWithNPC(
						"Use-rod",
						npcs.getClosestNpcWithMenuAction("Use-rod"),
						event);
			}

			if (config.enableCutOffcuts()
					&& client.getItemDefinition(event.getIdentifier())
					.getName()
					.equalsIgnoreCase("Knife"))
			{
				if (inventory.contains("Leaping trout")
						|| inventory.contains("Leaping salmon")
						|| inventory.contains("Leaping sturgeon"))
				{
					menuAdded.useItemOnItem(
							"Cut",
							inventory.getItem("Knife"),
							inventory.getItem("Knife"),
							event);
				}
			}

			if (config.enableLobsterPot()
					&& client.getItemDefinition(event.getIdentifier())
					.getName()
					.equalsIgnoreCase("Lobster pot"))
			{
				menuAdded.interactWithNPC(
						"Cage",
						npcs.getClosestNpcWithMenuAction("Cage"),
						event);
			}

			if (config.enableFishingRod()
					&& client.getItemDefinition(event.getIdentifier())
					.getName()
					.equalsIgnoreCase("Fly fishing rod"))
			{
				menuAdded.interactWithNPC(
						"Lure",
						npcs.getClosestNpcWithMenuAction("Lure"),
						event);
			}

			//  ██████╗ ██████╗  █████╗ ██╗   ██╗███████╗██████╗
			//  ██╔══██╗██╔══██╗██╔══██╗╚██╗ ██╔╝██╔════╝██╔══██╗
			//  ██████╔╝██████╔╝███████║ ╚████╔╝ █████╗  ██████╔╝
			//  ██╔═══╝ ██╔══██╗██╔══██║  ╚██╔╝  ██╔══╝  ██╔══██╗
			//  ██║     ██║  ██║██║  ██║   ██║   ███████╗██║  ██║
			//  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
			//

			if (config.enableUnnoteBones())
			{
				ItemDefinition def = client.getItemDefinition(event.getIdentifier());
				if (def.isStackable()
						&& def.getName()
						.toLowerCase()
						.contains("bones"))
				{
					menuAdded.useItemOnNPC(
							"Unnote",
							inventory.getItem(event.getIdentifier()),
							npcs.getClosestMatchingName("Phials"),
							event);
				}

			}

		}
	}

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	public static List<NPC> npcList = new ArrayList<>();

	@Subscribe
	private void on(NpcSpawned e)
	{
		NPC npc = e.getNpc();
		if (npc == null)
		{
			return;
		}
		npcList.add(npc);
	}

	@Subscribe
	private void on(NpcDespawned e)
	{
		npcList.remove(e.getNpc());
	}

	public void setSelected(WidgetInfo widgetInfo, int itemIndex, int itemId)
	{
		client.setSelectedItemWidget(widgetInfo.getId());
		client.setSelectedItemSlot(itemIndex);
		client.setSelectedItemID(itemId);
	}

	public void insertMenuEntry(MenuEntry e, boolean forceLeftClick)
	{
		client.insertMenuItem(
				e.getOption(),
				e.getTarget(),
				e.getOpcode(),
				e.getIdentifier(),
				e.getParam0(),
				e.getParam1(),
				forceLeftClick
		);
	}

	public static void writeTextToClipboard(String s)
	{
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable transferable = new StringSelection(s);
		clipboard.setContents(transferable, null);
	}

	private void debugOption(MenuEntry o, MenuOptionClicked e)
	{
		if (!config.enableDebugging())
		{
			return;
		}
		if (e.getOpcode() == MenuOpcode.WALK.getId())
		{
			return;
		}
		System.out.println(
				"\n" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:S").format(new Date())
						+ "\nOrig: Point: " + getPointClicked().getX() + ", " + getPointClicked().getY()
						+ "\nOrig: Option: " + o.getOption() + "   ||   Mod: Option: " + e.getOption()
						+ "\nOrig: Target: " + o.getTarget() + "   ||   Mod: Target: " + e.getTarget()
						+ "\nOrig: Identifier: " + o.getIdentifier() + "   ||   Mod: Identifier: " + e.getIdentifier()
						+ "\nOrig: Opcode: " + e.getMenuOpcode() + "   ||   Mod: Opcode: "	+ e.getMenuOpcode()
						+ "\nOrig: Param0: " + o.getParam0() + "   ||   Mod: Param0: " + e.getParam0()
						+ "\nOrig: Param1: " + o.getParam1() + "   ||   Mod: Param1: " + e.getParam1()
						+ "\nOrig: forceLeftClick: " + o.isForceLeftClick() + "   ||   Mod: forceLeftClick: " 	+ e.isForceLeftClick()
						+ "\nEvent consumed: " + e.isConsumed()
		);
		if (config.enableWriteToClipboard())
		{
			writeTextToClipboard(
					"```\n"
							+ "\nOrig: Point: " + getPointClicked().getX() + ", " + getPointClicked().getY()
							+ "\nOrig: Option: " + o.getOption() + "   ||   Mod: Option: " + e.getOption()
							+ "\nOrig: Target: " + o.getTarget() + "   ||   Mod: Target: " + e.getTarget()
							+ "\nOrig: Identifier: " + o.getIdentifier() + "   ||   Mod: Identifier: " + e.getIdentifier()
							+ "\nOrig: Opcode: " + e.getMenuOpcode() + "   ||   Mod: Opcode: "	+ e.getMenuOpcode()
							+ "\nOrig: Param0: " + o.getParam0() + "   ||   Mod: Param0: " + e.getParam0()
							+ "\nOrig: Param1: " + o.getParam1() + "   ||   Mod: Param1: " + e.getParam1()
							+ "\nOrig: forceLeftClick: " + o.isForceLeftClick() + "   ||   Mod: forceLeftClick: " 	+ e.isForceLeftClick()
							+ "\nEvent consumed: " + e.isConsumed()
							+ "\n```");
		}
	}

	private void addDropMenu(MenuOpened e)
	{
		MenuEntry[] origE = e.getMenuEntries();
		MenuEntry firstEntry = e.getFirstEntry();

		MenuEntry dropMatching = new MenuEntry("Drop-Matching",
				"<col=ffff00>Drop list",
				firstEntry.getIdentifier(),
				MenuOpcode.ITEM_USE.getId(),
				firstEntry.getParam0(),
				firstEntry.getParam1(),
				firstEntry.isForceLeftClick());

		MenuEntry dropExcept = new MenuEntry("Drop-Except",
				"<col=ffff00>Ignore list",
				firstEntry.getIdentifier(), MenuOpcode.ITEM_USE.getId(),
				firstEntry.getParam0(),
				firstEntry.getParam1(),
				firstEntry.isForceLeftClick());

		if (!config.enableDropExcept() && config.enableDropMatching())
		{
			if (origE.length == 3)
			{
				e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropMatching, origE[2]});
			}
			if (origE.length == 4)
			{
				e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropMatching, origE[2], origE[3]});
			}
			if (origE.length == 5)
			{
				e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropMatching, origE[2], origE[3], origE[4]});
			}
			if (origE.length == 6)
			{
				e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropMatching, origE[2], origE[3], origE[4], origE[5]});
			}
		}
		if (config.enableDropExcept() && !config.enableDropMatching())
		{
			if (origE.length == 3)
			{
				e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, origE[2]});
			}
			if (origE.length == 4)
			{
				e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, origE[2], origE[3]});
			}
			if (origE.length == 5)
			{
				e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, origE[2], origE[3], origE[4]});
			}
			if (origE.length == 6)
			{
				e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, origE[2], origE[3], origE[4], origE[5]});
			}
		}
		if (config.enableDropExcept() && config.enableDropMatching())
		{
			if (origE.length == 3)
			{
				e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, dropMatching, origE[2]});
			}
			if (origE.length == 4)
			{
				e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, dropMatching, origE[2], origE[3]});
			}
			if (origE.length == 5)
			{
				e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, dropMatching, origE[2], origE[3], origE[4]});
			}
			if (origE.length == 6)
			{
				e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, dropMatching, origE[2], origE[3], origE[4], origE[5]});
			}
		}
	}

	private void addBankMenu(MenuOpened e)
	{
		MenuEntry[] origE = e.getMenuEntries();

		List<GameObject> gameObjectList = objects.getGameObjectsMatching("Bank chest", "Bank booth");
		if (gameObjectList.size() == 0)
		{
			return;
		}
		GameObject object = gameObjectList.size() > 2
				? gameObjectList.get(new Random().nextInt(2))
				: gameObjectList.get(new Random().nextInt(gameObjectList.size()));
		if (object == null)
		{
			return;
		}
		MenuEntry openBank = new MenuEntry(
				Arrays.stream(client.getObjectDefinition(object.getId()).getActions()).findFirst().orElse("Bank"),
				"<col=ffff00>" + client.getObjectDefinition(object.getId()).getName(),
				object.getId(),
				MenuOpcode.GAME_OBJECT_FIRST_OPTION.getId(),
				object.getSceneMinLocation().getX(),
				object.getSceneMinLocation().getY(),
				false);

		e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], openBank});
	}

	public void click(Rectangle rectangle)
	{
		assert !client.isClientThread();
		Point point = getClickPoint(rectangle);
		click(point);
	}

	public void click(Point p)
	{
		assert !client.isClientThread();

		if (client.isStretchedEnabled())
		{
			final Dimension stretched = client.getStretchedDimensions();
			final Dimension real = client.getRealDimensions();
			final double width = (stretched.width / real.getWidth());
			final double height = (stretched.height / real.getHeight());
			final Point point = new Point((int) (p.getX() * width), (int) (p.getY() * height));
			mouseEvent(501, point);
			mouseEvent(502, point);
			mouseEvent(500, point);
			return;
		}
		setPointClicked(p);
		mouseEvent(501, p);
		mouseEvent(502, p);
		mouseEvent(500, p);
	}

	public Point getClickPoint(@NotNull Rectangle rect)
	{
		final int x = (int) (rect.getX() + getRandomIntBetweenRange((int) rect.getWidth() / 6 * -1, (int) rect.getWidth() / 6) + rect.getWidth() / 2);
		final int y = (int) (rect.getY() + getRandomIntBetweenRange((int) rect.getHeight() / 6 * -1, (int) rect.getHeight() / 6) + rect.getHeight() / 2);

		return new Point(x, y);
	}

	public int getRandomIntBetweenRange(int min, int max)
	{
		return (int) ((Math.random() * ((max - min) + 1)) + min);
	}

	private void mouseEvent(int id, @NotNull Point point)
	{
		MouseEvent e = new MouseEvent(
				client.getCanvas(), id,
				System.currentTimeMillis(),
				0, point.getX(), point.getY(),
				1, false, 1
		);

		client.getCanvas().dispatchEvent(e);
	}

}
