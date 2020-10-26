package com.nomore.inventoryitemindicators;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;
import java.util.regex.Pattern;

public class SceneOverlay extends Overlay {

    private final Client client;
    private final IIPlugin plugin;
    private final IIConfig config;

    @Inject
    public SceneOverlay(Client client, IIPlugin plugin, IIConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (plugin.inventoryFull && config.displayFull())
        {
            renderG(graphics, config.fullColor(), config.fullLocation().split(Pattern.quote(":")));
        }

        if (plugin.inventoryContains && config.displayContain())
        {
            renderG(graphics, config.containColor(), config.containLocation().split(Pattern.quote(":")));
        }

        return null;
    }

    private void renderG(Graphics2D graphics, Color color, String[] s)
    {
        if (s == null)
        {
            return;
        }
        graphics.setColor(color);
        graphics.fillRect(getParsedInt(s,0),
                getParsedInt(s,1),
                getParsedInt(s,2),
                getParsedInt(s,3));
    }

    private int getParsedInt(String[] string, int number)
    {
        return Integer.parseInt(string[number]);
    }
}
