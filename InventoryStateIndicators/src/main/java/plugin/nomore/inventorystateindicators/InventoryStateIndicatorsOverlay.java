package plugin.nomore.inventorystateindicators;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import plugin.nomore.inventorystateindicators.utils.Utils;

import javax.inject.Inject;
import java.awt.*;

public class InventoryStateIndicatorsOverlay extends Overlay
{

    @Inject
    private Client client;

    @Inject
    private InventoryStateIndicatorsPlugin plugin;

    @Inject
    private InventoryStateIndicatorsConfig config;

    @Inject
    private Utils utils;

    @Inject
    public InventoryStateIndicatorsOverlay() {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {

        if (config.enableInventoryFullIndicator())
        {
            if (plugin.isInventoryFull())
            {
                displayInventoryFullIndicator(graphics);
            }
        }

        if (config.enableInventoryContainsIndicator())
        {
            if (plugin.isInventoryContains())
            {
                displayInventoryContainsIndicator(graphics);
            }
        }

        if (config.enableInventoryDoesNotContainsIndicator())
        {
            if (plugin.isInventoryDoesNotContain())
            {
                displayInventoryDoesNotContainsIndicator(graphics);
            }
        }
        return null;
    }

    private void displayInventoryFullIndicator(Graphics2D graphics)
    {
        utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.inventoryFullLocation()), config.inventoryFullColor());
    }

    private void displayInventoryContainsIndicator(Graphics2D graphics)
    {
        utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.inventoryContainsLocation()), config.inventoryContainsColor());
    }

    private void displayInventoryDoesNotContainsIndicator(Graphics2D graphics)
    {
        utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.inventoryDoesNotContainsLocation()), config.inventoryDoesNotContainsColor());
    }
}
