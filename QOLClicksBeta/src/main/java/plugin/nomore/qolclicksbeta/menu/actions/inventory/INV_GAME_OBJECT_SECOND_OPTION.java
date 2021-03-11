package plugin.nomore.qolclicksbeta.menu.actions.inventory;

import joptsimple.internal.Strings;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicksbeta.QOLClicksConfig;
import plugin.nomore.qolclicksbeta.QOLClicksPlugin;
import plugin.nomore.qolclicksbeta.menu.scene.GameObj;
import plugin.nomore.qolclicksbeta.menu.scene.Inventory;
import plugin.nomore.qolclicksbeta.menu.scene.Npc;
import plugin.nomore.qolclicksbeta.utils.Utils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class INV_GAME_OBJECT_SECOND_OPTION
{

    @Inject
    Client client;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    QOLClicksConfig config;

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
        GameObject gameObjectToInteractWith = null;

        String fullConfigString = utils.rws(config.INV_GAME_OBJECT_SECOND_OPTION_CONFIG_STRING());
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
                    gameObjectToInteractWith = gameObj.getClosestGameObject(idsList);
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
                    gameObjectToInteractWith = gameObj.getClosestGameObject(id2);
                    break;
                }
            }
        }

        if (itemClicked == null || gameObjectToInteractWith == null)
        {
            return;
        }

        ObjectComposition def = client.getObjectDefinition(gameObjectToInteractWith.getId());

        String menuOption = Arrays.stream(def.getActions()).findFirst().orElse(null);
        if (Strings.isNullOrEmpty(menuOption))
        {
            return;
        }

        MenuEntry menuEntry = new MenuEntry(
                menuOption,
                "<col=ff9040>"
                        + client.getObjectDefinition(gameObjectToInteractWith.getId()).getName(),
                gameObjectToInteractWith.getId(),
                MenuAction.GAME_OBJECT_SECOND_OPTION.getId(),
                gameObjectToInteractWith.getSceneMinLocation().getX(),
                gameObjectToInteractWith.getSceneMinLocation().getY(),
                false
        );

        e.setMenuEntry(menuEntry);
        plugin.setQolClick(true);
    }
}
