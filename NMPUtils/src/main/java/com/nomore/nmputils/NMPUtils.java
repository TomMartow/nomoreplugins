/*
 * Copyright (c) 2019-2020, ganom <https://github.com/Ganom>
 * All rights reserved.
 * Licensed under GPL3, see LICENSE for the full scope.
 */
package com.nomore.nmputils;

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
public class NMPUtils extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private NMPConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ConfigManager configManager;

	@Provides
	NMPConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(NMPConfig.class);
	}

	@Override
	protected void startUp() {
	}

	@Override
	protected void shutDown() {
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

}
