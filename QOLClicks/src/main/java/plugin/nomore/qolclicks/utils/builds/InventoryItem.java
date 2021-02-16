package plugin.nomore.qolclicks.utils.builds;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.widgets.WidgetItem;

@Builder
@Data
public class InventoryItem
{
    WidgetItem item;
    String name;
}
