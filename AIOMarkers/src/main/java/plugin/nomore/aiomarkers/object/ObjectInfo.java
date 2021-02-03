package plugin.nomore.aiomarkers.object;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.TileObject;

@Data
@Builder
public class ObjectInfo
{
    TileObject tileObject;
    int plane;
}
