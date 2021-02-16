package plugin.nomore.qolclicks.utils;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.api.widgets.WidgetInfo;
import plugin.nomore.qolclicks.utils.builds.InventoryItem;

import javax.inject.Inject;

public class TargetMenues
{
    @Inject
    Client client;

    public MenuEntry getDropItem(InventoryItem inventoryItem)
    {
        return new MenuEntry("Drop",
                "<col=ff9040>" + inventoryItem.getName(),
                inventoryItem.getItem().getId(),
                MenuOpcode.ITEM_DROP.getId(),
                inventoryItem.getItem().getIndex(),
                WidgetInfo.INVENTORY.getId(),
                false);
    }

    public MenuEntry getInteractWithGameObject(String option, GameObject objectToInteractWith)
    {
        return new MenuEntry(
                option,
                "<col=ffff>" + client.getObjectDefinition(objectToInteractWith.getId()).getName(),
                objectToInteractWith.getId(),
                MenuOpcode.GAME_OBJECT_SECOND_OPTION.getId(),
                objectToInteractWith.getSceneMinLocation().getX(),
                objectToInteractWith.getSceneMinLocation().getY(),
                false);
    }
}
