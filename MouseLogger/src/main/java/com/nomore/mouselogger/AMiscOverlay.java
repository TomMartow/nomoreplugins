package com.nomore.mouselogger;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;

public class AMiscOverlay extends Overlay {

    private final Client client;
    private final AMiscPlugin plugin;
    private final AMiscConfig config;

    @Inject
    AMiscOverlay(Client client, AMiscPlugin plugin, AMiscConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D g) {
        if (client.getGameState() == GameState.LOGGED_IN) {
            if (config.displayMarker()) {
                int cWidth = client.getCanvasWidth();
                int cHeight = client.getCanvasHeight();
                g.setColor(new Color(100, 200, 100, config.markerTransparency()));
                g.fillRect(cWidth - 204, cHeight - 311, 204, 275);
            }
        }
        return null;
    }
}
