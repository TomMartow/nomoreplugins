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

public class INV_SPELL_CAST_ON_WIDGET
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
        String[] spellMenuSwap = utils.rws(config.INV_SPELL_CAST_ON_WIDGET_CONFIG_STRING()).split(",");

        WidgetItem itemClicked = inventory.getItems()
                .stream()
                .filter(item -> item != null
                        && Arrays.stream(spellMenuSwap)
                        .anyMatch(idString -> !Strings.isNullOrEmpty(idString)
                                && Integer.parseInt(idString) == item.getId())
                        && item.getIndex() == e.getActionParam())
                .findFirst()
                .orElse(null);

        if (itemClicked == null)
        {
            return;
        }

        if (Arrays.stream(spellMenuSwap).anyMatch(itemId -> Integer.parseInt(itemId) != itemClicked.getId()))
        {
            System.out.println();
            return;
        }

        plugin.setSelectSpell(config.INV_SPELL_CAST_ON_WIDGET_SPELL().getSpell());

        MenuEntry menuEntry = new MenuEntry(
                "Cast",
                config.INV_SPELL_CAST_ON_WIDGET_SPELL().getName(),
                itemClicked.getId(),
                MenuAction.CC_OP.getId(),
                1,
                WidgetInfo.SPELLBOOK.getId(),
                false
        );

        e.setMenuEntry(menuEntry);
        plugin.setQOLClick(true);
    }
}
