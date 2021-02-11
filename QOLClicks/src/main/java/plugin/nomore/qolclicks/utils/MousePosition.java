package plugin.nomore.qolclicks.utils;

import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.widgets.WidgetItem;

import javax.inject.Inject;
import java.awt.*;

public class MousePosition
{

    @Inject
    Client client;

    @Inject
    Random random;

    public int getCentreX(WidgetItem item)
    {
        return (int) item.getCanvasBounds().getCenterX();
    }

    public int getCentreY(WidgetItem item)
    {
        return (int) item.getCanvasBounds().getCenterY();
    }

    public Point getRandomPointWithinBounds(Rectangle rect)
    {

        if (rect == null)
        {
            return null;
        }

        int x = random.getIntBetween((int) rect.getX(), (int) rect.getX() + (int) rect.getWidth());
        int y = random.getIntBetween((int) rect.getY(), (int) rect.getY() + (int) rect.getHeight());
        return new Point(x, y);
    }

    public Point getRandomPointFromCentre(Rectangle rect, int pixelSize)
    {

        if (rect == null)
        {
            return null;
        }

        int x1 = (int) rect.getCenterX() - pixelSize;
        int y1 = (int) rect.getCenterY() - pixelSize;
        int x2 = (int) rect.getCenterX() + pixelSize;
        int y2 = (int) rect.getCenterY() + pixelSize;

        return new Point(random.getIntBetween(x1, x2), random.getIntBetween(y1, y2));
    }

    public Point getClickPoint(Rectangle rect)
    {

        final int x = (int) (rect.getX() + random.getIntBetween((int) rect.getWidth() / 6 * -1, (int) rect.getWidth() / 6) + rect.getWidth() / 2);
        final int y = (int) (rect.getY() + random.getIntBetween((int) rect.getHeight() / 6 * -1, (int) rect.getHeight() / 6) + rect.getHeight() / 2);

        return new Point(x, y);
    }

    public Point getClientBounds()
    {
        int x = client.getCanvas().getX();
        int y = client.getCanvas().getY();
        int width = client.getCanvasWidth();
        int height = client.getCanvasHeight();
        return new Point(random.getIntBetween(x, x + width), random.getIntBetween(y, y + height));
    }

}