package plugin.nomore.inventorytagsextended.builder;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.widgets.WidgetItem;

import java.awt.*;

@Builder
@Data
public class HighlightingObject
{
    String name;
    int id;
    WidgetItem widgetItem;
    int index;
    int quantity;
    Color color;
}
