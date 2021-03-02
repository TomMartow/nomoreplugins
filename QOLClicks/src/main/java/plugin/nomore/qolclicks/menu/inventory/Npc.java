package plugin.nomore.qolclicks.menu.inventory;

import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.queries.NPCQuery;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.Utils;

import javax.inject.Inject;

public class Npc
{

    @Inject
    private Client client;

    @Inject
    private QOLClicksConfig config;

    @Inject
    private QOLClicksPlugin plugin;

    @Inject
    private Inventory inventory;

    @Inject
    private Utils utils;

    public void interactWithNPC(MenuOptionClicked e)
    {
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
    }

    public void useItemOnNPC(MenuOptionClicked e)
    {
        WidgetItem itemClicked = inventory.getFirstItem(utils.getConfigArg(0, config.useItemOnNpcIds()));
        NPC npcToUseItemOn = getClosestNpc(utils.getConfigArg(1, config.useItemOnNpcIds()));
        if (itemClicked == null || npcToUseItemOn == null)
        {
            return;
        }

        if (e.getId() != itemClicked.getId() && e.getActionParam() != itemClicked.getIndex())
        {
            return;
        }

        plugin.setSelected(WidgetInfo.INVENTORY, itemClicked.getIndex(), itemClicked.getId());

        MenuEntry menuEntry = new MenuEntry(
                "Use",
                "<col=ff9040>"
                        + client.getItemDefinition(itemClicked.getId()).getName()
                        + "<col=ffffff> -> <col=ffff00>"
                        + client.getNpcDefinition(npcToUseItemOn.getId()).getName(),
                npcToUseItemOn.getIndex(),
                MenuAction.ITEM_USE_ON_NPC.getId(),
                0,
                0,
                false
        );

        e.setMenuEntry(menuEntry);
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
