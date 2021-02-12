package plugin.nomore.qolclicks.utils;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Point;
import plugin.nomore.qolclicks.QOLClicksPlugin;

import javax.inject.Inject;
import java.awt.event.MouseEvent;

@Slf4j
public class Mouse
{

    @Inject
    Client client;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    Random random;

    @Inject
    Sleep sleep;

    public void clickInstant(Point p)
    {
        log.info("Clicking at " + p.getX() + ", " + p.getY());
        sendMouseEvent(501, p);
        sleep.forXMillis(random.getIntBetween(60, 110));
        sendMouseEvent(502, p);
        sendMouseEvent(500, p);
    }

    private void sendMouseEvent(int id, Point point)
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