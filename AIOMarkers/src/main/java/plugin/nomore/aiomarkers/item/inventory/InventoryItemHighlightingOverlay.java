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
package plugin.nomore.aiomarkers.item.inventory;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import plugin.nomore.aiomarkers.AIOConfig;
import plugin.nomore.aiomarkers.AIOPlugin;
import plugin.nomore.nmputils.api.RenderAPI;

import javax.inject.Inject;
import java.awt.*;

public class InventoryItemHighlightingOverlay extends Overlay
{

    @Inject
    private RenderAPI render;

    @Inject
    private AIOConfig config;

    @Inject
    private AIOPlugin plugin;

    @Inject
    private InventoryItemMethods inventoryItemMethods;

    @Inject
    private Client client;

    @Inject
    public InventoryItemHighlightingOverlay()
    {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (config.enableInventoryItemHighlighting())
        {
            renderObjects(graphics);
        }
        return null;
    }

    private void renderObjects(Graphics2D graphics)
    {
        inventoryItemMethods.getInventoryItemsToHighlightHashMap().forEach((widgetItem, color) ->
        {
            if (widgetItem != null)
            {
                if (widgetItem.getWidget().isHidden())
                {
                    return;
                }
                Shape shape = widgetItem.getCanvasBounds();
                if (shape == null)
                {
                    return;
                }
                switch (config.objectRenderStyle())
                {
                    case BOX:
                    {
                        render.renderCentreBox(graphics, shape.getBounds(), color, config.objectIndicatorSize());
                        if (config.objectDisplayMouseHoveringIndicator()
                                && render.isMouseHoveringOver(shape.getBounds(), client.getMouseCanvasPosition()))
                        {
                            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.objectMouseHoveringIndicatorLocation()), color);
                        }
                        break;
                    }
                    case CLICKBOX:
                    {
                        render.clickbox(graphics, client.getMouseCanvasPosition(), shape.getBounds(), color);
                        if (config.objectDisplayMouseHoveringIndicator()
                                && render.isMouseHoveringOver(shape.getBounds(), client.getMouseCanvasPosition()))
                        {
                            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.objectMouseHoveringIndicatorLocation()), color);
                        }
                        break;
                    }
                    case HULL:
                    {
                        render.hull(graphics, shape.getBounds(), color);
                        if (config.objectDisplayMouseHoveringIndicator()
                                && render.isMouseHoveringOver(shape.getBounds(), client.getMouseCanvasPosition()))
                        {
                            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.objectMouseHoveringIndicatorLocation()), color);
                        }
                        break;
                    }
                    case FILL:
                    {
                        render.fill(graphics, shape.getBounds(), color);
                        if (config.objectDisplayMouseHoveringIndicator()
                                && render.isMouseHoveringOver(shape.getBounds(), client.getMouseCanvasPosition()))
                        {
                            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.objectMouseHoveringIndicatorLocation()), color);
                        }
                        break;
                    }
                }
            }
        });
    }

}
