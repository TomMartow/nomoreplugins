package plugin.nomore.qolclicks.utils;

import joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.queries.InventoryWidgetItemQuery;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.QOLClicksPlugin;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Slf4j
public class Inventory implements Runnable
{

    @Inject
    Client client;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    QOLClicksConfig config;

    @Inject
    StringUtils string;

    @Inject
    Sleep sleep;

    @Inject
    Menu menu;

    @Inject
    Mouse mouse;

    @Inject
    MousePosition mousePos;

    @Inject
    Random random;

    public static List<WidgetItem> itemsToDrop = null;
    public void setItemsToDrop(List<WidgetItem> itemList) { itemsToDrop = itemList; }
    public List<WidgetItem> getItemsToDrop() { return itemsToDrop; }

    public List<WidgetItem> getWidgetItemsThatMatchesConfigItemName()
    {
        List<WidgetItem> items = new ArrayList<>();
        for (WidgetItem item : getItems())
        {
            if (item == null)
            {
                continue;
            }
            for (String configItemName : string.removeWhiteSpaces(config.itemsToDrop()).split(","))
            {
                if (configItemName.equals(string.removeWhiteSpaces(client.getItemDefinition(item.getId()).getName())))
                {
                    items.add(item);
                }
            }
        }
        return items;
    }

    public void dropAll()
    {
        List<WidgetItem> items = getItems();
        if (items == null)
        {
            System.out.println("The items are null.");
            return;
        }
        plugin.executor.submit(() ->
        {
            try
            {
                plugin.setPaused(true);
                for (WidgetItem item : items)
                {
                    System.out.println(item.getId());
                    menu.setMenuEntry(menu.dropItem(item));
                    mouse.clickInstant(mousePos.getClickPoint(item.getCanvasBounds()));
                    sleep.forXMillis(random.getIntBetween(config.dropMinTime(), config.dropMaxTime()));
                }
                plugin.setPaused(false);
            }
            catch (RuntimeException e)
            {
                plugin.setPaused(false);
                log.info("Error");
            }
        });
    }

    public void dropItems(String... itemName)
    {
        dropItems(getItems(itemName));
    }

    public void dropItems(int... itemId)
    {
        dropItems(getItems(itemId));
    }

    public void dropItems(List<WidgetItem> items)
    {
        if (items == null)
        {
            System.out.println("The items are null.");
            return;
        }
        plugin.setPaused(true);
        plugin.executor.submit(() ->
        {
            try
            {
                for (WidgetItem item : items)
                {
                    if (item == null)
                    {
                        continue;
                    }
                    log.info("Dropping item in slot " + item.getIndex());
                    menu.setMenuEntry(menu.dropItem(item));
                    mouse.clickInstant(mousePos.getClickPoint(item.getCanvasBounds()));
                    sleep.forXMillis(random.getIntBetween(250, 800));
                }
                setItemsToDrop(null);
            }
            catch (RuntimeException e)
            {
                log.info("Error");
                setItemsToDrop(null);
            }
        });
    }

    public WidgetItem getItem(String itemName)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> string.removeWhiteSpaces(itemName)
                        .equalsIgnoreCase(string.removeWhiteSpaces(client.getItemDefinition(i.getId())
                                .getName())))
                .findFirst()
                .orElse(null);
    }

    public WidgetItem getItem(int itemId)
    {
        return new InventoryWidgetItemQuery()
                .idEquals(itemId)
                .result(client).first();
    }

    public List<WidgetItem> getItems()
    {
        Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
        if (inventoryWidget == null)
        {
            return null;
        }
        return new ArrayList<>(inventoryWidget.getWidgetItems());
    }

    public List<WidgetItem> getItems(String... itemNames)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> Arrays.stream(itemNames)
                        .anyMatch(s -> s.equalsIgnoreCase(client.getItemDefinition(i.getId())
                                .getName())))
                .collect(Collectors.toList());
    }

    public List<WidgetItem> getItems(int... itemIds)
    { ;
        return getItems().stream()
                .filter(item -> item != null
                        && Arrays.stream(itemIds)
                        .anyMatch(id -> id == item.getId()))
                .collect(Collectors.toList());
    }

    public List<WidgetItem> getItemsNotMatching(String... itemNames)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> Arrays.stream(itemNames)
                        .noneMatch(s -> s.equalsIgnoreCase(client.getItemDefinition(i.getId())
                                .getName())))
                .collect(Collectors.toList());
    }

    public List<WidgetItem> getItemsNotMatching(int... itemIds)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> Arrays.stream(itemIds)
                        .noneMatch(s -> s == i.getId()))
                .collect(Collectors.toList());
    }

    public boolean contains(String... itemNames)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .anyMatch(i -> Arrays.stream(itemNames)
                        .anyMatch(s -> s.equalsIgnoreCase(client.getItemDefinition(i.getId())
                                .getName())));
    }

    public boolean contains(int... itemIds)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .anyMatch(i -> Arrays.stream(itemIds)
                        .anyMatch(s -> s == i.getId()));
    }

    public WidgetItem getItemThatContains(String itemName)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> string.removeWhiteSpaces(itemName)
                        .contains(string.removeWhiteSpaces(client.getItemDefinition(i.getId())
                                .getName())))
                .findFirst()
                .orElse(null);
    }

    public List<WidgetItem> getItemsThatContains(String itemName)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> string.removeWhiteSpaces(itemName)
                        .contains(string.removeWhiteSpaces(client.getItemDefinition(i.getId())
                                .getName())))
                .collect(Collectors.toList());
    }

    public WidgetItem getItemWithAction(String action)
    {
        for (WidgetItem item : getItems())
        {
            if (item == null)
            {
                continue;
            }
            for (String inventoryAction : client.getItemDefinition(item.getId()).getInventoryActions())
            {
                if (Strings.isNullOrEmpty(inventoryAction))
                {
                    continue;
                }
                if (inventoryAction.equalsIgnoreCase(action))
                {
                    return item;
                }
            }
        }
        return null;
    }

    public List<WidgetItem> getItemsWithAction(String action)
    {
        List<WidgetItem> items = new ArrayList<>();
        for (WidgetItem item : getItems())
        {
            if (item == null)
            {
                continue;
            }
            for (String inventoryAction : client.getItemDefinition(item.getId()).getInventoryActions())
            {
                if (Strings.isNullOrEmpty(inventoryAction))
                {
                    continue;
                }
                if (inventoryAction.equalsIgnoreCase(action))
                {
                    items.add(item);
                }
            }
        }
        return items;
    }

    public WidgetItem getItemInSlotIfMatches(int id, int slotNumber)
    {
        // The first slot is 0, the slot next on the right is 1. The bottom right slot is 27.
        return getItems()
                .stream()
                .filter(item -> item != null
                        && item.getId() == id
                        && item.getIndex() == slotNumber)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void run()
    {

    }
}
