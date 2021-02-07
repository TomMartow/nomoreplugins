package plugin.nomore.upordown;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.GameObject;
import net.runelite.api.Tile;

@Data
@Builder
public class ElevationObject
{
    GameObject gameObject;
    Tile tile;
    boolean up;
    boolean down;

}
