package plugin.nomore.qolclicksbeta.menu.actions.inventory;

import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicksbeta.QOLClicksBetaConfig;
import plugin.nomore.qolclicksbeta.QOLClicksBetaPlugin;
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
        WidgetItem itemClicked = inventory.getFirstItem(utils.getConfigInt(0, config.INV_ITEM_USE_ON_WIDGET_ITEM_CONFIG_STRING()));
        WidgetItem selectedItem = inventory.getFirstItem(utils.getConfigInt(1, config.INV_ITEM_USE_ON_WIDGET_ITEM_CONFIG_STRING()));

        if (itemClicked == null || selectedItem == null)
        {
            return;
        }

        if (e.getId() != itemClicked.getId() && e.getActionParam() != itemClicked.getIndex())
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
