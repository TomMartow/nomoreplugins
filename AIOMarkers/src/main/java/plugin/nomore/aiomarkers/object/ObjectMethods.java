package plugin.nomore.aiomarkers.object;

import joptsimple.internal.Strings;
import net.runelite.api.*;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.api.queries.DecorativeObjectQuery;
import net.runelite.api.queries.GameObjectQuery;
import net.runelite.api.queries.GroundObjectQuery;
import net.runelite.api.queries.WallObjectQuery;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.WorldLocation;
import plugin.nomore.aiomarkers.AIOConfig;
import plugin.nomore.nmputils.api.StringAPI;

import javax.inject.Inject;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class ObjectMethods
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
        getAllObjects();
    }

    public void shutDown()
    {
        // At the shut down of the plugin, do the following:
        // Clear the highlighting list.
        objectsToHighlight.clear();
        // Clear the config object name and color hashmap.
        configObjectNameAndColor.clear();
        // Clear the config object id and color hashmap.
        configObjectIDAndColor.clear();
    }

    public void onGameObjectSpawned(GameObjectSpawned event) { onTileObject(event.getGameObject(), null); }

    public void onGameObjectChanged(GameObjectChanged event) { onTileObject(event.getGameObject(), event.getPrevious()); }

    public void onGameObjectDespawned(GameObjectDespawned event) { removeObject(event.getGameObject()); }

    public void onGroundObjectSpawned(GroundObjectSpawned event) { onTileObject(event.getGroundObject(), null); }

    public void onGroundObjectChanged(GroundObjectChanged event) { onTileObject(event.getGroundObject(), event.getPrevious()); }

    public void onGroundObjectDespawned(GroundObjectDespawned event) { removeObject(event.getGroundObject()); }

    public void onDecorativeObjectSpawned(DecorativeObjectSpawned event) { onTileObject(event.getDecorativeObject(), null); }

    public void onDecorativeObjectChanged(DecorativeObjectChanged event) { onTileObject(event.getDecorativeObject(), event.getPrevious()); }

    public void onDecorativeObjectDespawned(DecorativeObjectDespawned event) { removeObject(event.getDecorativeObject()); }

    public void onWallObjectSpawned(WallObjectSpawned event) { onTileObject(event.getWallObject(), null); }

    public void onWallObjectChanged(WallObjectChanged event) { onTileObject(event.getWallObject(), event.getPrevious()); }

    public void onWallObjectDespawned(WallObjectDespawned event) { removeObject(event.getWallObject()); }

    private void onTileObject(TileObject newObject, TileObject oldObject)
    {
        // Remove the oldObject from the objectsToHighlight hashmap.
        removeObject(oldObject);
        // Check if the newObject is null.
        if (newObject == null)
        {
            // Return as the newObject is null.
            return;
        }
        compareObject(newObject);
    }

    private void removeObject(TileObject tileObject)
    {
        objectsToHighlight.remove(tileObject);
    }

    // Create a hashmash for the object's we want to highlight.
    private static final HashMap<TileObject, Color> objectsToHighlight = new HashMap<>();

    private String objectName;

    private void compareObject(TileObject object)
    {
        // Check if the object is null.
        if (object == null)
        {
            // Return as the object is null.
            return;
        }
        // Make the objectName string empty.
        objectName = "";
        // Invoke the client thread to get the object definition and remove all whitespaces from the name.
        clientThread.invoke(() -> objectName = stringAPI.removeWhiteSpaces(client.getObjectDefinition(object.getId()).getName()));
        // Check if the string is not "null".
        if (!Strings.isNullOrEmpty(objectName) || !objectName.equals("null"))
        {
            // Stream through the hash map and for each key and value, do the following:
            configObjectNameAndColor.forEach((configObjectName, color) ->
            {
                // Check that the config object name in the hash map contains the object name.
                if (configObjectName.contains(objectName))
                {
                    // Put the object into the object to highlight hashmap if it isn't already there.
                    objectsToHighlight.putIfAbsent(object, color);
                }
            });
        }
        int objectId = object.getId();
        // Check that the object id does not equal -1.
        if (objectId != -1)
        {
            // Stream through the hash map and for each key and value, do the following:
            configObjectIDAndColor.forEach((configObjectId, color) ->
            {
                // Check that the config object id matches the object id.
                if (configObjectId == objectId)
                {
                    // Put the object into the object to highlight hashmap if it isn't already there.
                    objectsToHighlight.putIfAbsent(object, color);
                }
            });
        }
    }

    private void getAllObjects()
    {
        // Check if the game state does not equal logged in.
        if (client.getGameState() != GameState.LOGGED_IN)
        {
            // Return  as the game state does not equal logged in.
            return;
        }

        clientThread.invoke(() ->
        {
            // Retrieve all decorative objects in the scene.
            List<DecorativeObject> decorativeObjects = new DecorativeObjectQuery().result(client).list;
            // Use a for loop to iterate between each object in the list.
            for (DecorativeObject decorativeObject : decorativeObjects)
            {
                // Compare the objects attributes to the config text field.
                compareObject(decorativeObject);
            }

            // Retrieve all game objects in the scene.
            List<GameObject> gameObjects = new GameObjectQuery().result(client).list;
            // Use a for loop to iterate between each object in the list.
            for (GameObject gameObject : gameObjects)
            {
                // Compare the objects attributes to the config text field.
                compareObject(gameObject);
            }

            // Retrieve all ground objects in the scene.
            List<GroundObject> groundObjects = new GroundObjectQuery().result(client).list;
            // Use a for loop to iterate between each object in the list.
            for (GroundObject groundObject : groundObjects)
            {
                // Compare the objects attributes to the config text field.
                compareObject(groundObject);
            }

            // Retrieve all wall objects in the scene.
            List<WallObject> wallObjects = new WallObjectQuery().result(client).list;
            // Use a for loop to iterate between each object in the list.
            for (WallObject wallObject : wallObjects)
            {
                // Compare the objects attributes to the config text field.
                compareObject(wallObject);
            }
        });
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
        String configTextString = stringAPI.removeWhiteSpaces(config.objectConfigTextString());
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
    private HashMap<String, Color> configObjectNameAndColor = new HashMap<>();

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
        configObjectNameAndColor.putIfAbsent(objectName, objectColor);
    }

    // Create a hashmap with the object id as the key, and the color as the value.
    private HashMap<Integer, Color> configObjectIDAndColor = new HashMap<>();

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
        configObjectIDAndColor.putIfAbsent(Integer.parseInt(objectId), objectColor);
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
        if (event.getKey().equals("configObjectTextField")
                && config.enableObjectMarkers())
        {
            // Clear the objects highlighting list.
            objectsToHighlight.clear();
            // Clear the config object name and color list.
            configObjectNameAndColor.clear();
            // Clear the config object id and color list.
            configObjectIDAndColor.clear();
            // Get the config text field.
            getConfigTextField();
            // Get all of the objects in scene.
            getAllObjects();
        }
    }

    // ██████╗ ███████╗████████╗████████╗███████╗██████╗ ███████╗
    //██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
    //██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝███████╗
    //██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗╚════██║
    //╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║███████║
    //╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

    public HashMap<TileObject, Color> getObjectsToHighlightHashMap()
    {
        // Return the object to highlight list.
        return objectsToHighlight;
    }

    //███╗   ███╗██╗███████╗ ██████╗
    //████╗ ████║██║██╔════╝██╔════╝
    //██╔████╔██║██║███████╗██║
    //██║╚██╔╝██║██║╚════██║██║
    //██║ ╚═╝ ██║██║███████║╚██████╗
    //╚═╝     ╚═╝╚═╝╚══════╝ ╚═════╝

    public boolean doesPlayerHaveALineOfSightToObject(Player player, TileObject object)
    {
        // Check if the player is null.
        if (player == null)
        {
            // Return false as the player is null.
            return false;
        }
        // Check if the object is null.
        if (object == null)
        {
            // Return false as the object is null.
            return false;
        }
        // Create a world point object of the object.
        WorldPoint objectWP = object.getWorldLocation();
        // Check if the objectWP is null.
        if (objectWP == null)
        {
            // Return false as the objectWP is null.
            return false;
        }
        // Return the result of if the player has a light of sight (tiles) to the object.
        return player.getWorldArea().hasLineOfSightTo(client, objectWP.toWorldArea());
    }
}
