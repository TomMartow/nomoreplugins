package plugin.nomore.aiomarkers.item.ground;

import joptsimple.internal.Strings;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.events.ConfigChanged;
import plugin.nomore.aiomarkers.AIOConfig;
import plugin.nomore.nmputils.api.StringAPI;

import javax.inject.Inject;
import java.awt.*;
import java.util.HashMap;

public class GroundItemMethods
{

    // Inject the client
    @Inject Client client;

    // Inject the client thread.
    @Inject ClientThread clientThread;

    // Inject the current plugins config.
    @Inject AIOConfig config;

    // Inject the StringAPI from NMUtils.
    @Inject StringAPI stringAPI;

    public void startUp()
    {
        // At the start up of the plugin do the following:
        // Get the config text field.
        getConfigTextField();
    }

    public void shutDown()
    {
        // At the shut down of the plugin, do the following:
        // Clear the highlighting list.
        groundItemToHighlight.clear();
        // Clear the config object name and color hashmap.
        configGroundItemNameAndColor.clear();
        // Clear the config object id and color hashmap.
        configGroundItemIDAndColor.clear();
    }

    public void onGameStateChanged(GameStateChanged event)
    {
        // At a change of game state, do the following:
        // Clear the highlighting list.
        groundItemToHighlight.clear();
        // Clear the config object name and color hashmap.
        configGroundItemNameAndColor.clear();
        // Clear the config object id and color hashmap.
        configGroundItemIDAndColor.clear();
    }

    public void onItemSpawned(ItemSpawned event)
    {
        TileItem item = event.getItem();
        Tile tile = event.getTile();
        if (item == null || tile == null)
        {
            return;
        }
        compareGroundItem(item, tile);
    }

    public void onItemDespawned(ItemDespawned event)
    {
        GroundItemInfo groundItemInfo = GroundItemInfo.builder()
                .tileItem(event.getItem())
                .plane(event.getTile().getPlane())
                .build();
        groundItemToHighlight.remove(groundItemInfo);
    }

    // Create a hashmash for the object's we want to highlight.
    private static final HashMap<GroundItemInfo, Color> groundItemToHighlight = new HashMap<>();

    private String itemName;

    private void compareGroundItem(TileItem item, Tile tile)
    {
        // Check if the object is null.
        if (item == null || tile == null)
        {
            // Return as the object is null.
            return;
        }
        // Make the objectName string empty.
        itemName = "";
        // Invoke the client thread to get the object definition and remove all whitespaces from the name.
        clientThread.invoke(() -> itemName = stringAPI.removeWhiteSpaces(client.getItemDefinition(item.getId()).getName()));
        // Check if the string is not "null".
        if (!Strings.isNullOrEmpty(itemName) || !itemName.equals("null"))
        {
            // Stream through the hash map and for each key and value, do the following:
            configGroundItemNameAndColor.forEach((configItemName, color) ->
            {
                // Check that the config object name in the hash map contains the object name.
                if (configItemName.contains(itemName))
                {
                    // Build a new ObjectInfo object.
                    GroundItemInfo groundItemInfo = GroundItemInfo
                            .builder()
                            .tileItem(item)
                            .plane(tile.getPlane())
                            .build();
                    // Put the object into the object to highlight hashmap if it isn't already there.
                    groundItemToHighlight.putIfAbsent(groundItemInfo, color);
                }
            });
        }
        int itemId = item.getId();
        // Check that the object id does not equal -1.
        if (itemId != -1)
        {
            // Stream through the hash map and for each key and value, do the following:
            configGroundItemIDAndColor.forEach((configItemId, color) ->
            {
                // Check that the config object id matches the object id.
                if (configItemId == itemId)
                {
                    // Build a new ObjectInfo object.
                    GroundItemInfo groundItemInfo = GroundItemInfo
                            .builder()
                            .tileItem(item)
                            .plane(tile.getPlane())
                            .build();
                    // Put the object into the object to highlight hashmap if it isn't already there.
                    groundItemToHighlight.putIfAbsent(groundItemInfo, color);
                }
            });
        }
    }

    // ██████╗ ██████╗ ███╗   ██╗███████╗██╗ ██████╗
    //██╔════╝██╔═══██╗████╗  ██║██╔════╝██║██╔════╝
    //██║     ██║   ██║██╔██╗ ██║█████╗  ██║██║  ███╗
    //██║     ██║   ██║██║╚██╗██║██╔══╝  ██║██║   ██║
    //╚██████╗╚██████╔╝██║ ╚████║██║     ██║╚██████╔╝
    // ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝     ╚═╝ ╚═════╝

    //██████╗ ███████╗██╗      █████╗ ████████╗███████╗██████╗
    //██╔══██╗██╔════╝██║     ██╔══██╗╚══██╔══╝██╔════╝██╔══██╗
    //██████╔╝█████╗  ██║     ███████║   ██║   █████╗  ██║  ██║
    //██╔══██╗██╔══╝  ██║     ██╔══██║   ██║   ██╔══╝  ██║  ██║
    //██║  ██║███████╗███████╗██║  ██║   ██║   ███████╗██████╔╝
    //╚═╝  ╚═╝╚══════╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═════╝

    private void getConfigTextField()
    {
        // Get the config object text string that the user has provided and remove all whitespaces.
        String configTextString = stringAPI.removeWhiteSpaces(config.groundItemConfigTextString());
        // Check if the user has mistakenly deleted everything in the config text field.
        if (configTextString.isEmpty())
        {
            return;
        }
        // Split the string into parts at the "," character.
        String[] stringsSplitByComma = configTextString.split(",");
        // Use a for loop to check each part of the strings split by a comma.
        for (String s : stringsSplitByComma)
        {
            // Check if the user has mistakenly not provided a name or id for the object (noob).
            if (s.isEmpty())
            {
                return;
            }
            // Check if the string contains the ":" character.
            if (s.contains(":"))
            {
                // Split the string into parts at the ":" character.
                String[] stringsSplitByColon = s.split(":");
                // Check if the first part of the string contains numbers.
                if (!stringAPI.containsNumbers(stringsSplitByColon[0]))
                {
                    // The first part of the string does not contains numbers.
                    createItemName(stringsSplitByColon);
                }
                else
                {
                    // The first part of the string contains numbers.
                    createItemID(stringsSplitByColon);
                }
            }
            else
            {
                // Create a fake string array so we can use the same method's as above.
                String[] fakeStringArray = {s, ""};
                // Check if the first part of the string contains numbers.
                if (!stringAPI.containsNumbers(fakeStringArray[0]))
                {
                    // The first part of the string does not contains numbers.
                    createItemName(fakeStringArray);
                }
                else
                {
                    // The first part of the string contains numbers.
                    createItemID(fakeStringArray);
                }
            }
        }
    }

    // Create a hashmap with the object name as the key, and the color as the value.
    private HashMap<String, Color> configGroundItemNameAndColor = new HashMap<>();

    private void createItemName(String[] stringsSplitByColon)
    {
        // Create a string for the object name.
        String itemName = stringsSplitByColon[0];
        // Create a string object.
        String itemStringColor;
        // Try to do the following:
        try
        {
            // Check if the second part of the string array's length is not 6.
            if (stringsSplitByColon[1].length() != 6)
            {
                // Assign the object color as green because it's empty (possibly from the fakeStringArray).
                itemStringColor = "00FF00";
            }
            else
            {
                // Assign the second part of the string array to the object string color.
                itemStringColor = stringsSplitByColon[1];
            }
        }
        // Catch an array index out of bounds exception.
        catch (ArrayIndexOutOfBoundsException e)
        {
            // Assign the object color as green because it's empty (possibly from the fakeStringArray).
            itemStringColor = "00FF00";
        }
        // Create the color object.
        Color itemColor;
        // Try to create the color object from the string.
        try
        {
            // Use the second part of the string to create a color for the object.
            itemColor = Color.decode("#" + itemStringColor);
        }
        // If the try fails, catch the exception.
        catch (NumberFormatException nfe)
        {
            // Set the color as the default highlighting color.
            itemColor = config.groundItemDefaultHighlightColor();
            // Produce an error log for the console.
            System.out.println("Error decoding color for " + stringsSplitByColon[0]);
        }
        // Put the object name and color into the config object name and color hashmap.
        configGroundItemNameAndColor.putIfAbsent(itemName, itemColor);
    }

    // Create a hashmap with the object id as the key, and the color as the value.
    private HashMap<Integer, Color> configGroundItemIDAndColor = new HashMap<>();

    private void createItemID(String[] stringsSplitByColon)
    {
        // Create a string for the object name.
        String itemId = stringsSplitByColon[0];
        // Create a string object.
        String itemStringColor;
        // Try to do the following:
        try
        {
            // Check if the second part of the string array's length is not 6.
            if (stringsSplitByColon[1].length() != 6)
            {
                // Assign the object color as green because it's empty (possibly from the fakeStringArray).
                itemStringColor = "00FF00";
            }
            else
            {
                // Assign the second part of the string array to the object string color.
                itemStringColor = stringsSplitByColon[1];
            }
        }
        // Catch an array index out of bounds exception.
        catch (ArrayIndexOutOfBoundsException e)
        {
            // Assign the object color as green because it's empty (possibly from the fakeStringArray).
            itemStringColor = "00FF00";
        }
        // Create the color object.
        Color itemColor;
        // Try to create the color object from the string.
        try
        {
            // Use the second part of the string to create a color for the object.
            itemColor = Color.decode("#" + itemStringColor);
        }
        // If the try fails, catch the exception.
        catch (NumberFormatException nfe)
        {
            // Set the color as the default highlighting color.
            itemColor = config.groundItemDefaultHighlightColor();
            // Produce an error log for the console.
            System.out.println("Error decoding color for " + stringsSplitByColon[0]);
        }
        // Put the object id and color into the config object id and color hashmap.
        configGroundItemIDAndColor.putIfAbsent(Integer.parseInt(itemId), itemColor);
    }

    public void onConfigChanged(ConfigChanged event)
    {
        // Upon any config item being changed, do the following:
        // Check if the config changed belongs to the group aiomarkers.
        if (!event.getGroup().equals("aiomarkers"))
        {
            // Return as the event group does not belong to the aiomarkers plugin.
            return;
        }
        /* Check if the config event's key equals the config text field and the config option
        for highlighting objects is enabled. */
        if (event.getKey().equals("configGroundItemTextField")
                && config.enableGroundItemHighlighting())
        {
            // Clear the objects highlighting list.
            groundItemToHighlight.clear();
            // Clear the config object name and color list.
            configGroundItemNameAndColor.clear();
            // Clear the config object id and color list.
            configGroundItemIDAndColor.clear();
            // Get the config text field.
            getConfigTextField();
        }
    }

    // ██████╗ ███████╗████████╗████████╗███████╗██████╗ ███████╗
    //██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
    //██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝███████╗
    //██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗╚════██║
    //╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║███████║
    //╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

    public HashMap<GroundItemInfo, Color> getGroundItemsToHighlightHashMap()
    {
        // Return the object to highlight list.
        return groundItemToHighlight;
    }

    //███╗   ███╗██╗███████╗ ██████╗
    //████╗ ████║██║██╔════╝██╔════╝
    //██╔████╔██║██║███████╗██║
    //██║╚██╔╝██║██║╚════██║██║
    //██║ ╚═╝ ██║██║███████║╚██████╗
    //╚═╝     ╚═╝╚═╝╚══════╝ ╚═════╝

    public boolean doesPlayerHaveALineOfSightToGroundItem(Player player, TileItem item)
    {
        // Check if the player is null.
        if (player == null)
        {
            // Return false as the player is null.
            return false;
        }
        // Check if the object is null.
        if (item == null)
        {
            // Return false as the object is null.
            return false;
        }
        // Create a world point object of the object.
        WorldPoint tileItemWP = item.getTile().getWorldLocation();
        // Check if the objectWP is null.
        if (tileItemWP == null)
        {
            // Return false as the objectWP is null.
            return false;
        }
        // Return the result of if the player has a light of sight (tiles) to the object.
        return player.getWorldArea().hasLineOfSightTo(client, tileItemWP.toWorldArea());
    }
}
