package plugin.nomore.newplugin;

import lombok.Builder;
import lombok.Data;
import net.runelite.api.NPC;

@Builder
@Data
public class NPCs
{

    NPC npc;
    String name;
    int id;

}
