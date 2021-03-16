package plugin.nomore.qolclicksbeta.menu.scene;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.queries.NPCQuery;
import plugin.nomore.qolclicksbeta.QOLClicksBetaConfig;
import plugin.nomore.qolclicksbeta.QOLClicksBetaPlugin;
import plugin.nomore.qolclicksbeta.utils.Utils;

import javax.inject.Inject;
import java.util.List;

public class Npc
{

    @Inject
    private Client client;

    @Inject
    private QOLClicksBetaConfig config;

    @Inject
    private QOLClicksBetaPlugin plugin;

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
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

}
