package plugin.nomore.playerstatesindicators.utils;

import net.runelite.api.Point;
import net.runelite.client.ui.overlay.OverlayUtil;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

public class Utils
{

    public void hull(Graphics2D graphics, Shape convexHull, Color color)
    {
        if (convexHull == null)
        {
            return;
        }
        graphics.setColor(color);
        graphics.draw(convexHull);
    }

    public void clickbox(Graphics2D graphics, net.runelite.api.Point mousePos, Shape convexHull, Color color)
    {
        if (convexHull == null)
        {
            return;
        }
        OverlayUtil.renderClickBox(graphics, mousePos, convexHull, color);
    }

    public void outline(Graphics2D graphics, Shape bounds, Color color)
    {
        if (bounds == null)
        {
            return;
        }
        OverlayUtil.renderOutlinePolygon(graphics, bounds, color);
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

    public boolean isMouseHoveringOver(Shape shape, net.runelite.api.Point mouseCanvasPosition)
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
        String[] parts = rws(string).split(":");
        for (int i = 0; i < 4; i++)
        {
            String part = removeCharactersFromString(parts[i]);
            if (part.isEmpty())
            {
                break;
            }
            location[i] = Integer.parseInt(part);
        }
        return location;
    }

    public String removeCharactersFromString(String string)
    {
        return string.toLowerCase().replaceAll("\\D", "");
    }

    public String removeNumbersFromString(String string)
    {
        return string.toLowerCase().replaceAll("[0-9]", "");
    }

    public String rws(String string)
    {
        return string.toLowerCase().replaceAll("\\s+", "");
    }

    public boolean containsNumbers(String string) { return StringUtils.containsAny(string, "[0-9]+"); }

}
