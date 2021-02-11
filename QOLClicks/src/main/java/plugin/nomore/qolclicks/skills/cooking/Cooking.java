package plugin.nomore.qolclicks.skills.cooking;

import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.Menu;
import plugin.nomore.qolclicks.utils.Inventory;
import plugin.nomore.qolclicks.utils.Objects;

import javax.inject.Inject;

public class Cooking
{

    @Inject
    Client client;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    Inventory inventory;

    @Inject
    Objects objects;

    @Inject
    Menu menu;

    public boolean menuOptionClicked(MenuEntry event)
    {
        if (event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && event.getOption().equalsIgnoreCase("Cook"))
        {
            WidgetItem widgetItem = inventory.getItem(event.getIdentifier());
            if (widgetItem == null)
            {
                return false;
            }
            GameObject gameObject = objects.getClosestGameObjectMatching("Fire");
            if (gameObject == null)
            {
                gameObject = objects.getClosestGameObjectWithAction("Cook");
                if (gameObject == null)
                {
                    return false;
                }
            }
            menu.useItemOnGameObject("Use",
                    "<col=ff9040>"
                            + client.getItemDefinition(widgetItem.getId()).getName()
                            + "<col=ffffff> -> <col=ffff>"
                            + client.getObjectDefinition(gameObject.getId()).getName(),
                    gameObject,
                    widgetItem,
                    event);
        }
        return true;
    }

    public void menuEntryAdded(MenuEntryAdded event)
    {
        if (event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && client.getItemDefinition(event.getIdentifier()).getName().contains("Raw"))
        {
            if (client == null || event.isForceLeftClick())
            {
                return;
            }
            WidgetItem widgetItem = inventory.getItem(event.getIdentifier());
            if (widgetItem == null)
            {
                return;
            }
            GameObject gameObject = objects.getClosestGameObjectMatching("Fire", "Range");
            if (gameObject == null)
            {
                return;
            }
            event.setOption("Cook");
            event.setTarget("<col=ffff00>" + client.getItemDefinition(widgetItem.getId()).getName());
            plugin.insertMenuEntry(event, true);
        }
    }

}
