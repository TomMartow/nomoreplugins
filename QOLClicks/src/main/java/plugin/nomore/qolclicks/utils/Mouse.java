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
        if (client.isClientThread())
        {
            plugin.executor.submit(() ->
            {
                try
                {
                    log.info("Clicking at " + p.getX() + ", " + p.getY());
                    sendMouseEvent(501, p);
                    sleep.forXMillis(random.getIntBetween(60, 110));
                    sendMouseEvent(502, p);
                    sendMouseEvent(500, p);
                }
                catch (RuntimeException e)
                {
                    e.printStackTrace();
                }
            });
            return;
        }
        log.info("Clicking at " + p.getX() + ", " + p.getY());
        sendMouseEvent(501, p);
        sleep.forXMillis(random.getIntBetween(60, 110));
        sendMouseEvent(502, p);
        sendMouseEvent(500, p);
    }

    public void clickWithDelay(Point p, int delay)
    {
        plugin.executor.submit(() ->
        {
            try
            {
                QOLClicksPlugin.paused = true;
                sleep.forXMillis(delay);
                log.info("Clicking at " + p.getX() + ", " + p.getY());
                sendMouseEvent(501, p);
                sleep.forXMillis(random.getIntBetween(60, 110));
                sendMouseEvent(502, p);
                sendMouseEvent(500, p);
                QOLClicksPlugin.paused = false;
            }
            catch (RuntimeException e)
            {
                QOLClicksPlugin.paused = false;
                e.printStackTrace();
            }
        });
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