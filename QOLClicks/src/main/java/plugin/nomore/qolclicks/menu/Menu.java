package plugin.nomore.qolclicks.menu;

import net.runelite.api.*;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksPlugin;

import javax.inject.Inject;

public class Menu
{

    @Inject
    QOLClicksPlugin plugin;

    public void useItemOnItem(String option, String target, WidgetItem itemClicked, WidgetItem itemToBeUsedOn, MenuEntry event)
    {
        plugin.setSelected(WidgetInfo.INVENTORY, itemToBeUsedOn.getIndex(), itemToBeUsedOn.getId());
        event.setOption(option);
        event.setTarget(target);
        event.setIdentifier(itemClicked.getId());
        event.setOpcode(MenuOpcode.ITEM_USE_ON_WIDGET_ITEM.getId());
        event.setForceLeftClick(false);
    }

    public void interactWithNpc(String option, String target, NPC npc, MenuEntry event)
    {
        event.setOption(option);
        event.setTarget(target);
        event.setIdentifier(npc.getIndex());
        event.setOpcode(MenuOpcode.NPC_FIRST_OPTION.getId());
        event.setParam0(0);
        event.setParam1(0);
        event.setForceLeftClick(false);
    }

    public void interactWithGameObject(String option, String target, GameObject object, MenuEntry event)
    {
        event.setOption(option);
        event.setTarget(target);
        event.setIdentifier(object.getId());
        event.setOpcode(MenuOpcode.GAME_OBJECT_SECOND_OPTION.getId());
        event.setParam0(object.getSceneMinLocation().getX());
        event.setParam1(object.getSceneMinLocation().getY());
        event.setForceLeftClick(false);
    }

    public void useItemOnGameObject(String option, String target, GameObject object, WidgetItem widgetItem, MenuEntry event)
    {
        plugin.setSelected(WidgetInfo.INVENTORY, widgetItem.getIndex(), widgetItem.getId());
        event.setOption(option);
        event.setTarget(target);
        event.setIdentifier(object.getId());
        event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
        event.setParam0(object.getSceneMinLocation().getX());
        event.setParam1(object.getSceneMinLocation().getY());
        event.setForceLeftClick(false);
    }

}
