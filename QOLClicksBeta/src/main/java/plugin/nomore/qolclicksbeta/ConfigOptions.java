package plugin.nomore.qolclicksbeta;

public enum ConfigOptions
{

    // Click item in inventory to perform action.

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

    public final String enumString;

    ConfigOptions(String enumString) {
        this.enumString = enumString;
    }

    public String toString() {
        return this.enumString;
    }
}
