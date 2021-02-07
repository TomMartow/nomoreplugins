package plugin.nomore.nmoneclicker.skills.cooking;

import com.google.common.collect.ImmutableSet;
import net.runelite.api.*;
import net.runelite.api.events.Menu;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.nmoneclicker.NMOneClickerPlugin;
import plugin.nomore.nmoneclicker.menu.Inventory;
import plugin.nomore.nmoneclicker.menu.Object;

import javax.inject.Inject;
import java.util.Set;

public class Cooking
{

    @Inject
    Client client;

    @Inject
    NMOneClickerPlugin plugin;

    @Inject
    Inventory inventory;

    @Inject
    Object object;

    private static final Set<Integer> RAW_FOOD_ID = ImmutableSet.of(
            ItemID.RAW_TROUT, ItemID.RAW_SALMON, ItemID.RAW_LOBSTER
    );

    public boolean menuOptionClicked(MenuEntry event)
    {
        if (event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && RAW_FOOD_ID.contains(event.getIdentifier()))
        {
            WidgetItem widgetItem = inventory.getWidgetItem(event.getIdentifier());
            if (widgetItem == null)
            {
                System.out.println(client.getItemDefinition(event.getIdentifier()).getName());
                return false;
            }
            GameObject gameObject = object.getGameObject(26185, 26181);
            if (gameObject == null)
            {
                return false;
            }
            plugin.setSelected(WidgetInfo.INVENTORY, widgetItem.getIndex(), widgetItem.getId());
            event.setIdentifier(gameObject.getId());
            object.setParams(event, gameObject);
            object.useItemOnObject(event);
        }
        return true;
    }

    public void menuEntryAdded(MenuEntryAdded event)
    {
        if (event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && RAW_FOOD_ID.contains(event.getIdentifier()))
        {
            if (client == null || event.isForceLeftClick())
            {
                return;
            }
            WidgetItem widgetItem = inventory.getWidgetItem(event.getIdentifier());
            if (widgetItem == null)
            {
                return;
            }
            GameObject gameObject = object.getGameObject(26185, 26181);
            if (gameObject == null)
            {
                return;
            }
            MenuEntry menuEntryClone = event.clone();
            menuEntryClone.setTarget("<col=ff9040>" + client.getItemDefinition(widgetItem.getId()).getName() + "<col=ffffff> -> <col=ffff>" + client.getObjectDefinition(gameObject.getId()).getName());
            plugin.insertMenuEntry(menuEntryClone, true);
        }
    }

}
