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
package plugin.nomore.nmoneclicker.item.inventory;

import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.*;
import plugin.nomore.nmoneclicker.AIOConfig;
import plugin.nomore.nmoneclicker.AIOPlugin;
import plugin.nomore.nmoneclicker.item.inventory.builder.HighlightingObject;
import plugin.nomore.nmputils.api.RenderAPI;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;

public class InventoryItemHighlightingOverlay extends WidgetItemOverlay
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
        showOnInventory();
    }

    @Override
    protected void renderItemOverlay(Graphics2D graphics, int i, WidgetItem widgetItem)
    {
        if (config.enableInventoryItemHighlighting())
        {
            renderInventoryItem(graphics);
        }
    }

    private void renderInventoryItem(Graphics2D graphics)
    {
        List<HighlightingObject> inventoryItemsToHighlight = inventoryItemMethods.getInventoryItemsToHighlight();
        for (HighlightingObject highlightingObject : inventoryItemsToHighlight)
        {
            if (highlightingObject.getWidgetItem().getWidget().isHidden())
            {
                return;
            }

            WidgetItem item = highlightingObject.getWidgetItem();
            Color color = highlightingObject.getColor();
            if (item == null)
            {
                continue;
            }

            Rectangle bounds = item.getCanvasBounds();
            if (bounds == null)
            {
                return;
            }

            switch (config.inventoryItemRenderStyle())
            {
                case BOX:
                    render.renderCentreBox(graphics, bounds, color, config.inventoryItemIndicatorSize());
                    showMouseOverlay(graphics, item, color);
                    break;
                case HULL:
                    render.clickbox(graphics, client.getMouseCanvasPosition(), item.getCanvasBounds(), color);
                    showMouseOverlay(graphics, item, color);
                    break;
                case CLICKBOX:
                    render.hull(graphics, item.getCanvasBounds(), color);
                    showMouseOverlay(graphics, item, color);
                    break;
                case FILL:
                    render.fill(graphics, item.getCanvasBounds(), color);
                    showMouseOverlay(graphics, item, color);
                    break;
            }

        }
    }

    private void showMouseOverlay(Graphics2D graphics, WidgetItem item, Color color)
    {
        if (config.inventoryItemDisplayMouseHoveringIndicator()
                && item.getWidget().containsMouse())
        {
            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.inventoryItemMouseHoveringIndicatorLocation()), color);
        }
    }

}
