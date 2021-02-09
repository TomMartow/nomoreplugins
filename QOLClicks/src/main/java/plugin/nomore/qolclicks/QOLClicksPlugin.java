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

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;

import net.runelite.api.events.*;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import org.pf4j.Extension;
import plugin.nomore.qolclicks.misc.Banking;
import plugin.nomore.qolclicks.skills.cooking.Cooking;
import plugin.nomore.qolclicks.skills.firemaking.Firemaking;
import plugin.nomore.qolclicks.skills.fishing.Fishing;
import plugin.nomore.qolclicks.skills.prayer.Prayer;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	@Inject
	private Client client;

	@Inject
	private QOLClicksConfig config;

	@Inject
	private Firemaking firemaking;

	@Inject
	private Cooking cooking;

	@Inject
	private Fishing fishing;

	@Inject
	private Prayer prayer;

	@Inject
	private Banking banking;

	@Inject
	public ExecutorService executor;

	public boolean iterating = false;

	@Provides
	QOLClicksConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(QOLClicksConfig.class);
	}

	@Override
	protected void startUp()
	{
		executor = Executors.newSingleThreadExecutor();
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
		executor.shutdown();
		iterating = false;
	}

	@Subscribe
	private void on(MenuOptionClicked event)
	{
		MenuEntry clone = event.clone();

		String origOption = event.getOption();
		String origTarget = event.getTarget();
		int origId = event.getIdentifier();
		MenuOpcode origMenuOpcode = event.getMenuOpcode();
		int origP0 = event.getParam0();
		int origP1 = event.getParam1();
		boolean origIsFLC = event.isForceLeftClick();

		if (config.enableFiremaking())
		{
			if (!firemaking.menuOptionClicked(event))
			{
				event.setMenuEntry(clone);
			}
		}

		if (config.enableCooking())
		{
			if (!cooking.menuOptionClicked(event))
			{
				event.setMenuEntry(clone);
			}
		}

		if (config.enableFishingRod()
				|| config.enableLobsterPot())
		{
			if (!fishing.menuOptionClicked(event))
			{
				event.setMenuEntry(clone);
			}
		}

		if (config.enableBanking())
		{
			if (!banking.menuOptionClicked(event))
			{
				event.setMenuEntry(clone);
			}
		}

		if (config.enableUnnoteBones())
		{
			if (!prayer.menuOptionClicked(event))
			{
				event.setMenuEntry(clone);
			}
		}

		/*
		if (config.enable()
				&& !.menuOptionClicked(event))
		{
			event.consume();
		}

		 */

		if (config.enableDebugging() && event.getOpcode() != MenuOpcode.WALK.getId())
		{
			System.out.println(
					"\n" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:S").format(new Date())
							+ "\nOrig: Option: " + origOption + "   ||   Mod: Option: " + event.getOption()
							+ "\nOrig: Target: " + origTarget + "   ||   Mod: Target: " + event.getTarget()
							+ "\nOrig: Identifier: " + origId + "   ||   Mod: Identifier: " + event.getIdentifier()
							+ "\nOrig: Opcode: " + origMenuOpcode + "   ||   Mod: Opcode: "	+ event.getMenuOpcode()
							+ "\nOrig: Param0: " + origP0 + "   ||   Mod: Param0: " + event.getParam0()
							+ "\nOrig: Param1: " + origP1 + "   ||   Mod: Param1: " + event.getParam1()
							+ "\nOrig: forceLeftClick: " + origIsFLC + "   ||   Mod: forceLeftClick: " 	+ event.isForceLeftClick()
			);

			if (config.enableWriteToClipboard())
			{
				writeTextToClipboard(
						"```\n"
						+ "\n" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:S").format(new Date())
						+ "\nOrig: Option: " + origOption + "   ||   Mod: Option: " + event.getOption()
						+ "\nOrig: Target: " + origTarget + "   ||   Mod: Target: " + event.getTarget()
						+ "\nOrig: Identifier: " + origId + "   ||   Mod: Identifier: " + event.getIdentifier()
						+ "\nOrig: Opcode: " + origMenuOpcode + "   ||   Mod: Opcode: "	+ event.getMenuOpcode()
						+ "\nOrig: Param0: " + origP0 + "   ||   Mod: Param0: " + event.getParam0()
						+ "\nOrig: Param1: " + origP1 + "   ||   Mod: Param1: " + event.getParam1()
						+ "\nOrig: forceLeftClick: " + origIsFLC + "   ||   Mod: forceLeftClick: " 	+ event.isForceLeftClick()
						+ "\n```");
			}
		}
	}

	@Subscribe
	private void on(MenuEntryAdded event)
	{
		if (config.enableFiremaking())
		{
			firemaking.menuEntryAdded(event);
		}

		if (config.enableCooking())
		{
			cooking.menuEntryAdded(event);
		}

		if (config.enableFishingRod()
				|| config.enableLobsterPot())
		{
			fishing.menuEntryAdded(event);
		}

		if (config.enableBanking())
		{
			banking.menuEntryAdded(event);
		}

		if (config.enableUnnoteBones())
		{
			prayer.menuEntryAdded(event);
		}
	}

	public static List<NPC> npcList = new ArrayList<>();
	public List<NPC> getNpcList() { return npcList; }

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

}
