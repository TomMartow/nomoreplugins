package plugin.nomore.qolclicks.menu.scene;

import net.runelite.api.Client;
import net.runelite.api.queries.InventoryWidgetItemQuery;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;

import javax.inject.Inject;
import java.util.List;

public class Inventory
{

    @Inject
    Client client;

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

    public WidgetItem getItemInSlot(int id, int index)
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

    public boolean isOpen()
    {
        if (client.getWidget(WidgetInfo.INVENTORY) == null)
        {
            return false;
        }
        return !client.getWidget(WidgetInfo.INVENTORY).isHidden();
    }

    public boolean isFull()
    {
        return getEmptySlots() <= 0;
    }

    public boolean isEmpty()
    {
        return getEmptySlots() >= 28;
    }

    public int getEmptySlots()
    {
        Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
        if (inventoryWidget != null)
        {
            return 28 - inventoryWidget.getWidgetItems().size();
        }
        else
        {
            return -1;
        }
    }

}
