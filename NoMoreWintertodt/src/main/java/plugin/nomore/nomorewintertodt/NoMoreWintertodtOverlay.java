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
package plugin.nomore.nomorewintertodt;

import java.awt.*;
import java.util.Arrays;
import javax.inject.Inject;

import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

public class NoMoreWintertodtOverlay extends Overlay
{

	private final Client client;
	private final NoMoreWintertodtPlugin plugin;
	private final NoMoreWintertodtConfig config;

	@Inject
	NoMoreWintertodtOverlay(final Client client, final NoMoreWintertodtPlugin plugin, final NoMoreWintertodtConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	private final static int pyromancerAnimation = 7627;
	boolean isGameActive;
	boolean doesEastPyroNeedHelp;
	boolean doesWestPyroNeedHelp;

	@Override
	public Dimension render(Graphics2D graphics)
	{
		isGameActive = plugin.isMinigameActive();
		if (config.displayMinigameStatus())
		{
			graphics.setColor(getGameActiveColor());
			graphics.fillRect(0, 0, 5, 5);
		}

		if (isGameActive)
		{
			renderWidget(graphics);
		}
		wintertodtWidget();

		for (NPC npc : plugin.getPyromancerNPC())
		{
			if (npc.getWorldLocation().getPlane() == client.getPlane()
					&& Arrays.asList(npc.getComposition().getActions()).contains("Help"))
			{
				Shape npcShape = npc.getConvexHull();
				if (npcShape != null)
				{
					switch (config.locationSide())
					{
						case EAST:
							if (npc.getWorldLocation().getX() > 1630 && npc.getWorldLocation().getY() < 4000)
							{
								renderStyleChoice(graphics, npcShape, config.pyroColor(), config.pyroBoxSize());
								doesEastPyroNeedHelp = true;
							}
							else
							{
								doesEastPyroNeedHelp = false;
							}
							break;
						case WEAST:
							if (npc.getWorldLocation().getX() < 1630 && npc.getWorldLocation().getY() < 4000)
							{
								renderStyleChoice(graphics, npcShape, config.pyroColor(), config.pyroBoxSize());
								doesWestPyroNeedHelp = true;
							}
							else
							{
								doesWestPyroNeedHelp = false;
							}
							break;
					}
				}
			}
			else
			{
				if (npc.getWorldLocation().getX() > 1630 && npc.getWorldLocation().getY() < 4000)
				{
					doesEastPyroNeedHelp = false;
				}
				if (npc.getWorldLocation().getX() < 1630 && npc.getWorldLocation().getY() < 4000)
				{
					doesWestPyroNeedHelp = false;
				}
			}
		}

		plugin.getObjects().forEach(object -> {
			if (object != null
					&& object.getWorldLocation().getPlane() == client.getPlane())
			{
				Shape objectShape = object.getClickbox();
				if (objectShape != null)
				{
					switch (config.locationSide())
					{
						case EAST:
							if (!doesEastPyroNeedHelp && (object.getWorldLocation().getX() > 1630 && object.getWorldLocation().getY() < 4000))
								renderObjects(graphics, object, objectShape);
							break;
						case WEAST:
							if (!doesWestPyroNeedHelp && (object.getWorldLocation().getX() < 1630 && object.getWorldLocation().getY() < 4000))
								renderObjects(graphics, object, objectShape);
							break;
					}
				}
			}
		});

		return null;
	}

	public void wintertodtWidget()
	{
		Widget wintertodtWidget = client.getWidget(396,2);
		if (wintertodtWidget == null)
		{
			return;
		}
		switch (config.wintertotdHUD())
		{
			case VISIBLE:
				wintertodtWidget.setHidden(false);
				break;
			case HIDDEN:
				wintertodtWidget.setHidden(true);
				break;
			case MIXED:
				wintertodtWidget.setHidden(plugin.isMinigameActive());
				break;
		}
	}

	public void renderWidget(Graphics2D graphics)
	{
		Widget points = client.getWidget(396, 7);
		if (points == null)
		{
			return;
		}
		Color color = Color.RED.darker();
		int numberOfPoints = Integer.parseInt(points.getText().replaceAll("[\\D]", ""));
		if (numberOfPoints >= 500)
		{
			color = Color.GREEN.darker();
		}
		OverlayUtil.renderTextLocation(graphics, new Point(5, 30), String.valueOf(numberOfPoints), color);
	}

	public void renderObjects(Graphics2D graphics, TileObject object, Shape objectClickBox)
	{
		if (isGameActive)
		{
			if (object.getId() == ObjectID.BRUMA_ROOTS)
				renderStyleChoice(graphics, objectClickBox, config.brumaColor(), config.brumaBoxSize());
			if (object.getId() == ObjectID.BURNING_BRAZIER_29314)
				renderStyleChoice(graphics, objectClickBox, config.litBrazierColor(), config.litBrazierBoxSize());
			if (object.getId() == ObjectID.BRAZIER_29313)
				renderStyleChoice(graphics, objectClickBox, config.brokenBrazierColor(), config.brokenBrazierBoxSize());
		}
		if (object.getId() == ObjectID.BRAZIER_29312)
			renderStyleChoice(graphics, objectClickBox, config.unlitBrazierColor(), config.unlitBrazierBoxSize());
	}

	public void renderStyleChoice(Graphics2D graphics, Shape shapeClickBox, Color color, int boxSize)
	{
		switch (config.renderStyle())
		{
			case CLICKBOX:
				renderClickBox(graphics, shapeClickBox, color);
				break;
			case FILL:
				renderFilledClickBox(graphics, shapeClickBox, color);
				break;
			case BOX:
				renderBox(graphics, shapeClickBox, color, boxSize);
				break;
		}
	}

	public void renderClickBox(Graphics2D graphics, Shape shapeClickBox, Color color)
	{
		if (shapeClickBox != null)
		{
			OverlayUtil.renderPolygon(graphics, shapeClickBox, color);
		}
	}

	public void renderFilledClickBox(Graphics2D graphics, Shape shapeClickBox, Color color)
	{
		if (shapeClickBox != null)
		{
			OverlayUtil.renderPolygon(graphics, shapeClickBox, color);
		}
	}

	public void renderBox(Graphics2D graphics, Shape shapeClickBox, Color color, int boxSize)
	{
		if (shapeClickBox != null)
		{
			int x = (int) shapeClickBox.getBounds().getCenterX() - boxSize / 2;
			int y = (int) shapeClickBox.getBounds().getCenterY() - boxSize / 2;
			graphics.setColor(color);
			graphics.fillRect(x, y, boxSize, boxSize);
		}
	}

	public Color getGameActiveColor()
	{
		if (plugin.isMinigameActive())
		{
			return Color.GREEN;
		}
		else
		{
			return Color.RED;
		}
	}
}
