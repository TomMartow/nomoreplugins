package plugin.nomore.nmoneclicker.misc;

import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.menus.MenuManager;
import plugin.nomore.nmoneclicker.NMOneClickerPlugin;
import plugin.nomore.nmoneclicker.menu.Display;
import plugin.nomore.nmoneclicker.menu.Object;

import javax.inject.Inject;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Banking
{

    @Inject
    Client client;

    @Inject
    NMOneClickerPlugin plugin;

    @Inject
    Display display;

    @Inject
    Object object;

    private static final int[] BANK_IDS = {
            ObjectID.BANK_BOOTH_10355,
            ObjectID.BANK_BOOTH_10355
    };

    public boolean menuOptionClicked(MenuEntry event)
    {
        if (event.getOpcode() == MenuOpcode.CC_OP.getId()
                && event.getOption().equals("Bank"))
        {
            GameObject bank = object.getGameObject(BANK_IDS);
            if (bank == null)
            {
                return false;
            }
            event.setIdentifier(bank.getId());
            event.setOpcode(MenuOpcode.GAME_OBJECT_SECOND_OPTION.getId());
            object.setParams(event, bank);
            event.setForceLeftClick(false);
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
            GameObject bank = object.getGameObject(BANK_IDS);
            if (bank == null)
            {
                return false;
            }
            MenuEntry bankE = createMenuEntry("Bank",
                    "<col=ffff>" + client.getObjectDefinition(bank.getId()).getName(),
                    bank.getId(),
                    MenuOpcode.GAME_OBJECT_SECOND_OPTION,
                    bank.getSceneMinLocation().getX(),
                    bank.getSceneMinLocation().getY(),
                    false);
            MenuEntry cancelE = createMenuEntry("Cancel", "", 1, MenuOpcode.CANCEL, -1, display.getInventoryTabID(), false);
            MenuEntry inventoryE = createMenuEntry("Inventory", "", 1, MenuOpcode.CC_OP, -1, display.getInventoryTabID(), false);
            client.setMenuEntries(new MenuEntry[]{cancelE, bankE, inventoryE});
        }
        return true;
    }

    public MenuEntry createMenuEntry(String option, String target, int id, MenuOpcode opcode, int p0, int p1, boolean forceLeft)
    {
        return new MenuEntry(option, target, id, opcode.getId(), p0, p1, forceLeft);
    }

}
