package plugin.nomore.qolclicksbeta.menu.actions.inventory;

import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicksbeta.QOLClicksConfig;
import plugin.nomore.qolclicksbeta.QOLClicksPlugin;
import plugin.nomore.qolclicksbeta.menu.scene.GameObj;
import plugin.nomore.qolclicksbeta.menu.scene.Inventory;
import plugin.nomore.qolclicksbeta.menu.scene.Npc;
import plugin.nomore.qolclicksbeta.utils.Utils;

import javax.inject.Inject;

public class INV_ITEM_USE_ON_WIDGET_ITEM
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
        WidgetItem selectedItem = null;

        String fullConfigString = utils.rws(config.INV_ITEM_USE_ON_WIDGET_ITEM_CONFIG_STRING());
        String[] fullSplitConfigString = fullConfigString.split(",");

        for (String individualConfigString : fullSplitConfigString)
        {
            String[] configClickedItemAndSelectedItemIdString = new String[]{"-1", "-1"};
            String[] individualSplitConfigString = individualConfigString.split(":");

            try
            {
                configClickedItemAndSelectedItemIdString[0] = individualSplitConfigString[0];
                configClickedItemAndSelectedItemIdString[1] = individualSplitConfigString[1];
            }
            catch (Exception exc)
            {
                configClickedItemAndSelectedItemIdString[0] = "-1";
                configClickedItemAndSelectedItemIdString[1] = "-1";
            }

            int configClickedItemId = Integer.parseInt(configClickedItemAndSelectedItemIdString[0]);
            int configSelectedItemId = Integer.parseInt(configClickedItemAndSelectedItemIdString[1]);

            if (configClickedItemId == -1 || configSelectedItemId == -1)
            {
                continue;
            }

            if (configClickedItemId == itemClickedId)
            {
                itemClicked = inventory.getItemInSlot(itemClickedId, itemClickedSlot);
                selectedItem = inventory.getFirstItem(configSelectedItemId);
                break;
            }
        }

        if (itemClicked == null || selectedItem == null)
        {
            return;
        }

        plugin.setSelectedItem(WidgetInfo.INVENTORY, selectedItem.getIndex(), selectedItem.getId());

        MenuEntry menuEntry = new MenuEntry(
                "Use",
                "<col=ff9040>"
                        + client.getItemDefinition(itemClicked.getId()).getName()
                        + "<col=ffffff> -> <col=ff9040>"
                        + client.getItemDefinition(selectedItem.getId()).getName(),
                itemClicked.getId(),
                MenuAction.ITEM_USE_ON_WIDGET_ITEM.getId(),
                itemClicked.getIndex(),
                WidgetInfo.INVENTORY.getId(),
                false
        );

        e.setMenuEntry(menuEntry);
        plugin.setQolClick(true);
    }
}
