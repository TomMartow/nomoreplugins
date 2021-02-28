package plugin.nomore.objectmarkersextended;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.TileObject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import plugin.nomore.objectmarkersextended.builder.HighlightingObject;
import plugin.nomore.objectmarkersextended.utils.Rendering;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;

public class ObjectMarkersExtendedOverlay extends Overlay
{

    @Inject
    private Client client;

    @Inject
    private ObjectMarkersExtendedPlugin plugin;

    @Inject
    private ObjectMarkersExtendedConfig config;

    @Inject
    private Rendering render;

    @Inject
    public ObjectMarkersExtendedOverlay()
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
        renderObjects(graphics, player);
        return null;
    }

    private void renderObjects(Graphics2D graphics, Player player)
    {
        List<HighlightingObject> objectsToHighlight = plugin.getObjectToHighlight();
        for (HighlightingObject highlightingObject : objectsToHighlight)
        {
            TileObject object = highlightingObject.getObject();
            Color color = highlightingObject.getColor();
            if (object == null)
            {
                continue;
            }

            if (player.getWorldLocation().getPlane() != highlightingObject.getPlane())
            {
                return;
            }

            if (config.objectLineOfSight()
                    && !plugin.doesPlayerHaveALineOfSightToObject(player, highlightingObject))
            {
                return;
            }

            switch (config.objectRenderStyle())
            {
                case BOX:
                    render.renderObjectCentreBox(graphics, object, color, config.objectIndicatorSize());
                    showMouseOverlay(graphics, object, color);
                    break;
                case HULL:
                    render.clickbox(graphics, client.getMouseCanvasPosition(), object.getClickbox(), color);
                    showMouseOverlay(graphics, object, color);
                    break;
                case CLICKBOX:
                    render.hull(graphics, object.getClickbox(), color);
                    showMouseOverlay(graphics, object, color);
                    break;
                case FILL:
                    render.fill(graphics, object.getClickbox(), color);
                    showMouseOverlay(graphics, object, color);
                    break;
            }

        }
    }

    private void showMouseOverlay(Graphics2D graphics, TileObject object, Color color)
    {
        if (config.objectDisplayMouseHoveringIndicator()
                && render.isMouseHoveringOver(object.getClickbox(), client.getMouseCanvasPosition()))
        {
            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.objectMouseHoveringIndicatorLocation()), color);
        }
    }

}
