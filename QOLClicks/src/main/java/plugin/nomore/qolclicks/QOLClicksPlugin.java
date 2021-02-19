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
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.util.HotkeyListener;
import org.pf4j.Extension;
import plugin.nomore.qolclicks.utils.automation.*;
import plugin.nomore.qolclicks.utils.automation.Random;
import plugin.nomore.qolclicks.utils.debugging.Debug;
import plugin.nomore.qolclicks.utils.menu.Added;
import plugin.nomore.qolclicks.utils.menu.Clicked;
import plugin.nomore.qolclicks.utils.menu.Opened;
import plugin.nomore.qolclicks.utils.scene.builds.GameObjectItem;
import plugin.nomore.qolclicks.utils.scene.builds.InventoryItem;
import plugin.nomore.qolclicks.utils.scene.Inventory;
import plugin.nomore.qolclicks.utils.scene.Npcs;
import plugin.nomore.qolclicks.utils.scene.Objects;
import plugin.nomore.qolclicks.utils.menu.TargetMenus;

import javax.inject.Inject;
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
	@Inject private ClientThread clientThread;
	@Inject private QOLClicksConfig config;
	@Inject private Keybinds keybinds;
	@Inject private KeyManager keyManager;
	@Inject private Clicked clicked;
	@Inject private Added added;
	@Inject private Opened opened;
	@Inject private Random random;
	@Inject private Inventory inventory;
	@Inject private Objects objects;
	@Inject private Npcs npcs;
	@Inject private Format string;
	@Inject private TargetMenus targetMenus;
	@Inject private Debug debug;
	@Inject private Mouse mouse;
	@Inject private Automation automation;

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
		keyManager.registerKeyListener(keybinds);
	}

	@Override
	protected void shutDown()
	{
		log.info("QOL Clicks: finished.");
		keyManager.unregisterKeyListener(keybinds);
	}

	@Subscribe
	private void on(MenuOpened e)
	{
		if (e.getFirstEntry().getParam1() == WidgetInfo.INVENTORY.getId())
		{
			if (config.enableDropMatching()
					|| config.enableDropExcept())
			{
				opened.addDropMenu(e);
			}
		}

		if (e.getFirstEntry().getMenuOpcode() == MenuOpcode.CC_OP
				&& e.getFirstEntry().getOption().equalsIgnoreCase("Inventory"))
		{
			if (config.enableBanking())
			{
				opened.addBankMenu(e);
			}
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
			clicked.useItemOnItem(
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
				clicked.useItemOnGameObject(
						inventory.getItem(e.getIdentifier()),
						objects.getClosestGameObjectMatching("Fire"),
						e);
			}
			else if (config.enableRange() && !config.enableFire())
			{
				clicked.useItemOnGameObject(
						inventory.getItem(e.getIdentifier()),
						objects.getClosestGameObjectMatching("Range"),
						e);
			}
			else if (config.enableRange() && config.enableFire())
			{
				clicked.useItemOnGameObject(
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
			NPC npc;
			if (config.findClosest())
			{
				npc = npcList.get(0);
			}
			else
			{
				npc = npcList.size() > 2
						? npcList.get(new java.util.Random().nextInt(2))
						: npcList.get(new java.util.Random().nextInt(npcList.size()));
			}
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
				mouse.clickR(client.getCanvas().getBounds());
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
						mouse.clickC(knife.getCanvasBounds());
						try
						{
							Thread.sleep(random.getRandomIntBetweenRange(config.dropMinTime(), config.dropMaxTime()));
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
							Thread.sleep(random.getRandomIntBetweenRange(600, 1200));
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
				clicked.dropItem(
						fish,
						e);
			}
			else
			{
				clicked.useItemOnItem(
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
			NPC npc;
			if (config.findClosest())
			{
				npc = npcList.get(0);
			}
			else
			{
				npc = npcList.size() > 2
						? npcList.get(new java.util.Random().nextInt(2))
						: npcList.get(new java.util.Random().nextInt(npcList.size()));
			}
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
				mouse.clickR(client.getCanvas().getBounds());
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
			NPC npc;
			if (config.findClosest())
			{
				npc = npcList.get(0);
			}
			else
			{
				npc = npcList.size() > 2
					? npcList.get(new java.util.Random().nextInt(2))
					: npcList.get(new java.util.Random().nextInt(npcList.size()));
			}
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
				mouse.clickR(client.getCanvas().getBounds());
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
				clicked.useItemOnNPC(
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
			automation.dropMatching();
			e.consume();
			return;
		}

		if (e.getMenuOpcode() == MenuOpcode.ITEM_USE
				&& e.getOption().equals("Drop-Except"))
		{
			automation.dropExcept();
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

		if (e.getOption().equals("Open")
				&& e.getTarget().contains("Bank"))
		{

			List<GameObject> gameObjectList = objects.getGameObjectsMatching("Bank booth", "Bank chest");

			if (gameObjectList.size() == 0)
			{
				return;
			}
			GameObject object;
			if (config.findClosest())
			{
				object = gameObjectList.get(0);
			}
			else
			{
				object = gameObjectList.size() > 2
						? gameObjectList.get(new java.util.Random().nextInt(2))
						: gameObjectList.get(new java.util.Random().nextInt(gameObjectList.size()));
			}
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
						gameObjectItem.getActions()[1],
						"<col=ffff>" + gameObjectItem.getName(),
						gameObjectItem.getObject().getId(),
						MenuOpcode.GAME_OBJECT_SECOND_OPTION.getId(),
						gameObjectItem.getObject().getSceneMinLocation().getX(),
						gameObjectItem.getObject().getSceneMinLocation().getY(),
						false));
				mouse.clickC(client.getCanvas().getBounds());
			}).start();
			e.consume();
			return;
		}



		if (getTargetMenu() != null)
		{
			e.setMenuEntry(getTargetMenu());
			setTargetMenu(null);
		}

		debug.clickedEntry(originalEntry, e);
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
				added.useItemOnItem(
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

			if (client.getItemDefinition(event.getIdentifier())
				.getName()
				.toLowerCase()
				.contains("raw")
					&& (config.enableFire()
					|| config.enableRange()))
			{
				if (!config.enableRange() && config.enableFire())
				{
					added.useItemOnGameObject(
							"Cook",
							inventory.getItem(event.getIdentifier()),
							objects.getClosestGameObjectMatching("Fire"),
							event);
				}
				else if (config.enableRange() && !config.enableFire())
				{
					added.useItemOnGameObject(
							"Cook",
							inventory.getItem(event.getIdentifier()),
							objects.getClosestGameObjectMatching("Range"),
							event);
				}
				else if (config.enableRange() && config.enableFire())
				{
					added.useItemOnGameObject(
							"Cook",
							inventory.getItem(event.getIdentifier()),
							objects.getMatchingGameObjectsSortedByClosest("Fire", "Range").get(0),
							event);
				}
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
				added.interactWithNPC(
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
					added.useItemOnItem(
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
				added.interactWithNPC(
						"Cage",
						npcs.getClosestNpcWithMenuAction("Cage"),
						event);
			}

			if (config.enableFishingRod()
					&& client.getItemDefinition(event.getIdentifier())
					.getName()
					.equalsIgnoreCase("Fly fishing rod"))
			{
				added.interactWithNPC(
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
					added.useItemOnNPC(
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

}
