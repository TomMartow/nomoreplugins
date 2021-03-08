package plugin.nomore.qolclicksbeta;

public enum QOLClickCategory
{

    // Click item in inventory to perform action.

    ITEM_TITLE("-- Click an Inventory Item --"),
    UNUSED_1(""),
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
    UNUSED_2(""),

    // Click npc to perform action.

    NPC_TITLE("-- Click an NPC --"),
    UNUSED_3(""),
    NPC_ITEM_USE_ON_NPC("Click NPC -> Use Item on NPC"),
    NPC_SPELL_CAST_ON_NPC("Click NPC -> Cast Spell on NPC"),
    UNUSED_4(""),

    // Click game object to perform action
    GAME_OBJECT_TITLE("-- Click a Game Object --"),
    UNUSED_5(""),
    GAME_OBJECT_ITEM_USE_ON_GAME_OBJECT("Click Game Object -> Use Item on Object"),
    NPC_SPELL_CAST_ON_GAME_OBJECT("Click Game Object -> Cast Spell on Game Object"),

    // Misc


    SPELL_CAST_ON_WIDGET("Not currently implemented"),
    SPELL_CAST_ON_NPC("Not currently implemented"),
    SPELL_CAST_ON_GAME_OBJECT("Not currently implemented"),
    SPELL_CAST_ON_GROUND_ITEM("Not currently implemented"),
    SPELL_CAST_ON_PLAYER("Not currently implemented"),
    WIDGET_FIRST_OPTION("Not currently implemented"),
    WIDGET_SECOND_OPTION("Not currently implemented"),
    WIDGET_THIRD_OPTION("Not currently implemented"),
    WIDGET_FOURTH_OPTION("Not currently implemented"),
    WIDGET_FIFTH_OPTION("Not currently implemented"),
    WIDGET_TYPE_1("Not currently implemented"),
    WIDGET_TYPE_2("Not currently implemented"),
    WIDGET_TYPE_3("Not currently implemented"),
    WIDGET_TYPE_4("Not currently implemented"),
    WIDGET_TYPE_5("Not currently implemented"),
    WIDGET_TYPE_6("Not currently implemented");

    public final String enumString;

    QOLClickCategory(String enumString) {
        this.enumString = enumString;
    }

    public String toString() {
        return this.enumString;
    }
}
