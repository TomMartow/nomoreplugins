package plugin.nomore.nmoneclicker.item.ground;

import joptsimple.internal.Strings;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import plugin.nomore.nmoneclicker.AIOConfig;
import plugin.nomore.nmoneclicker.KeyboardListener;
import plugin.nomore.nmoneclicker.item.ground.builder.ConfigObject;
import plugin.nomore.nmoneclicker.item.ground.builder.HighlightingObject;
import plugin.nomore.nmputils.api.StringAPI;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GroundItemMethods
{
    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Inject
    private AIOConfig config;

    @Inject
    private StringAPI stringAPI;

    @Inject
    private ItemManager itemManager;

    @Inject
    private KeyboardListener keyboardListener;

    private static final List<HighlightingObject> groundItemsToHighlight = new ArrayList<>();
    private final List<ConfigObject> configObjects = new ArrayList<>();

    public void startUp()
    {
        getConfigTextField();
    }

    public void shutDown()
    {
        groundItemsToHighlight.clear();
    }

    public void onGameStateChanged(GameStateChanged event)
    {
        switch (event.getGameState())
        {
            case LOADING:
                groundItemsToHighlight.clear();
                getConfigTextField();
                break;
        }
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
        TileItem item = event.getItem();
        Tile tile = event.getTile();
        if (item == null || tile == null)
        {
            return;
        }
        groundItemsToHighlight.removeIf(highlightingObject
                -> highlightingObject.getTileItem() == createHighlightingObject(item, null).getTileItem());
    }

    private void compareGroundItem(TileItem item, Tile tile)
    {
        HighlightingObject highlightingObject = createHighlightingObject(item, tile);
        if (highlightingObject == null)
        {
            return;
        }
        String itemName = highlightingObject.getName();
        int itemId = item.getId();
        System.out.println(itemName + ", " + itemId);
        for (ConfigObject configObject : configObjects)
        {
            if (!Strings.isNullOrEmpty(configObject.getName()))
            {
                if (configObject.getName()
                        .equalsIgnoreCase(stringAPI.removeWhiteSpaces(itemName))
                        || itemId == configObject.getId())
                {
                    highlightingObject.setColor(configObject.getColor());
                    groundItemsToHighlight.add(highlightingObject);
                    System.out.println(configObject.getName() + " matches " + stringAPI.removeWhiteSpaces(itemName));
                }
            }
            if (itemId == configObject.getId())
            {
                highlightingObject.setColor(configObject.getColor());
                groundItemsToHighlight.add(highlightingObject);
                System.out.println(configObject.getId() + " matches " + itemId);
            }
        }
    }

    private HighlightingObject createHighlightingObject(TileItem item, Tile tile)
    {
        ItemDefinition def = itemManager.getItemDefinition(item.getId());
        return HighlightingObject.builder()
                .name(def.getName())
                .id(item.getId())
                .tileItem(item)
                .tile(tile)
                .quantity(item.getQuantity())
                .gePrice(def.getPrice())
                .haPrice(def.getHaPrice())
                .plane(item.getTile().getWorldLocation().getPlane())
                .build();
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
        String configTextString = stringAPI.removeWhiteSpaces(config.groundItemConfigTextString());
        if (Strings.isNullOrEmpty(configTextString))
        {
            return;
        }
        String[] stringsSplitByComma = configTextString.split(",");
        for (String commaSplit : stringsSplitByComma)
        {
            if (commaSplit.contains(":"))
            {
                String[] colonSplit;
                String[] colonToAdd = commaSplit.split(":");
                if (colonToAdd.length == 0)
                {
                    return;
                }
                if (colonToAdd.length == 1)
                {
                    colonSplit = new String[]{colonToAdd[0], ""};
                }
                else
                {
                    colonSplit = new String[]{colonToAdd[0], colonToAdd[1]};
                }
                if (stringAPI.containsNumbers(colonSplit[0]))
                {
                    createConfigObject(null, checkInt(colonSplit[0]), colonSplit[1]);
                }
                else
                {
                    createConfigObject(colonSplit[0], -1, colonSplit[1]);
                }
            }
            else
            {
                String[] fakeSplit = {commaSplit, null, null};
                if (stringAPI.containsNumbers(fakeSplit[0]))
                {
                    createConfigObject(null, checkInt(fakeSplit[0]), null);
                }
                else
                {
                    createConfigObject(fakeSplit[0], -1, null);
                }
            }
        }
    }

    private int checkInt(String stringNum)
    {
        String charRemoved = stringAPI.removeCharactersFromString(stringNum);
        if (Strings.isNullOrEmpty(charRemoved))
        {
            return -1;
        }
        int number;
        if (charRemoved.length() > 8)
        {
            number = Integer.parseInt(charRemoved.substring(0, 8));
        }
        else
        {
            number = Integer.parseInt(charRemoved);
        }
        return number;
    }

    private void createConfigObject(String configItemName, int configItemId, String configItemColor)
    {
        if (Strings.isNullOrEmpty(configItemName))
        {
            configItemName = "null";
        }
        if (Strings.isNullOrEmpty(configItemColor))
        {
            configItemColor = "null";
        }
        try
        {
            if (configItemColor.length() != 6)
            {
                configItemColor = "00FF00";
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            if (Strings.isNullOrEmpty(configItemColor))
            {
                configItemColor = "00FF00";
            }
        }
        Color actualConfigColor = config.groundItemDefaultHighlightColor();
        try
        {
            actualConfigColor = Color.decode("#" + configItemColor);
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("Error decoding color for " + configItemColor);
        }
        ConfigObject configObject = ConfigObject.builder()
                .name(configItemName)
                .id(configItemId)
                .color(actualConfigColor)
                .build();
        configObjects.add(configObject);
    }


    public void onConfigChanged(ConfigChanged event)
    {
        if (!event.getGroup().equals("aiomarkers"))
        {
            return;
        }
        if (event.getKey().equals("configGroundItemTextField")
                && config.enableGroundItemHighlighting())
        {
            groundItemsToHighlight.clear();
            configObjects.clear();
            getConfigTextField();
        }
    }

    // ██████╗ ███████╗████████╗████████╗███████╗██████╗ ███████╗
    //██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
    //██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝███████╗
    //██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗╚════██║
    //╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║███████║
    //╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

    public List<HighlightingObject> getGroundItemsToHighlight()
    {
        return groundItemsToHighlight;
    }

    //███╗   ███╗██╗███████╗ ██████╗
    //████╗ ████║██║██╔════╝██╔════╝
    //██╔████╔██║██║███████╗██║
    //██║╚██╔╝██║██║╚════██║██║
    //██║ ╚═╝ ██║██║███████║╚██████╗
    //╚═╝     ╚═╝╚═╝╚══════╝ ╚═════╝

    public boolean doesPlayerHaveALineOfSightToItem(Player player, TileItem item)
    {
        if (player == null)
        {
            return false;
        }
        if (item == null)
        {
            return false;
        }
        return player.getWorldArea().hasLineOfSightTo(client, item.getTile().getWorldLocation().toWorldArea());
    }
}
