package plugin.nomore.qolclicks.utils.scene;

import joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.queries.InventoryWidgetItemQuery;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.menus.MenuManager;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.automation.Mouse;
import plugin.nomore.qolclicks.utils.menu.Clicked;
import plugin.nomore.qolclicks.utils.automation.Random;
import plugin.nomore.qolclicks.utils.automation.Format;
import plugin.nomore.qolclicks.utils.menu.TargetMenus;
import plugin.nomore.qolclicks.utils.scene.builds.InventoryItem;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Inventory
{

    @Inject private Client client;
    @Inject private QOLClicksPlugin plugin;
    @Inject private QOLClicksConfig config;
    @Inject private Format format;
    @Inject private Clicked clicked;
    @Inject private Random random;
    @Inject private MenuManager menuManager;
    @Inject private TargetMenus targetMenus;
    @Inject private Mouse mouse;

    public void dropItem(InventoryItem inventoryItem)
    {
        menuManager.addPriorityEntry("drop", inventoryItem.getName());
        menuManager.addPriorityEntry("release", inventoryItem.getName());
        menuManager.addPriorityEntry("destroy", inventoryItem.getName());
        new Thread(() ->
        {
            plugin.setTargetMenu(targetMenus.createDropItem(inventoryItem));
            mouse.clickC(inventoryItem.getItem().getCanvasBounds());
        }).start();
        menuManager.removePriorityEntry("drop", inventoryItem.getName());
        menuManager.removePriorityEntry("release", inventoryItem.getName());
        menuManager.removePriorityEntry("destroy", inventoryItem.getName());
    }

    public void dropItems(List<InventoryItem> items)
    {
        for (InventoryItem inventoryItem : items)
        {
            menuManager.addPriorityEntry("drop", inventoryItem.getName());
            menuManager.addPriorityEntry("release", inventoryItem.getName());
            menuManager.addPriorityEntry("destroy", inventoryItem.getName());
        }
        plugin.setIterating(true);
        new Thread(() ->
        {
            for (InventoryItem inventoryItem : items)
            {
                plugin.setTargetMenu(targetMenus.createDropItem(inventoryItem));
                mouse.clickC(inventoryItem.getItem().getCanvasBounds());
                try
                {
                    Thread.sleep(random.getRandomIntBetweenRange(config.dropMinTime(), config.dropMaxTime()));
                }
                catch (InterruptedException e)
                {
                    plugin.setIterating(false);
                    e.printStackTrace();
                }
            }
            plugin.setIterating(false);
        }).start();
        for (InventoryItem inventoryItem : items)
        {
            menuManager.removePriorityEntry("drop", inventoryItem.getName());
            menuManager.removePriorityEntry("release", inventoryItem.getName());
            menuManager.removePriorityEntry("destroy", inventoryItem.getName());
        }
    }

    public List<InventoryItem> sortDropListOrder(List<InventoryItem> items)
    {
        String[] dropOrder = format.string(config.dropOrder()).split(",");
        List<InventoryItem> sortedDropItems = new ArrayList<>();
        for (int i = 0; i < dropOrder.length; i++)
        {
            try
            {
                InventoryItem item = items.get(Integer.parseInt(dropOrder[i]));
                if (item == null)
                {
                    continue;
                }
                //log.info(" Name: " + item.getName() + " DropOrder: " + dropOrder[i] + " Index: " + item.getItem().getIndex());
                sortedDropItems.add(item);
            }
            catch (IndexOutOfBoundsException e)
            {
                log.info("NO: " + dropOrder[i]);
            }
        }
        return sortedDropItems;
    }

    public WidgetItem getItem(String itemName)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> format.string(itemName)
                        .equalsIgnoreCase(format.string(client.getItemDefinition(i.getId())
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

    public WidgetItem getLastItem(String... itemNames)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .list
                .stream()
                .sorted(Collections.reverseOrder())
                .filter(i -> i != null
                        && Arrays.stream(itemNames)
                        .anyMatch(s -> s.equalsIgnoreCase(client.getItemDefinition(i.getId())
                                .getName())))
                .findFirst()
                .orElse(null);
    }

    public WidgetItem getLastItem(int... itemId)
    {
        return new InventoryWidgetItemQuery()
                .idEquals(itemId)
                .result(client)
                .last();
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
        Widget inventory = client.getWidget(WidgetInfo.INVENTORY);
        if (inventory == null)
        {
            return null;
        }
        return inventory
                .getWidgetItems()
                .stream()
                .filter(i -> i != null
                        && Arrays.stream(itemNames)
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
                .filter(i -> format.string(itemName)
                        .contains(format.string(client.getItemDefinition(i.getId())
                                .getName())))
                .findFirst()
                .orElse(null);
    }

    public List<WidgetItem> getItemsThatContains(String itemName)
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .stream()
                .filter(i -> format.string(itemName)
                        .contains(format.string(client.getItemDefinition(i.getId())
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
}
