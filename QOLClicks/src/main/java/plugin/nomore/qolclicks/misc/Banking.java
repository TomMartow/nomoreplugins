package plugin.nomore.qolclicks.misc;

import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.menu.Menu;
import plugin.nomore.qolclicks.utils.Display;
import plugin.nomore.qolclicks.utils.Objects;

import javax.inject.Inject;

public class Banking
{

    @Inject
    Client client;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    Menu menu;

    @Inject
    Display display;

    @Inject
    Objects objects;

    public boolean menuOptionClicked(MenuEntry event)
    {
        if (event.getOpcode() == MenuOpcode.CC_OP.getId()
                && event.getOption().equals("Bank"))
        {
            GameObject object = objects.getClosestGameObjectWithAction("Bank");
            if (object == null)
            {
                return false;
            }
            menu.interactWithGameObject("Bank",
                    "<col=ffff>" + client.getObjectDefinition(object.getId()).getName(),
                    object,
                    event);
        }
        return true;
    }

    public boolean menuEntryAdded(MenuEntryAdded event)
    {
        if (event.getOpcode() == MenuOpcode.CC_OP.getId()
                && event.getOption().equals("Inventory"))
        {
            if (client == null || event.isForceLeftClick())
            {
                return false;
            }
            GameObject object = objects.getClosestGameObjectWithAction("Bank");
            if (object == null)
            {
                return false;
            }
            event.setOption("Bank");
            event.setTarget("<col=ffff00>" + client.getObjectDefinition(object.getId()).getName());
            plugin.insertMenuEntry(event, false);
        }
        return true;
    }

}
