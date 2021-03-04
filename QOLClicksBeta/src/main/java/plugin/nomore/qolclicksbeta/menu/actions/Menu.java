package plugin.nomore.qolclicksbeta.menu.actions;

import net.runelite.api.MenuAction;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.MenuOptionClicked;
import plugin.nomore.qolclicksbeta.QOLClicksBetaConfig;
import plugin.nomore.qolclicksbeta.menu.actions.inventory.*;

import javax.inject.Inject;

public class Menu
{

    @Inject QOLClicksBetaConfig config;
    @Inject INV_ITEM_USE inv_item_use;
    @Inject INV_ITEM_USE_ON_NPC inv_item_use_on_npc;
    @Inject INV_ITEM_USE_ON_WIDGET_ITEM inv_item_use_on_widget_item;
    @Inject INV_ITEM_USE_ON_GAME_OBJECT inv_item_use_on_game_object;
    @Inject INV_NPC_FIRST_OPTION inv_npc_first_option;
    @Inject INV_NPC_SECOND_OPTION inv_npc_second_option;
    @Inject INV_NPC_THIRD_OPTION inv_npc_third_option;
    @Inject INV_NPC_FOURTH_OPTION inv_npc_fourth_option;
    @Inject INV_NPC_FIFTH_OPTION inv_npc_fifth_option;
    @Inject INV_GAME_OBJECT_FIRST_OPTION inv_game_object_first_option;
    @Inject INV_GAME_OBJECT_SECOND_OPTION inv_game_object_second_option;
    @Inject INV_GAME_OBJECT_THIRD_OPTION inv_game_object_third_option;
    @Inject INV_GAME_OBJECT_FOURTH_OPTION inv_game_object_fourth_option;
    @Inject INV_GAME_OBJECT_FIFTH_OPTION inv_game_object_fifth_option;
    @Inject INV_SPELL_CAST_ON_WIDGET inv_spell_cast_on_widget;
    @Inject INV_SPELL_CAST_ON_NPC inv_spell_cast_on_npc;
    @Inject INV_SPELL_CAST_ON_GAME_OBJECT inv_spell_cast_on_game_object;
    @Inject INV_SPELL_CAST_ON_GROUND_ITEM inv_spell_cast_on_ground_item;

    public void onOpen(MenuOpened e)
    {

    }

    public void onAdded(MenuEntryAdded e)
    {

    }

    public void onClicked(MenuOptionClicked e)
    {

        if (e.getMenuAction() == MenuAction.ITEM_USE)
        {
            if (config.ENABLE_INV_ITEM_USE()) { inv_item_use.check(e); }
            if (config.ENABLE_INV_ITEM_USE_ON_NPC()) { inv_item_use_on_npc.check(e); }
            if (config.ENABLE_INV_ITEM_USE_ON_GAME_OBJECT()) { inv_item_use_on_game_object.check(e); }
            if (config.ENABLE_INV_ITEM_USE_ON_WIDGET_ITEM()) { inv_item_use_on_widget_item.check(e); }
            if (config.ENABLE_INV_NPC_FIRST_OPTION()) { inv_npc_first_option.check(e); }
            if (config.ENABLE_INV_NPC_SECOND_OPTION()) { inv_npc_second_option.check(e); }
            if (config.ENABLE_INV_NPC_THIRD_OPTION()) { inv_npc_third_option.check(e); }
            if (config.ENABLE_INV_NPC_FOURTH_OPTION()) { inv_npc_fourth_option.check(e); }
            if (config.ENABLE_INV_NPC_FIFTH_OPTION()) { inv_npc_fifth_option.check(e); }
            if (config.ENABLE_INV_GAME_OBJECT_FIRST_OPTION()) { inv_game_object_first_option.check(e); }
            if (config.ENABLE_INV_GAME_OBJECT_SECOND_OPTION()) { inv_game_object_second_option.check(e); }
            if (config.ENABLE_INV_GAME_OBJECT_THIRD_OPTION()) { inv_game_object_third_option.check(e); }
            if (config.ENABLE_INV_GAME_OBJECT_FOURTH_OPTION()) { inv_game_object_fourth_option.check(e); }
            if (config.ENABLE_INV_GAME_OBJECT_FIFTH_OPTION()) { inv_game_object_fifth_option.check(e); }
            if (config.ENABLE_INV_SPELL_CAST_ON_WIDGET()) { inv_spell_cast_on_widget.check(e); }
            if (config.ENABLE_INV_SPELL_CAST_ON_NPC()) { inv_spell_cast_on_npc.check(e); }
            if (config.ENABLE_INV_SPELL_CAST_ON_GAME_OBJECT()) { inv_spell_cast_on_game_object.check(e); }
            if (config.ENABLE_INV_SPELL_CAST_ON_GROUND_ITEM()) { inv_spell_cast_on_ground_item.check(e); }
        }

    }

}
