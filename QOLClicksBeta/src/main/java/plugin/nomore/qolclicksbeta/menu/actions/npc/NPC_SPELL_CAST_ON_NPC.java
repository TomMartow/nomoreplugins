package plugin.nomore.qolclicks.menu.actions.npc;

import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksBetaConfig;
import plugin.nomore.qolclicks.QOLClicksBetaPlugin;
import plugin.nomore.qolclicks.menu.scene.GameObj;
import plugin.nomore.qolclicks.menu.scene.Inventory;
import plugin.nomore.qolclicks.menu.scene.Npc;
import plugin.nomore.qolclicks.utils.Utils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class NPC_SPELL_CAST_ON_NPC
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
        NPC npcToCastSpellOn = null;

        String fullConfigString = utils.rws(config.INV_SPELL_CAST_ON_NPC_CONFIG_STRING());
        String[] fullSplitConfigString = fullConfigString.split(",");

        List<Integer> idsList = new ArrayList<>();

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

            if (individualPart[1].contains("/"))
            {
                String[] stringIds = individualPart[1].split("/");
                for (String stringId : stringIds)
                {
                    try
                    {
                        idsList.add(Integer.parseInt(stringId));
                    }
                    catch (Exception ignored) {}
                }

                if (id1 == -1)
                {
                    continue;
                }

                if (id1 == itemClickedId)
                {
                    itemClicked = inventory.getItemInSlot(itemClickedId, itemClickedSlot);
                    npcToCastSpellOn = npc.getClosestNpc(idsList);
                    break;
                }
            }
            else
            {
                int id2 = Integer.parseInt(individualPart[1]);

                if (id1 == -1 || id2 == -1)
                {
                    continue;
                }

                if (id1 == itemClickedId)
                {
                    itemClicked = inventory.getItemInSlot(itemClickedId, itemClickedSlot);
                    npcToCastSpellOn = npc.getClosestNpc(id2);
                    break;
                }
            }
        }

        if (itemClicked == null || npcToCastSpellOn == null)
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
