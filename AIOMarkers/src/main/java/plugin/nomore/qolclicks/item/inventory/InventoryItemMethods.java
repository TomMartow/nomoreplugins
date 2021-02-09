package plugin.nomore.qolclicks.item.inventory;

import joptsimple.internal.Strings;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import plugin.nomore.qolclicks.AIOConfig;
import plugin.nomore.qolclicks.KeyboardListener;
import plugin.nomore.qolclicks.item.inventory.builder.ConfigObject;
import plugin.nomore.qolclicks.item.inventory.builder.HighlightingObject;
import plugin.nomore.nmputils.api.StringAPI;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryItemMethods
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

    private static final List<HighlightingObject> inventoryItemsToHighlight = new ArrayList<>();
    private final List<ConfigObject> configObjects = new ArrayList<>();
    private int gameTick = 5;
    private boolean initialCheck = false;

    public void startUp()
    {
        getConfigTextField();
        clientThread.invoke(this::getAllInventoryItems);
    }

    public void shutDown()
    {
        inventoryItemsToHighlight.clear();
    }

    public void onGameTick(GameTick event)
    {
        Widget clickHereToPlay = client.getWidget(378,87);
        if (clickHereToPlay != null)
        {
            if (!clickHereToPlay.isHidden())
            {
                initialCheck = false;
                gameTick = 5;
                return;
            }
        }
        if (!initialCheck && clickHereToPlay == null)
        {
            Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
            if (inventory == null)
            {
                return;
            }
            if (inventory.isHidden())
            {
                return;
            }
            if (gameTick != 0)
            {
                gameTick -= 1;
                inventoryItemsToHighlight.clear();
                getConfigTextField();
                getAllInventoryItems();
            }
            else
            {
                initialCheck = true;
            }
        }
    }

    public void onGameStateChanged(GameStateChanged event)
    {
        switch (event.getGameState())
        {
            case LOGGED_IN:
            case LOADING:
            case LOGIN_SCREEN_AUTHENTICATOR:
            case UNKNOWN:
            case STARTING:
            case CONNECTION_LOST:
            case LOGIN_SCREEN:
            case LOGGING_IN:
            case HOPPING:
                gameTick = 5;
                initialCheck = false;
        }
    }

    public void onItemContainerChanged(ItemContainerChanged event)
    {
        inventoryItemsToHighlight.clear();
        Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
        if (inventory == null || inventory.isHidden())
        {
            return;
        }
        for (WidgetItem item : inventory.getWidgetItems())
        {
            if (item == null)
            {
                continue;
            }
            compareInventoryItem(item);
        }
    }

    public void getAllInventoryItems()
    {
        if (client.getLocalPlayer() == null)
        {
            return;
        }
        assert client.isClientThread();

        Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
        if (inventory == null || inventory.isHidden())
        {
            return;
        }
        for (WidgetItem item : inventory.getWidgetItems())
        {
            if (item == null)
            {
                continue;
            }
            compareInventoryItem(item);
        }
    }

    private void compareInventoryItem(WidgetItem item)
    {
        HighlightingObject highlightingObject = createHighlightingObject(item);
        if (highlightingObject == null)
        {
            return;
        }
        String itemName = highlightingObject.getName();
        int itemId = item.getId();
        for (ConfigObject configObject : configObjects)
        {
            if (!Strings.isNullOrEmpty(configObject.getName()))
            {
                if (configObject.getName()
                        .equalsIgnoreCase(stringAPI.removeWhiteSpaces(itemName)))
                {
                    highlightingObject.setColor(configObject.getColor());
                    inventoryItemsToHighlight.add(highlightingObject);
                }
            }
            if (itemId == configObject.getId())
            {
                highlightingObject.setColor(configObject.getColor());
                inventoryItemsToHighlight.add(highlightingObject);
            }
        }
    }

    private HighlightingObject createHighlightingObject(WidgetItem item)
    {
        ItemDefinition def = itemManager.getItemDefinition(item.getId());
        return HighlightingObject.builder()
                .name(def.getName())
                .id(item.getId())
                .widgetItem(item)
                .index(item.getIndex())
                .quantity(item.getQuantity())
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
        String configTextString = stringAPI.removeWhiteSpaces(config.inventoryItemConfigTextString());
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

    private void createConfigObject(String configInventoryItemName, int configInventoryItemId, String configInventoryItemColor)
    {
        if (Strings.isNullOrEmpty(configInventoryItemName))
        {
            configInventoryItemName = "null";
        }
        if (Strings.isNullOrEmpty(configInventoryItemColor))
        {
            configInventoryItemColor = "null";
        }
        try
        {
            if (configInventoryItemColor.length() != 6)
            {
                configInventoryItemColor = "00FF00";
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            if (Strings.isNullOrEmpty(configInventoryItemColor))
            {
                configInventoryItemColor = "00FF00";
            }
        }
        Color actualConfigColor = config.inventoryItemDefaultHighlightColor();
        try
        {
            actualConfigColor = Color.decode("#" + configInventoryItemColor);
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("Error decoding color for " + configInventoryItemColor);
        }
        ConfigObject configObject = ConfigObject.builder()
                .name(configInventoryItemName)
                .id(configInventoryItemId)
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
        if (event.getKey().equals("configInventoryItemTextField")
                && config.enableInventoryItemHighlighting())
        {
            inventoryItemsToHighlight.clear();
            configObjects.clear();
            getConfigTextField();
            clientThread.invoke(this::getAllInventoryItems);
        }
    }

    // ██████╗ ███████╗████████╗████████╗███████╗██████╗ ███████╗
    //██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
    //██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝███████╗
    //██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗╚════██║
    //╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║███████║
    //╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

    public List<HighlightingObject> getInventoryItemsToHighlight()
    {
        return inventoryItemsToHighlight;
    }
}
