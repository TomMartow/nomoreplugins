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
import net.runelite.api.Point;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
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
import java.util.ArrayList;
import java.util.List;

@Extension
@PluginDescriptor(
		name = "QOL Clicks ➯ (Beta)",
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

	@Inject
	private ChatMessageManager chatMessageManager;

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
	Rectangle clickArea = null;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	List<TileItem> groundItems = new ArrayList<>();

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	MenuEntry qolMenuEntry = null;

	private final HotkeyListener toggle = new HotkeyListener(() -> config.automationKeybind())
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
		// Stop the spoof click from clicking constantly.
		setSpoofClick(false);
		MenuEntry clonedEntry = new MenuEntry(e.getMenuOption(),
				e.getMenuTarget(),
				e.getId(),
				e.getMenuAction().getId(),
				e.getActionParam(),
				e.getWidgetId(),
				false);

		menu.onClicked(e);
		if (getQolMenuEntry() != null)
		{
			e.setMenuEntry(qolMenuEntry);
		}

		if (isSpoofClick())
		{
			new Thread(() ->
					automation.clickR(getClickArea())).start();
			e.consume();
			return;
		}

		// Display marker
		if (qolClick
				&& config.displayQOLClickOverlay())
		{
			arrow.draw(e);
			setQolClick(false);
		}

		setQolMenuEntry(null);
		debugMessage(e);
	}

	@Subscribe
	private void on(MenuEntryAdded e) { menu.onAdded(e); }

	@Subscribe
	private void on(ItemSpawned e)
	{
		TileItem item = e.getItem();
		if (item == null)
		{
			return;
		}
		groundItems.add(item);
	}

	@Subscribe
	private void on(ItemDespawned e)
	{
		groundItems.remove(e.getItem());
	}

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
		if (e.getMenuAction() == MenuAction.WALK
				|| e.getMenuOption().equalsIgnoreCase("Cancel"))
		{
			return;
		}

		String debugString = automation.getClickedPoint().equals(new Point(0, 0))
				? 	"O: " + e.getMenuOption() +
				  ", T: " + e.getMenuTarget() +
				  ", ID: " + e.getId() +
				  ", MA: " + e.getMenuAction() +
				  ", A: " + e.getActionParam() +
				  ", WID: " + e.getWidgetId()
				:	"QOLSpoof: x" + automation.getClickedPoint().getX() + " y" + automation.getClickedPoint().getY() +
				  ", O: " + e.getMenuOption() +
				  ", T: " + e.getMenuTarget() +
				  ", ID: " + e.getId() +
				  ", MA: " + e.getMenuAction() +
				  ", A: " + e.getActionParam() +
				  ", WID: " + e.getWidgetId();

		if (config.enableDebug())
		{
			System.out.println(debugString);
		}

		if (config.enableGameMessage())
		{
			clientThread.invoke(() ->
			{
				String chatMessage = new ChatMessageBuilder()
						.append(ChatColorType.HIGHLIGHT)
						.append(debugString)
						.build();

				chatMessageManager
						.queue(QueuedMessage.builder()
								.type(ChatMessageType.CONSOLE)
								.runeLiteFormattedMessage(chatMessage)
								.build());
			});
		}

		if (config.enableClipboard())
		{
			StringSelection stringSelection = new StringSelection("``` \n" + debugString + "\n ```");
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		}

		automation.setClickedPoint(new Point(0,0));
	}
}

