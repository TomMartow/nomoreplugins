package plugin.nomore.interactionhud.builds;

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
