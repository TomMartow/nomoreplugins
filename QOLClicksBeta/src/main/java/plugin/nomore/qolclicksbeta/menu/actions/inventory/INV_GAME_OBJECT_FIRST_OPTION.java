package plugin.nomore.qolclicksbeta.menu.actions.inventory;

import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicksbeta.QOLClicksBetaConfig;
import plugin.nomore.qolclicksbeta.QOLClicksBetaPlugin;
import plugin.nomore.qolclicksbeta.menu.scene.GameObj;
import plugin.nomore.qolclicksbeta.menu.scene.Inventory;
import plugin.nomore.qolclicksbeta.menu.scene.Npc;
import plugin.nomore.qolclicksbeta.utils.Utils;

import javax.inject.Inject;

public class INV_GAME_OBJECT_FIRST_OPTION
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
        WidgetItem itemClicked = inventory.getItemInSlot(utils.getConfigInt(0, config.INV_GAME_OBJECT_FIRST_OPTION_CONFIG_STRING()), e.getActionParam());
        GameObject gameObjectToInteractWith = gameObj.getClosestGameObject(utils.getConfigInt(1, config.INV_GAME_OBJECT_FIRST_OPTION_CONFIG_STRING()));
        if (itemClicked == null
                || gameObjectToInteractWith == null)
        {
            return;
        }

        if (itemClicked.getId() != e.getId()
                || itemClicked.getIndex() != e.getActionParam())
        {
            return;
        }

        MenuEntry menuEntry = new MenuEntry(
                config.INV_GAME_OBJECT_FIRST_OPTION_MENU_OPTION(),
                "<col=ff9040>"
                        + client.getObjectDefinition(gameObjectToInteractWith.getId()).getName(),
                gameObjectToInteractWith.getId(),
                MenuAction.GAME_OBJECT_FIRST_OPTION.getId(),
                gameObjectToInteractWith.getSceneMinLocation().getX(),
                gameObjectToInteractWith.getSceneMinLocation().getY(),
                false
        );

        e.setMenuEntry(menuEntry);
        plugin.setQOLClick(true);
    }
}
