/*
 * Copyright (c) 2019-2020, ganom <https://github.com/Ganom>
 * All rights reserved.
 * Licensed under GPL3, see LICENSE for the full scope.
 */
package net.runelite.client.plugins.nmutils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.Provides;
import io.reactivex.rxjava3.annotations.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.queries.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ColorUtil;
import org.jetbrains.annotations.NotNull;
import org.pf4j.Extension;

@Extension
@PluginDescriptor(
		name = "NMUtils",
		type = PluginType.UTILITY
		//hidden = true
)
@Slf4j
@SuppressWarnings("unused")
@Singleton
public class Utils extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private UtilsConfig config;

	@Inject
	private UtilsOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ExecutorService execute;

	@Provides
	UtilsConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(UtilsConfig.class);
	}

	@Override
	protected void startUp() {
		timeLastIdle = Instant.now();
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() {
		execute.shutdown();
		overlayManager.remove(overlay);
	}

	// ANSI Regular http://patorjk.com/software/taag/#p=display&v=1&f=ANSI%20Shadow&t=

	//██████╗ ██╗      █████╗ ██╗   ██╗███████╗██████╗
	//██╔══██╗██║     ██╔══██╗╚██╗ ██╔╝██╔════╝██╔══██╗
	//██████╔╝██║     ███████║ ╚████╔╝ █████╗  ██████╔╝
	//██╔═══╝ ██║     ██╔══██║  ╚██╔╝  ██╔══╝  ██╔══██╗
	//██║     ███████╗██║  ██║   ██║   ███████╗██║  ██║
	//╚═╝     ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝

	Instant timeLastIdle = null;
	Instant timeLastNotIdle = null;
	boolean sentMessage = false;
	@Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) boolean isPlayerIdle = false;

	@Subscribe
	private void on(ClientTick event)
	{
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return;
		}

		animationCheck(player);
		if (timeLastIdle != null)
		{
			if (Instant.now().isAfter(timeLastIdle.plusMillis(config.playerIdleTime())))
			{
				if (!isPlayerIdle)
				{
					sentMessage = false;
				}
				isPlayerIdle = true;
				if (!sentMessage)
				{
					sendMessage("PLAYER: Animation idle.");
					sentMessage = true;
				}
			}
			else
			{
				if (isPlayerIdle)
				{
					sentMessage = false;
				}
				isPlayerIdle = false;
				if (!sentMessage)
				{
					sentMessage = true;
				}
			}
		}
	}

	private void animationCheck(Player player)
	{
		if (player.getAnimation() == AnimationID.IDLE && timeLastIdle == null)
		{
			timeLastIdle = Instant.now();
			if (timeLastNotIdle != null)
			timeLastNotIdle = null;
		}
		if (player.getAnimation() != AnimationID.IDLE && timeLastNotIdle == null)
		{
			isPlayerIdle = false;
			timeLastNotIdle = Instant.now();
			timeLastIdle = null;
		}
	}

	//███╗   ███╗██╗███████╗ ██████╗███████╗██╗     ██╗      █████╗ ███╗   ██╗███████╗ ██████╗ ██╗   ██╗███████╗
	//████╗ ████║██║██╔════╝██╔════╝██╔════╝██║     ██║     ██╔══██╗████╗  ██║██╔════╝██╔═══██╗██║   ██║██╔════╝
	//██╔████╔██║██║███████╗██║     █████╗  ██║     ██║     ███████║██╔██╗ ██║█████╗  ██║   ██║██║   ██║███████╗
	//██║╚██╔╝██║██║╚════██║██║     ██╔══╝  ██║     ██║     ██╔══██║██║╚██╗██║██╔══╝  ██║   ██║██║   ██║╚════██║
	//██║ ╚═╝ ██║██║███████║╚██████╗███████╗███████╗███████╗██║  ██║██║ ╚████║███████╗╚██████╔╝╚██████╔╝███████║
	//╚═╝     ╚═╝╚═╝╚══════╝ ╚═════╝╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝╚══════╝ ╚═════╝  ╚═════╝ ╚══════╝

	public String getCurrentTime()
	{
		return new SimpleDateFormat("HH:mm:ss:S").format(new Date());
	}

	public String getCurrentDate()
	{
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}

	//██╗███╗   ██╗██╗   ██╗███████╗███╗   ██╗████████╗ ██████╗ ██████╗ ██╗   ██╗
	//██║████╗  ██║██║   ██║██╔════╝████╗  ██║╚══██╔══╝██╔═══██╗██╔══██╗╚██╗ ██╔╝
	//██║██╔██╗ ██║██║   ██║█████╗  ██╔██╗ ██║   ██║   ██║   ██║██████╔╝ ╚████╔╝
	//██║██║╚██╗██║╚██╗ ██╔╝██╔══╝  ██║╚██╗██║   ██║   ██║   ██║██╔══██╗  ╚██╔╝
	//██║██║ ╚████║ ╚████╔╝ ███████╗██║ ╚████║   ██║   ╚██████╔╝██║  ██║   ██║
	//╚═╝╚═╝  ╚═══╝  ╚═══╝  ╚══════╝╚═╝  ╚═══╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝   ╚═╝

	public Widget getInventory()
	{
		assert client.isClientThread();
		if (client.getLocalPlayer() == null)
		{
			return null;
		}
		if (client.getWidget(WidgetInfo.INVENTORY) == null)
		{
			return null;
		}
		return client.getWidget(WidgetInfo.INVENTORY);
	}

	public List<WidgetItem> getInventoryItems()
	{
		return getInventory()
				.getWidgetItems()
				.stream()
				.filter(w -> w != null
						&& w.getId() != -1)
				.collect(Collectors.toList());
	}

	public WidgetItem getInventoryItem(String itemName)
	{
		return getInventoryItems()
				.stream()
				.filter(i -> removeWhiteSpaces(getItemName(i))
						.contains(removeWhiteSpaces(itemName)))
				.findFirst()
				.orElse(null);
	}

	public WidgetItem getInventoryItem(int itemId)
	{
		return getInventoryItems()
				.stream()
				.filter(w -> w.getWidget() != null
						&& w.getId() != -1)
				.findFirst()
				.orElse(null);
	}

	public boolean isInventoryFull()
	{
		return getInventoryItems().size() == 28;
	}

	public String getItemName(WidgetItem item) { return removeWhiteSpaces(client.getItemDefinition(item.getId()).getName()); }

	public String getItemName(int id)
	{
		return removeWhiteSpaces(client.getItemDefinition(id).getName());
	}

	public ItemDefinition getItemDefinition(WidgetItem item) { return client.getItemDefinition(item.getId()); }

	public boolean doesInventoryContain(String itemName)
	{
		return getInventoryItems()
				.stream()
				.anyMatch(i -> removeWhiteSpaces(getItemName(i))
						.contains(removeWhiteSpaces(itemName)));
	}

	public boolean doesInventoryContain(int itemId)
	{
		return getInventoryItems()
				.stream()
				.anyMatch(w -> w.getId() == itemId);
	}

	public List<WidgetItem> getInventoryItemsMatching(String... itemName)
	{
		return getInventoryItems()
				.stream()
				.filter(i -> Arrays.stream(itemName)
						.anyMatch(s -> removeWhiteSpaces(s)
								.contains(getItemName(i))))
				.collect(Collectors.toList());
	}

	public List<WidgetItem> getInventoryItemsMatching(int... itemId)
	{
		return getInventoryItems()
				.stream()
				.filter(i -> Arrays.stream(itemId)
						.allMatch(n -> n == i.getId()))
				.collect(Collectors.toList());
	}

	public List<WidgetItem> getInventoryItemsNotMatching(String... itemName)
	{
		return getInventoryItems()
				.stream()
				.filter(i -> Arrays.stream(itemName)
						.noneMatch(s -> removeWhiteSpaces(s)
								.contains(getItemName(i))))
				.collect(Collectors.toList());
	}

	public List<WidgetItem> getInventoryItemsNotMatching(int... itemId)
	{
		return getInventoryItems()
				.stream()
				.filter(i -> Arrays.stream(itemId)
						.allMatch(n -> n != i.getId()))
				.collect(Collectors.toList());
	}

	//██████╗  █████╗ ███╗   ██╗██╗  ██╗
	//██╔══██╗██╔══██╗████╗  ██║██║ ██╔╝
	//██████╔╝███████║██╔██╗ ██║█████╔╝
	//██╔══██╗██╔══██║██║╚██╗██║██╔═██╗
	//██████╔╝██║  ██║██║ ╚████║██║  ██╗
	//╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝

	public Widget getBank()
	{
		assert client.isClientThread();
		if (client.getLocalPlayer() == null)
		{
			return null;
		}
		if (client.getWidget(WidgetInfo.BANK_CONTAINER) == null)
		{
			return null;
		}
		return client.getWidget(WidgetInfo.BANK_CONTAINER);
	}

	public boolean isBankOpen()
	{
		return getBank() != null && !getBank().isHidden();
	}

	public List<WidgetItem> getBankItems()
	{
		assert client.isClientThread();
		if (client.getLocalPlayer() == null)
		{
			return null;
		}
		return getBank()
				.getWidgetItems()
				.stream()
				.filter(w -> w != null
						&& w.getId() != -1)
				.collect(Collectors.toList());
	}

	public WidgetItem getBankItem(String itemName)
	{
		Optional<WidgetItem> item = getBankItems()
				.stream()
				.findFirst()
				.filter(w -> w.getWidget() != null
						&& w.getId() != -1
						&& w.getWidget()
						.getName()
						.equalsIgnoreCase(itemName));
		return item.orElse(null);
	}

	public WidgetItem getBankItem(int itemId)
	{
		Optional<WidgetItem> item = getBankItems()
				.stream()
				.findFirst()
				.filter(w -> w.getWidget() != null
						&& w.getId() != -1);
		return item.orElse(null);
	}

	public boolean doesBankContain(String itemName)
	{
		return getBankItems()
				.stream()
				.anyMatch(w -> w.getWidget() != null
						&& getItemName(w)
						.equalsIgnoreCase(itemName));
	}

	public boolean doesBankContain(int itemId)
	{
		return getBankItems()
				.stream()
				.anyMatch(w -> w.getWidget() != null
						&& w.getId() == itemId);
	}

	public List<Integer> getBankSlotsThatContain(String itemName)
	{
		int i = 0;
		List<Integer> slots = new ArrayList<>();
		for (WidgetItem item : getBankItems())
		{
			i++;
			if (item == null || item.getId() == -1)
			{
				continue;
			}
			ItemDefinition itemDefinition = client.getItemDefinition(item.getId());
			if (itemDefinition.getName().toLowerCase().equalsIgnoreCase(itemName))
			{
				slots.add(i);
			}
		}
		return slots;
	}

	public List<Integer> getBankSlotsThatContain(int itemId)
	{
		int i = 0;
		List<Integer> slots = new ArrayList<>();
		for (WidgetItem item : getBankItems())
		{
			i++;
			if (item == null || item.getId() == -1)
			{
				continue;
			}
			if (item.getId() == itemId)
			{
				slots.add(i);
			}
		}
		return slots;
	}

	//███╗   ██╗██████╗  ██████╗███████╗
	//████╗  ██║██╔══██╗██╔════╝██╔════╝
	//██╔██╗ ██║██████╔╝██║     ███████╗
	//██║╚██╗██║██╔═══╝ ██║     ╚════██║
	//██║ ╚████║██║     ╚██████╗███████║
	//╚═╝  ╚═══╝╚═╝      ╚═════╝╚══════╝

	public List<NPC> getListOfAllNPCS()
	{
		assert client.isClientThread();
		if (client.getLocalPlayer() == null)
		{
			return null;
		}
		return new NPCQuery().result(client).list;
	}

	public List<NPC> getListOfAllNPCSWithinDistance(WorldPoint worldpoint, int distance)
	{
		assert client.isClientThread();
		if (client.getLocalPlayer() == null)
		{
			return null;
		}
		return new NPCQuery().isWithinDistance(worldpoint, distance).result(client).list;
	}

	public List<NPC> getListOfNPCMatchingIds(Collection<Integer> id)
	{
		assert client.isClientThread();
		if (client.getLocalPlayer() == null)
		{
			return null;
		}
		return new NPCQuery().idEquals(id).result(client).list;
	}

	public List<NPC> getListOfNPCMatchingIdsWithinDistance(Collection<Integer> id, WorldPoint worldpoint, int distance)
	{
		assert client.isClientThread();
		if (client.getLocalPlayer() == null)
		{
			return null;
		}
		return new NPCQuery().idEquals(id).isWithinDistance(worldpoint, distance).result(client).list;
	}

	public List<NPC> getNPCSortedByDistance()
	{
		assert client.isClientThread();
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return null;
		}
		return new NPCQuery()
				.result(client)
				.stream()
				.sorted(Comparator.comparing(entityType -> entityType
						.getLocalLocation()
						.distanceTo(player.getLocalLocation())))
				.collect(Collectors.toList());
	}

	public List<NPC> getNPCSortedByDistance(int... ids)
	{
		assert client.isClientThread();
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return null;
		}
		return new NPCQuery()
				.idEquals(ids)
				.result(client)
				.stream()
				.sorted(Comparator.comparing(entityType -> entityType
						.getLocalLocation()
						.distanceTo(player.getLocalLocation())))
				.collect(Collectors.toList());
	}

	public List<NPC> getNPCSortedByDistance(String... name)
	{
		assert client.isClientThread();
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return null;
		}
		return new NPCQuery()
				.nameContains(name)
				.result(client)
				.stream()
				.sorted(Comparator.comparing(entityType -> entityType
						.getLocalLocation()
						.distanceTo(player.getLocalLocation())))
				.collect(Collectors.toList());
	}

	//██████╗  █████╗ ███╗   ███╗███████╗ ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗███████╗
	//██╔════╝ ██╔══██╗████╗ ████║██╔════╝██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝██╔════╝
	//██║  ███╗███████║██╔████╔██║█████╗  ██║   ██║██████╔╝     ██║█████╗  ██║        ██║   ███████╗
	//██║   ██║██╔══██║██║╚██╔╝██║██╔══╝  ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║   ╚════██║
	//╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║   ███████║
	//╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝ ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚══════╝

	public List<GameObject> getGameObjectSortedByDistance()
	{
		assert client.isClientThread();
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return null;
		}
		return new GameObjectQuery()
				.result(client)
				.stream()
				.sorted(Comparator.comparing(entityType -> entityType
						.getLocalLocation()
						.distanceTo(player.getLocalLocation())))
				.collect(Collectors.toList());
	}

	public List<GameObject> getGameObjectSortedByDistance(int... ids)
	{
		assert client.isClientThread();
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return null;
		}
		return new GameObjectQuery()
				.idEquals(ids)
				.result(client)
				.stream()
				.sorted(Comparator.comparing(entityType -> entityType
						.getLocalLocation()
						.distanceTo(player.getLocalLocation())))
				.collect(Collectors.toList());
	}

	public List<GameObject> getGameObjectSortedByDistance(String... names)
	{
		assert client.isClientThread();
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return null;
		}
		return new GameObjectQuery()
				.filter(gameObject -> Arrays.stream(names)
						.anyMatch(name -> removeWhiteSpaces(name)
								.contains(removeWhiteSpaces(client.getObjectDefinition(gameObject.getId()).getName()))))
				.result(client)
				.stream()
				.sorted(Comparator.comparing(entityType -> entityType
						.getLocalLocation()
						.distanceTo(player.getLocalLocation())))
				.collect(Collectors.toList());
	}

	//██████╗ ███████╗███╗   ██╗██████╗ ███████╗██████╗ ██╗███╗   ██╗ ██████╗
	//██╔══██╗██╔════╝████╗  ██║██╔══██╗██╔════╝██╔══██╗██║████╗  ██║██╔════╝
	//██████╔╝█████╗  ██╔██╗ ██║██║  ██║█████╗  ██████╔╝██║██╔██╗ ██║██║  ███╗
	//██╔══██╗██╔══╝  ██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗██║██║╚██╗██║██║   ██║
	//██║  ██║███████╗██║ ╚████║██████╔╝███████╗██║  ██║██║██║ ╚████║╚██████╔╝
	//╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝

	public void renderWidgetItem(Graphics2D graphics, WidgetItem item, Color color)
	{
		if (item == null)
		{
			return;
		}
		if (item.getWidget() != null && item.getWidget().isHidden())
		{
			return;
		}
		Rectangle rect = item.getCanvasBounds();
		if (rect == null)
		{
			return;
		}
		int x = (int) rect.getX();
		int y = (int) rect.getY();
		int w = (int) rect.getWidth();
		int h = (int) rect.getHeight();
		renderScreenIndicator(graphics, x, y, w, h, color);
	}

	public void renderWidgetItemCentreBox(Graphics2D graphics, WidgetItem item, int boxSize, Color color)
	{
		if (item == null)
		{
			return;
		}
		if (item.getWidget() != null && item.getWidget().isHidden())
		{
			return;
		}
		Rectangle rect = item.getCanvasBounds();
		if (rect == null)
		{
			return;
		}
		renderCentreBox(graphics, rect, color, boxSize);
	}

	public void renderCrosshair(Graphics2D graphics, Point point)
	{
		graphics.setColor(Color.GREEN);
		graphics.drawLine(point.getX(), point.getY(), point.getX(), point.getY());
	}

	//███╗   ███╗ ██████╗ ██╗   ██╗███████╗███████╗    ███████╗██╗   ██╗███████╗███╗   ██╗████████╗
	//████╗ ████║██╔═══██╗██║   ██║██╔════╝██╔════╝    ██╔════╝██║   ██║██╔════╝████╗  ██║╚══██╔══╝
	//██╔████╔██║██║   ██║██║   ██║███████╗█████╗      █████╗  ██║   ██║█████╗  ██╔██╗ ██║   ██║
	//██║╚██╔╝██║██║   ██║██║   ██║╚════██║██╔══╝      ██╔══╝  ╚██╗ ██╔╝██╔══╝  ██║╚██╗██║   ██║
	//██║ ╚═╝ ██║╚██████╔╝╚██████╔╝███████║███████╗    ███████╗ ╚████╔╝ ███████╗██║ ╚████║   ██║
	//╚═╝     ╚═╝ ╚═════╝  ╚═════╝ ╚══════╝╚══════╝    ╚══════╝  ╚═══╝  ╚══════╝╚═╝  ╚═══╝   ╚═╝

	@Getter(AccessLevel.PUBLIC)@Setter(AccessLevel.PUBLIC) public Point clickPoint;

	public void click(Rectangle rect)
	{
		assert !client.isClientThread();
		if (rect == null)
		{
			return;
		}
		Point point = getClickPoint(rect);
		click(point);
	}

	public Point getClickPoint(Rectangle rect)
	{
		if (rect == null)
		{
			return null;
		}
		int x = getRandomNumber((int) rect.getX(), (int) rect.getX() + (int) rect.getWidth());
		int y = getRandomNumber((int) rect.getY(), (int) rect.getY() + (int) rect.getHeight());
		return new Point(x, y);
	}

	public void click(Point p)
	{
		assert !client.isClientThread();

		setClickPoint(p);
		sendMessage("MOUSE: Click at x: " + p.getX() + ", y: " + p.getY());
		sendMouseEvent(MouseEvent.MOUSE_PRESSED, p);
		sendMouseEvent(MouseEvent.MOUSE_RELEASED, p);
		sendMouseEvent(MouseEvent.MOUSE_CLICKED, p);
	}

	private void sendMouseEvent(int id, @NotNull Point point)
	{
		MouseEvent e = new MouseEvent(
				client.getCanvas(), id,
				System.currentTimeMillis(),
				0, point.getX(), point.getY(),
				1, false, 1
		);
		client.getCanvas().dispatchEvent(e);
	}

	//███╗   ███╗███████╗███╗   ██╗██╗   ██╗     ██████╗ ██████╗ ████████╗██╗ ██████╗ ███╗   ██╗
	//████╗ ████║██╔════╝████╗  ██║██║   ██║    ██╔═══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
	//██╔████╔██║█████╗  ██╔██╗ ██║██║   ██║    ██║   ██║██████╔╝   ██║   ██║██║   ██║██╔██╗ ██║
	//██║╚██╔╝██║██╔══╝  ██║╚██╗██║██║   ██║    ██║   ██║██╔═══╝    ██║   ██║██║   ██║██║╚██╗██║
	//██║ ╚═╝ ██║███████╗██║ ╚████║╚██████╔╝    ╚██████╔╝██║        ██║   ██║╚██████╔╝██║ ╚████║
	//╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝ ╚═════╝      ╚═════╝ ╚═╝        ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝

	public MenuEntry combineItem(String item1Name, String item2Name)
	{
	if (!doesInventoryContain(item1Name) || !doesInventoryContain(item2Name))
	{
		return null;
	}
	WidgetItem item1 = getInventoryItem(item1Name);
	WidgetItem item2 = getInventoryItem(item2Name);
	if (item1 == null
			|| item1.getWidget().isHidden()
			|| item2 == null
			|| item2.getWidget().isHidden())
	{
		return null;
	}
	return combineItem(item1, item2);
	}

	public MenuEntry combineItem(int item1Id, int item2Id)
	{
		if (!doesInventoryContain(item1Id) || !doesInventoryContain(item2Id))
		{
			return null;
		}
		WidgetItem item1 = getInventoryItem(item1Id);
		WidgetItem item2 = getInventoryItem(item2Id);
		if (item1 == null
				|| item1.getWidget().isHidden()
				|| item2 == null
				|| item2.getWidget().isHidden())
		{
			return null;
		}
		return combineItem(item1, item2);
	}

	public MenuEntry combineItem(WidgetItem item1, WidgetItem item2)
	{
		setSelectedItem(WidgetInfo.INVENTORY, item1);
		return new MenuEntry("Use",
				"<col=ff9040>" + getItemName(item1) + "<col=ffffff> -> <col=ff9040>" + getItemName(item2),
				item2.getId(),
				MenuOpcode.ITEM_USE_ON_WIDGET_ITEM.getId(),
				item2.getIndex(),
				WidgetInfo.INVENTORY.getId(),
				false);
	}

	public void setSelectedItem(WidgetInfo widgetInfo, WidgetItem item)
	{
		if (widgetInfo == null || item == null || item.getWidget().isHidden())
		{
			return;
		}
		client.setSelectedItemWidget(widgetInfo.getId());
		client.setSelectedItemID(item.getId());
		client.setSelectedItemSlot(item.getIndex());
	}

	public MenuEntry selectItem(String itemName)
	{
		WidgetItem item = getInventoryItem(itemName);
		if (item == null)
		{
			return null;
		}
		return selectItem(item);
	}

	public MenuEntry selectItem(int id)
	{
		WidgetItem item = getInventoryItem(id);
		if (item == null)
		{
			return null;
		}
		return selectItem(item);
	}

	public MenuEntry selectItem(WidgetItem item)
	{
		return new MenuEntry("Use",
				"<col=ff9040>" + getItemName(item),
				item.getId(),
				MenuOpcode.ITEM_USE.getId(),
				item.getIndex(),
				WidgetInfo.INVENTORY.getId(),
				false);
	}

	public MenuEntry dropItem(String itemName)
	{
		WidgetItem item = getInventoryItem(itemName);
		if (item == null)
		{
			return null;
		}
		return dropItem(item);
	}

	public MenuEntry dropItem(int id)
	{
		WidgetItem item = getInventoryItem(id);
		if (item == null)
		{
			return null;
		}
		return selectItem(item);
	}

	public MenuEntry dropItem(WidgetItem item)
	{
		return new MenuEntry("Drop",
				"<col=ff9040>" + getItemName(item),
				item.getId(),
				MenuOpcode.ITEM_DROP.getId(),
				item.getIndex(),
				WidgetInfo.INVENTORY.getId(),
				false);
	}

	public MenuEntry interactWithGameObject(GameObject gameObject)
	{
		if (gameObject == null)
		{
			return null;
		}
		return setMenuEntry("", "",
				gameObject.getId(),
				MenuOpcode.GAME_OBJECT_FIRST_OPTION.getId(),
				gameObject.getSceneMinLocation().getX(),
				gameObject.getSceneMinLocation().getY(),
				false);
	}

	public MenuEntry interactWithNPC(NPC npc)
	{
		if (npc == null)
		{
			return null;
		}
		return setMenuEntry("", "",
				npc.getIndex(),
				MenuOpcode.NPC_FIRST_OPTION.getId(),
				0,
				0,
				false);
	}

	public MenuEntry tradeWithNPC(NPC npc)
	{
		if (npc == null)
		{
			return null;
		}
		return setMenuEntry("", "",
				npc.getIndex(),
				MenuOpcode.NPC_SECOND_OPTION.getId(),
				0,
				0,
				false);
	}

	public MenuEntry setMenuEntry(String o, String t, int id, int op, int p0, int p1, boolean f)
	{
		insertMenuAction(o, t, id, op, p0, p1, f);
		invokeMenuAction(o, t, id, op, p0, p1);
		return new MenuEntry(o, t, id, op, p0, p1, f);
	}

	public void invokeMenuAction(String o, String t, int id, int op, int p0, int p1)
	{
		client.invokeMenuAction(o, t, id, op, p0, p1);
	}

	public void insertMenuAction(String o, String t, int id, int op, int p0, int p1, boolean f)
	{
		client.insertMenuItem(o, t, id, op, p0, p1, f);
	}

	//██████╗██╗  ██╗ █████╗ ████████╗██████╗  ██████╗ ██╗  ██╗
	//██╔════╝██║  ██║██╔══██╗╚══██╔══╝██╔══██╗██╔═══██╗╚██╗██╔╝
	//██║     ███████║███████║   ██║   ██████╔╝██║   ██║ ╚███╔╝
	//██║     ██╔══██║██╔══██║   ██║   ██╔══██╗██║   ██║ ██╔██╗
	//╚██████╗██║  ██║██║  ██║   ██║   ██████╔╝╚██████╔╝██╔╝ ██╗
	//╚═════╝╚═╝  ╚═╝╚═╝   ╚═╝   ╚═╝   ╚═════╝  ╚═════╝ ╚═╝  ╚═╝

	public void sendMessage(String message)
	{
		if (client.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", ColorUtil.wrapWithColorTag("[" + getCurrentTime() + "] "+ message, Color.RED), "");
			System.out.println(message);
		}
	}

	//██████╗  ██████╗ ██╗   ██╗███╗   ██╗██████╗ ███████╗
	//██╔══██╗██╔═══██╗██║   ██║████╗  ██║██╔══██╗██╔════╝
	//██████╔╝██║   ██║██║   ██║██╔██╗ ██║██║  ██║███████╗
	//██╔══██╗██║   ██║██║   ██║██║╚██╗██║██║  ██║╚════██║
	//██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝███████║
	//╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝

	public Rectangle getSceneBounds()
	{
		return new Rectangle(
				client.getCanvas().getX() + 100,
				client.getCanvas().getY() + 100,
				client.getCanvasWidth() - 100,
				client.getCanvasHeight() - 100
		);
	}

	public Point getCentreRandomBounds(Rectangle rect, int i)
	{
		if (rect == null)
		{
			return null;
		}
		return new Point(
				(int) rect.getCenterX() + getRandomNumber(-i, i),
				(int) rect.getCenterY() + getRandomNumber(-i, i)
		);
	}

	public Rectangle getBounds(Rectangle rectangle)
	{
		if (rectangle == null)
		{
			return null;
		}
		return rectangle.getBounds();
	}

	public Shape getBounds(Shape shape)
	{
		if (shape == null)
		{
			return null;
		}
		return shape.getBounds();
	}

	public Rectangle getNPCBounds(Shape shape)
	{
		if (shape == null)
		{
			return null;
		}
		return shape.getBounds();
	}

	//██████╗ ███████╗███╗   ██╗██████╗ ███████╗██████╗ ██╗███╗   ██╗ ██████╗
	//██╔══██╗██╔════╝████╗  ██║██╔══██╗██╔════╝██╔══██╗██║████╗  ██║██╔════╝
	//██████╔╝█████╗  ██╔██╗ ██║██║  ██║█████╗  ██████╔╝██║██╔██╗ ██║██║  ███╗
	//██╔══██╗██╔══╝  ██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗██║██║╚██╗██║██║   ██║
	//██║  ██║███████╗██║ ╚████║██████╔╝███████╗██║  ██║██║██║ ╚████║╚██████╔╝
	//╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝

	public int[] getIndicatorLocation(String string)
	{
		int[] location = {0,0,5,5};
		if (string.isEmpty())
		{
			return location;
		}
		String[] parts = removeWhiteSpaces(string).split(":");
		for (int i = 0; i < 4; i++)
		{
			String part = removeCharactersFromString(parts[i]);
			if (part.isEmpty())
			{
				break;
			}
			location[i] = Integer.parseInt(part);
		}
		return location;
	}

	public void renderCentreBox(Graphics2D graphics, Rectangle bounds, Color color, int boxSize)
	{
		if (bounds == null)
		{
			return;
		}
		int x = (int) bounds.getCenterX() - boxSize / 2;
		int y = (int) bounds.getCenterY() - boxSize / 2;
		graphics.setColor(color);
		graphics.fillRect(x, y, boxSize, boxSize);
	}

	public void renderNPCCentreBox(Graphics2D graphics, NPC npc, Color color, int boxSize)
	{
		Shape shape = npc.getConvexHull();
		if (shape == null)
		{
			return;
		}
		int x = (int) shape.getBounds().getCenterX() - boxSize / 2;
		int y = (int) shape.getBounds().getCenterY() - boxSize / 2;
		graphics.setColor(color);
		graphics.fillRect(x, y, boxSize, boxSize);
	}

	public void renderCenterTileBox(Graphics2D graphics, WorldPoint worldPoint, Color color, int boxSize)
	{
		LocalPoint lp = LocalPoint.fromWorld(client, worldPoint);
		if (lp == null)
		{
			return;
		}
		Polygon polygon = Perspective.getCanvasTilePoly(client, lp);
		if (polygon == null)
		{
			return;
		}
		Rectangle bounds = polygon.getBounds();
		if (bounds == null)
		{
			return;
		}
		renderCentreBox(graphics, bounds, color, boxSize);
	}

	public void renderScreenIndicator(Graphics2D graphics, int x, int y, int width, int height, Color color)
	{
		if (color == null)
		{
			color = Color.RED;
		}
		graphics.setColor(color);
		graphics.fillRect(x, y, width, height);
	}







	public String removeCharactersFromString(String string)
	{
		return string.toLowerCase().replaceAll("\\D", "");
	}

	public String removeNumbersFromString(String string)
	{
		return string.toLowerCase().replaceAll("[0-9]", "");
	}

	public String removeWhiteSpaces(String string)
	{
		return string.toLowerCase().replaceAll("\\s+", "");
	}

	public int getRandomNumber(int min, int max)
	{
		return (int) ((Math.random() * ((max - min) + 1)) + min);
	}

	@Nullable
	public GameObject findNearestGameObject(int... ids)
	{
		return new GameObjectQuery()
				.idEquals(ids)
				.result(client)
				.nearestTo(client.getLocalPlayer());
	}

	@Nullable
	public WallObject findNearestWallObject(int... ids)
	{
		return new WallObjectQuery()
				.idEquals(ids)
				.result(client)
				.nearestTo(client.getLocalPlayer());
	}

	@Nullable
	public DecorativeObject findNearestDecorObject(int... ids)
	{
		return new DecorativeObjectQuery()
				.idEquals(ids)
				.result(client)
				.nearestTo(client.getLocalPlayer());
	}

	@Nullable
	public GroundObject findNearestGroundObject(int... ids)
	{
		return new GroundObjectQuery()
				.idEquals(ids)
				.result(client)
				.nearestTo(client.getLocalPlayer());
	}

	public List<GameObject> getGameObjects(int... ids)
	{
		return new GameObjectQuery()
				.idEquals(ids)
				.result(client)
				.list;
	}

	public List<WallObject> getWallObjects(int... ids)
	{
		return new WallObjectQuery()
				.idEquals(ids)
				.result(client)
				.list;
	}

	public List<DecorativeObject> getDecorObjects(int... ids)
	{
		return new DecorativeObjectQuery()
				.idEquals(ids)
				.result(client)
				.list;
	}

	public List<GroundObject> getGroundObjects(int... ids)
	{
		return new GroundObjectQuery()
				.idEquals(ids)
				.result(client)
				.list;
	}

	@Nullable
	public TileObject findNearestObject(int... ids)
	{
		GameObject gameObject = findNearestGameObject(ids);

		if (gameObject != null)
		{
			return gameObject;
		}

		WallObject wallObject = findNearestWallObject(ids);

		if (wallObject != null)
		{
			return wallObject;
		}
		DecorativeObject decorativeObject = findNearestDecorObject(ids);

		if (decorativeObject != null)
		{
			return decorativeObject;
		}

		return findNearestGroundObject(ids);
	}

	public boolean isPlayerWithinArea(Player player, int x1, int y2, int x2, int y1, int z)
	{
		WorldPoint playerLocation = player.getWorldLocation();
		return playerLocation.getX() >= x1
				&& playerLocation.getX() <= x2
				&& playerLocation.getY() >= y1
				&& playerLocation.getY() <= y2
				&& playerLocation.getPlane() == z;
	}

	public boolean isPlayerLocation(int playerLocation, boolean greaterThan, int i)
	{
		if (greaterThan)
		{
			return playerLocation >= i;
		}
		else
		{
			return playerLocation <= i;
		}
	}

	public boolean doesTileObjectExistAtLocation(TileObject t, WorldPoint c)
	{
		if (t == null)
		{
			return false;
		}
		WorldPoint tWP = t.getWorldLocation();
		if (tWP == null || c == null)
		{
			return false;
		}
		return tWP.equals(c);
	}

	public boolean isGameObjectWithinArea(GameObject gameObject, int x1, int y2, int x2, int y1, int z)
	{
		if (gameObject == null)
		{
			return false;
		}
		WorldPoint objectLocation = gameObject.getWorldLocation();
		return objectLocation.getX() >= x1
				&& objectLocation.getX() <= x2
				&& objectLocation.getY() >= y1
				&& objectLocation.getY() <= y2
				&& objectLocation.getPlane() == z;
	}

	public boolean isGroundObjectWithinArea(GroundObject groundObject, int x1, int y2, int x2, int y1, int z)
	{
		if (groundObject == null)
		{
			return false;
		}
		WorldPoint objectLocation = groundObject.getWorldLocation();
		return objectLocation.getX() >= x1
				&& objectLocation.getX() <= x2
				&& objectLocation.getY() >= y1
				&& objectLocation.getY() <= y2
				&& objectLocation.getPlane() == z;
	}

	public boolean isWallObjectWithinArea(WallObject wallObject, int x1, int y2, int x2, int y1, int z)
	{
		if (wallObject == null)
		{
			return false;
		}
		WorldPoint objectLocation = wallObject.getWorldLocation();
		return objectLocation.getX() >= x1
				&& objectLocation.getX() <= x2
				&& objectLocation.getY() >= y1
				&& objectLocation.getY() <= y2
				&& objectLocation.getPlane() == z;
	}

	public boolean isDecorativeObjectWithinArea(DecorativeObject decorativeObject, int x1, int y2, int x2, int y1, int z)
	{
		if (decorativeObject == null)
		{
			return false;
		}
		WorldPoint objectLocation = decorativeObject.getWorldLocation();
		return objectLocation.getX() >= x1
				&& objectLocation.getX() <= x2
				&& objectLocation.getY() >= y1
				&& objectLocation.getY() <= y2
				&& objectLocation.getPlane() == z;
	}

	public boolean isTileObjectWithinArea(TileObject tileObject, int x1, int y2, int x2, int y1, int z)
	{
		if (tileObject == null)
		{
			return false;
		}
		WorldPoint objectLocation = tileObject.getWorldLocation();
		return objectLocation.getX() >= x1
				&& objectLocation.getX() <= x2
				&& objectLocation.getY() >= y1
				&& objectLocation.getY() <= y2
				&& objectLocation.getPlane() == z;
	}

	//█████╗ ██╗   ██╗████████╗ ██████╗ ███╗   ███╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
	//██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗████╗ ████║██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
	//███████║██║   ██║   ██║   ██║   ██║██╔████╔██║███████║   ██║   ██║██║   ██║██╔██╗ ██║
	//██╔══██║██║   ██║   ██║   ██║   ██║██║╚██╔╝██║██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
	//██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║ ╚═╝ ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
	//╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝

	@Getter(AccessLevel.PUBLIC)@Setter(AccessLevel.PUBLIC) MenuEntry targetMenu;

	@Subscribe
	private void onMenuOptionClicked(MenuOptionClicked e)
	{
		if (getTargetMenu() == null)
		{
			return;
		}
		e.setMenuEntry(getTargetMenu());
		setTargetMenu(null);
	}

	@SneakyThrows
	public void dropAll(String... itemName)
	{
		assert !client.isClientThread();
		List<WidgetItem> itemList = getInventoryItemsMatching(itemName);
		for (WidgetItem item : itemList)
		{
			if (item == null || item.getWidget().isHidden())
			{
				return;
			}
			setTargetMenu(dropItem(item));
			click(getCentreRandomBounds(item.getCanvasBounds(), 20));
			Thread.sleep(getRandomNumber(300, 600));
		}
		Thread.sleep(getRandomNumber(300, 600));
	}

	@SneakyThrows
	public void dropAll(int... itemId)
	{
		assert !client.isClientThread();
		List<WidgetItem> itemList = getInventoryItemsMatching(itemId);
		for (WidgetItem item : itemList)
		{
			if (item == null || item.getWidget().isHidden())
			{
				return;
			}
			setTargetMenu(dropItem(item));
			click(getCentreRandomBounds(item.getCanvasBounds(), 20));
			Thread.sleep(getRandomNumber(300, 600));
		}
		Thread.sleep(getRandomNumber(300, 600));
	}

	@SneakyThrows
	public void dropAll()
	{
		assert !client.isClientThread();
		List<WidgetItem> itemList = getInventoryItems();
		for (WidgetItem item : itemList)
		{
			if (item == null || item.getWidget().isHidden())
			{
				return;
			}
			setTargetMenu(dropItem(item));
			click(getCentreRandomBounds(item.getCanvasBounds(), 20));
			Thread.sleep(getRandomNumber(300, 600));
		}
		Thread.sleep(getRandomNumber(300, 600));
	}

	@SneakyThrows
	public void dropAllExcept(String... itemName)
	{
		assert !client.isClientThread();
		List<WidgetItem> itemList = getInventoryItemsNotMatching(itemName);
		for (WidgetItem item : itemList)
		{
			if (item == null || item.getWidget().isHidden())
			{
				return;
			}
			setTargetMenu(dropItem(item));
			click(getCentreRandomBounds(item.getCanvasBounds(), 20));
			Thread.sleep(getRandomNumber(300, 600));
		}
		Thread.sleep(getRandomNumber(300, 600));
	}

	@SneakyThrows
	public void dropAllExcept(int... itemId)
	{
		assert !client.isClientThread();
		List<WidgetItem> itemList = getInventoryItemsNotMatching(itemId);
		for (WidgetItem item : itemList)
		{
			if (item == null || item.getWidget().isHidden())
			{
				return;
			}
			setTargetMenu(dropItem(item));
			click(getCentreRandomBounds(item.getCanvasBounds(), 20));
			Thread.sleep(getRandomNumber(300, 600));
		}
		Thread.sleep(getRandomNumber(300, 600));
	}

	@SneakyThrows
	public void sleep(int time)
	{
		assert !client.isClientThread();
		sendMessage("SLEEP: " + time + " milliseconds.");
		Thread.sleep(time);
	}

}
