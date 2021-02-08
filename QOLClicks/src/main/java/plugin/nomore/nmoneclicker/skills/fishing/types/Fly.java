package plugin.nomore.nmoneclicker.skills.fishing.types;

import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import plugin.nomore.nmoneclicker.QOLClicksPlugin;
import plugin.nomore.nmoneclicker.menu.Character;

import javax.inject.Inject;

public class Fly
{

    @Inject
    Client client;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    Character character;

    public boolean menuOptionClicked(MenuEntry event)
    {
        NPC npc = character.findNearestNpc("Rod Fishing spot");
        if (npc == null)
        {
            return false;
        }
        event.setOption("Lure");
        event.setTarget("<col=ffff00>" + client.getNpcDefinition(npc.getId()).getName());
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
        event.setOption("Lure");
        event.setTarget("<col=ffff00>" + client.getNpcDefinition(npc.getId()).getName());
        plugin.insertMenuEntry(event, true);
    }

}
