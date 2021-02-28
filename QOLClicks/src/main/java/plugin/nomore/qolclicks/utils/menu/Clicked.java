package plugin.nomore.qolclicks.utils.menu;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.scene.Objects;

import javax.inject.Inject;

@Slf4j
public class Clicked
{

    @Inject private Client client;
    @Inject private QOLClicksPlugin plugin;
    @Inject private Clicked clicked;
    @Inject private Objects objects;

    public void dropItem(WidgetItem itemClicked, MenuOptionClicked e)
    {
        if (itemClicked == null)
        {
            return;
        }
        e.setMenuOption("Drop");
        e.setMenuTarget("<col=ff9040>" + client.getItemDefinition(itemClicked.getId()).getName());
        e.setId(itemClicked.getId());
        e.setMenuAction(MenuAction.ITEM_FOURTH_OPTION);
        e.setActionParam(itemClicked.getIndex());
    }

    public void useItemOnItem(WidgetItem itemClicked, WidgetItem itemToBeUsedOn, MenuOptionClicked e)
    {
        if (itemClicked == null)
        {
            return;
        }
        if (itemToBeUsedOn == null)
        {
            return;
        }
        plugin.setSelected(WidgetInfo.INVENTORY, itemToBeUsedOn.getIndex(), itemToBeUsedOn.getId());
        e.setMenuOption("Use");
        e.setMenuTarget(
                "<col=ff9040>"
                + client.getItemDefinition(itemClicked.getId()).getName()
                + "<col=ffffff> -> <col=ff9040>"
                + client.getItemDefinition(itemToBeUsedOn.getId()).getName());
        e.setId(itemClicked.getId());
        e.setMenuAction(MenuAction.ITEM_USE_ON_WIDGET_ITEM);
    }

    public void interactWithNpc(NPC npcToInteractWith, MenuOptionClicked event)
    {
        if (npcToInteractWith == null)
        {
            return;
        }
        event.setMenuOption("Use-rod");
        event.setMenuTarget("<col=ffff00>" + client.getNpcDefinition(npcToInteractWith.getId()).getName());
        event.setId(npcToInteractWith.getIndex());
        event.setMenuAction(MenuAction.NPC_FIRST_OPTION);
        event.setActionParam(0);
        event.setForceLeftClick(false);
        MenuEntry menuEntry = new MenuEntry(
                option,
                target,
                objectToInteractWith.getId(),
                MenuAction.GAME_OBJECT_SECOND_OPTION.getId(),
                objectToInteractWith.getSceneMinLocation().getX(),
                objectToInteractWith.getSceneMinLocation().getY(),
                false);
        event.setMenuEntry(menuEntry);
    }

    public void interactWithGameObject(String option, String target, GameObject objectToInteractWith, MenuOptionClicked event)
    {
        MenuEntry menuEntry = new MenuEntry(
                option,
                target,
                objectToInteractWith.getId(),
                MenuAction.GAME_OBJECT_SECOND_OPTION.getId(),
                objectToInteractWith.getSceneMinLocation().getX(),
                objectToInteractWith.getSceneMinLocation().getY(),
                false);
        event.setMenuEntry(menuEntry);
    }

    public void useItemOnGameObject(WidgetItem itemClicked, GameObject objectToUseItemOn, MenuOptionClicked event)
    {
        if (itemClicked == null)
        {
            return;
        }
        if (objectToUseItemOn == null)
        {
            return;
        }
        plugin.setSelected(WidgetInfo.INVENTORY, itemClicked.getIndex(), itemClicked.getId());
        event.setMenuOption("Use");
        event.setMenuTarget(
                "<col=ff9040>"
                + client.getItemDefinition(itemClicked.getId()).getName()
                + "<col=ffffff> -> <col=ffff>"
                + client.getObjectDefinition(objectToUseItemOn.getId()).getName());
        event.setId(objectToUseItemOn.getId());
        event.setMenuAction(MenuAction.ITEM_USE_ON_GAME_OBJECT);
        event.setActionParam(objectToUseItemOn.getSceneMinLocation().getX());
        event.setParam1(objectToUseItemOn.getSceneMinLocation().getY());
    }

    public void useItemOnNPC(String option, WidgetItem itemClicked, NPC npcToUseItemOn, MenuOptionClicked e)
    {
        if (client == null)
        {
            return;
        }
        if (itemClicked == null)
        {
            return;
        }
        if (npcToUseItemOn == null)
        {
            return;
        }
        plugin.setSelected(WidgetInfo.INVENTORY, itemClicked.getIndex(), itemClicked.getId());
        e.setMenuOption(option);
        e.setMenuTarget("<col=ff9040>" + client.getItemDefinition(itemClicked.getId()).getName() + "<col=ffffff> -> <col=ffff00>" + client.getNpcDefinition(npcToUseItemOn.getId()).getName());
        e.setId(npcToUseItemOn.getIndex());
        e.setMenuAction(MenuAction.ITEM_USE_ON_NPC);
        e.setActionParam(0);
        e.setParam1(0);
    }

}
