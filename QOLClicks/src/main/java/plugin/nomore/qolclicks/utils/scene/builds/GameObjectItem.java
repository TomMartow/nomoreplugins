package plugin.nomore.qolclicks.utils.scene.builds;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.GameObject;

@Builder
@Data
public class GameObjectItem
{
    GameObject object;
    String name;
    String[] actions;
}
