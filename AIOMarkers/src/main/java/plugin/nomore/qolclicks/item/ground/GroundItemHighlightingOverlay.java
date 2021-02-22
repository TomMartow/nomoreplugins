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
package plugin.nomore.qolclicks.item.ground;

import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.*;
import plugin.nomore.qolclicks.item.ground.builder.HighlightingObject;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;

public class GroundItemHighlightingOverlay extends Overlay {

    @Inject
    private Rendering render;

    @Inject
    private GroundItem config;

    @Inject
    private AIOPlugin plugin;

    @Inject
    private GroundItemMethods groundItemMethods;

    @Inject
    private Client client;

    @Inject
    public GroundItemHighlightingOverlay()
    {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Player player = client.getLocalPlayer();
        if (player == null)
        {
            return null;
        }
        if (config.enableGroundItemHighlighting())
        {
            renderGroundItems(graphics, player);
        }
        return null;
    }

    private void renderGroundItems(Graphics2D graphics, Player player) {

        List<HighlightingObject> inventoryItemsToHighlight = groundItemMethods.getGroundItemsToHighlight();
        for (HighlightingObject highlightingObject : inventoryItemsToHighlight)
        {

            TileItem item = highlightingObject.getTileItem();
            Tile tile = highlightingObject.getTile();
            Color color = highlightingObject.getColor();
            if (item == null || tile == null)
            {
                continue;
            }

            if (player.getWorldLocation().getPlane() != highlightingObject.getPlane())
            {
                return;
            }

            if (config.groundItemLineOfSight()
                    && !groundItemMethods.doesPlayerHaveALineOfSightToItem(player, item))
            {
                return;
            }

            LocalPoint localPoint = LocalPoint.fromWorld(client, tile.getWorldLocation());
            if (localPoint == null)
            {
                return;
            }

            switch (config.groundItemRenderStyle())
            {
                case BOX:
                    Shape box = Perspective.getClickbox(client, item.getModel(), 0, localPoint);
                    if (box == null)
                    {
                        return;
                    }
                    render.renderCentreBox(graphics, box.getBounds(), color, config.inventoryItemIndicatorSize());
                    showMouseOverlay(graphics, box, color);
                    break;
                case HULL:
                    Shape hull = Perspective.getClickbox(client, item.getModel(), 0, localPoint);
                    if (hull == null)
                    {
                        return;
                    }
                    render.clickbox(graphics, client.getMouseCanvasPosition(), hull, color);
                    showMouseOverlay(graphics, hull, color);
                    break;
                case CLICKBOX:
                    Shape clickbox = Perspective.getClickbox(client, item.getModel(), 0, localPoint);
                    if (clickbox == null)
                    {
                        return;
                    }
                    render.hull(graphics, clickbox, color);
                    showMouseOverlay(graphics, clickbox, color);
                    break;
                case FILL:
                    Shape fill = Perspective.getClickbox(client, item.getModel(), 0, localPoint);
                    if (fill == null)
                    {
                        return;
                    }
                    render.fill(graphics, fill, color);
                    showMouseOverlay(graphics, fill, color);
                    break;
                case TILE_OUTLINE:
                    Polygon tileOutline = Perspective.getCanvasTilePoly(client, localPoint, 0);
                    if (tileOutline == null)
                    {
                        return;
                    }
                    OverlayUtil.renderOutlinePolygon(graphics, tileOutline, color);
                    showMouseOverlay(graphics, tileOutline, color);
                    break;
                case TILE_FILLED:
                    Polygon tileFilled = Perspective.getCanvasTilePoly(client, localPoint, 0);
                    if (tileFilled == null)
                    {
                        return;
                    }
                    OverlayUtil.renderFilledPolygon(graphics, tileFilled, color);
                    showMouseOverlay(graphics, tileFilled, color);
                    break;
            }
        }
    }

    private void showMouseOverlay(Graphics2D graphics, Shape shape, Color color)
    {
        if (config.groundItemDisplayMouseHoveringIndicator()
                && render.isMouseHoveringOver(shape, client.getMouseCanvasPosition()))
        {
            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.groundItemMouseHoveringIndicatorLocation()), color);
        }
    }

    private void showMouseOverlay(Graphics2D graphics, Polygon poly, Color color)
    {
        if (config.groundItemDisplayMouseHoveringIndicator()
                && render.isMouseHoveringOver(poly, client.getMouseCanvasPosition()))
        {
            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.groundItemMouseHoveringIndicatorLocation()), color);
        }
    }
}