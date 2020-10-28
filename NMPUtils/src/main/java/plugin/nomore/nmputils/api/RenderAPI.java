package plugin.nomore.nmputils.api;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.WidgetItem;

import javax.inject.Inject;
import java.awt.*;

public class RenderAPI
{

    @Inject
    private Client client;

    @Inject
    private StringAPI string;

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
        renderScreenIndicator(graphics, x, y, w, h, color);
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
        String[] parts = this.string.removeWhiteSpaces(string).split(":");
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

    public void renderScreenIndicator(Graphics2D graphics, int x, int y, int width, int height, Color color)
    {
        if (color == null)
        {
            color = Color.RED;
        }
        graphics.setColor(color);
        graphics.fillRect(x, y, width, height);
    }

}