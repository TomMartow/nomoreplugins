package plugin.nomore.qolclicksbeta.builds;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;

@Builder
@Data
public class BuiltObject
{

    GameObject gameObject;
    Tile tile;
    long systemTime;

}
