package plugin.nomore.qolclicksbeta.menu.scene;

import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.queries.NPCQuery;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicksbeta.QOLClicksBetaConfig;
import plugin.nomore.qolclicksbeta.QOLClicksBetaPlugin;
import plugin.nomore.qolclicksbeta.utils.Utils;

import javax.inject.Inject;

public class Npc
{

    @Inject
    private Client client;

    @Inject
    private QOLClicksBetaConfig config;

    @Inject
    private QOLClicksBetaPlugin plugin;

    @Inject
    private Inventory inventory;

    @Inject
    private Utils utils;

    public void interactWithNPC(MenuOptionClicked e)
    {
        /*
        WidgetItem itemClicked = inventory.getFirstItem(utils.getConfigArg(0, config.npcFirstOptionIds()));
        NPC npcToInteractWith = getClosestNpc(utils.getConfigArg(1, config.npcFirstOptionIds()));

        if (itemClicked == null || npcToInteractWith == null)
        {
            return;
        }

        if (e.getId() != itemClicked.getId() && e.getActionParam() != itemClicked.getIndex())
        {
            return;
        }

        MenuEntry menuEntry = new MenuEntry(
                config.npcOption(),
                "<col=ffff00>" + client.getNpcDefinition(npcToInteractWith.getId()).getName(),
                npcToInteractWith.getIndex(),
                MenuAction.NPC_FIRST_OPTION.getId(),
                0,
                0,
                false
        );

        e.setMenuEntry(menuEntry);
        plugin.setQOLClick(true);

         */
    }

    public void useItemOnNPC(MenuOptionClicked e)
    {

    }

    public NPC getClosestNpc(int... ids)
    {
        if (client.getLocalPlayer() == null)
        {
            return null;
        }
        return new NPCQuery()
                .idEquals(ids)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    public NPC getClosestNpcThatNameEquals(String... namesEqual)
    {
        if (client.getLocalPlayer() == null)
        {
            return null;
        }
        return new NPCQuery()
                .nameEquals(namesEqual)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    public NPC getClosestNpcThatNameContains(String... namesContain)
    {
        if (client.getLocalPlayer() == null)
        {
            return null;
        }
        return new NPCQuery()
                .nameContains(namesContain)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

}
