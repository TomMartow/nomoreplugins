package net.runelite.client.plugins.nmutils;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.*;

public class UtilsOverlay extends Overlay {

    @Inject
    private Client client;

    @Inject
    private Utils utils;

    @Inject
    public UtilsOverlay(Client client) {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (utils.getClickPoint() != null)
        {
            graphics.setColor(Color.magenta);
            graphics.drawLine(
                    utils.getClickPoint().getX() - 15,
                    utils.getClickPoint().getY() + 15,
                    utils.getClickPoint().getX() + 15,
                    utils.getClickPoint().getY() - 15); //line 1
            graphics.drawLine(
                    utils.getClickPoint().getX() + 15,
                    utils.getClickPoint().getY() + 15,
                    utils.getClickPoint().getX() - 15,
                    utils.getClickPoint().getY() - 15); //line 2
        }
        return null;
    }
}
