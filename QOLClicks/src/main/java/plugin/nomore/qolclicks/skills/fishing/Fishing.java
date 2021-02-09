package plugin.nomore.qolclicks.skills.fishing;

import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.skills.fishing.types.Barb;
import plugin.nomore.qolclicks.skills.fishing.types.Cage;
import plugin.nomore.qolclicks.skills.fishing.types.Fly;

import javax.inject.Inject;

public class Fishing
{

    @Inject
    Client client;

    @Inject
    QOLClicksConfig config;

    @Inject
    Fly fly;

    @Inject
    Cage cage;

    @Inject
    Barb barb;


    public boolean menuOptionClicked(MenuEntry event)
    {
        if (config.enableFishingRod()
                && event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && event.getOption().equals("Lure")
                && !fly.menuOptionClicked(event))
        {
            return false;
        }
        if (config.enableLobsterPot()
                && event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && event.getOption().equals("Cage")
                && !cage.menuOptionClicked(event))
        {
            return false;
        }
        if (config.enableBarbarianRod()
                && event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && event.getOption().equals("Fish")
                && !barb.menuOptionClicked(event))
        {
            return false;
        }
        return true;
    }

    public void menuEntryAdded(MenuEntryAdded event)
    {
        if (config.enableFishingRod()
                && event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && client.getItemDefinition(event.getIdentifier()).getName().equalsIgnoreCase("Fly fishing rod"))
        {
            fly.menuEntryAdded(event);
        }
        if (config.enableLobsterPot()
                && event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && client.getItemDefinition(event.getIdentifier()).getName().equalsIgnoreCase("Lobster pot"))
        {
            cage.menuEntryAdded(event);
        }
        if (config.enableBarbarianRod()
                && event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && client.getItemDefinition(event.getIdentifier()).getName().equalsIgnoreCase("Barbarian rod"))
        {
            barb.menuEntryAdded(event);
        }
    }

}
