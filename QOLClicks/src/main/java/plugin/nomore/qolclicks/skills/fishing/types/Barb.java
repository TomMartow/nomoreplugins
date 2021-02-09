package plugin.nomore.qolclicks.skills.fishing.types;

import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuEntryAdded;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.menu.Menu;
import plugin.nomore.qolclicks.utils.Npcs;

import javax.inject.Inject;

public class Barb
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
        NPC npc = npcs.getClosestMatching(10096);
        if (npc == null)
        {
            return false;
        }
        menu.interactWithNpc("Use-rod",
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
        NPC npc = npcs.getClosestMatching(10096);
        if (npc == null)
        {
            return;
        }
        event.setOption("Fish");
        event.setTarget("<col=ffff00>" + client.getNpcDefinition(npc.getId()).getName());
        plugin.insertMenuEntry(event, true);
    }

}
