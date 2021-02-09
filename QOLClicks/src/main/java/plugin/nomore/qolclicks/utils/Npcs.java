package plugin.nomore.qolclicks.utils;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import net.runelite.api.queries.NPCQuery;
import plugin.nomore.qolclicks.utils.StringUtils;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Npcs
{

    @Inject
    Client client;

    @Inject
    StringUtils string;

    public List<NPC> get()
    {
        return new NPCQuery().result(client).list;
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

    public NPC getClosestNpcWithAction(String action)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        Stream<NPC> npcActions = get()
                .stream()
                .filter(npc
                        -> npc != null
                        && Arrays.stream(client.getNpcDefinition(npc.getId()).getActions())
                        .anyMatch(a -> a.equalsIgnoreCase(action)));

        return npcActions
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public List<NPC> getNpcsWithAction(String action)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        Stream<NPC> objectActions = get()
                .stream()
                .filter(npc
                        -> npc != null
                        && Arrays.stream(client.getNpcDefinition(npc.getId()).getActions())
                        .anyMatch(a -> a.equalsIgnoreCase(action)));

        return objectActions
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .stream()
                .collect(Collectors.toList());
    }

}
