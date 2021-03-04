package plugin.nomore.qolclicksbeta.menu.actions.inventory;

import joptsimple.internal.Strings;
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
import java.util.Arrays;

public class INV_ITEM_USE
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
        String[] dropMenuSwap = utils.rws(config.INV_ITEM_USE_CONFIG_STRING()).split(",");

        WidgetItem itemClicked = inventory.getItems()
                .stream()
                .filter(item -> item != null
                        && Arrays.stream(dropMenuSwap)
                        .anyMatch(idString -> !Strings.isNullOrEmpty(idString)
                                && Integer.parseInt(idString) == item.getId())
                        && item.getIndex() == e.getActionParam())
                .findFirst()
                .orElse(null);

        if (itemClicked == null)
        {
            return;
        }

        MenuEntry menuEntry = new MenuEntry(
                "Drop",
                "<col=ff9040>"
                        + client.getItemDefinition(itemClicked.getId()).getName(),
                itemClicked.getId(),
                MenuAction.ITEM_FIFTH_OPTION.getId(),
                itemClicked.getIndex(),
                WidgetInfo.INVENTORY.getId(),
                false
        );

        e.setMenuEntry(menuEntry);
        plugin.setQOLClick(true);
    }

}
