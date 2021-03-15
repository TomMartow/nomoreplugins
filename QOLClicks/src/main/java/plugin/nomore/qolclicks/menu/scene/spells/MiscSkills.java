package plugin.nomore.qolclicks.menu.scene.spells;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.widgets.WidgetInfo;

@Getter
@AllArgsConstructor
public enum MiscSkills
{

    // STANDARD

    ENCHANT_CROSSBOW_BOLT("Enchant Crossbow Bolt", WidgetInfo.SPELL_ENCHANT_CROSSBOW_BOLT),
    LVL1_ENCHANT("Lvl-1 Enchant", WidgetInfo.SPELL_LVL_1_ENCHANT),
    BONES_TO_BANANAS("Bones to Bananas", WidgetInfo.SPELL_BONES_TO_BANANAS),
    LOW_LEVEL_ALCHEMY("Low Level Alchemy", WidgetInfo.SPELL_LOW_LEVEL_ALCHEMY),
    LVL2_ENCHANT("Lvl-2 Enchant", WidgetInfo.SPELL_LVL_2_ENCHANT),
    TELEKINETIC_GRAB("Telekinetic Grab", WidgetInfo.SPELL_TELEKINETIC_GRAB),
    CRUMBLE_UNDEAD("Crumble Undead", WidgetInfo.SPELL_CRUMBLE_UNDEAD),
    SUPERHEAT_ITEM("Superheat Item", WidgetInfo.SPELL_SUPERHEAT_ITEM),
    LVL3_ENCHANT("Lvl-3 Enchant", WidgetInfo.SPELL_LVL_3_ENCHANT),
    HIGH_LEVEL_ALCHEMY("High Level Alchemy", WidgetInfo.SPELL_HIGH_LEVEL_ALCHEMY),
    CHARGE_WATER_ORB("Charge Water Orb", WidgetInfo.SPELL_CHARGE_WATER_ORB),
    LVL4_ENCHANT("Lvl-4 Enchant", WidgetInfo.SPELL_LVL_4_ENCHANT),
    CHARGE_EARTH_ORB("Charge Earth Orb", WidgetInfo.SPELL_CHARGE_EARTH_ORB),
    BONES_TO_PEACHES("Bones to Peaches", WidgetInfo.SPELL_BONES_TO_PEACHES),
    CHARGE_FIRE_ORB("Charge Fire Orb", WidgetInfo.SPELL_CHARGE_FIRE_ORB),
    CHARGE_AIR_ORB("Charge Air Orb", WidgetInfo.SPELL_CHARGE_AIR_ORB),
    LVL5_ENCHANT("Lvl-5 Enchant", WidgetInfo.SPELL_LVL_5_ENCHANT),
    LVL6_ENCHANT("Lvl-6 Enchant", WidgetInfo.SPELL_LVL_6_ENCHANT),
    LVL7_ENCHANT("Lvl-7 Enchant", WidgetInfo.SPELL_LVL_7_ENCHANT),

    // LUNAR

    BAKE_PIE("Bake Pie", WidgetInfo.SPELL_BAKE_PIE),
    NPC_CONTACT("NPC Contact", WidgetInfo.SPELL_NPC_CONTACT),
    HUMIDIFY("Humidify", WidgetInfo.SPELL_HUMIDIFY),
    SPIN_FLAX("Spin Flax", WidgetInfo.SPELL_SPIN_FLAX),
    SUPERGLASS_MAKE("Superglass Make", WidgetInfo.SPELL_SUPERGLASS_MAKE),
    TAN_LEATHER("Tan Leather", WidgetInfo.SPELL_TAN_LEATHER),
    DREAM("Dream", WidgetInfo.SPELL_DREAM),
    STRING_JEWELLERY("String Jewellery", WidgetInfo.SPELL_STRING_JEWELLERY),
    MAGIC_IMBUE("Magic Imbue", WidgetInfo.SPELL_MAGIC_IMBUE),
    PLANK_MAKE("Plank Make", WidgetInfo.SPELL_PLANK_MAKE),
    RECHARGE_DRAGONSTONE("Recharge Dragonstone", WidgetInfo.SPELL_RECHARGE_DRAGONSTONE),

    // ARCEUUS

    REANIMATE_GOBLIN("Reanimate Goblin", WidgetInfo.SPELL_REANIMATE_GOBLIN),
    REANIMATE_MONKEY("Reanimate Monkey", WidgetInfo.SPELL_REANIMATE_MONKEY),
    REANIMATE_IMP("Reanimate Imp", WidgetInfo.SPELL_REANIMATE_IMP),
    REANIMATE_MINOTAUR("Reanimate Minotaur", WidgetInfo.SPELL_REANIMATE_MINOTAUR),
    REANIMATE_SCORPION("Reanimate Scorpion", WidgetInfo.SPELL_REANIMATE_SCORPION),
    REANIMATE_BEAR("Reanimate Bear", WidgetInfo.SPELL_REANIMATE_BEAR),
    REANIMATE_UNICORN("Reanimate Unicorn", WidgetInfo.SPELL_REANIMATE_UNICORN),
    REANIMATE_DOG("Reanimate Dog", WidgetInfo.SPELL_REANIMATE_DOG),
    REANIMATE_CHAOS_DRUID("Reanimate Chaos Druid", WidgetInfo.SPELL_REANIMATE_CHAOS_DRUID),
    REANIMATE_GIANT("Reanimate Giant", WidgetInfo.SPELL_REANIMATE_GIANT),
    REANIMATE_OGRE("Reanimate Ogre", WidgetInfo.SPELL_REANIMATE_OGRE),
    REANIMATE_ELF("Reanimate Elf", WidgetInfo.SPELL_REANIMATE_ELF),
    REANIMATE_TROLL("Reanimate Troll", WidgetInfo.SPELL_REANIMATE_TROLL),
    REANIMATE_HORROR("Reanimate Horror", WidgetInfo.SPELL_REANIMATE_HORROR),
    REANIMATE_KALPHITE("Reanimate Kalphite", WidgetInfo.SPELL_REANIMATE_KALPHITE),
    REANIMATE_DAGANNOTH("Reanimate Dagannoth", WidgetInfo.SPELL_REANIMATE_DAGANNOTH),
    REANIMATE_BLOODVELD("Reanimate Bloodveld", WidgetInfo.SPELL_REANIMATE_BLOODVELD),
    REANIMATE_TZHAAR("Reanimate TzHaar", WidgetInfo.SPELL_REANIMATE_TZHAAR),
    REANIMATE_DEMON("Reanimate Demon", WidgetInfo.SPELL_REANIMATE_DEMON),
    REANIMATE_AVIANSIE("Reanimate Aviansie", WidgetInfo.SPELL_REANIMATE_AVIANSIE),
    REANIMATE_ABYSSAL_CREATURE("Reanimate Abyssal Creature", WidgetInfo.SPELL_REANIMATE_ABYSSAL),
    REANIMATE_DRAGON("Reanimate Dragon", WidgetInfo.SPELL_REANIMATE_DRAGON);

    private String name;
    private WidgetInfo spell;

    @Override
    public String toString()
    {
        return getName();
    }
}