package plugin.nomore.nmputils.api;

import net.runelite.api.Client;
import net.runelite.api.TileItem;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemSpawned;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GroundItemAPI
{

    @Inject
    private Client client;

    @Inject
    private StringAPI string;

    public static List<TileItem> groundItems = new ArrayList<>();
    
    //   ██████╗ ██████╗  ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
    //  ██╔════╝ ██╔══██╗██╔═══██╗██║   ██║████╗  ██║██╔══██╗
    //  ██║  ███╗██████╔╝██║   ██║██║   ██║██╔██╗ ██║██║  ██║
    //  ██║   ██║██╔══██╗██║   ██║██║   ██║██║╚██╗██║██║  ██║
    //  ╚██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
    //   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
    //  ██╗████████╗███████╗███╗   ███╗                      
    //  ██║╚══██╔══╝██╔════╝████╗ ████║                      
    //  ██║   ██║   █████╗  ██╔████╔██║                      
    //  ██║   ██║   ██╔══╝  ██║╚██╔╝██║                      
    //  ██║   ██║   ███████╗██║ ╚═╝ ██║                      
    //  ╚═╝   ╚═╝   ╚══════╝╚═╝     ╚═╝                      
    //                                                       
        
    public void onGroundItemSpawned(ItemSpawned event)
    {
        onItem(event.getItem(), null);
    }

    public void onGroundItemDespawned(ItemDespawned event)
    {
        onItem(null, event.getItem());
    }

    private void onItem(TileItem newObject, TileItem oldObject)
    {
        groundItems.remove(oldObject);
        if (newObject == null)
        {
            return;
        }
        groundItems.add(newObject);
    }

    public List<TileItem> getGroundItems()
    {
        return groundItems;
    }

    public TileItem getClosestGroundItem()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundItems()
                .stream()
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getTile()
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public TileItem getClosestGroundItemMatching(String groundItemName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundItems()
                .stream()
                .filter(groundItem
                        -> groundItem != null
                        && client.getObjectDefinition(groundItem.getId())
                        .getName()
                        .equalsIgnoreCase(groundItemName))
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getTile()
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public TileItem getClosestGroundItemMatching(int groundItemId)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundItems()
                .stream()
                .filter(groundItem
                        -> groundItem != null
                        && groundItem.getId() == groundItemId)
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getTile()
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public List<TileItem> getGroundItemsMatching(String... groundItemNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundItems()
                .stream()
                .filter(groundItem -> groundItem != null
                        && Arrays.stream(groundItemNames)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(groundItem.getId())
                                        .getName()))))
                .collect(Collectors.toList());
    }

    public List<TileItem> getGroundItemsMatching(int... groundItemIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundItems()
                .stream()
                .filter(groundItem -> groundItem != null
                        && Arrays.stream(groundItemIds)
                        .anyMatch(itemId -> itemId == groundItem.getId()))
                .collect(Collectors.toList());
    }

    public List<TileItem> getMatchingGroundItemsSortedByClosest(String... groundItemNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundItems()
                .stream()
                .filter(groundItem -> groundItem != null
                        && Arrays.stream(groundItemNames)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(groundItem.getId())
                                        .getName()))))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getTile()
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<TileItem> getMatchingGroundItemsSortedByClosest(int... groundItemIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundItems()
                .stream()
                .filter(groundItem -> groundItem != null
                        && Arrays.stream(groundItemIds)
                        .anyMatch(itemId -> itemId == groundItem.getId()))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getTile()
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }
    
}
