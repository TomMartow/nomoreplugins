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

@ConfigGroup("qolclicksbeta")
public interface QOLClicksBetaConfig extends Config
{

    enum Options
    {
        INV_ITEM_USE("Click Item -> Drop Item"),
        INV_ITEM_USE_ON_NPC("Click Item -> Use on NPC"),
        INV_ITEM_USE_ON_GAME_OBJECT("Click Item -> Use on Game Object"),
        INV_ITEM_USE_ON_WIDGET_ITEM("Click Item -> Use on Item"),
        INV_NPC_FIRST_OPTION("Click Item -> NPC First Option"),
        INV_NPC_SECOND_OPTION("Click Item -> NPC Second Option"),
        INV_NPC_THIRD_OPTION("Click Item -> NPC Third Option"),
        INV_NPC_FOURTH_OPTION("Click Item -> NPC Fourth Option"),
        INV_NPC_FIFTH_OPTION("Click Item -> NPC Fifth Option"),
        INV_GAME_OBJECT_FIRST_OPTION("Click Item -> Game Object First Option"),
        INV_GAME_OBJECT_SECOND_OPTION("Click Item -> Game Object Second Option"),
        INV_GAME_OBJECT_THIRD_OPTION("Click Item -> Game Object Third Option"),
        INV_GAME_OBJECT_FOURTH_OPTION("Click Item -> Game Object Fourth Option"),
        INV_GAME_OBJECT_FIFTH_OPTION("Click Item -> Game Object Fifth Option"),
        INV_SPELL_CAST_ON_WIDGET("Click Item -> Cast Spell on Item"),
        INV_SPELL_CAST_ON_NPC("Click Item -> Cast Spell on NPC"),
        INV_SPELL_CAST_ON_GAME_OBJECT("Click Item -> Cast Spell on Game Object"),
        INV_SPELL_CAST_ON_GROUND_ITEM("Click Item -> Cast Spell on Ground Item"),
        INV_SPELL_CAST_ON_PLAYER("Click Item ->  Cast Spell on Player"),

        // Click npc to perform action.

        NPC_ITEM_USE_ON_NPC("Click NPC -> Use Item on NPC"),
        NPC_SPELL_CAST_ON_NPC("Click NPC -> Cast Spell on NPC"),

        // Click game object to perform action

        GAME_OBJECT_ITEM_USE_ON_GAME_OBJECT("Click Game Object -> Use Item on Object"),
        NPC_SPELL_CAST_ON_GAME_OBJECT("Click Game Object -> Cast Spell on Game Object"),

        // Misc

        SPELL_CAST_ON_WIDGET("Click Spell ->"),
        SPELL_CAST_ON_NPC(""),
        SPELL_CAST_ON_GAME_OBJECT(""),
        SPELL_CAST_ON_GROUND_ITEM(""),
        SPELL_CAST_ON_PLAYER(""),
        WIDGET_FIRST_OPTION(""),
        WIDGET_SECOND_OPTION(""),
        WIDGET_THIRD_OPTION(""),
        WIDGET_FOURTH_OPTION(""),
        WIDGET_FIFTH_OPTION(""),
        WIDGET_TYPE_1(""),
        WIDGET_TYPE_2(""),
        WIDGET_TYPE_3(""),
        WIDGET_TYPE_4(""),
        WIDGET_TYPE_5(""),
        WIDGET_TYPE_6("");

        final String name;

        Options(String name) {
            this.name = name;
        }
    }

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
    default Options configOptions() { return Options.INV_ITEM_USE; }
    
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
    default String INV_ITEM_USE_EXPLAINATION() { return ""; }

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
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_ITEM_USE_ON_NPC_EXPLAINATION() { return ""; }

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
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_ITEM_USE_ON_GAME_OBJECT_EXPLAINATION() { return ""; }

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
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_ITEM_USE_ON_WIDGET_ITEM_EXPLAINATION() { return ""; }

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
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_NPC_FIRST_OPTION_EXPLAINATION() { return ""; }

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
            keyName = "INV_NPC_FIRST_OPTION_CONFIG_STRING",
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_NPC_SECOND_OPTION_EXPLAINATION() { return ""; }

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
            keyName = "INV_NPC_SECOND_OPTION_CONFIG_STRING",
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_NPC_THIRD_OPTION_EXPLAINATION() { return ""; }

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
            keyName = "INV_NPC_THIRD_OPTION_CONFIG_STRING",
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_NPC_FOURTH_OPTION_EXPLAINATION() { return ""; }

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
            keyName = "INV_NPC_FOURTH_OPTION_CONFIG_STRING",
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_NPC_FIFTH_OPTION_EXPLAINATION() { return ""; }

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
            keyName = "INV_NPC_FIFTH_OPTION_CONFIG_STRING",
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_GAME_OBJECT_FIRST_OPTION_EXPLAINATION() { return ""; }

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
            keyName = "INV_GAME_OBJECT_FIRST_OPTION_CONFIG_STRING",
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_GAME_OBJECT_SECOND_OPTION_EXPLAINATION() { return ""; }

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
            keyName = "INV_GAME_OBJECT_SECOND_OPTION_CONFIG_STRING",
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_GAME_OBJECT_THIRD_OPTION_EXPLAINATION() { return ""; }

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
            keyName = "INV_GAME_OBJECT_THIRD_OPTION_CONFIG_STRING",
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_GAME_OBJECT_FOURTH_OPTION_EXPLAINATION() { return ""; }

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
            keyName = "INV_GAME_OBJECT_FOURTH_OPTION_CONFIG_STRING",
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_GAME_OBJECT_FIFTH_OPTION_EXPLAINATION() { return ""; }

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
            keyName = "INV_GAME_OBJECT_FIFTH_OPTION_CONFIG_STRING",
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_SPELL_CAST_ON_WIDGET_EXPLAINATION() { return ""; }

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
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Item"
    )
    default String INV_SPELL_CAST_ON_WIDGET_CONFIG_STRING() { return ""; }

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
    default String INV_SPELL_CAST_ON_NPC_EXPLAINATION() { return ""; }

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
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_SPELL_CAST_ON_GAME_OBJECT_EXPLAINATION() { return ""; }

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
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
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
    default String INV_SPELL_CAST_ON_GROUND_ITEM_EXPLAINATION() { return ""; }

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
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Ground Item"
    )
    default String INV_SPELL_CAST_ON_GROUND_ITEM_CONFIG_STRING() { return ""; }

    //INV_SPELL_CAST_ON_PLAYER
    //
    //

    @ConfigItem(
            keyName = "INV_SPELL_CAST_ON_PLAYER_EXPLAINATION",
            name = "Instructions",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Player"
    )
    default String INV_SPELL_CAST_ON_PLAYER_EXPLAINATION() { return ""; }

    @ConfigItem(
            keyName = "ENABLE_INV_SPELL_CAST_ON_PLAYER",
            name = "Enable",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Player"
    )
    default boolean ENABLE_INV_SPELL_CAST_ON_PLAYER() { return false; }

    @ConfigItem(
            keyName = "INV_SPELL_CAST_ON_PLAYER_CONFIG_STRING",
            name = "TEXT_BOX_PLACEHOLDER_TEXT",
            description = "",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "Click Item -> Cast Spell on Player"
    )
    default String INV_SPELL_CAST_ON_PLAYER_CONFIG_STRING() { return ""; }




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
}