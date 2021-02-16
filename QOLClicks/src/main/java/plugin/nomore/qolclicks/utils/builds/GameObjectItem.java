package plugin.nomore.qolclicks.utils.builds;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.GameObject;
import net.runelite.api.widgets.WidgetItem;

@Builder
@Data
public class GameObjectItem
{
    GameObject object;
    String name;
    String[] actions;
}
