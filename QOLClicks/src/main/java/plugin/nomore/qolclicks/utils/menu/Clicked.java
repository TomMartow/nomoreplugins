package plugin.nomore.qolclicks.utils.menu;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
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

    public void dropItem(WidgetItem itemClicked, MenuEntry e)
    {
        if (itemClicked == null)
        {
            return;
        }
        e.setOption("Drop");
        e.setTarget("<col=ff9040>" + client.getItemDefinition(itemClicked.getId()).getName());
        e.setIdentifier(itemClicked.getId());
        e.setOpcode(MenuOpcode.ITEM_DROP.getId());
        e.setParam0(itemClicked.getIndex());
        e.setForceLeftClick(false);
    }

    public void useItemOnItem(WidgetItem itemClicked, WidgetItem itemToBeUsedOn, MenuEntry e)
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
        e.setOption("Use");
        e.setTarget(
                "<col=ff9040>"
                + client.getItemDefinition(itemClicked.getId()).getName()
                + "<col=ffffff> -> <col=ff9040>"
                + client.getItemDefinition(itemToBeUsedOn.getId()).getName());
        e.setIdentifier(itemClicked.getId());
        e.setOpcode(MenuOpcode.ITEM_USE_ON_WIDGET_ITEM.getId());
        e.setForceLeftClick(false);
    }

    public void interactWithNpc(NPC npcToInteractWith, MenuEntry event)
    {
        if (npcToInteractWith == null)
        {
            return;
        }
        event.setOption("Use-rod");
        event.setTarget("<col=ffff00>" + client.getNpcDefinition(npcToInteractWith.getId()).getName());
        event.setIdentifier(npcToInteractWith.getIndex());
        event.setOpcode(MenuOpcode.NPC_FIRST_OPTION.getId());
        event.setParam0(0);
        event.setParam1(0);
        event.setForceLeftClick(false);
    }

    public void interactWithGameObject(String option, String target, GameObject objectToInteractWith, MenuEntry event)
    {
        event.setOption(option);
        event.setTarget(target);
        event.setIdentifier(objectToInteractWith.getId());
        event.setOpcode(MenuOpcode.GAME_OBJECT_SECOND_OPTION.getId());
        event.setParam0(objectToInteractWith.getSceneMinLocation().getX());
        event.setParam1(objectToInteractWith.getSceneMinLocation().getY());
        event.setForceLeftClick(false);
    }

    public void useItemOnGameObject(WidgetItem itemClicked, GameObject objectToUseItemOn, MenuEntry event)
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
        event.setOption("Use");
        event.setTarget(
                "<col=ff9040>"
                + client.getItemDefinition(itemClicked.getId()).getName()
                + "<col=ffffff> -> <col=ffff>"
                + client.getObjectDefinition(objectToUseItemOn.getId()).getName());
        event.setIdentifier(objectToUseItemOn.getId());
        event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
        event.setParam0(objectToUseItemOn.getSceneMinLocation().getX());
        event.setParam1(objectToUseItemOn.getSceneMinLocation().getY());
        event.setForceLeftClick(false);
    }

    public void useItemOnNPC(String option, WidgetItem itemClicked, NPC npcToUseItemOn, MenuEntry e)
    {
        if (client == null || e.isForceLeftClick())
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
        e.setOption(option);
        e.setTarget("<col=ff9040>" + client.getItemDefinition(itemClicked.getId()).getName() + "<col=ffffff> -> <col=ffff00>" + client.getNpcDefinition(npcToUseItemOn.getId()).getName());
        e.setIdentifier(npcToUseItemOn.getIndex());
        e.setOpcode(MenuOpcode.ITEM_USE_ON_NPC.getId());
        e.setParam0(0);
        e.setParam1(0);
        e.setForceLeftClick(false);
    }

}
