/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package plugin.nomore.qolclicksbeta;


import net.runelite.client.config.*;
import plugin.nomore.qolclicksbeta.menu.scene.spells.*;

@ConfigGroup("qolclicksbeta")
public interface QOLClicksBetaConfig extends Config
{

    @ConfigTitle(
            keyName = "configOptionsTitle",
            name = "Configuration Options",
            description = "",
            position = 1
    )
    String configOptionsTitle = "configOptionsTitle";

    @ConfigItem(
            keyName = "configEnum",
            name = "Show options for",
            description = "",
            position = 2,
            title = "configOptionsTitle"
    )
    default ConfigOptions configOptions() { return ConfigOptions.INV_ITEM_USE; }
    
    /*
    @ConfigItem(
            keyName = "[REPLACE]_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "[REPLACE]"
    )
    default String [REPLACE]_EXPLAINATION() { return ""; }

    @ConfigItem(
            keyName = "ENABLE_[REPLACE]",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "[REPLACE]"
    )
    default boolean ENABLE_[REPLACE]() { return false; }

    @ConfigItem(
            keyName = "[REPLACE]_CONFIG_STRING",
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "[REPLACE]"
    )
    default String [REPLACE]_CONFIG_STRING() { return ""; }
    */
    
    //INV_ITEM_USE
    //
    //

    @ConfigItem(
            keyName = "INV_ITEM_USE_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Drop Item"
    )
    default String INV_ITEM_USE_EXPLAINATION()
    {
        return "In the text box below, add the id's of the inventory items split by \",\". " +
                "\n\nFormat - 1,2,3,4 " +
                "\n\nWhen you click on the item matching the Item ID, it will drop the item.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_ITEM_USE",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Drop Item"
    )
    default boolean ENABLE_INV_ITEM_USE() { return false; }

    @ConfigItem(
            keyName = "INV_ITEM_USE_CONFIG_STRING",
            name = "Item IDs",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Drop Item"
    )
    default String INV_ITEM_USE_CONFIG_STRING() { return ""; }
    
    //INV_ITEM_USE_ON_NPC
    //
    //

    @ConfigItem(
            keyName = "INV_ITEM_USE_ON_NPC_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Use on NPC"
    )
    default String INV_ITEM_USE_ON_NPC_EXPLAINATION()
    {
        return "In the text box below, input the Item ID followed by \":\", followed by the NPC ID. " +
                "\n\nFormat - 1:1 " +
                "\n\nWhen you click on the item matching the Item ID, it will use that item on an npc matching the NPC ID.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_ITEM_USE_ON_NPC",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Use on NPC"
    )
    default boolean ENABLE_INV_ITEM_USE_ON_NPC() { return false; }

    @ConfigItem(
            keyName = "INV_ITEM_USE_ON_NPC_CONFIG_STRING",
            name = "Item ID : NPC ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Use on NPC"
    )
    default String INV_ITEM_USE_ON_NPC_CONFIG_STRING() { return ""; }

    //INV_ITEM_USE_ON_GAME_OBJECT
    //
    //

    @ConfigItem(
            keyName = "INV_ITEM_USE_ON_GAME_OBJECT_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Use on Game Object"
    )
    default String INV_ITEM_USE_ON_GAME_OBJECT_EXPLAINATION()
    {
        return "In the text box below, input the Item ID followed by \":\", followed by the Game Object ID." +
            "\n\nFormat - 1:1 " +
            "\n\nWhen you click on the item matching the Item ID, it will use that item on an game object matching the Game Object ID.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_ITEM_USE_ON_GAME_OBJECT",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Use on Game Object"
    )
    default boolean ENABLE_INV_ITEM_USE_ON_GAME_OBJECT() { return false; }

    @ConfigItem(
            keyName = "INV_ITEM_USE_ON_GAME_OBJECT_CONFIG_STRING",
            name = "Item ID : Game Object ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Use on Game Object"
    )
    default String INV_ITEM_USE_ON_GAME_OBJECT_CONFIG_STRING() { return ""; }

    //INV_ITEM_USE_ON_WIDGET_ITEM
    //
    //

    @ConfigItem(
            keyName = "INV_ITEM_USE_ON_WIDGET_ITEM_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Use on Item"
    )
    default String INV_ITEM_USE_ON_WIDGET_ITEM_EXPLAINATION()
    {
        return "In the text box below, input the Clicked Item ID you want to click on followed by \":\", followed by the Selected Item ID that you want to be selected. " +
                "\n\nFormat - 1:1 " +
                "\n\nWhen you click on the item matching the Clicked Item ID, the item matching the Selected Item ID will be used on the item clicked.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_ITEM_USE_ON_WIDGET_ITEM",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Use on Item"
    )
    default boolean ENABLE_INV_ITEM_USE_ON_WIDGET_ITEM() { return false; }

    @ConfigItem(
            keyName = "INV_ITEM_USE_ON_WIDGET_ITEM_CONFIG_STRING",
            name = "Clicked Item ID : Selected Item ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Use on Item"
    )
    default String INV_ITEM_USE_ON_WIDGET_ITEM_CONFIG_STRING() { return ""; }

    //INV_NPC_FIRST_OPTION
    //
    //

    @ConfigItem(
            keyName = "INV_NPC_FIRST_OPTION_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC First Option"
    )
    default String INV_NPC_FIRST_OPTION_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click on followed by \":\", followed by the NPC ID that you want to interact with using the NPCs first menu option. " +
                "\n\nFormat - 1:1 " +
                "\n\nWhen you click on the item matching the Item ID, your player will interact with the npc matching the NPC ID using the NPCs first menu option.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_NPC_FIRST_OPTION",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC First Option"
    )
    default boolean ENABLE_INV_NPC_FIRST_OPTION() { return false; }

    @ConfigItem(
            keyName = "INV_NPC_FIRST_OPTION_MENU_OPTION",
            name = "Menu Option",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC First Option"
    )
    default String INV_NPC_FIRST_OPTION_MENU_OPTION() { return "Talk-to"; }

    @ConfigItem(
            keyName = "INV_NPC_FIRST_OPTION_CONFIG_STRING",
            name = "Item ID : NPC ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC First Option"
    )
    default String INV_NPC_FIRST_OPTION_CONFIG_STRING() { return ""; }

    //INV_NPC_SECOND_OPTION
    //
    //

    @ConfigItem(
            keyName = "INV_NPC_SECOND_OPTION_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Second Option"
    )
    default String INV_NPC_SECOND_OPTION_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click on followed by \":\", followed by the NPC ID that you want to interact with using the NPCs second menu option. " +
                "\n\nFormat - 1:1 " +
                "\n\nWhen you click on the item matching the Item ID, your player will interact with the npc matching the NPC ID using the NPCs second menu option.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_NPC_SECOND_OPTION",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Second Option"
    )
    default boolean ENABLE_INV_NPC_SECOND_OPTION() { return false; }

    @ConfigItem(
            keyName = "INV_NPC_SECOND_OPTION_MENU_OPTION",
            name = "Menu Option",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Second Option"
    )
    default String INV_NPC_SECOND_OPTION_MENU_OPTION() { return "Talk-to"; }

    @ConfigItem(
            keyName = "INV_NPC_SECOND_OPTION_CONFIG_STRING",
            name = "Item ID : NPC ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Second Option"
    )
    default String INV_NPC_SECOND_OPTION_CONFIG_STRING() { return ""; }

    //INV_NPC_THIRD_OPTION
    //
    //

    @ConfigItem(
            keyName = "INV_NPC_THIRD_OPTION_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Third Option"
    )
    default String INV_NPC_THIRD_OPTION_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click on followed by \":\", followed by the NPC ID that you want to interact with using the NPCs third menu option. " +
                "\n\nFormat - 1:1 " +
                "\n\nWhen you click on the item matching the Item ID, your player will interact with the npc matching the NPC ID using the NPCs third menu option.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_NPC_THIRD_OPTION",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Third Option"
    )
    default boolean ENABLE_INV_NPC_THIRD_OPTION() { return false; }

    @ConfigItem(
            keyName = "INV_NPC_THIRD_OPTION_MENU_OPTION",
            name = "Menu Option",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Third Option"
    )
    default String INV_NPC_THIRD_OPTION_MENU_OPTION() { return "Talk-to"; }

    @ConfigItem(
            keyName = "INV_NPC_THIRD_OPTION_CONFIG_STRING",
            name = "Item ID : NPC ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Third Option"
    )
    default String INV_NPC_THIRD_OPTION_CONFIG_STRING() { return ""; }

    //INV_NPC_FOURTH_OPTION
    //
    //

    @ConfigItem(
            keyName = "INV_NPC_FOURTH_OPTION_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Fourth Option"
    )
    default String INV_NPC_FOURTH_OPTION_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click on followed by \":\", followed by the NPC ID that you want to interact with using the NPCs fourth menu option. " +
                "\n\nFormat - 1:1 " +
                "\n\nWhen you click on the item matching the Item ID, your player will interact with the npc matching the NPC ID using the NPCs fourth menu option.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_NPC_FOURTH_OPTION",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Fourth Option"
    )
    default boolean ENABLE_INV_NPC_FOURTH_OPTION() { return false; }

    @ConfigItem(
            keyName = "INV_NPC_FOURTH_OPTION_MENU_OPTION",
            name = "Menu Option",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Fourth Option"
    )
    default String INV_NPC_FOURTH_OPTION_MENU_OPTION() { return "Talk-to"; }

    @ConfigItem(
            keyName = "INV_NPC_FOURTH_OPTION_CONFIG_STRING",
            name = "Item ID : NPC ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Fourth Option"
    )
    default String INV_NPC_FOURTH_OPTION_CONFIG_STRING() { return ""; }

    //INV_NPC_FIFTH_OPTION
    //
    //

    @ConfigItem(
            keyName = "INV_NPC_FIFTH_OPTION_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Fifth Option"
    )
    default String INV_NPC_FIFTH_OPTION_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click on followed by \":\", followed by the NPC ID that you want to interact with using the NPCs fifth menu option. " +
                "\n\nFormat - 1:1 " +
                "\n\nWhen you click on the item matching the Item ID, your player will interact with the npc matching the NPC ID using the NPCs fifth menu option.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_NPC_FIFTH_OPTION",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Fifth Option"
    )
    default boolean ENABLE_INV_NPC_FIFTH_OPTION() { return false; }

    @ConfigItem(
            keyName = "INV_NPC_FIFTH_OPTION_MENU_OPTION",
            name = "Menu Option",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Fifth Option"
    )
    default String INV_NPC_FIFTH_OPTION_MENU_OPTION() { return "Talk-to"; }

    @ConfigItem(
            keyName = "INV_NPC_FIFTH_OPTION_CONFIG_STRING",
            name = "Item ID : NPC ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> NPC Fifth Option"
    )
    default String INV_NPC_FIFTH_OPTION_CONFIG_STRING() { return ""; }

    //INV_GAME_OBJECT_FIRST_OPTION
    //
    //

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_FIRST_OPTION_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object First Option"
    )
    default String INV_GAME_OBJECT_FIRST_OPTION_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click on followed by \":\", followed by the Game Object ID that you want to interact with using the Game Objects first menu option. " +
                "\n\nFormat - 1:1 " +
                "\n\nWhen you click on the item matching the Item ID, your player will interact with the game object matching the Game Object ID using the Game Objects first menu option.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_GAME_OBJECT_FIRST_OPTION",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object First Option"
    )
    default boolean ENABLE_INV_GAME_OBJECT_FIRST_OPTION() { return false; }

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_FIRST_OPTION_MENU_OPTION",
            name = "Menu Option",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object First Option"
    )
    default String INV_GAME_OBJECT_FIRST_OPTION_MENU_OPTION() { return "Bank"; }

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_FIRST_OPTION_CONFIG_STRING",
            name = "Item ID : Game Object ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object First Option"
    )
    default String INV_GAME_OBJECT_FIRST_OPTION_CONFIG_STRING() { return ""; }

    //INV_GAME_OBJECT_SECOND_OPTION
    //
    //

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_SECOND_OPTION_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Second Option"
    )
    default String INV_GAME_OBJECT_SECOND_OPTION_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click on followed by \":\", followed by the Game Object ID that you want to interact with using the Game Objects second menu option. " +
                "\n\nFormat - 1:1 " +
                "\n\nWhen you click on the item matching the Item ID, your player will interact with the game object matching the Game Object ID using the Game Objects second menu option.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_GAME_OBJECT_SECOND_OPTION",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Second Option"
    )
    default boolean ENABLE_INV_GAME_OBJECT_SECOND_OPTION() { return false; }

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_SECOND_OPTION_MENU_OPTION",
            name = "Menu Option",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Second Option"
    )
    default String INV_GAME_OBJECT_SECOND_OPTION_MENU_OPTION() { return "Bank"; }

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_SECOND_OPTION_CONFIG_STRING",
            name = "Item ID : Game Object ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Second Option"
    )
    default String INV_GAME_OBJECT_SECOND_OPTION_CONFIG_STRING() { return ""; }

    //INV_GAME_OBJECT_THIRD_OPTION
    //
    //

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_THIRD_OPTION_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Third Option"
    )
    default String INV_GAME_OBJECT_THIRD_OPTION_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click on followed by \":\", followed by the Game Object ID that you want to interact with using the Game Objects third menu option. " +
                "\n\nFormat - 1:1 " +
                "\n\nWhen you click on the item matching the Item ID, your player will interact with the game object matching the Game Object ID using the Game Objects third menu option.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_GAME_OBJECT_THIRD_OPTION",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Third Option"
    )
    default boolean ENABLE_INV_GAME_OBJECT_THIRD_OPTION() { return false; }

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_THIRD_OPTION_MENU_OPTION",
            name = "Menu Option",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Third Option"
    )
    default String INV_GAME_OBJECT_THIRD_OPTION_MENU_OPTION() { return "Bank"; }

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_THIRD_OPTION_CONFIG_STRING",
            name = "Item ID : Game Object ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Third Option"
    )
    default String INV_GAME_OBJECT_THIRD_OPTION_CONFIG_STRING() { return ""; }

    //INV_GAME_OBJECT_FOURTH_OPTION
    //
    //

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_FOURTH_OPTION_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Fourth Option"
    )
    default String INV_GAME_OBJECT_FOURTH_OPTION_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click on followed by \":\", followed by the Game Object ID that you want to interact with using the Game Objects fourth menu option. " +
                "\n\nFormat - 1:1 " +
                "\n\nWhen you click on the item matching the Item ID, your player will interact with the game object matching the Game Object ID using the Game Objects fourth menu option.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_GAME_OBJECT_FOURTH_OPTION",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Fourth Option"
    )
    default boolean ENABLE_INV_GAME_OBJECT_FOURTH_OPTION() { return false; }

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_FOURTH_OPTION_MENU_OPTION",
            name = "Menu Option",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Fourth Option"
    )
    default String INV_GAME_OBJECT_FOURTH_OPTION_MENU_OPTION() { return "Bank"; }

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_FOURTH_OPTION_CONFIG_STRING",
            name = "Item ID : Game Object ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Fourth Option"
    )
    default String INV_GAME_OBJECT_FOURTH_OPTION_CONFIG_STRING() { return ""; }

    //INV_GAME_OBJECT_FIFTH_OPTION
    //
    //

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_FIFTH_OPTION_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Fifth Option"
    )
    default String INV_GAME_OBJECT_FIFTH_OPTION_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click on followed by \":\", followed by the Game Object ID that you want to interact with using the Game Objects fifth menu option. " +
                "\n\nFormat - 1:1 " +
                "\n\nWhen you click on the item matching the Item ID, your player will interact with the game object matching the Game Object ID using the Game Objects fifth menu option.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_GAME_OBJECT_FIFTH_OPTION",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Fifth Option"
    )
    default boolean ENABLE_INV_GAME_OBJECT_FIFTH_OPTION() { return false; }

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_FIFTH_OPTION_MENU_OPTION",
            name = "Menu Option",
            description = "The Menu Option is the ",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Fifth Option"
    )
    default String INV_GAME_OBJECT_FIFTH_OPTION_MENU_OPTION() { return "Bank"; }

    @ConfigItem(
            keyName = "INV_GAME_OBJECT_FIFTH_OPTION_CONFIG_STRING",
            name = "Item ID : Game Object ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Game Object Fifth Option"
    )
    default String INV_GAME_OBJECT_FIFTH_OPTION_CONFIG_STRING() { return ""; }

    //INV_SPELL_CAST_ON_WIDGET
    //
    //

    @ConfigItem(
            keyName = "INV_SPELL_CAST_ON_WIDGET_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Item"
    )
    default String INV_SPELL_CAST_ON_WIDGET_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to cast a spell on." +
                "\n\nFormat - ItemID,ItemID,ItemID" +
                "\n\nWhen you click on the item matching the Item ID, the spell selected will be cast on the item.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_SPELL_CAST_ON_WIDGET",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Item"
    )
    default boolean ENABLE_INV_SPELL_CAST_ON_WIDGET() { return false; }

    @ConfigItem(
            keyName = "INV_SPELL_CAST_ON_WIDGET_CONFIG_STRING",
            name = "Item ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Item"
    )
    default String INV_SPELL_CAST_ON_WIDGET_CONFIG_STRING() { return ""; }

    @ConfigItem(
            keyName = "INV_SPELL_CAST_ON_WIDGET_SPELL",
            name = "Spell",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Item"
    )
    default Skilling INV_SPELL_CAST_ON_WIDGET_SPELL() { return Skilling.HIGH_ALCH; }

    //INV_SPELL_CAST_ON_NPC
    //
    //

    @ConfigItem(
            keyName = "INV_SPELL_CAST_ON_NPC_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on NPC"
    )
    default String INV_SPELL_CAST_ON_NPC_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click, followed by \":\", followed by the Spell ID, followed by \":\", followed by the NPC ID that you wish to cash a spell on." +
                "\n\nFormat - ItemID:SpellID:NPCID" +
                "\n\nWhen you click on the item matching the Item ID, a spell will be cast that matches the Spell ID on an npc matching the NPC ID.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_SPELL_CAST_ON_NPC",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on NPC"
    )
    default boolean ENABLE_INV_SPELL_CAST_ON_NPC() { return false; }

    @ConfigItem(
            keyName = "INV_SPELL_CAST_ON_NPC_CONFIG_STRING",
            name = "Item ID : Spell ID : NPC ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on NPC"
    )
    default String INV_SPELL_CAST_ON_NPC_CONFIG_STRING() { return ""; }

    //INV_SPELL_CAST_ON_GAME_OBJECT
    //
    //

    @ConfigItem(
            keyName = "INV_SPELL_CAST_ON_GAME_OBJECT_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Game Object"
    )
    default String INV_SPELL_CAST_ON_GAME_OBJECT_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click, followed by \":\", followed by the Spell ID, followed by \":\", followed by the Game Object ID that you wish to cash a spell on." +
                "\n\nFormat - ItemID:SpellID:GameObjectID" +
                "\n\nWhen you click on the item matching the Item ID, a spell will be cast that matches the Spell ID on an game object matching the Game Object ID.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_SPELL_CAST_ON_GAME_OBJECT",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Game Object"
    )
    default boolean ENABLE_INV_SPELL_CAST_ON_GAME_OBJECT() { return false; }

    @ConfigItem(
            keyName = "INV_SPELL_CAST_ON_GAME_OBJECT_CONFIG_STRING",
            name = "Item ID : Spell ID : Game Object ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Game Object"
    )
    default String INV_SPELL_CAST_ON_GAME_OBJECT_CONFIG_STRING() { return ""; }

    //INV_SPELL_CAST_ON_GROUND_ITEM
    //
    //

    @ConfigItem(
            keyName = "INV_SPELL_CAST_ON_GROUND_ITEM_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Ground Item"
    )
    default String INV_SPELL_CAST_ON_GROUND_ITEM_EXPLAINATION()
    {
        return "In the text box below, input the Item ID you want to click, followed by \":\", followed by the Spell ID, followed by \":\", followed by the Ground Item ID that you wish to cash a spell on." +
                "\n\nFormat - ItemID:SpellID:GroundItemID" +
                "\n\nWhen you click on the item matching the Item ID, a spell will be cast that matches the Spell ID on an ground item matching the Ground Item ID.";
    }

    @ConfigItem(
            keyName = "ENABLE_INV_SPELL_CAST_ON_GROUND_ITEM",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Ground Item"
    )
    default boolean ENABLE_INV_SPELL_CAST_ON_GROUND_ITEM() { return false; }

    @ConfigItem(
            keyName = "INV_SPELL_CAST_ON_GROUND_ITEM_CONFIG_STRING",
            name = "Item ID : Spell ID : Ground Item ID",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Ground Item"
    )
    default String INV_SPELL_CAST_ON_GROUND_ITEM_CONFIG_STRING() { return ""; }




//   █████╗ ██╗   ██╗████████╗ ██████╗ ███╗   ███╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
//  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗████╗ ████║██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
//  ███████║██║   ██║   ██║   ██║   ██║██╔████╔██║███████║   ██║   ██║██║   ██║██╔██╗ ██║
//  ██╔══██║██║   ██║   ██║   ██║   ██║██║╚██╔╝██║██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
//  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║ ╚═╝ ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
//  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
//

    @ConfigTitle(
            keyName = "automationTitle",
            name = "Automation Options",
            description = "",
            position = 15
    )
    String automationTitle = "automationTitle";

    @ConfigItem(
            keyName = "hideAutomationSettings",
            name = "Show automation options",
            description = "",
            position = 16,
            title = "automationTitle"
    )
    default boolean hideAutomationSettings() { return false; }

    @ConfigItem(
            keyName = "droppingKeybind",
            name = "Keybind",
            description = "If enabled, when the drop-matching hotkey is pressed, all items that match in the box below will be dropped.",
            position = 17,
            title = "automationTitle",
            hidden = true,
            unhide = "hideAutomationSettings",
            unhideValue = "true"
    )
    default Keybind dropKeybind() { return Keybind.NOT_SET; }

    @ConfigItem(
            keyName = "dropMatching",
            name = "Drop matching",
            description = "If enabled, when the drop-matching hotkey is pressed, all items that match in the box below will be dropped.",
            position = 18,
            title = "automationTitle",
            hidden = true,
            unhide = "hideAutomationSettings",
            unhideValue = "true"
    )
    default boolean dropMatching() { return false; }

    @ConfigItem(
            keyName = "matchingList",
            name = "Matching list",
            description = "If enabled, when the drop-matching hotkey is pressed, all items that match in the box below will be dropped.",
            position = 19,
            title = "automationTitle",
            hidden = true,
            unhide = "hideAutomationSettings",
            unhideValue = "true"
    )
    default String matchingList() { return "Bones"; }

    @ConfigItem(
            keyName = "dropExcept",
            name = "Drop except",
            description = "If enabled, when the drop-matching hotkey is pressed, all items that match in the box below will be dropped.",
            position = 20,
            title = "automationTitle",
            hidden = true,
            unhide = "hideAutomationSettings",
            unhideValue = "true"
    )
    default boolean dropExcept() { return false; }

    @ConfigItem(
            keyName = "exceptList",
            name = "Except list",
            description = "If enabled, when the drop-matching hotkey is pressed, all items that don't match in the box below will be dropped.",
            position = 21,
            title = "automationTitle",
            hidden = true,
            unhide = "hideAutomationSettings",
            unhideValue = "true"
    )
    default String exceptList() { return "Bones"; }

    @ConfigItem(
            keyName = "listOrder",
            name = "Order",
            description = "The iteration order.",
            position = 22,
            title = "automationTitle",
            hidden = true,
            unhide = "hideAutomationSettings",
            unhideValue = "true"
    )
    default String listOrder() { return "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27"; }

    @ConfigItem(
            keyName = "minTime",
            name = "Min (millis)",
            description = "The minimum time between dropping.",
            position = 23,
            title = "automationTitle",
            hidden = true,
            unhide = "hideAutomationSettings",
            unhideValue = "true"
    )
    default String minTime() { return "250"; }

    @ConfigItem(
            keyName = "maxTime",
            name = "Max (millis)",
            description = "The maximum time between dropping.",
            position = 24,
            title = "automationTitle",
            hidden = true,
            unhide = "hideAutomationSettings",
            unhideValue = "true"
    )
    default String maxTime() { return "1000"; }

//  ███╗   ███╗██╗███████╗ ██████╗
//  ████╗ ████║██║██╔════╝██╔════╝
//  ██╔████╔██║██║███████╗██║
//  ██║╚██╔╝██║██║╚════██║██║
//  ██║ ╚═╝ ██║██║███████║╚██████╗
//  ╚═╝     ╚═╝╚═╝╚══════╝ ╚═════╝
//

    @ConfigTitle(
            keyName = "miscTitle",
            name = "Miscellaneous Options",
            description = "",
            position = 25
    )
    String miscTitle = "miscTitle";

    @ConfigItem(
            keyName = "displayQOLClickOverlay",
            name = "Display qol click overlay",
            description = "If enabled, will display an overlay over an NPC / Object that you're interacting with.",
            position = 26,
            title = "miscTitle"
    )
    default boolean displayQOLClickOverlay() { return true; }

    @ConfigItem(
            keyName = "enableDebug",
            name = "Enable console debugging",
            description = "",
            position = 26,
            title = "miscTitle"
    )
    default boolean enableDebug() { return true; }

    @ConfigItem(
            keyName = "enableClipboard",
            name = "Enable copy to clipboard",
            description = "",
            position = 26,
            title = "miscTitle",
            hidden = true,
            unhide = "enableDebug",
            unhideValue = "true"
    )
    default boolean enableClipboard() { return true; }
}