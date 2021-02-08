package plugin.nomore.nmoneclicker.skills.fishing;

import com.google.common.collect.ImmutableSet;
import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import plugin.nomore.nmoneclicker.skills.fishing.types.Cage;
import plugin.nomore.nmoneclicker.skills.fishing.types.Fly;

import javax.inject.Inject;
import java.util.Set;

public class Fishing
{

    @Inject
    Client client;

    @Inject
    Fly fly;

    @Inject
    Cage cage;

    private static final Set<Integer> FLY_FISHING_IDS = ImmutableSet.of(
            ItemID.FLY_FISHING_ROD, ItemID.PEARL_FLY_FISHING_ROD
    );
    private static final Integer CAGE_FISHING_ID = ItemID.LOBSTER_POT;

    public boolean menuOptionClicked(MenuEntry event)
    {
        if (event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && FLY_FISHING_IDS.contains(event.getIdentifier())
                && event.getOption().equals("Lure"))
        {
            return fly.menuOptionClicked(event);
        }
        if (event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && CAGE_FISHING_ID == event.getIdentifier()
                && event.getOption().equals("Cage"))
        {
            return cage.menuOptionClicked(event);
        }
        return true;
    }

    public void menuEntryAdded(MenuEntryAdded event)
    {
        if (event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && FLY_FISHING_IDS.contains(event.getIdentifier()))
        {
            fly.menuEntryAdded(event);
        }
        if (event.getOpcode() == MenuOpcode.ITEM_USE.getId()
                && CAGE_FISHING_ID == event.getIdentifier())
        {
            cage.menuEntryAdded(event);
        }
    }

}
