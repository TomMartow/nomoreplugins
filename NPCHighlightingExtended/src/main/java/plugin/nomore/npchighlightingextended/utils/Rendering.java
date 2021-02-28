package plugin.nomore.npchighlightingextended.utils;

import net.runelite.api.Point;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.*;

public class Rendering
{

    @Inject
    private Client client;

    @Inject
    private StringFormat string;

    // New

    public void hull(Graphics2D graphics, Shape convexHull, Color color)
    {
        if (convexHull == null)
        {
            return;
        }
        graphics.setColor(color);
        graphics.draw(convexHull);
    }

    public void clickbox(Graphics2D graphics, Point mousePos, Shape convexHull, Color color)
    {
        if (convexHull == null)
        {
            return;
        }
        OverlayUtil.renderHoverableArea(graphics, convexHull, mousePos, color, color.darker(), color.brighter());


    }

    public void outline(Graphics2D graphics, Shape bounds, Point mousePos, Color color)
    {
        if (bounds == null)
        {
            return;
        }
        OverlayUtil.renderHoverableArea(graphics, bounds, mousePos, null, color, null);
    }

    public void fill(Graphics2D graphics, Shape convexHull, Color color)
    {
        if (convexHull == null)
        {
            return;
        }
        graphics.setColor(color);
        graphics.fill(convexHull);
    }

    public boolean isMouseHoveringOver(Shape shape, Point mouseCanvasPosition)
    {
        if (shape == null)
        {
            return false;
        }
        return shape.contains(mouseCanvasPosition.getX(), mouseCanvasPosition.getY());
    }

    public boolean isMouseHoveringOver(Rectangle rect, Point mouseCanvasPosition)
    {
        if (rect == null)
        {
            return false;
        }
        return rect.contains(mouseCanvasPosition.getX(), mouseCanvasPosition.getY());
    }

    public void canvasIndicator(Graphics2D graphics, int[] indicatorLocation, Color color)
    {
        if (color == null)
        {
            color = Color.RED;
        }
        graphics.setColor(color);
        graphics.fillRect(indicatorLocation[0],
                indicatorLocation[1],
                indicatorLocation[2],
                indicatorLocation[3]);
    }

    public void canvasIndicator(Graphics2D graphics, int x, int y, int width, int height, Color color)
    {
        if (color == null)
        {
            color = Color.RED;
        }
        graphics.setColor(color);
        graphics.fillRect(x, y, width, height);
    }

    public int[] getCanvasIndicatorLocation(String string)
    {
        int[] location = {0,0,5,5};
        if (string.isEmpty())
        {
            return location;
        }
        String[] parts = this.string.rws(string).split(":");
        for (int i = 0; i < 4; i++)
        {
            String part = this.string.removeCharactersFromString(parts[i]);
            if (part.isEmpty())
            {
                break;
            }
            location[i] = Integer.parseInt(part);
        }
        return location;
    }

    // old

    public void renderWidgetItem(Graphics2D graphics, WidgetItem item, Color color)
    {
        if (item == null)
        {
            return;
        }
        if (item.getWidget() != null && item.getWidget().isHidden())
        {
            return;
        }
        Rectangle rect = item.getCanvasBounds();
        if (rect == null)
        {
            return;
        }
        int x = (int) rect.getX();
        int y = (int) rect.getY();
        int w = (int) rect.getWidth();
        int h = (int) rect.getHeight();
        canvasIndicator(graphics, x, y, w, h, color);
    }

    public void renderWidgetItemCentreBox(Graphics2D graphics, WidgetItem item, int boxSize, Color color)
    {
        if (item == null)
        {
            return;
        }
        if (item.getWidget() != null && item.getWidget().isHidden())
        {
            return;
        }
        Rectangle rect = item.getCanvasBounds();
        if (rect == null)
        {
            return;
        }
        renderCentreBox(graphics, rect, color, boxSize);
    }

    public int[] getIndicatorLocation(String string)
    {
        int[] location = {0,0,5,5};
        if (string.isEmpty())
        {
            return location;
        }
        String[] parts = this.string.rws(string).split(":");
        for (int i = 0; i < 4; i++)
        {
            String part = this.string.removeCharactersFromString(parts[i]);
            if (part.isEmpty())
            {
                break;
            }
            location[i] = Integer.parseInt(part);
        }
        return location;
    }

    public void centreTileBox(Graphics2D graphics, WorldPoint worldPoint, Color color, int boxSize)
    {
        LocalPoint lp = LocalPoint.fromWorld(client, worldPoint);
        if (lp == null)
        {
            return;
        }
        Polygon polygon = Perspective.getCanvasTilePoly(client, lp);
        if (polygon == null)
        {
            return;
        }
        Rectangle bounds = polygon.getBounds();
        if (bounds == null)
        {
            return;
        }
        renderCentreBox(graphics, bounds, color, boxSize);
    }

    public void renderCentreBox(Graphics2D graphics, Rectangle bounds, Color color, int boxSize)
    {
        if (bounds == null)
        {
            return;
        }
        int x = (int) bounds.getCenterX() - boxSize / 2;
        int y = (int) bounds.getCenterY() - boxSize / 2;
        graphics.setColor(color);
        graphics.fillRect(x, y, boxSize, boxSize);
    }

    public void renderNPCCentreBox(Graphics2D graphics, NPC npc, Color color, int boxSize)
    {
        Shape shape = npc.getConvexHull();
        if (shape == null)
        {
            return;
        }
        int x = (int) shape.getBounds().getCenterX() - boxSize / 2;
        int y = (int) shape.getBounds().getCenterY() - boxSize / 2;
        graphics.setColor(color);
        graphics.fillRect(x, y, boxSize, boxSize);
    }

    public void renderObjectCentreBox(Graphics2D graphics, TileObject object, Color color, int boxSize)
    {
        Shape shape = object.getClickbox();
        if (shape == null)
        {
            return;
        }
        int x = (int) shape.getBounds().getCenterX() - boxSize / 2;
        int y = (int) shape.getBounds().getCenterY() - boxSize / 2;
        graphics.setColor(color);
        graphics.fillRect(x, y, boxSize, boxSize);
    }

    public void renderCenterTileBox(Graphics2D graphics, WorldPoint worldPoint, Color color, int boxSize)
    {
        LocalPoint lp = LocalPoint.fromWorld(client, worldPoint);
        if (lp == null)
        {
            return;
        }
        Polygon polygon = Perspective.getCanvasTilePoly(client, lp);
        if (polygon == null)
        {
            return;
        }
        Rectangle bounds = polygon.getBounds();
        if (bounds == null)
        {
            return;
        }
        renderCentreBox(graphics, bounds, color, boxSize);
    }
}
