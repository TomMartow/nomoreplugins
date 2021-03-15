package plugin.nomore.qolclicks.menu.actions.inventory;

import joptsimple.internal.Strings;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksBetaConfig;
import plugin.nomore.qolclicks.QOLClicksBetaPlugin;
import plugin.nomore.qolclicks.menu.scene.GameObj;
import plugin.nomore.qolclicks.menu.scene.Inventory;
import plugin.nomore.qolclicks.menu.scene.Npc;
import plugin.nomore.qolclicks.enums.QOLSpoofClickCategory;
import plugin.nomore.qolclicks.utils.Utils;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class INV_GAME_OBJECT_THIRD_OPTION
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
        GameObject gameObjectToInteractWith = null;

        String fullConfigString = config.INV_GAME_OBJECT_THIRD_OPTION_CONFIG_STRING();
        String menuOption = "";
        String[] fullSplitConfigString = fullConfigString.split(",");

        List<Integer> idsList = new ArrayList<>();

        for (String individualConfigString : fullSplitConfigString)
        {
            String[] individualPart = new String[]{"-1", "-1", ""};
            String[] individualSplitConfigString = individualConfigString.split(":");

            try
            {
                individualPart[0] = utils.rws(individualSplitConfigString[0]);
                individualPart[1] = utils.rws(individualSplitConfigString[1]);
                menuOption = individualSplitConfigString[2];
            }
            catch (Exception exc)
            {
                individualPart[0] = "-1";
                individualPart[1] = "-1";
                menuOption = "";
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

        if (itemClicked == null || gameObjectToInteractWith == null || Strings.isNullOrEmpty(menuOption))
        {
            return;
        }

        ObjectComposition def = client.getObjectDefinition(gameObjectToInteractWith.getId());

        MenuEntry menuEntry = new MenuEntry(
                menuOption,
                "<col=ffff>"
                        + def.getName(),
                gameObjectToInteractWith.getId(),
                MenuAction.GAME_OBJECT_THIRD_OPTION.getId(),
                gameObjectToInteractWith.getSceneMinLocation().getX(),
                gameObjectToInteractWith.getSceneMinLocation().getY(),
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
