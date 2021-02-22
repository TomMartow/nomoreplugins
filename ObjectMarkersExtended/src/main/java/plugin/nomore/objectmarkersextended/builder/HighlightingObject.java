package plugin.nomore.objectmarkersextended.builder;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;

import java.awt.*;

@Builder
@Data
public class HighlightingObject
{
    String name;
    int id;
    TileObject object;
    Tile tile;
    int plane;
    Color color;
}
