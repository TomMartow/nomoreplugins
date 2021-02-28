package plugin.nomore.inventorytagsextended;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.*;
import plugin.nomore.inventorytagsextended.utils.Rendering;
import plugin.nomore.inventorytagsextended.builder.HighlightingObject;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;

public class InventoryTagsExtendedOverlay extends WidgetItemOverlay
{

    @Inject
    private Client client;

    @Inject
    private InventoryTagsExtendedPlugin plugin;

    @Inject
    private InventoryTagsExtendedConfig config;

    @Inject
    private Rendering render;

    @Inject
    public InventoryTagsExtendedOverlay()
    {
        showOnInventory();
    }

    @Override
    public void renderItemOverlay(Graphics2D graphics, int i, WidgetItem widgetItem)
    {
        renderInventoryItem(graphics);
    }

    private void renderInventoryItem(Graphics2D graphics)
    {
        List<HighlightingObject> inventoryItemsToHighlight = plugin.getInventoryItemsToHighlight();
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
                && item.getCanvasBounds().contains(new Point(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY())))
        {
            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.inventoryItemMouseHoveringIndicatorLocation()), color);
        }
    }

}
