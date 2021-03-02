package plugin.nomore.qolclicks.menu.inventory;

import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.queries.InventoryWidgetItemQuery;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.Utils;

import javax.inject.Inject;
import java.util.List;

public class Inventory
{

    @Inject
    Client client;
    
    @Inject
    QOLClicksConfig config;
    
    @Inject
    QOLClicksPlugin plugin;

    @Inject
    Utils utils;
    
    public void useItemOnItem(MenuOptionClicked e)
    {
        WidgetItem itemClicked = getFirstItem(utils.getConfigArg(0, config.itemOnItemIds()));
        WidgetItem selectedItem = getFirstItem(utils.getConfigArg(1, config.itemOnItemIds()));

        if (itemClicked == null || selectedItem == null)
        {
            return;
        }

        if (e.getId() != itemClicked.getId() && e.getActionParam() != itemClicked.getIndex())
        {
            return;
        }

        plugin.setSelected(WidgetInfo.INVENTORY, selectedItem.getIndex(), selectedItem.getId());

        MenuEntry menuEntry = new MenuEntry(
                "Use",
                "<col=ff9040>"
                        + client.getItemDefinition(itemClicked.getId()).getName()
                        + "<col=ffffff> -> <col=ff9040>"
                        + client.getItemDefinition(selectedItem.getId()).getName(),
                itemClicked.getId(),
                MenuAction.ITEM_USE_ON_WIDGET_ITEM.getId(),
                itemClicked.getIndex(),
                WidgetInfo.INVENTORY.getId(),
                false
        );

        e.setMenuEntry(menuEntry);
    }

    public WidgetItem getFirstItem(int id)
    {
        return new InventoryWidgetItemQuery()
                .idEquals(id)
                .result(client)
                .first();
    }

    public WidgetItem getLastItem(int id)
    {
        return new InventoryWidgetItemQuery()
                .idEquals(id)
                .result(client)
                .last();
    }

    public WidgetItem getItemWithIndex(int id, int index)
    {
        return new InventoryWidgetItemQuery()
                .idEquals(id)
                .indexEquals(index)
                .result(client)
                .first();
    }

    public List<WidgetItem> getItems()
    {
        return new InventoryWidgetItemQuery()
                .result(client)
                .list;
    }

}
