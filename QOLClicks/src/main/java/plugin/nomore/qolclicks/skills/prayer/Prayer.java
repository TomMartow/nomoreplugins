package plugin.nomore.qolclicks.skills.prayer;

import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.Npcs;
import plugin.nomore.qolclicks.utils.Inventory;
import plugin.nomore.qolclicks.skills.prayer.types.Unnote;

import javax.inject.Inject;

public class Prayer
{

    @Inject
    Client client;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    QOLClicksConfig config;

    @Inject
    Inventory inventory;

    @Inject
    Npcs npcs;

    @Inject
    Unnote unnote;

    public boolean menuOptionClicked(MenuEntry event)
    {
        if (config.enableUnnoteBones()
                && event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && event.getOption().equals("Unnote"))
        {
            return unnote.menuOptionClicked(event);
        }
        return true;
    }

    public void menuEntryAdded(MenuEntryAdded event)
    {
        if (config.enableUnnoteBones()
                && event.getOpcode() == MenuOpcode.ITEM_USE.getId())
        {
            unnote.menuEntryAdded(event);
        }
    }

}
