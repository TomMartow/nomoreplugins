package plugin.nomore.qolclicks.utils.automation;

import net.runelite.api.Client;
import net.runelite.api.Point;
import org.jetbrains.annotations.NotNull;
import plugin.nomore.qolclicks.QOLClicksPlugin;

import javax.inject.Inject;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Mouse
{

    @Inject private Client client;
    @Inject private QOLClicksPlugin plugin;
    @Inject private Random random;

    public void clickC(Rectangle rectangle)
    {
        assert !client.isClientThread();
        Point point = random.getClickPoint(rectangle);
        click(point);
    }

    public void clickR(Rectangle rectangle)
    {
        assert !client.isClientThread();
        Point point = random.getRandomPoint(rectangle);
        click(point);
    }

    public void click(Point p)
    {
        assert !client.isClientThread();

        if (client.isStretchedEnabled())
        {
            final Dimension stretched = client.getStretchedDimensions();
            final Dimension real = client.getRealDimensions();
            final double width = (stretched.width / real.getWidth());
            final double height = (stretched.height / real.getHeight());
            final net.runelite.api.Point point = new Point((int) (p.getX() * width), (int) (p.getY() * height));
            mouseEvent(501, point);
            mouseEvent(502, point);
            mouseEvent(500, point);
            return;
        }
        plugin.setPointClicked(p);
        mouseEvent(501, p);
        mouseEvent(502, p);
        mouseEvent(500, p);
    }

    private void mouseEvent(int id, @NotNull Point point)
    {
        MouseEvent e = new MouseEvent(
                client.getCanvas(), id,
                System.currentTimeMillis(),
                0, point.getX(), point.getY(),
                1, false, 1
        );

        client.getCanvas().dispatchEvent(e);
    }

}
