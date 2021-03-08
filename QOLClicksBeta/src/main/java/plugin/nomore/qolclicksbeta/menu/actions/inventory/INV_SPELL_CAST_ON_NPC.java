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

public class INV_SPELL_CAST_ON_NPC
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

        WidgetItem itemClicked = inventory.getFirstItem(utils.getConfigInt(0, config.INV_SPELL_CAST_ON_NPC_CONFIG_STRING()));

        if (itemClicked == null)
        {
            return;
        }

        NPC npcToCastSpellOn = npc.getClosestNpc(utils.getConfigInt(1, config.INV_SPELL_CAST_ON_NPC_CONFIG_STRING()));

        if (npcToCastSpellOn == null)
        {
            return;
        }

        plugin.setSelectSpell(config.INV_SPELL_CAST_ON_NPC_SPELL().getSpell());

        MenuEntry menuEntry = new MenuEntry(
                "Cast",
                "<col=00ff00>" + config.INV_SPELL_CAST_ON_NPC_SPELL().getName()
                        + "</col><col=ffffff> -> <col=ffff00>"
                        + client.getNpcDefinition(npcToCastSpellOn.getId()).getName(),
                npcToCastSpellOn.getIndex(),
                MenuAction.SPELL_CAST_ON_NPC.getId(),
                0,
                0,
                false
        );

        e.setMenuEntry(menuEntry);
        plugin.setQolClick(true);
    }
}
