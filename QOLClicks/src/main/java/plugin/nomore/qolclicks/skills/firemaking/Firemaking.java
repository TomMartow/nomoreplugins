package plugin.nomore.qolclicks.skills.firemaking;

import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.Inventory;
import plugin.nomore.qolclicks.utils.Menu;

import javax.inject.Inject;

public class Firemaking
{

    @Inject
    Client client;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    Inventory inventory;

    @Inject
    Menu menu;

    public boolean menuOptionClicked(MenuEntry event)
    {
        if (event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && event.getOption().equalsIgnoreCase("Burn"))
        {
            WidgetItem itemClicked = inventory.getItemInSlotIfMatches(event.getIdentifier(), event.getParam0());
            if (itemClicked == null)
            {
                return false;
            }
            WidgetItem itemToBeUsedOn = inventory.getItemThatContains("Tinderbox");
            if (itemToBeUsedOn == null)
            {
                return false;
            }
            menu.useItemOnItem("Use",
                    "<col=ff9040>" + client.getItemDefinition(event.getIdentifier()).getName() + "<col=ffffff> -> <col=ff9040>" + client.getItemDefinition(itemToBeUsedOn.getId()).getName(),
                    itemClicked,
                    itemToBeUsedOn,
                    event);
        }
        return true;
    }

    public void menuEntryAdded(MenuEntryAdded event)
    {
        if (event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && client.getItemDefinition(event.getIdentifier()).getName().toLowerCase().contains("logs"))
        {
            if (client == null || event.isForceLeftClick())
            {
                return;
            }
            WidgetItem itemToBeUsedOn = inventory.getItemThatContains("Tinderbox");
            if (itemToBeUsedOn == null)
            {
                return;
            }
            event.setOption("Burn");
            event.setTarget("<col=ffff00>" + client.getItemDefinition(event.getIdentifier()).getName());
            plugin.insertMenuEntry(event, true);
        }
    }

}
