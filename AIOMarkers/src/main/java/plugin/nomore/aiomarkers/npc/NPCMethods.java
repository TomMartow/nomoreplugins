package plugin.nomore.aiomarkers.npc;

import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.events.PlayerDespawned;
import net.runelite.api.events.PlayerSpawned;
import net.runelite.client.events.ConfigChanged;
import plugin.nomore.aiomarkers.AIOConfig;
import plugin.nomore.aiomarkers.KeyboardListener;
import net.runelite.api.*;
import plugin.nomore.nmputils.api.NPCAPI;
import plugin.nomore.nmputils.api.StringAPI;

import javax.inject.Inject;
import java.awt.*;
import java.util.*;
import java.util.List;

public class NPCMethods
{

    // Inject the client.
    @Inject
    private Client client;

    // Inject the current plugins config.
    @Inject
    private AIOConfig config;

    // Inject NoMore's string api from NMPUtils.
    @Inject
    private StringAPI stringAPI;

    // Inject the runelite keyboard listener.
    @Inject
    private KeyboardListener keyboardListener;

    // Create a static string for the tag option.
    private static final String TAG = "NPC: Tag";
    // Create a static string for the untag option.
    private static final String UNTAG = "NPC: Untag";

    // Create a set of MenuOpcode's.
    private static final Set<MenuOpcode> NPC_MENU_ACTIONS = Set.of(
            MenuOpcode.NPC_FIRST_OPTION,
            MenuOpcode.NPC_SECOND_OPTION,
            MenuOpcode.NPC_THIRD_OPTION,
            MenuOpcode.NPC_FOURTH_OPTION,
            MenuOpcode.NPC_FIFTH_OPTION
    );

    public void startUp()
    {
        // At the start up of the plugin do the following:
        // Get the config text field.
        getConfigTextField();
        // Get all of the npc's in scene.
        getAllNpcs();
    }

    public void shutDown()
    {
        // At the shut down of the plugin, do the following:
        // Clear the highlighting list.
        npcsToHighlight.clear();
        // Clear the config npc name and color hashmap.
        configNPCNameAndColor.clear();
        // Clear the config npc id and color hashmap.
        configNPCIDAndColor.clear();
        // Clear the othe players list.
        otherPlayersList.clear();
    }

    //███╗   ██╗██████╗  ██████╗
    //████╗  ██║██╔══██╗██╔════╝
    //██╔██╗ ██║██████╔╝██║
    //██║╚██╗██║██╔═══╝ ██║
    //██║ ╚████║██║     ╚██████╗
    //╚═╝  ╚═══╝╚═╝      ╚═════╝

    public void onNPCSpawned(NpcSpawned event)
    {
        // Upon an npc spawning, do the following:
        // Check the npc against the config text field.
        compareNPC(event.getNpc());
    }

    public void onNPCDespawned(NpcDespawned event)
    {
        // Upon an npc despawning, do the following:
        // Remove the npc from the highlighting list if it is present.
        npcsToHighlight.remove(event.getNpc());
    }

    // Create a hashmash for the npc's we want to highlight.
    private static final HashMap<NPC, Color> npcsToHighlight = new HashMap<>();

    private void compareNPC(NPC npc)
    {
        // Check if the npc is null.
        if (npc == null)
        {
            // Return as the npc is null.
            return;
        }
        // Create a string object from the npc name and remove all whitespaces from it.
        if (npc.getName() == null)
        {
            return;
        }
        String npcName = stringAPI.removeWhiteSpaces(npc.getName());
        // Check that the npc name is not null.
        if (npcName != null)
        {
            // Stream through the hash map and for each key and value, do the following:
            configNPCNameAndColor.forEach((configNPCName, color) ->
            {
                // Check that the config npc name in the hash map contains the npc name.
                if (configNPCName.contains(npcName))
                {
                    // Put the npc into the npc to highlight hashmap if it isn't already there.
                    npcsToHighlight.putIfAbsent(npc, color);
                }
            });
        }
        // Create an int object from the npc id.
        int npcId = npc.getId();
        // Check that the npc id does not equal -1.
        if (npcId != -1)
        {
            // Stream through the hash map and for each key and value, do the following:
            configNPCIDAndColor.forEach((configNPCId, color) ->
            {
                // Check that the config npc id matches the npc id.
                if (configNPCId == npcId)
                {
                    // Put the npc into the npc to highlight hashmap if it isn't already there.
                    npcsToHighlight.putIfAbsent(npc, color);
                }
            });
        }
    }

    private void getAllNpcs()
    {
        // Check if the game state does not equal logged in.
        if (client.getGameState() != GameState.LOGGED_IN)
        {
            // Return  as the game state does not equal logged in.
            return;
        }
        // Retrieve all npc's in the scene.
        List<NPC> npcs = client.getNpcs();
        // Use a for loop to iterate between each npc in the list.
        for (NPC npc : npcs)
        {
            // Compare the npc's attributes to the config text field.
            compareNPC(npc);
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
        // Get the config npc text string that the user has provided and remove all whitespaces.
        String configTextString = stringAPI.removeWhiteSpaces(config.npcConfigTextString());
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
            // Check if the user has mistakenly not provided a name or id for the npc (noob).
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
                    createNPCName(stringsSplitByColon);
                }
                else
                {
                    // The first part of the string contains numbers.
                    createNPCID(stringsSplitByColon);
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
                    createNPCName(fakeStringArray);
                }
                else
                {
                    // The first part of the string contains numbers.
                    createNPCID(fakeStringArray);
                }
            }
        }
    }

    // Create a hashmap with the npc name as the key, and the color as the value.
    private HashMap<String, Color> configNPCNameAndColor = new HashMap<>();

    private void createNPCName(String[] stringsSplitByColon)
    {
        // Create a string for the npc name.
        String npcName = stringsSplitByColon[0];
        // Create a string object.
        String npcStringColor;
        // Try to do the following:
        try
        {
            // Check if the second part of the string array's length is not 6.
            if (stringsSplitByColon[1].length() != 6)
            {
                // Assign the npc color as green because it's empty (possibly from the fakeStringArray).
                npcStringColor = "00FF00";
            }
            else
            {
                // Assign the second part of the string array to the npc string color.
                npcStringColor = stringsSplitByColon[1];
            }
        }
        // Catch an array index out of bounds exception.
        catch (ArrayIndexOutOfBoundsException e)
        {
            // Assign the npc color as green because it's empty (possibly from the fakeStringArray).
            npcStringColor = "00FF00";
        }
        // Create the color object.
        Color npcColor;
        // Try to create the color object from the string.
        try
        {
            // Use the second part of the string to create a color for the npc.
            npcColor = Color.decode("#" + npcStringColor);
        }
        // If the try fails, catch the exception.
        catch (NumberFormatException nfe)
        {
            // Set the color as the default highlighting color.
            npcColor = config.npcDefaultHighlightColor();
            // Produce an error log for the console.
            System.out.println("Error decoding color for " + stringsSplitByColon[0]);
        }
        // Put the npc name and color into the config npc name and color hashmap.
        configNPCNameAndColor.putIfAbsent(npcName, npcColor);
    }

    // Create a hashmap with the npc id as the key, and the color as the value.
    private HashMap<Integer, Color> configNPCIDAndColor = new HashMap<>();

    private void createNPCID(String[] stringsSplitByColon)
    {
        // Create a string for the npc name.
        String npcId = stringsSplitByColon[0];
        // Create a string object.
        String npcStringColor;
        // Try to do the following:
        try
        {
            // Check if the second part of the string array's length is not 6.
            if (stringsSplitByColon[1].length() != 6)
            {
                // Assign the npc color as green because it's empty (possibly from the fakeStringArray).
                npcStringColor = "00FF00";
            }
            else
            {
                // Assign the second part of the string array to the npc string color.
                npcStringColor = stringsSplitByColon[1];
            }
        }
        // Catch an array index out of bounds exception.
        catch (ArrayIndexOutOfBoundsException e)
        {
            // Assign the npc color as green because it's empty (possibly from the fakeStringArray).
            npcStringColor = "00FF00";
        }
        // Create the color object.
        Color npcColor;
        // Try to create the color object from the string.
        try
        {
            // Use the second part of the string to create a color for the npc.
            npcColor = Color.decode("#" + npcStringColor);
        }
        // If the try fails, catch the exception.
        catch (NumberFormatException nfe)
        {
            // Set the color as the default highlighting color.
            npcColor = config.npcDefaultHighlightColor();
            // Produce an error log for the console.
            System.out.println("Error decoding color for " + stringsSplitByColon[0]);
        }
        // Put the npc id and color into the config npc id and color hashmap.
        configNPCIDAndColor.putIfAbsent(Integer.parseInt(npcId), npcColor);
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
        for highlighting npc's is enabled. */
        if (event.getKey().equals("configNPCTextField")
                && config.enableNPCHighlighting())
        {
            // Clear the npc highlighting list.
            npcsToHighlight.clear();
            // Clear the config npc name and color list.
            configNPCNameAndColor.clear();
            // Clear the config npc id and color list.
            configNPCIDAndColor.clear();
            // Get the config text field.
            getConfigTextField();
            // Get all of the npc's in scene.
            getAllNpcs();
        }
    }

    // ██████╗ ████████╗██╗  ██╗███████╗██████╗
    //██╔═══██╗╚══██╔══╝██║  ██║██╔════╝██╔══██╗
    //██║   ██║   ██║   ███████║█████╗  ██████╔╝
    //██║   ██║   ██║   ██╔══██║██╔══╝  ██╔══██╗
    //╚██████╔╝   ██║   ██║  ██║███████╗██║  ██║
    //╚═════╝    ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
    //
    //██████╗ ██╗      █████╗ ██╗   ██╗███████╗██████╗ ███████╗
    //██╔══██╗██║     ██╔══██╗╚██╗ ██╔╝██╔════╝██╔══██╗██╔════╝
    //██████╔╝██║     ███████║ ╚████╔╝ █████╗  ██████╔╝███████╗
    //██╔═══╝ ██║     ██╔══██║  ╚██╔╝  ██╔══╝  ██╔══██╗╚════██║
    //██║     ███████╗██║  ██║   ██║   ███████╗██║  ██║███████║
    //╚═╝     ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

    // Create a list containing the player object.
    private static final List<Player> otherPlayersList = new ArrayList<>();

    public void onPlayerSpawned(PlayerSpawned event)
    {
        // Upon a player spawning, do the following:
        // Check if the player is null.
        if (event.getPlayer() == null)
        {
            // Return as the player is null.
            return;
        }
        // Add the player to the other players list.
        otherPlayersList.add(event.getPlayer());
    }

    public void onPlayerDespawned(PlayerDespawned event)
    {
        // Upon a player despawning, do the following:
        // Check if the player is null.
        if (event.getPlayer() == null)
        {
            // Return as the player is null.
            return;
        }
        // Remove the player to the other players list.
        otherPlayersList.remove(event.getPlayer());
    }

    // ██████╗ ███████╗████████╗████████╗███████╗██████╗ ███████╗
    //██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
    //██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝███████╗
    //██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗╚════██║
    //╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║███████║
    //╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

    public HashMap<NPC, Color> getNpcsToHighlightHashMap()
    {
        // Return the npc to highlight list.
        return npcsToHighlight;
    }

    public List<Player> getOtherPlayersList()
    {
        // Return the other players list.
        return otherPlayersList;
    }

    //███╗   ███╗██╗███████╗ ██████╗
    //████╗ ████║██║██╔════╝██╔════╝
    //██╔████╔██║██║███████╗██║
    //██║╚██╔╝██║██║╚════██║██║
    //██║ ╚═╝ ██║██║███████║╚██████╗
    //╚═╝     ╚═╝╚═╝╚══════╝ ╚═════╝

    public boolean doesPlayerHaveALineOfSightToNPC(Player player, NPC npc)
    {
        // Check if the player is null.
        if (player == null)
        {
            // Return false as the player is null.
            return false;
        }
        // Check if the npc is null.
        if (npc == null)
        {
            // Return false as the npc is null.
            return false;
        }
        // Return the result of if the player has a light of sight (tiles) to the npc.
        return player.getWorldArea().hasLineOfSightTo(client, npc.getWorldArea());
    }
}
