package plugin.nomore.qolclicksbeta.menu.actions.inventory;

import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicksbeta.QOLClicksBetaConfig;
import plugin.nomore.qolclicksbeta.QOLClicksBetaPlugin;
import plugin.nomore.qolclicksbeta.menu.scene.GameObj;
import plugin.nomore.qolclicksbeta.menu.scene.Inventory;
import plugin.nomore.qolclicksbeta.menu.scene.Npc;
import plugin.nomore.qolclicksbeta.enums.QOLSpoofClickCategory;
import plugin.nomore.qolclicksbeta.utils.Utils;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        int itemClickedId = e.getId();
        int itemClickedSlot = e.getActionParam();

        WidgetItem itemClicked = null;
        NPC npcToUseItemOn = null;

        String fullConfigString = utils.rws(config.INV_ITEM_USE_ON_NPC_CONFIG_STRING());
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
                    npcToUseItemOn = npc.getClosestNpc(idsList);
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
                    npcToUseItemOn = npc.getClosestNpc(id2);
                    break;
                }
            }
        }

        if (itemClicked == null || npcToUseItemOn == null)
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

        plugin.setQolMenuEntry(menuEntry);
        plugin.setQolClick(true);

        if (config.enableQOLSpoofClick())
        {
            plugin.setSpoofClick(true);

            int[] loc = utils.getCanvasIndicatorLocation(config.customSpoofClickLocation());
            plugin.setClickArea(
                    config.qolSpoofClickCategory() == QOLSpoofClickCategory.FULL_CLIENT
                            ? client.getCanvas().getBounds()
                            : new Rectangle(loc[0], loc[1], loc[2], loc[3])
            );
        }
    }

}
