package plugin.nomore.qolclicks.menu.actions.inventory;

import joptsimple.internal.Strings;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksBetaConfig;
import plugin.nomore.qolclicks.QOLClicksBetaPlugin;
import plugin.nomore.qolclicks.menu.scene.GameObj;
import plugin.nomore.qolclicks.menu.scene.Inventory;
import plugin.nomore.qolclicks.menu.scene.Npc;
import plugin.nomore.qolclicks.utils.Utils;

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

        plugin.setSelectSpell(config.INV_SPELL_CAST_ON_WIDGET_SPELL().getSpell());

        MenuEntry menuEntry = new MenuEntry(
                "Cast",
                "<col=00ff00>"
                        + config.INV_SPELL_CAST_ON_WIDGET_SPELL().getName()
                        + "</col><col=ffffff> -> <col=ff9040>"
                        + client.getItemDefinition(itemClicked.getId()).getName(),
                itemClicked.getId(),
                MenuAction.ITEM_USE_ON_WIDGET.getId(),
                itemClicked.getIndex(),
                WidgetInfo.INVENTORY.getId(),
                false
        );

        WidgetInfo spell = config.INV_SPELL_CAST_ON_WIDGET_SPELL().getSpell();

        if (spell == WidgetInfo.SPELL_HIGH_LEVEL_ALCHEMY)
        {
            plugin.setOpenedSpellbook(true);
        }

        e.setMenuEntry(menuEntry);
        plugin.setQolClick(true);
    }
}