package plugin.nomore.nmoneclicker.skills.fishing.types;

import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import plugin.nomore.nmoneclicker.NMOneClickerPlugin;
import plugin.nomore.nmoneclicker.menu.Character;

import javax.inject.Inject;
import java.util.Set;

public class Fly
{

    @Inject
    Client client;

    @Inject
    NMOneClickerPlugin plugin;

    @Inject
    Character character;

    public boolean menuOptionClicked(MenuEntry event)
    {
        NPC npc = character.findNearestNpc("Rod Fishing spot");
        if (npc == null)
        {
            return false;
        }
        event.setIdentifier(npc.getIndex());
        event.setOpcode(MenuOpcode.NPC_FIRST_OPTION.getId());
        event.setParam0(0);
        event.setParam1(0);
        return true;
    }

    public void menuEntryAdded(MenuEntryAdded event)
    {
        if (client == null || event.isForceLeftClick())
        {
            return;
        }
        NPC npc = character.findNearestNpc("Rod Fishing spot");
        if (npc == null)
        {
            return;
        }
        MenuEntry menuEntryClone = event.clone();
        menuEntryClone.setOption("Lure");
        menuEntryClone.setTarget("<col=ffff00>" + client.getNpcDefinition(npc.getId()).getName());
        plugin.insertMenuEntry(menuEntryClone, true);
    }

}
