package plugin.nomore.nmoneclicker.skills.firemaking;

import com.google.common.collect.ImmutableSet;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.nmoneclicker.QOLClicksPlugin;
import plugin.nomore.nmoneclicker.menu.Inventory;

import javax.inject.Inject;
import java.util.Set;

public class Firemaking
{

    @Inject
    Client client;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    Inventory inventory;

    private static final Set<Integer> LOG_ID = ImmutableSet.of(
            ItemID.LOGS, ItemID.OAK_LOGS, ItemID.WILLOW_LOGS, ItemID.TEAK_LOGS,
            ItemID.MAPLE_LOGS, ItemID.MAHOGANY_LOGS, ItemID.YEW_LOGS, ItemID.MAGIC_LOGS,
            ItemID.REDWOOD_LOGS
    );

    public boolean menuOptionClicked(MenuEntry event)
    {
        if (event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && LOG_ID.contains(event.getIdentifier()))
        {
            WidgetItem widgetItem = inventory.getWidgetItem("Tinderbox");
            if (widgetItem == null)
            {
                return false;
            }
            plugin.setSelected(WidgetInfo.INVENTORY, widgetItem.getIndex(), widgetItem.getId());
            inventory.useItemOnItem(event);
        }
        return true;
    }

    public boolean menuEntryAdded(MenuEntryAdded event)
    {
        if (event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && LOG_ID.contains(event.getIdentifier()))
        {
            if (client == null || event.isForceLeftClick())
            {
                return false;
            }
            WidgetItem tinderBox = inventory.getWidgetItem("Tinderbox");
            WidgetItem log = inventory.getWidgetItem(event.getIdentifier());
            if (tinderBox == null || log == null)
            {
                return false;
            }
            MenuEntry menuEntryClone = event.clone();
            menuEntryClone.setTarget("<col=ff9040>" + client.getItemDefinition(tinderBox.getId()).getName() + "<col=ffffff> -> " + client.getItemDefinition(log.getId()).getName());
            plugin.insertMenuEntry(menuEntryClone, true);
        }
        return true;
    }

}
