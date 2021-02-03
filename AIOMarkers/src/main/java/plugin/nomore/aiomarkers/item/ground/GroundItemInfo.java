package plugin.nomore.aiomarkers.item.ground;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.TileItem;

@Data
@Builder
public class GroundItemInfo
{
    TileItem tileItem;
    int plane;
}
