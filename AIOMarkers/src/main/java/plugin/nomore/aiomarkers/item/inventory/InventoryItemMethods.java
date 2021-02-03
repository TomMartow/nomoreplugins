package plugin.nomore.aiomarkers.item.inventory;

import joptsimple.internal.Strings;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.events.ConfigChanged;
import plugin.nomore.aiomarkers.AIOConfig;
import plugin.nomore.nmputils.api.StringAPI;

import javax.inject.Inject;
import java.awt.*;
import java.util.HashMap;

public class InventoryItemMethods
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
        // Get all of the object's in scene.
        getAllInventoryItems();
    }

    public void shutDown()
    {
        // At the shut down of the plugin, do the following:
        // Clear the highlighting list.
        inventoryItemsToHighlight.clear();
        // Clear the config object name and color hashmap.
        configInventoryItemNameAndColor.clear();
        // Clear the config object id and color hashmap.
        configInventoryItemIDAndColor.clear();
    }

    public void onGameStateChanged(GameStateChanged event)
    {
        switch (event.getGameState())
        {
            case LOGGED_IN:
                break;
            case HOPPING:
            case LOADING:
            case UNKNOWN:
            case STARTING:
            case LOGGING_IN:
            case LOGIN_SCREEN:
            case CONNECTION_LOST:
            case LOGIN_SCREEN_AUTHENTICATOR:
                // At a change of game state, do the following:
                // Clear the highlighting list.
                inventoryItemsToHighlight.clear();
                // Clear the config object name and color hashmap.
                configInventoryItemNameAndColor.clear();
                // Clear the config object id and color hashmap.
                configInventoryItemIDAndColor.clear();
                break;
        }
    }

    public void onItemContainerChanged(ItemContainerChanged event)
    {
        if (event.getItemContainer() == client.getItemContainer(InventoryID.INVENTORY))
        {
            Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
            if (inventory == null || inventory.isHidden())
            {
                return;
            }
            inventoryItemsToHighlight.clear();
            for (WidgetItem item : inventory.getWidgetItems())
            {
                compareInventoryItems(item);
            }
        }
    }

    // Create a hashmash for the object's we want to highlight.
    private static final HashMap<WidgetItem, Color> inventoryItemsToHighlight = new HashMap<>();
    private String inventoryItemName;

    private void compareInventoryItems(WidgetItem item)
    {
        // Check if the object is null.
        if (item == null || item.getId() == -1)
        {
            // Return as the object is null.
            return;
        }
        // Make the objectName string empty.
        inventoryItemName = "";
        // Invoke the client thread to get the object definition and remove all whitespaces from the name.
        clientThread.invoke(() -> inventoryItemName = stringAPI.removeWhiteSpaces(client.getItemDefinition(item.getId()).getName()));
        // Check if the string is not "null".
        if (!Strings.isNullOrEmpty(inventoryItemName) || !inventoryItemName.equals("null"))
        {
            // Stream through the hash map and for each key and value, do the following:
            configInventoryItemNameAndColor.forEach((configObjectName, color) ->
            {
                // Check that the config object name in the hash map contains the object name.
                if (configObjectName.contains(inventoryItemName))
                {
                    // Put the object into the object to highlight hashmap if it isn't already there.
                    inventoryItemsToHighlight.putIfAbsent(item, color);
                }
            });
        }
        int objectId = item.getId();
        // Check that the object id does not equal -1.
        if (objectId != -1)
        {
            // Stream through the hash map and for each key and value, do the following:
            configInventoryItemIDAndColor.forEach((configObjectId, color) ->
            {
                // Check that the config object id matches the object id.
                if (configObjectId == objectId)
                {
                    // Put the object into the object to highlight hashmap if it isn't already there.
                    inventoryItemsToHighlight.putIfAbsent(item, color);
                }
            });
        }
    }

    private void getAllInventoryItems()
    {
        // Check if the game state does not equal logged in.
        if (client.getGameState() != GameState.LOGGED_IN)
        {
            // Return  as the game state does not equal logged in.
            return;
        }
        Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
        if (inventory == null)
        {
            return;
        }
        for (WidgetItem item : inventory.getWidgetItems())
        {
            compareInventoryItems(item);
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
        String configTextString = stringAPI.removeWhiteSpaces(config.inventoryItemConfigTextString());
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
                    createObjectName(stringsSplitByColon);
                }
                else
                {
                    // The first part of the string contains numbers.
                    createObjectID(stringsSplitByColon);
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
                    createObjectName(fakeStringArray);
                }
                else
                {
                    // The first part of the string contains numbers.
                    createObjectID(fakeStringArray);
                }
            }
        }
    }

    // Create a hashmap with the object name as the key, and the color as the value.
    private HashMap<String, Color> configInventoryItemNameAndColor = new HashMap<>();

    private void createObjectName(String[] stringsSplitByColon)
    {
        // Create a string for the object name.
        String objectName = stringsSplitByColon[0];
        // Create a string object.
        String objectStringColor;
        // Try to do the following:
        try
        {
            // Check if the second part of the string array's length is not 6.
            if (stringsSplitByColon[1].length() != 6)
            {
                // Assign the object color as green because it's empty (possibly from the fakeStringArray).
                objectStringColor = "00FF00";
            }
            else
            {
                // Assign the second part of the string array to the object string color.
                objectStringColor = stringsSplitByColon[1];
            }
        }
        // Catch an array index out of bounds exception.
        catch (ArrayIndexOutOfBoundsException e)
        {
            // Assign the object color as green because it's empty (possibly from the fakeStringArray).
            objectStringColor = "00FF00";
        }
        // Create the color object.
        Color objectColor;
        // Try to create the color object from the string.
        try
        {
            // Use the second part of the string to create a color for the object.
            objectColor = Color.decode("#" + objectStringColor);
        }
        // If the try fails, catch the exception.
        catch (NumberFormatException nfe)
        {
            // Set the color as the default highlighting color.
            objectColor = config.objectDefaultHighlightColor();
            // Produce an error log for the console.
            System.out.println("Error decoding color for " + stringsSplitByColon[0]);
        }
        // Put the object name and color into the config object name and color hashmap.
        configInventoryItemNameAndColor.putIfAbsent(objectName, objectColor);
    }

    // Create a hashmap with the object id as the key, and the color as the value.
    private HashMap<Integer, Color> configInventoryItemIDAndColor = new HashMap<>();

    private void createObjectID(String[] stringsSplitByColon)
    {
        // Create a string for the object name.
        String objectId = stringsSplitByColon[0];
        // Create a string object.
        String objectStringColor;
        // Try to do the following:
        try
        {
            // Check if the second part of the string array's length is not 6.
            if (stringsSplitByColon[1].length() != 6)
            {
                // Assign the object color as green because it's empty (possibly from the fakeStringArray).
                objectStringColor = "00FF00";
            }
            else
            {
                // Assign the second part of the string array to the object string color.
                objectStringColor = stringsSplitByColon[1];
            }
        }
        // Catch an array index out of bounds exception.
        catch (ArrayIndexOutOfBoundsException e)
        {
            // Assign the object color as green because it's empty (possibly from the fakeStringArray).
            objectStringColor = "00FF00";
        }
        // Create the color object.
        Color objectColor;
        // Try to create the color object from the string.
        try
        {
            // Use the second part of the string to create a color for the object.
            objectColor = Color.decode("#" + objectStringColor);
        }
        // If the try fails, catch the exception.
        catch (NumberFormatException nfe)
        {
            // Set the color as the default highlighting color.
            objectColor = config.objectDefaultHighlightColor();
            // Produce an error log for the console.
            System.out.println("Error decoding color for " + stringsSplitByColon[0]);
        }
        // Put the object id and color into the config object id and color hashmap.
        configInventoryItemIDAndColor.putIfAbsent(Integer.parseInt(objectId), objectColor);
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
        if (event.getKey().equals("configInventoryItemTextField")
                && config.enableInventoryItemHighlighting())
        {
            // Clear the objects highlighting list.
            inventoryItemsToHighlight.clear();
            // Clear the config object name and color list.
            configInventoryItemNameAndColor.clear();
            // Clear the config object id and color list.
            configInventoryItemIDAndColor.clear();
            // Get the config text field.
            getConfigTextField();
            // Get all of the objects in scene.
            getAllInventoryItems();
        }
    }

    // ██████╗ ███████╗████████╗████████╗███████╗██████╗ ███████╗
    //██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
    //██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝███████╗
    //██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗╚════██║
    //╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║███████║
    //╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

    public HashMap<WidgetItem, Color> getInventoryItemsToHighlightHashMap()
    {
        // Return the object to highlight list.
        return inventoryItemsToHighlight;
    }
}
