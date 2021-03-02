package plugin.nomore.qolclicksbeta.builds;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.widgets.WidgetItem;

@Builder
@Data
public class BuiltInventoryItem
{
    WidgetItem widgetItem;
    String name;
}
