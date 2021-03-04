package plugin.nomore.qolclicksbeta.menu.scene;

import net.runelite.api.Client;
import net.runelite.api.queries.InventoryWidgetItemQuery;
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

}
