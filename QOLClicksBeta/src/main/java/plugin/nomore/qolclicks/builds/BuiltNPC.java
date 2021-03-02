package plugin.nomore.qolclicks.builds;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.NPC;

@Builder
@Data
public class BuiltNPC
{

    NPC npc;
    long systemTime;

}
