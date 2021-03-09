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

public class INV_NPC_FOURTH_OPTION
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
        int itemClickedId = e.getId();
        int itemClickedSlot = e.getActionParam();

        WidgetItem itemClicked = null;
        NPC npcToInteractWith = null;

        String fullConfigString = utils.rws(config.INV_NPC_FOURTH_OPTION_CONFIG_STRING());
        String[] fullSplitConfigString = fullConfigString.split(",");

        for (String individualConfigString : fullSplitConfigString)
        {
            String[] individualPart = new String[]{"-1", "-1"};
            String[] individualSplitConfigString = individualConfigString.split(":");

            try
            {
                individualPart[0] = individualSplitConfigString[0];
                individualPart[1] = individualSplitConfigString[1];
            }
            catch (Exception exc)
            {
                individualPart[0] = "-1";
                individualPart[1] = "-1";
            }

            int id1 = Integer.parseInt(individualPart[0]);
            int id2 = Integer.parseInt(individualPart[1]);

            if (id1 == -1 || id2 == -1)
            {
                continue;
            }

            if (id1 == itemClickedId)
            {
                itemClicked = inventory.getItemInSlot(itemClickedId, itemClickedSlot);
                npcToInteractWith = npc.getClosestNpc(id2);
                break;
            }
        }

        if (itemClicked == null || npcToInteractWith == null)
        {
            return;
        }

        MenuEntry menuEntry = new MenuEntry(
                config.INV_NPC_FOURTH_OPTION_MENU_OPTION(),
                "<col=ff9040>"
                        + client.getNpcDefinition(npcToInteractWith.getId()).getName(),
                npcToInteractWith.getIndex(),
                MenuAction.NPC_FOURTH_OPTION.getId(),
                0,
                0,
                false
        );

        e.setMenuEntry(menuEntry);
        plugin.setQolClick(true);
    }
}
