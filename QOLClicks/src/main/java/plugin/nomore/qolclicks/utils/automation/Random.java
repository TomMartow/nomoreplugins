package plugin.nomore.qolclicks.utils.automation;

import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.widgets.WidgetItem;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Random
{

    @Inject
    Client client;

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

        int x = ThreadLocalRandom.current().nextInt((int) rect.getX(), (int) rect.getX() + (int) rect.getWidth());
        int y = ThreadLocalRandom.current().nextInt((int) rect.getY(), (int) rect.getY() + (int) rect.getHeight());
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

        return new Point(ThreadLocalRandom.current().nextInt(x1, x2), ThreadLocalRandom.current().nextInt(y1, y2));
    }

    public Point getClickPoint(Rectangle rect)
    {

        final int x = (int) (rect.getX() + ThreadLocalRandom.current().nextInt((int) rect.getWidth() / 6 * -1, (int) rect.getWidth() / 6) + rect.getWidth() / 2);
        final int y = (int) (rect.getY() + ThreadLocalRandom.current().nextInt((int) rect.getHeight() / 6 * -1, (int) rect.getHeight() / 6) + rect.getHeight() / 2);

        return new Point(x, y);
    }

    public Point getClientBounds()
    {
        int x = client.getCanvas().getX();
        int y = client.getCanvas().getY();
        int width = client.getCanvasWidth();
        int height = client.getCanvasHeight();
        return new Point(ThreadLocalRandom.current().nextInt(x, x + width), ThreadLocalRandom.current().nextInt(y, y + height));
    }

    public Point getRandomPoint(@NotNull Rectangle rect)
    {
        final int x = getRandomIntBetweenRange((int) rect.getX(), (int) rect.getX() + (int) rect.getWidth());
        final int y = getRandomIntBetweenRange((int) rect.getY(), (int) rect.getY() + (int) rect.getHeight());

        return new Point(x, y);
    }

    public int getRandomIntBetweenRange(int min, int max)
    {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

}