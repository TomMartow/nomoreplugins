/*
 * Copyright (c) 2018 kulers
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
package net.runelite.client.plugins.nomoreinventorytags;

import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.ui.overlay.infobox.Timer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InventoryTagsOverlay extends WidgetItemOverlay {
	private final ItemManager itemManager;
	private final InventoryTagsPlugin plugin;
	private final InventoryTagsConfig config;
	private final Client client;

	private HashMap<Point, Timer> timerHashMap = new HashMap<>();

	@Inject
	private InventoryTagsOverlay(final ItemManager itemManager, final InventoryTagsPlugin plugin, final InventoryTagsConfig config, Client client) {
		this.itemManager = itemManager;
		this.plugin = plugin;
		this.config = config;
		this.client = client;
		showOnEquipment();
		showOnInventory();
	}

	@Override
	public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem itemWidget) {

		if (config.showMouseHoveringOverInventoryIcon()
				&& itemWidget.getCanvasBounds().contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY())) {
			graphics.setColor(config.mouseHoverIndicatorColour());
			graphics.fillRect(config.mouseHoverX(), config.mouseHoverY(), config.mouseHoverIndicatorWidth(), config.mouseHoverIndicatorHeight());
		}

		String group = plugin.getTag(itemId);
		if (group != null) {
			Color color = plugin.getGroupNameColor(group);
			if (color != null) {
				Rectangle bounds = itemWidget.getCanvasBounds();
				switch (config.renderStyle()) {
					case OFF:
						break;
					case OUTLINE:
						if (config.hoverDelay() > 0 && bounds.contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY())) {
							timerHashMap.put(itemWidget.getCanvasLocation(), new Timer(config.hoverDelay(), ChronoUnit.MILLIS, new BufferedImage(1,1,1), plugin));
						}
						if (timerHashMap.containsKey(itemWidget.getCanvasLocation())) {
							Timer timer = timerHashMap.get(itemWidget.getCanvasLocation());
							Duration timeLeft = Duration.between(Instant.now(), timer.getEndTime());
							if (timeLeft.isNegative()) {
								timerHashMap.remove(itemWidget.getCanvasLocation());
							}
						}
						else {
							BufferedImage outline = itemManager.getItemOutline(itemId, itemWidget.getQuantity(), color);
							graphics.drawImage(outline, (int) bounds.getX(), (int) bounds.getY(), null);
						}
						break;
					case BOX:
						int boxSize = config.boxSize();
						if (config.hoverDelay() > 0 && bounds.contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY())) {
							timerHashMap.put(itemWidget.getCanvasLocation(), new Timer(config.hoverDelay(), ChronoUnit.MILLIS, new BufferedImage(1,1,1), plugin));
						}
						if (timerHashMap.containsKey(itemWidget.getCanvasLocation())) {
							Timer timer = timerHashMap.get(itemWidget.getCanvasLocation());
							Duration timeLeft = Duration.between(Instant.now(), timer.getEndTime());
							if (timeLeft.isNegative()) {
								timerHashMap.remove(itemWidget.getCanvasLocation());
							}
						}
						else {
							int x = (int) bounds.getCenterX() - boxSize / 2;
							int y = (int) bounds.getCenterY() - boxSize / 2;
							graphics.setColor(color);
							graphics.fillRect(x, y, boxSize, boxSize);
						}
						break;
				}
			}
		}
	}
}