package plugin.nomore.grounditemsextended;

import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.*;
import plugin.nomore.grounditemsextended.builder.HighlightingObject;
import plugin.nomore.grounditemsextended.utils.Rendering;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;

public class GroundMarkersExtendedOverlay extends Overlay
{

    @Inject
    private Client client;

    @Inject
    private GroundMarkersExtendedPlugin plugin;

    @Inject
    private GroundMarkersExtendedConfig config;

    @Inject
    private Rendering render;

    @Inject
    public GroundMarkersExtendedOverlay()
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
        renderGroundItems(graphics, player);
        return null;
    }

    private void renderGroundItems(Graphics2D graphics, Player player) {

        List<HighlightingObject> inventoryItemsToHighlight = plugin.getGroundItemsToHighlight();
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
                    && !plugin.doesPlayerHaveALineOfSightToItem(player, item))
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
                    render.renderCentreBox(graphics, box.getBounds(), color, config.groundItemIndicatorSize());
                    showMouseOverlay(graphics, box, color);
                    break;
                case HULL:
                    Shape hull = Perspective.getClickbox(client, item.getModel(), 0, localPoint);
                    if (hull == null)
                    {
                        return;
                    }
                    render.hull(graphics, hull, color);
                    showMouseOverlay(graphics, hull, color);
                    break;
                case CLICKBOX:
                    Shape clickbox = Perspective.getClickbox(client, item.getModel(), 0, localPoint);
                    if (clickbox == null)
                    {
                        return;
                    }
                    render.clickbox(graphics, client.getMouseCanvasPosition(), clickbox, color);
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
