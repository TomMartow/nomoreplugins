/*
 * Copyright (c) 2018, Cas <https://github.com/casvandongen>
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
package com.nomore.mycharacterindicators;

import java.awt.*;

import com.nomore.mycharacterindicators.configenums.PlayerIndicatorsEnum;
import net.runelite.client.config.*;

@ConfigGroup("playerstateindicators")
public interface PlayerStateConfig extends Config {

    @ConfigTitleSection(
            keyName = "firstTitle",
            name = "Player Indicators",
            description = "",
            position = 1
    )
    default Title firstTitle() {
        return new Title();
    }

    @ConfigItem(
            keyName = "playerIndicatorsEnum",
            name = "Display options for",
            description = "Drop down menu to display configuration options.",
            position = 2,
            titleSection = "firstTitle"
    )
    default PlayerIndicatorsEnum playerIndicatorsEnum() { return PlayerIndicatorsEnum.HITPOINTS; }

    @ConfigItem(
            keyName = "displayLowHP",
            name = "Enabled",
            description = "Enable the indicator.",
            position = 3,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "HITPOINTS",
            titleSection = "firstTitle"
    )
    default boolean displayLowHP() { return false; }

    @ConfigItem(
            keyName = "hpLevel",
            name = "HP level",
            description = "Displays an indicator when the HP level is below a certain value.",
            position = 4,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "HITPOINTS",
            titleSection = "firstTitle"
    )
    default int lowHPLevel() { return 10; }

    @ConfigItem(
            keyName = "highlightEatItems",
            name = "Highlight food",
            description = "Highlights items that can be consumed.",
            position = 5,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "HITPOINTS",
            titleSection = "firstTitle"
    )
    default boolean highlightEatItems() { return false; }

    @ConfigItem(
            keyName = "hpLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 100:100:10:10",
            position = 6,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "HITPOINTS",
            titleSection = "firstTitle"
    )
    default String hpLocation() { return "50:0:5:5"; }

    @ConfigItem(
            keyName = "hpColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 7,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "HITPOINTS",
            titleSection = "firstTitle"
    )
    default Color hpColor() { return Color.RED; }

    @ConfigItem(
            keyName = "displayLowPrayer",
            name = "Enabled",
            description = "Enable the indicator.",
            position = 8,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "PRAYER",
            titleSection = "firstTitle"
    )
    default boolean displayLowPrayer() {
        return false;
    }

    @ConfigItem(
            keyName = "prayerLevel",
            name = "Prayer level",
            description = "Displays an indicator when the Prayer level is below a certain value.",
            position = 9,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "PRAYER",
            titleSection = "firstTitle"
    )
    default int lowPrayerLevel() { return 10; }

    @ConfigItem(
            keyName = "prayerLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 100:100:10:10",
            position = 10,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "PRAYER",
            titleSection = "firstTitle"
    )
    default String prayerLocation() { return "55:0:5:5"; }

    @ConfigItem(
            keyName = "prayerColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 11,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "PRAYER",
            titleSection = "firstTitle"
    )
    default Color prayerColor() { return Color.CYAN; }

    @ConfigItem(
            keyName = "displayLowEnergy",
            name = "Enabled",
            description = "Enable the indicator.",
            position = 12,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "ENERGY",
            titleSection = "firstTitle"
    )
    default boolean displayLowEnergy() { return false; }

    @ConfigItem(
            keyName = "energyLevel",
            name = "Energy level",
            description = "Displays an indicator when the run energy level is below a certain value.",
            position = 13,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "ENERGY",
            titleSection = "firstTitle"
    )
    default int lowEnergyLevel() { return 10; }

    @ConfigItem(
            keyName = "energyLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 100:100:10:10",
            position = 14,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "ENERGY",
            titleSection = "firstTitle"
    )
    default String energyLocation() { return "60:0:5:5"; }

    @ConfigItem(
            keyName = "energyColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 15,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "ENERGY",
            titleSection = "firstTitle"
    )
    default Color energyColor() { return Color.ORANGE; }

    @ConfigItem(
            keyName = "displayLowSpecial",
            name = "Enabled",
            description = "Enable the indicator.",
            position = 16,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "SPECIAL",
            titleSection = "firstTitle"
    )
    default boolean displayLowSpecial() { return false; }

    @ConfigItem(
            keyName = "lowSpecialLevel",
            name = "Special level",
            description = "Displays an indicator when the special attack level is below a certain value.",
            position = 17,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "SPECIAL",
            titleSection = "firstTitle"
    )
    default int lowSpecialLevel() { return 10; }

    @ConfigItem(
            keyName = "specialLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 100:100:10:10",
            position = 18,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "SPECIAL",
            titleSection = "firstTitle"
    )
    default String specialLocation() { return "65:0:5:5"; }

    @ConfigItem(
            keyName = "specialColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 19,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "SPECIAL",
            titleSection = "firstTitle"
    )
    default Color specialColor() { return Color.YELLOW; }

    @ConfigItem(
            keyName = "displayLowAttack",
            name = "Enabled",
            description = "Enable the indicator.",
            position = 20,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "ATTACK",
            titleSection = "firstTitle"
    )
    default boolean displayLowAttack() { return false; }

    @ConfigItem(
            keyName = "lowAttackLevel",
            name = "Attack level",
            description = "Displays an indicator when the attack level is below a certain value.",
            position = 21,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "ATTACK",
            titleSection = "firstTitle"
    )
    default int lowAttackLevel() { return 10; }

    @ConfigItem(
            keyName = "attackLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 100:100:10:10",
            position = 22,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "ATTACK",
            titleSection = "firstTitle"
    )
    default String attackLocation() { return "70:0:5:5"; }

    @ConfigItem(
            keyName = "attackColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 23,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "ATTACK",
            titleSection = "firstTitle"
    )
    default Color attackColor() { return Color.RED; }

    @ConfigItem(
            keyName = "displayLowstrength",
            name = "Enabled",
            description = "Enable the indicator.",
            position = 24,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "STRENGTH",
            titleSection = "firstTitle"
    )
    default boolean displayLowStrength() { return false; }

    @ConfigItem(
            keyName = "lowStrengthLevel",
            name = "Strength level",
            description = "Displays an indicator when the strength level is below a certain value.",
            position = 25,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "STRENGTH",
            titleSection = "firstTitle"
    )
    default int lowStrengthLevel() { return 10; }

    @ConfigItem(
            keyName = "strengthLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 100:100:10:10",
            position = 26,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "STRENGTH",
            titleSection = "firstTitle"
    )
    default String strengthLocation() { return "75:0:5:5"; }

    @ConfigItem(
            keyName = "strengthColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 27,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "STRENGTH",
            titleSection = "firstTitle"
    )
    default Color strengthColor() { return Color.RED; }

    @ConfigItem(
            keyName = "displayLowDefence",
            name = "Enabled",
            description = "Enable the indicator.",
            position = 28,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "DEFENCE",
            titleSection = "firstTitle"
    )
    default boolean displayLowDefence() { return false; }

    @ConfigItem(
            keyName = "lowDefenceLevel",
            name = "Defence level",
            description = "Displays an indicator when the defence level is below a certain value.",
            position = 29,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "DEFENCE",
            titleSection = "firstTitle"
    )
    default int lowDefenceLevel() { return 10; }

    @ConfigItem(
            keyName = "defenceLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 100:100:10:10",
            position = 30,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "DEFENCE",
            titleSection = "firstTitle"
    )
    default String defenceLocation() { return "80:0:5:5"; }

    @ConfigItem(
            keyName = "defenceColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 31,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "DEFENCE",
            titleSection = "firstTitle"
    )
    default Color defenceColor() { return Color.RED; }

    @ConfigItem(
            keyName = "displayLowMagic",
            name = "Enabled",
            description = "Enable the indicator.",
            position = 32,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "MAGIC",
            titleSection = "firstTitle"
    )
    default boolean displayLowMagic() { return false; }

    @ConfigItem(
            keyName = "lowMagicLevel",
            name = "Magic level",
            description = "Displays an indicator when the magic level is below a certain value.",
            position = 33,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "MAGIC",
            titleSection = "firstTitle"
    )
    default int lowMagicLevel() { return 10; }

    @ConfigItem(
            keyName = "magicLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 100:100:10:10",
            position = 34,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "MAGIC",
            titleSection = "firstTitle"
    )
    default String magicLocation() { return "85:0:5:5"; }

    @ConfigItem(
            keyName = "magicColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 35,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "MAGIC",
            titleSection = "firstTitle"
    )
    default Color magicColor() { return Color.RED; }

    @ConfigItem(
            keyName = "displayLowRanging",
            name = "Enabled",
            description = "Enable the indicator.",
            position = 36,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "RANGING",
            titleSection = "firstTitle"
    )
    default boolean displayLowRanging() { return false; }

    @ConfigItem(
            keyName = "lowRangingLevel",
            name = "Ranging level",
            description = "Displays an indicator when the ranging level is below a certain value.",
            position = 37,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "RANGING",
            titleSection = "firstTitle"
    )
    default int lowRangingLevel() { return 10; }

    @ConfigItem(
            keyName = "rangingLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 100:100:10:10",
            position = 38,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "RANGING",
            titleSection = "firstTitle"
    )
    default String rangingLocation() { return "90:0:5:5"; }

    @ConfigItem(
            keyName = "rangingColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 39,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "RANGING",
            titleSection = "firstTitle"
    )
    default Color rangingColor() { return Color.RED; }

    @ConfigItem(
            keyName = "displayIdle",
            name = "Enabled",
            description = "Enable the indicator.",
            position = 40,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "IDLE",
            titleSection = "firstTitle"
    )
    default boolean displayIdle() { return false; }

    @ConfigItem(
            keyName = "animationIdle",
            name = "Animations",
            description = "Enable the indicator if the player is not animating.",
            position = 41,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "IDLE",
            titleSection = "firstTitle"
    )
    default boolean animationIdle() { return false; }

    @ConfigItem(
            keyName = "interactingIdle",
            name = "Interacting",
            description = "Enable the indicator if the player is not interacting.",
            position = 42,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "IDLE",
            titleSection = "firstTitle"
    )
    default boolean interactingIdle() { return false; }

    @ConfigItem(
            keyName = "movementIdle",
            name = "Movement",
            description = "Enable the indicator if the player is not moving.",
            position = 43,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "IDLE",
            titleSection = "firstTitle"
    )
    default boolean movementIdle() { return false; }

    @ConfigItem(
            keyName = "idleTime",
            name = "Idle time (milliseconds)",
            description = "Displays an indicator when the player has been idle for x amount of time.",
            position = 44,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "IDLE",
            titleSection = "firstTitle"
    )
    @Units(Units.MILLISECONDS)
    default int idleTime() { return 1000; }

    @ConfigItem(
            keyName = "idleLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 100:100:10:10",
            position = 45,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "IDLE",
            titleSection = "firstTitle"
    )
    default String idleLocation() { return "95:0:5:5"; }

    @ConfigItem(
            keyName = "idleColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 46,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "IDLE",
            titleSection = "firstTitle"
    )
    default Color idleColor() { return Color.RED; }

    @ConfigItem(
            keyName = "displayConnected",
            name = "Enabled",
            description = "Enable the indicator if the player is connected to the game.",
            position = 47,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "GAME",
            titleSection = "firstTitle"
    )
    default boolean displayConnected() { return false; }

    @ConfigItem(
            keyName = "connectedLocation",
            name = "Indicator location",
            description = "Indicator location, format to use: x.y.width.height e.g 100:100:10:10",
            position = 48,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "GAME",
            titleSection = "firstTitle"
    )
    default String connectedLocation() { return "0:0:5:5"; }

    @ConfigItem(
            keyName = "connectedColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 49,
            hidden = true,
            unhide = "playerIndicatorsEnum",
            unhideValue = "GAME",
            titleSection = "firstTitle"
    )
    default Color connectedColor() { return Color.GREEN; }
}
