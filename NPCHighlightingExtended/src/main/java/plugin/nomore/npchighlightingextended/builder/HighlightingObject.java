package plugin.nomore.npchighlightingextended.builder;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.NPC;

import java.awt.*;

@Builder
@Data
public class HighlightingObject
{
    String name;
    int id;
    NPC npc;
    Color color;
}
