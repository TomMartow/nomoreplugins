package plugin.nomore.qolclicksbeta.menu.actions.inventory;

import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicksbeta.QOLClicksBetaConfig;
import plugin.nomore.qolclicksbeta.QOLClicksBetaPlugin;
import plugin.nomore.qolclicksbeta.menu.scene.GameObj;
import plugin.nomore.qolclicksbeta.menu.scene.Inventory;
import plugin.nomore.qolclicksbeta.menu.scene.Npc;
import plugin.nomore.qolclicksbeta.utils.Utils;

import javax.inject.Inject;

public class INV_ITEM_USE_ON_NPC
{

    @Inject
    Client client;

    @Inject
    QOLClicksBetaPlugin plugin;

    @Inject
    QOLClicksBetaConfig config;

    @Inject
    Inventory inventory;

    @Inject
    GameObj gameObj;

    @Inject
    Npc npc;

    @Inject
    Utils utils;

    public void check(MenuOptionClicked e)
    {
        WidgetItem itemClicked = inventory.getItemInSlot(utils.getConfigInt(0, config.INV_ITEM_USE_ON_NPC_CONFIG_STRING()), e.getActionParam());
        NPC npcToUseItemOn = npc.getClosestNpc(utils.getConfigInt(1, config.INV_ITEM_USE_ON_NPC_CONFIG_STRING()));
        if (itemClicked == null
                || npcToUseItemOn == null)
        {
            return;
        }

        if (itemClicked.getId() != e.getId()
                || itemClicked.getIndex() != e.getActionParam())
        {
            return;
        }

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
        plugin.setQOLClick(true);
    }

}
