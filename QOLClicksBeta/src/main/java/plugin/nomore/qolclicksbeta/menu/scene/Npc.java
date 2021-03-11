package plugin.nomore.qolclicksbeta.menu.scene;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.queries.NPCQuery;
import plugin.nomore.qolclicksbeta.QOLClicksConfig;
import plugin.nomore.qolclicksbeta.QOLClicksPlugin;
import plugin.nomore.qolclicksbeta.utils.Utils;

import javax.inject.Inject;
import java.util.List;

public class Npc
{

    @Inject
    private Client client;

    @Inject
    private QOLClicksConfig config;

    @Inject
    private QOLClicksPlugin plugin;

    @Inject
    private Inventory inventory;

    @Inject
    private Utils utils;

    public NPC getClosestNpc(int... ids)
    {
        if (client.getLocalPlayer() == null)
        {
            return null;
        }
        return new NPCQuery()
                .idEquals(ids)
                .filter(npc -> npc != null
                        && !npc.isDead())
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    public NPC getClosestNpc(List<Integer> ids)
    {
        if (client.getLocalPlayer() == null)
        {
            return null;
        }
        return new NPCQuery()
                .idEquals(ids)
                .filter(npc -> npc != null
                        && !npc.isDead())
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    public NPC getClosestNpcThatNameEquals(String... namesEqual)
    {
        if (client.getLocalPlayer() == null)
        {
            return null;
        }
        return new NPCQuery()
                .nameEquals(namesEqual)
                .filter(npc -> npc != null
                        && !npc.isDead())
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    public NPC getClosestNpcThatNameContains(String... namesContain)
    {
        if (client.getLocalPlayer() == null)
        {
            return null;
        }
        return new NPCQuery()
                .nameContains(namesContain)
                .filter(npc -> npc != null
                        && !npc.isDead())
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

}
