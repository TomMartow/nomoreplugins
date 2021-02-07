package plugin.nomore.nmoneclicker.menu;

import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory
{

    @Inject
    Client client;

    public void useItemOnItem(MenuEntry event)
    {
        event.setOpcode(MenuOpcode.ITEM_USE_ON_WIDGET_ITEM.getId());
    }

    public void dropItem(MenuEntry event) { event.setOpcode(WidgetInfo.INVENTORY.getId()); }

    public WidgetItem getWidgetItem(String itemName)
    {
        Widget widget = client.getWidget(WidgetInfo.INVENTORY);
        if (widget == null)
        {
            return null;
        }
        for (WidgetItem item : widget.getWidgetItems())
        {
            if (item != null
                    && client.getItemDefinition(item.getId()).getName().equalsIgnoreCase(itemName))
            {
                return item;
            }
        }
        return null;
    }

    public WidgetItem getWidgetItem(int id)
    {
        Widget widget = client.getWidget(WidgetInfo.INVENTORY);
        if (widget == null)
        {
            return null;
        }
        for (WidgetItem item : widget.getWidgetItems())
        {
            if (item != null
                    && id == item.getId())
            {
                return item;
            }
        }
        return null;
    }

    public List<WidgetItem> getWidgetItems()
    {
        Widget widget = client.getWidget(WidgetInfo.INVENTORY);
        if (widget == null)
        {
            return null;
        }
        return getWidgetItems()
                .stream()
                .filter(widgetItem -> widgetItem != null
                        && widgetItem.getId() != -1)
                .collect(Collectors.toList());
    }

}
