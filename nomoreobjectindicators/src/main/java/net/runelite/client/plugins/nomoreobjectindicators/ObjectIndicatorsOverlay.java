/*
 * Copyright (c) 2019, Owain van Brakel <https://github.com/Owain94>
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
package net.runelite.client.plugins.nomoreobjectindicators;

import net.runelite.api.*;
import net.runelite.client.graphics.ModelOutlineRenderer;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;

import static java.lang.Math.floor;

class ObjectIndicatorsOverlay extends Overlay
{
	private static final Color TRANSPARENT = new Color(0, 0, 0, 0);

	private final Client client;
	private final ObjectIndicatorsPlugin plugin;
	private final ObjectIndicatorsConfig config;
	private final ModelOutlineRenderer modelOutliner;

	@Inject
	private ObjectIndicatorsOverlay(final Client client, final ObjectIndicatorsPlugin plugin, final ObjectIndicatorsConfig config, final ModelOutlineRenderer modelOutliner)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		this.modelOutliner = modelOutliner;
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.LOW);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}//

	@Override
	public Dimension render(Graphics2D graphics)
	{
		for (ColorTileObject colorTileObject : plugin.getObjects())
		{
			TileObject object = colorTileObject.getTileObject();
			Color objectColor = colorTileObject.getColor();

			if (object.getPlane() != client.getPlane())
			{
				continue;
			}

			if (objectColor == null || !config.rememberObjectColors())
			{
				// Fallback to the current config if the object is marked before the addition of multiple colors
				Color color = config.objectMarkerColor();
				int opacity = (int) floor(config.objectMarkerAlpha() * 2.55);
				objectColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
			}

			switch (config.objectMarkerRenderStyle())
			{
				case OUTLINE:
					switch (config.objectMarkerOutlineRenderStyle())
					{
						case THIN_OUTLINE:
							modelOutliner.drawOutline(object, 1, objectColor);
							break;

						case NORMAL_OUTLINE:
							modelOutliner.drawOutline(object, 2, objectColor);
							break;

						case THIN_GLOW:
							modelOutliner.drawOutline(object, 4, objectColor, TRANSPARENT);
							break;

						case GLOW:
							modelOutliner.drawOutline(object, 8, objectColor, TRANSPARENT);
							break;
					}
					break;
				case HULL:
					final Shape polygon;
					Shape polygon2 = null;

					if (object instanceof GameObject)
					{
						polygon = ((GameObject) object).getConvexHull();
					}
					else if (object instanceof WallObject)
					{
						polygon = ((WallObject) object).getConvexHull();
						polygon2 = ((WallObject) object).getConvexHull2();
					}
					else if (object instanceof DecorativeObject)
					{
						polygon = ((DecorativeObject) object).getConvexHull();
						polygon2 = ((DecorativeObject) object).getConvexHull2();
					}
					else
					{
						polygon = object.getCanvasTilePoly();
					}

					if (polygon != null)
					{
						OverlayUtil.renderPolygon(graphics, polygon, objectColor);
					}

					if (polygon2 != null)
					{
						OverlayUtil.renderPolygon(graphics, polygon2, objectColor);
					}
					break;
				case CLICKBOX:
					Shape clickbox = object.getClickbox();
					if (clickbox != null)
					{
						OverlayUtil.renderHoverableArea(graphics, object.getClickbox(), client.getMouseCanvasPosition(), TRANSPARENT, objectColor, objectColor.darker());
					}
					break;
				case FILL:
					Shape fill = object.getClickbox();
					if (fill != null) {
						if (!(object.getId() == 11391 || object.getId() == 11390)) { // Objects not to mark.
							OverlayUtil.renderFilledPolygon(graphics, object.getClickbox(), objectColor);
							if (config.showMouseHover() && object.getClickbox().contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY())) {
								graphics.setColor(config.mouseHoverIndicatorColour());
								graphics.fillRect(config.mouseHoverX(), config.mouseHoverY(), config.mouseHoverIndicatorWidth(), config.mouseHoverIndicatorHeight());
							}
						}
					}
					break;
				case BOX:
					Shape box = object.getClickbox();
					if (box != null) {
						if (!(object.getId() == 11391 || object.getId() == 11390)) { // Objects not to mark.
							int x = (int) box.getBounds().getCenterX() - config.boxSize() / 2;
							int y = (int) box.getBounds().getCenterY() - config.boxSize() / 2;
							graphics.setColor(objectColor);
							graphics.fillRect(x, y, config.boxSize(), config.boxSize());
							if (config.showMouseHover() && object.getClickbox().contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY())) {
								graphics.setColor(config.mouseHoverIndicatorColour());
								graphics.fillRect(config.mouseHoverX(), config.mouseHoverY(), config.mouseHoverIndicatorWidth(), config.mouseHoverIndicatorHeight());
							}
						}
					}
					break;

			}
		}

		return null;
	}
}