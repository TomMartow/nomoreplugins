package plugin.nomore.nmputils.api;

import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NPCAPI
{

    @Inject
    private Client client;

    @Inject
    private StringAPI string;

    public static List<NPC> npcs = new ArrayList<>();

    public void onNPCSpawned(NPC npc)
    {
        npcs.add(npc);
    }

    public void onNPCDespawned(NPC npc)
    {
        npcs.remove(npc);
    }

    public List<NPC> get()
    {
        return npcs;
    }

    public NPC getClosest()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public NPC getClosestMatching(String npcName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .filter(npc
                        -> npc != null
                        && npc.getName() != null
                        && npc.getName().equalsIgnoreCase(npcName))
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public NPC getClosestMatching(int npcId)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .filter(npc
                        -> npc != null
                        && npc.getId() == npcId)
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public List<NPC> getNpcsMatching(String... npcNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .filter(i -> i != null
                        && i.getName() != null
                        && Arrays.stream(npcNames)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(i.getName()))))
                .collect(Collectors.toList());
    }

    public List<NPC> getNpcsMatching(int... npcIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .filter(i -> i != null
                        && Arrays.stream(npcIds)
                        .anyMatch(itemId -> itemId == i.getId()))
                .collect(Collectors.toList());
    }

    public List<NPC> getNpcsMatchingSortedByClosest(String... npcNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .filter(i -> i != null
                        && i.getName() != null
                        && Arrays.stream(npcNames)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(i.getName()))))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<NPC> getNpcsMatchingSortedByClosest(int... npcIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .filter(i -> i != null
                        && Arrays.stream(npcIds)
                        .anyMatch(itemId -> itemId == i.getId()))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    ////////////////////////////

    // MenuEntries

    public MenuEntry attack(NPC npc)
    {
        if (npc == null)
        {
            return null;
        }
        return new MenuEntry("", "",
                npc.getIndex(),
                MenuAction.NPC_SECOND_OPTION.getId(),
                0,
                0,
                false);
    }

    public MenuEntry trade(NPC npc)
    {
        if (npc == null)
        {
            return null;
        }
        return new MenuEntry("", "",
                npc.getIndex(),
                MenuAction.NPC_SECOND_OPTION.getId(),
                0,
                0,
                false);
    }

}
