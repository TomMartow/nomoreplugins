package plugin.nomore.qolclicks.builds;

import lombok.*;
import net.runelite.api.GameObject;
import net.runelite.api.Tile;

@Builder
@Data
public class BuiltObject
{

    GameObject gameObject;
    Tile tile;
    long systemTime;

}
