package plugin.nomore.nmoneclicker.menu;

import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.api.NPC;
import net.runelite.api.queries.NPCQuery;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class Character
{

    @Inject
    Client client;

    @Nullable
    public NPC findNearestNpc(int... ids)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .idEquals(ids)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    @Nullable
    public NPC findNearestNpc(String... names)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new NPCQuery()
                .nameContains(names)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

}
