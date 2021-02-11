package plugin.nomore.qolclicks.utils;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import plugin.nomore.qolclicks.QOLClicksPlugin;

import javax.inject.Inject;

@Slf4j
public class Menu
{

    @Inject
    Client client;

    @Inject
    ClientThread clientThread;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    ItemManager itemManager;

    @Inject
    Inventory inventory;

    public static MenuEntry targetMenu = null;
    public MenuEntry getTargetMenu() { return targetMenu; }
    public void setTargetMenuNull() { targetMenu = null; }

    public void setMenuEntry(MenuEntry m)
    {
        targetMenu = new MenuEntry(
                m.getOption(),
                m.getTarget(),
                m.getIdentifier(),
                m.getOpcode(),
                m.getParam0(),
                m.getParam1(),
                m.isForceLeftClick());
    }

    public MenuEntry dropItem(WidgetItem item)
    {
        return new MenuEntry(
                "Drop",
                "<col=ff9040>",
                item.getId(),
                MenuOpcode.ITEM_DROP.getId(),
                item.getIndex(),
                WidgetInfo.INVENTORY.getId(),
                false);
    }

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
