package plugin.nomore.qolclicksbeta.menu.scene.spells;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.widgets.WidgetInfo;

@Getter
@AllArgsConstructor
public enum CombatSpells
{

    // STANDARD

    WIND_STRIKE("Wind Strike", WidgetInfo.SPELL_WIND_STRIKE),
    CONFUSE("Confuse", WidgetInfo.SPELL_CONFUSE),
    WATER_STRIKE("Water Strike", WidgetInfo.SPELL_WATER_STRIKE),
    EARTH_STRIKE("Earth Strike", WidgetInfo.SPELL_EARTH_STRIKE),
    WEAKEN("Weaken", WidgetInfo.SPELL_WEAKEN),
    FIRE_STRIKE("Fire Strike", WidgetInfo.SPELL_FIRE_STRIKE),
    WIND_BOLT("Wind Bolt", WidgetInfo.SPELL_WIND_BOLT),
    CURSE("Curse", WidgetInfo.SPELL_CURSE),
    BIND("Bind", WidgetInfo.SPELL_BIND),
    WATER_BOLT("Water Bolt", WidgetInfo.SPELL_WATER_BOLT),
    LVL2_ENCHANT("Lvl-2 Enchant", WidgetInfo.SPELL_LVL_2_ENCHANT),
    EARTH_BOLT("Earth Bolt", WidgetInfo.SPELL_EARTH_BOLT),
    FIRE_BOLT("Fire Bolt", WidgetInfo.SPELL_FIRE_BOLT),
    CRUMBLE_UNDEAD("Crumble Undead", WidgetInfo.SPELL_CRUMBLE_UNDEAD),
    WIND_BLAST("Wind Blast", WidgetInfo.SPELL_WIND_BLAST),
    WATER_BLAST("Water Blast", WidgetInfo.SPELL_WATER_BLAST),
    IBAN_BLAST("Iban Blast", WidgetInfo.SPELL_IBAN_BLAST),
    SNARE("Snare", WidgetInfo.SPELL_SNARE),
    MAGIC_DART("Magic Dart", WidgetInfo.SPELL_MAGIC_DART),
    EARTH_BLAST("Earth Blast", WidgetInfo.SPELL_EARTH_BLAST),
    FIRE_BLAST("Fire Blast", WidgetInfo.SPELL_FIRE_BLAST),
    SARADOMIN_STRIKE("Saradomin Strike", WidgetInfo.SPELL_SARADOMIN_STRIKE),
    CLAWS_OF_GUTHIX("Claws Of Guthix", WidgetInfo.SPELL_CLAWS_OF_GUTHIX),
    FLAMES_OF_ZAMORAK("Flames Of Zamorak", WidgetInfo.SPELL_FLAMES_OF_ZAMORAK),
    WIND_WAVE("Wind Wave", WidgetInfo.SPELL_WIND_WAVE),
    WATER_WAVE("Water Wave", WidgetInfo.SPELL_WATER_WAVE),
    VULNERABILITY("Vulnerability", WidgetInfo.SPELL_VULNERABILITY),
    EARTH_WAVE("Earth Wave", WidgetInfo.SPELL_EARTH_WAVE),
    ENFEEBLE("Enfeeble", WidgetInfo.SPELL_ENFEEBLE),
    FIRE_WAVE("Fire Wave", WidgetInfo.SPELL_FIRE_WAVE),
    ENTANGLE_("Entangle ", WidgetInfo.SPELL_ENTANGLE),
    STUN("Stun", WidgetInfo.SPELL_STUN),
    WIND_SURGE("Wind Surge", WidgetInfo.SPELL_WIND_SURGE),
    WATER_SURGE("Water Surge", WidgetInfo.SPELL_WATER_SURGE),
    EARTH_SURGE("Earth Surge", WidgetInfo.SPELL_EARTH_SURGE),
    FIRE_SURGE("Fire Surge", WidgetInfo.SPELL_FIRE_SURGE),

    // ANCIENT

    SMOKE_RUSH("Smoke Rush", WidgetInfo.SPELL_SMOKE_RUSH),
    SHADOW_RUSH("Shadow Rush", WidgetInfo.SPELL_SHADOW_RUSH),
    BLOOD_RUSH("Blood Rush", WidgetInfo.SPELL_BLOOD_RUSH),
    ICE_RUSH("Ice Rush", WidgetInfo.SPELL_ICE_RUSH),
    SMOKE_BURST("Smoke Burst", WidgetInfo.SPELL_SMOKE_BURST),
    SHADOW_BURST("Shadow Burst", WidgetInfo.SPELL_SHADOW_BURST),
    BLOOD_BURST("Blood Burst", WidgetInfo.SPELL_BLOOD_BURST),
    ICE_BURST("Ice Burst", WidgetInfo.SPELL_ICE_BURST),
    SMOKE_BLITZ("Smoke Blitz", WidgetInfo.SPELL_SMOKE_BURST),
    SHADOW_BLITZ("Shadow Blitz", WidgetInfo.SPELL_SHADOW_BLITZ),
    BLOOD_BLITZ("Blood Blitz", WidgetInfo.SPELL_BLOOD_BLITZ),
    ICE_BLITZ("Ice Blitz", WidgetInfo.SPELL_ICE_BLITZ),
    SMOKE_BARRAGE("Smoke Barrage", WidgetInfo.SPELL_SMOKE_BARRAGE),
    SHADOW_BARRAGE("Shadow Barrage", WidgetInfo.SPELL_SHADOW_BARRAGE),
    BLOOD_BARRAGE("Blood Barrage", WidgetInfo.SPELL_BLOOD_BARRAGE),
    ICE_BARRAGE("Ice Barrage", WidgetInfo.SPELL_ICE_BARRAGE);

    private String name;
    private WidgetInfo spell;

    @Override
    public String toString()
    {
        return getName();
    }
}