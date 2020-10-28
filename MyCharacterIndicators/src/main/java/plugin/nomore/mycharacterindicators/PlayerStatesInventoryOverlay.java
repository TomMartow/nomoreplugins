package plugin.nomore.mycharacterindicators;

import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import javax.inject.Inject;
import java.awt.*;

public class PlayerStatesInventoryOverlay extends WidgetItemOverlay {

    private final Client client;
    private final PlayerStatePlugin plugin;
    private final PlayerStateConfig config;

    @Inject
    public PlayerStatesInventoryOverlay(Client client, PlayerStatePlugin plugin, PlayerStateConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        showOnInventory();
    }

    @Override
    protected void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem itemWidget) {

        if (config.displayLowHP() && config.highlightEatItems())
        {
            plugin.getEatItems().forEach((item, id) ->
            {
                if (itemWidget.getId() == id)
                {
                    int boxSize = 4;
                    int x = (int) itemWidget.getCanvasBounds().getCenterX() - boxSize / 2;
                    int y = (int) itemWidget.getCanvasBounds().getCenterY() - boxSize / 2;
                    graphics.setColor(config.hpColor());
                    graphics.fillRect(x, y, boxSize, boxSize);
                }
            });
        }

    }
}
