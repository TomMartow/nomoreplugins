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
package plugin.nomore.aiomarkers.item.ground;

import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.TileItem;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.*;
import plugin.nomore.aiomarkers.AIOConfig;
import plugin.nomore.aiomarkers.AIOPlugin;
import plugin.nomore.nmputils.api.RenderAPI;

import javax.inject.Inject;
import java.awt.*;

public class GroundItemHighlightingOverlay extends Overlay
{

    @Inject
    private RenderAPI render;

    @Inject
    private AIOConfig config;

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

    private void renderGroundItems(Graphics2D graphics, Player player)
    {
        groundItemMethods.getGroundItemsToHighlightHashMap().forEach((groundItemInfo, color) ->
        {
            TileItem item = groundItemInfo.getTileItem();
            int itemPlane = groundItemInfo.getPlane();
            if (item != null
                    && !config.groundItemLineOfSight()
                    || config.groundItemLineOfSight() && groundItemMethods.doesPlayerHaveALineOfSightToGroundItem(player, item))
            {
                assert item != null;
                if (itemPlane != player.getWorldLocation().getPlane())
                {
                    return;
                }
                LocalPoint localPoint = LocalPoint.fromWorld(client, item.getTile().getWorldLocation());
                if (localPoint == null)
                {
                    return;
                }
                switch (config.groundItemRenderStyle())
                {
                    case BOX:
                    {
                        final Polygon tile = Perspective.getCanvasTilePoly(client, localPoint, item.getModelHeight() / 2);
                        if (tile == null)
                        {
                            return;
                        }
                        render.renderCentreBox(graphics, tile.getBounds(), color, config.groundItemIndicatorSize());
                        if (config.groundItemDisplayMouseHoveringIndicator()
                                && render.isMouseHoveringOver(tile, client.getMouseCanvasPosition()))
                        {
                            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.groundItemMouseHoveringIndicatorLocation()), color);
                        }
                        break;
                    }
                    case CLICKBOX:
                    {
                        Shape clickbox = Perspective.getClickbox(client, item.getModel(), 0, localPoint);
                        if (clickbox == null)
                        {
                            return;
                        }
                        render.clickbox(graphics, client.getMouseCanvasPosition(), clickbox, color);
                        if (config.groundItemDisplayMouseHoveringIndicator()
                                && render.isMouseHoveringOver(clickbox, client.getMouseCanvasPosition()))
                        {
                            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.groundItemMouseHoveringIndicatorLocation()), color);
                        }
                        break;
                    }
                    case HULL:
                    {
                        Shape clickbox = Perspective.getClickbox(client, item.getModel(), 0, localPoint);
                        if (clickbox == null)
                        {
                            return;
                        }
                        render.hull(graphics, clickbox, color);
                        if (config.groundItemDisplayMouseHoveringIndicator()
                                && render.isMouseHoveringOver(clickbox, client.getMouseCanvasPosition()))
                        {
                            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.groundItemMouseHoveringIndicatorLocation()), color);
                        }
                        break;
                    }
                    case FILL:
                    {
                        Shape clickbox = Perspective.getClickbox(client, item.getModel(), 0, localPoint);
                        if (clickbox == null)
                        {
                            return;
                        }
                        render.fill(graphics, clickbox, color);
                        if (config.groundItemDisplayMouseHoveringIndicator()
                                && render.isMouseHoveringOver(clickbox, client.getMouseCanvasPosition()))
                        {
                            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.groundItemMouseHoveringIndicatorLocation()), color);
                        }
                        break;
                    }
                    case TILE_OUTLINE:
                    {
                        final Polygon tile = Perspective.getCanvasTilePoly(client, localPoint, -1);
                        if (tile == null)
                        {
                            return;
                        }
                        render.outline(graphics, tile, color);
                        if (config.groundItemDisplayMouseHoveringIndicator()
                                && render.isMouseHoveringOver(tile, client.getMouseCanvasPosition()))
                        {
                            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.groundItemMouseHoveringIndicatorLocation()), color);
                        }
                        break;
                    }
                    case TILE_FILLED:
                    {
                        final Polygon tile = Perspective.getCanvasTilePoly(client, localPoint, -1);
                        if (tile == null)
                        {
                            return;
                        }
                        render.fill(graphics, tile, color);
                        if (config.groundItemDisplayMouseHoveringIndicator()
                                && render.isMouseHoveringOver(tile, client.getMouseCanvasPosition()))
                        {
                            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.groundItemMouseHoveringIndicatorLocation()), color);
                        }
                        break;
                    }
                }
            }
        });
    }

}
