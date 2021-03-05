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
package plugin.nomore.qolclicksbeta;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.HotkeyListener;
import org.pf4j.Extension;
import plugin.nomore.qolclicksbeta.highlighting.Arrow;
import plugin.nomore.qolclicksbeta.menu.actions.Menu;
import plugin.nomore.qolclicksbeta.menu.scene.Inventory;
import plugin.nomore.qolclicksbeta.utils.Automation;

import javax.inject.Inject;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

@Extension
@PluginDescriptor(
		name = "QOL Clicks (Beta)",
		description = "QOL fixes that should be implemented.",
		tags = {"nomore", "qol", "click"}
)
@Slf4j
public class QOLClicksBetaPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private QOLClicksBetaConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private QOLClicksBetaOverlay overlay;

	@Inject
	private KeyManager keyManager;

	@Inject
	private Menu menu;

	@Inject
	private Automation automation;

	@Inject
	private Arrow arrow;

	@Inject
	private Inventory inventory;

	@Provides
	QOLClicksBetaConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(QOLClicksBetaConfig.class);
	}

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	boolean qolClick = false;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	boolean spoofClick = false;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	boolean openedSpellbook = false;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	static Rectangle clickArea = null;

	private final HotkeyListener toggle = new HotkeyListener(() -> config.dropKeybind())
	{
		@Override
		public void hotkeyPressed()
		{
			automation.setReadyToDrop(true);
		}
	};

	@Override
	protected void startUp()
	{
		keyManager.registerKeyListener(toggle);
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		keyManager.unregisterKeyListener(toggle);
		overlayManager.remove(overlay);
	}

	@Subscribe
	private void on(GameTick e)
	{
		if (automation.isBusy())
		{
			return;
		}

		if (automation.isReadyToDrop())
		{
			automation.dropItems();
		}

		if (isOpenedSpellbook())
		{
			if (!inventory.isOpen())
			{
				client.runScript(915, 3);
				setOpenedSpellbook(false);
			}
		}
	}

	@Subscribe
	private void on(MenuOpened e) { menu.onOpen(e); }

	@Subscribe
	private void on(MenuOptionClicked e)
	{
		if (automation.getTargetMenu() == null)
		{
			menu.onClicked(e);
		}
		else
		{
			e.setMenuEntry(automation.getTargetMenu());
			if (isSpoofClick())
			{
				new Thread(() ->
				{
					automation.clickR(getClickArea());
					setSpoofClick(false);
					e.consume();
				}).start();
				return;
			}
			automation.setTargetMenu(null);
		}

		if (qolClick
				&& config.displayQOLClickOverlay())
		{
			arrow.draw(e);
			setQolClick(false);
		}

		debugMessage(e);
	}

	@Subscribe
	private void on(MenuEntryAdded e) { menu.onAdded(e); }

	public void setSelectedItem(WidgetInfo widgetInfo, int itemIndex, int itemId)
	{
		client.setSelectedItemWidget(widgetInfo.getId());
		client.setSelectedItemSlot(itemIndex);
		client.setSelectedItemID(itemId);
	}

	public void setSelectSpell(WidgetInfo info)
	{
		final Widget widget = client.getWidget(info);
		client.setSelectedSpellName("<col=00ff00>" + widget.getName() + "</col>");
		client.setSelectedSpellWidget(widget.getId());
		client.setSelectedSpellChildIndex(-1);
	}

	public void insertMenuEntry(MenuEntry e, boolean forceLeftClick)
	{
		client.insertMenuItem(
				e.getOption(),
				e.getTarget(),
				e.getOpcode(),
				e.getId(),
				e.getParam0(),
				e.getParam1(),
				forceLeftClick
		);
	}

	private void debugMessage(MenuOptionClicked e)
	{
		if (!config.enableDebug() || e.getMenuAction() == MenuAction.WALK
				|| e.getMenuOption().equalsIgnoreCase("Cancel"))
		{
			return;
		}
		System.out.println(
				"Point: " + automation.getClickedPoint() + "\n" +
				"isQOLClick: " + isQolClick() + "\n" +
				"Option: " + e.getMenuOption() + "\n" +
				"Target: " + e.getMenuTarget() + "\n" +
				"ID: " + e.getId() + "\n" +
				"MenuAction: " + e.getMenuAction() + "\n" +
				"ActionParam: " + e.getActionParam() + "\n" +
				"WidgetID: " + e.getWidgetId() + "\n"
		);

		if (!config.enableClipboard())
		{
			return;
		}

		String myString =
				"Point: " + automation.getClickedPoint() + "\n" +
				"isQOLClick: " + isQolClick() + "\n" +
				"Option: " + e.getMenuOption() + "\n" +
				"Target: " + e.getMenuTarget() + "\n" +
				"ID: " + e.getId() + "\n" +
				"MenuAction: " + e.getMenuAction() + "\n" +
				"ActionParam: " + e.getActionParam() + "\n" +
				"WidgetID: " + e.getWidgetId() + "\n";
		StringSelection stringSelection = new StringSelection(myString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
}