package plugin.nomore.qolclicks.skills.fishing.types;

import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.Menu;
import plugin.nomore.qolclicks.utils.Npcs;

import javax.inject.Inject;

public class Fly
{

    @Inject
    Client client;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    Npcs npcs;

    @Inject
    Menu menu;

    public boolean menuOptionClicked(MenuEntry event)
    {
        NPC npc = npcs.getClosestMatchingName("Rod Fishing spot");
        if (npc == null)
        {
            return false;
        }
        menu.interactWithNpc("Lure",
                "<col=ffff00>" + client.getNpcDefinition(npc.getId()).getName(),
                npc,
                event);
        return true;
    }

    public void menuEntryAdded(MenuEntryAdded event)
    {
        if (client == null || event.isForceLeftClick())
        {
            return;
        }
        NPC npc = npcs.getClosestMatchingName("Rod Fishing spot");
        if (npc == null)
        {
            return;
        }
        event.setOption("Lure");
        event.setTarget("<col=ffff00>" + client.getNpcDefinition(npc.getId()).getName());
        plugin.insertMenuEntry(event, true);
    }

}
