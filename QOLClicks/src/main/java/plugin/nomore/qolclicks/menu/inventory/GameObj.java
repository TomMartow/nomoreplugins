package plugin.nomore.qolclicks.menu.inventory;

import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.queries.GameObjectQuery;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.Utils;

import javax.inject.Inject;

public class GameObj
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

    public void interactWithGameObject(MenuOptionClicked e)
    {
        WidgetItem itemClicked = inventory.getFirstItem(utils.getConfigArg(0, config.gameObjectFirstOptionIds()));
        GameObject gameObjectToInteractWith = getClosestGameObject(utils.getConfigArg(1, config.gameObjectFirstOptionIds()));

        if (itemClicked == null || gameObjectToInteractWith == null)
        {
            return;
        }

        if (e.getId() != itemClicked.getId() && e.getActionParam() != itemClicked.getIndex())
        {
            return;
        }

        MenuEntry menuEntry = new MenuEntry(
                config.gameObjectOption(),
                "<col=ffff00>" + client.getObjectDefinition(gameObjectToInteractWith.getId()).getName(),
                gameObjectToInteractWith.getId(),
                MenuAction.GAME_OBJECT_FIRST_OPTION.getId(),
                gameObjectToInteractWith.getSceneMinLocation().getX(),
                gameObjectToInteractWith.getSceneMinLocation().getY(),
                false
        );

        e.setMenuEntry(menuEntry);
    }

    public void useItemOnGameObject(MenuOptionClicked e)
    {
        WidgetItem itemClicked = inventory.getFirstItem(utils.getConfigArg(0, config.useItemOnObjectIds()));
        GameObject gameObjectToUseItemOn = getClosestGameObject(utils.getConfigArg(1, config.useItemOnObjectIds()));
        if (itemClicked == null || gameObjectToUseItemOn == null)
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
                        + client.getNpcDefinition(gameObjectToUseItemOn.getId()).getName(),
                gameObjectToUseItemOn.getId(),
                MenuAction.ITEM_USE_ON_GAME_OBJECT.getId(),
                gameObjectToUseItemOn.getSceneMinLocation().getX(),
                gameObjectToUseItemOn.getSceneMinLocation().getY(),
                false
        );

        e.setMenuEntry(menuEntry);
    }

    public GameObject getClosestGameObject(int... ids)
    {
        if (client.getLocalPlayer() == null)
        {
            return null;
        }
        return new GameObjectQuery()
                .idEquals(ids)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

}
