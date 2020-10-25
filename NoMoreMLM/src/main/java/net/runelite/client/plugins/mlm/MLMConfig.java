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
package net.runelite.client.plugins.mlm;

import net.runelite.client.config.*;

import java.awt.Color;

@ConfigGroup("NoMoreMLM")
public interface MLMConfig extends Config
{
	@ConfigSection(
			keyName = "oreVeinSection",
			name = "Ore Veins",
			description = "",
			position = 1
	)
	default boolean oreVeinSection() { return false; }

	@ConfigTitleSection(
			keyName = "lowerOreVeinSectionTitle",
			name = "Lower Level Ore Veins",
			description = "",
			position = 2,
			section = "oreVeinSection"
	)
	default Title lowerOreVeinSectionTitle() { return new Title(); }

	@ConfigItem(
			keyName = "enableLowerVeinIndicator",
			name = "Enable",
			description = "Displays an indicator over the lower floor ore veins.",
			position = 3,
			titleSection = "lowerOreVeinSectionTitle"
	)
	default boolean enableLowerVeinIndicator() { return false; }

	@ConfigItem(
			keyName = "enableLowerNorthVeins",
			name = "North (Use only 1)",
			description = "",
			position = 4,
			titleSection = "lowerOreVeinSectionTitle"
	)
	default boolean enableLowerNorthVeins() { return false; }

	@ConfigItem(
			keyName = "enableLowerEastVeins",
			name = "East (Use only 1)",
			description = "",
			position = 5,
			titleSection = "lowerOreVeinSectionTitle"
	)
	default boolean enableLowerEastVeins() { return false; }

	@ConfigItem(
			keyName = "enableLowerWestVeins",
			name = "West (Use only 1)",
			description = "",
			position = 6,
			titleSection = "lowerOreVeinSectionTitle"
	)
	default boolean enableLowerWestVeins() { return false; }

	@ConfigItem(
			keyName = "enableLowerSouthVeins",
			name = "South (Use only 1)",
			description = "",
			position = 7,
			titleSection = "lowerOreVeinSectionTitle"
	)
	default boolean enableLowerSouthVeins() { return false; }

	@ConfigItem(
			keyName = "lowerVeinSize",
			name = "Box size",
			description = "The size of the indicator.",
			position = 8,
			titleSection = "lowerOreVeinSectionTitle"
	)
	default int lowerVeinSize() { return 4; }

	@ConfigItem(
			keyName = "lowerVeinColor",
			name = "Color",
			description = "The color of the indicator.",
			position = 9,
			titleSection = "lowerOreVeinSectionTitle"
	)
	default Color lowerVeinColor() { return Color.GREEN; }

	@ConfigTitleSection(
			keyName = "upperOreVeinSectionTitle",
			name = "Upper Level Ore Veins",
			description = "",
			position = 10,
			section = "oreVeinSection"
	)
	default Title upperOreVeinSectionTitle() { return new Title(); }

	@ConfigItem(
			keyName = "enableUpperVeinIndicator",
			name = "Enable",
			description = "Displays an indicator over the upper floor ore veins.",
			position = 11,
			titleSection = "upperOreVeinSectionTitle"
	)
	default boolean enableUpperVeinIndicator() { return false; }

	@ConfigItem(
			keyName = "enableUpperNorthWestOres",
			name = "Dislay NW veins",
			description = "",
			position = 12,
			titleSection = "upperOreVeinSectionTitle"
	)
	default boolean enableUpperNorthWestOres() { return false; }

	@ConfigItem(
			keyName = "enableUpperNorthEastOres",
			name = "Dislay NE veins",
			description = "",
			position = 13,
			titleSection = "upperOreVeinSectionTitle"
	)
	default boolean enableUpperNorthEastOres() { return false; }

	@ConfigItem(
			keyName = "enableUpperSouthOres",
			name = "Dislay S veins",
			description = "",
			position = 14,
			titleSection = "upperOreVeinSectionTitle"
	)
	default boolean enableUpperSouthOres() { return false; }

	@ConfigItem(
			keyName = "upperVeinSize",
			name = "Box size",
			description = "The size of the indicator.",
			position = 15,
			titleSection = "upperOreVeinSectionTitle"
	)
	default int upperVeinSize() { return 4; }

	@ConfigItem(
			keyName = "upperVeinColor",
			name = "Color",
			description = "The color of the indicator.",
			position = 16,
			titleSection = "upperOreVeinSectionTitle"
	)
	default Color upperVeinColor() { return Color.GREEN; }

	@ConfigSection(
			keyName = "ladderSection",
			name = "Ladder",
			description = "",
			position = 13
	)
	default boolean ladderSection() { return false; }

	@ConfigTitleSection(
			keyName = "ladderObjectIndicatorTitle",
			name = "Object Indicator",
			description = "",
			position = 14,
			section = "ladderSection"
	)
	default Title ladderObjectIndicatorTitle() { return new Title(); }

	@ConfigItem(
			keyName = "enableLadderIndicator",
			name = "Enable",
			description = "Enables the indicator.",
			position = 15,
			titleSection = "ladderObjectIndicatorTitle"
	)
	default boolean enableLadderIndicator() { return false; }

	@ConfigItem(
			keyName = "ladderSize",
			name = "Box size",
			description = "The size of the indicator.",
			position = 16,
			titleSection = "ladderObjectIndicatorTitle"
	)
	default int ladderSize() { return 4; }

	@ConfigItem(
			keyName = "ladderColor",
			name = "Color",
			description = "The color of the indicator.",
			position = 17,
			titleSection = "ladderObjectIndicatorTitle"
	)
	default Color ladderColor() { return Color.YELLOW; }

	@ConfigSection(
			keyName = "waterWheelSection",
			name = "Water Wheel",
			description = "",
			position = 18
	)
	default boolean waterWheelSection() { return false; }

	@ConfigTitleSection(
			keyName = "waterWheelCofigTitle",
			name = "Object Indicator",
			description = "",
			position = 19,
			section = "waterWheelSection"
	)
	default Title waterWheelCofigTitle() { return new Title(); }

	@ConfigItem(
			keyName = "enableWaterWheelIndicator",
			name = "Enable",
			description = "Enables the indicator.",
			position = 20,
			titleSection = "waterWheelCofigTitle"
	)
	default boolean enableWaterWheelIndicator() { return false; }

	@ConfigItem(
			keyName = "waterWheelSize",
			name = "Box size",
			description = "The size of the indicator.",
			position = 21,
			titleSection = "waterWheelCofigTitle"
	)
	default int waterWheelSize() { return 4; }

	@ConfigItem(
			keyName = "waterWheelColor",
			name = "Color",
			description = "The color of the indicator.",
			position = 22,
			titleSection = "waterWheelCofigTitle"
	)
	default Color waterWheelColor() { return Color.RED; }

	@ConfigTitleSection(
			keyName = "waterWheelCanvasIndicatorSection",
			name = "Scene Indicator",
			description = "",
			position = 23,
			section = "waterWheelSection"
	)
	default Title waterWheelCanvasIndicatorSection() { return new Title(); }

	@ConfigItem(
			keyName = "enableWaterWheelSceneIndicator",
			name = "Enable",
			description = "Enables the indicator.",
			position = 24,
			titleSection = "waterWheelCanvasIndicatorSection"
	)
	default boolean enableWaterWheelSceneIndicator() { return false; }

	@Range(
			min = 1,
			max = 2
	)
	@ConfigItem(
			keyName = "amountOfBrokenWaterWheelToDisplaySceneIndicator",
			name = "Amount to display",
			description = "The number of broken water wheels to display the scene indicator.",
			position = 25,
			titleSection = "waterWheelCanvasIndicatorSection"
	)
	default int amountOfBrokenWaterWheelToDisplaySceneIndicator() { return 2; }

	@ConfigItem(
			keyName = "waterWheelSceneIndicatorLocation",
			name = "Location X:Y:Width:Height",
			description = "The location of the indicator on screen.",
			position = 26,
			titleSection = "waterWheelCanvasIndicatorSection"
	)
	default String waterWheelSceneIndicatorLocation() { return "0:0:5:5"; }

	@ConfigItem(
			keyName = "waterWheelSceneIndicatorColor",
			name = "Color",
			description = "The color of the indicator.",
			position = 27,
			titleSection = "waterWheelCanvasIndicatorSection"
	)
	default Color waterWheelSceneIndicatorColor() { return Color.RED; }

	@ConfigSection(
			keyName = "hopperSection",
			name = "Hopper",
			description = "",
			position = 28
	)
	default boolean hopperSection() { return false; }

	@ConfigTitleSection(
			keyName = "hopperObjectIndicatorTitle",
			name = "Object Indicator",
			description = "",
			position = 29,
			section = "hopperSection"
	)
	default Title hopperObjectIndicatorTitle() { return new Title(); }

	@ConfigItem(
			keyName = "enableHopperObjectIndicator",
			name = "Enable",
			description = "Enables the object indicator.",
			position = 30,
			titleSection = "hopperObjectIndicatorTitle"
	)
	default boolean enableHopperObjectIndicator() { return false; }

	@ConfigItem(
			keyName = "hopperObjectIndicatorSize",
			name = "Box size",
			description = "The size of the object indicator.",
			position = 31,
			titleSection = "hopperObjectIndicatorTitle"
	)
	default int hopperObjectIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "hopperObjectIndicatorColor",
			name = "Color",
			description = "The color of the object indicator.",
			position = 32,
			titleSection = "hopperObjectIndicatorTitle"
	)
	default Color hopperObjectIndicatorColor() { return Color.ORANGE; }

	@ConfigSection(
			keyName = "sackSection",
			name = "Sack",
			description = "",
			position = 33
	)
	default boolean sackSection() { return false; }

	@ConfigTitleSection(
			keyName = "sackObjectIndicatorTitle",
			name = "Object Indicator",
			description = "",
			position = 34,
			section = "sackSection"
	)
	default Title sackObjectIndicatorTitle() { return new Title(); }

	@ConfigItem(
			keyName = "enableSackObjectIndicator",
			name = "Enable",
			description = "Enables the object indicator.",
			position = 35,
			titleSection = "sackObjectIndicatorTitle"
	)
	default boolean enableSackObjectIndicator() { return false; }

	@ConfigItem(
			keyName = "sackObjectIndicatorSize",
			name = "Box size",
			description = "The size of the object indicator.",
			position = 36,
			titleSection = "sackObjectIndicatorTitle"
	)
	default int sackObjectIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "sackObjectIndicatorColor",
			name = "Color",
			description = "The color of the object indicator.",
			position = 37,
			titleSection = "sackObjectIndicatorTitle"
	)
	default Color sackObjectIndicatorColor() { return Color.CYAN; }

	@ConfigSection(
			keyName = "bankSection",
			name = "Bank",
			description = "",
			position = 38
	)
	default boolean bankSection() { return false; }

	@ConfigTitleSection(
			keyName = "bankObjectIndicatorTitle",
			name = "Object Indicator",
			description = "",
			position = 39,
			section = "bankSection"
	)
	default Title bankObjectIndicatorTitle() { return new Title(); }

	@ConfigItem(
			keyName = "enableBankObjectIndicator",
			name = "Enable",
			description = "Enables the object indicator.",
			position = 40,
			titleSection = "bankObjectIndicatorTitle"
	)
	default boolean enableBankObjectIndicator() { return false; }

	@ConfigItem(
			keyName = "bankObjectIndicatorSize",
			name = "Box size",
			description = "The size of the object indicator.",
			position = 41,
			titleSection = "bankObjectIndicatorTitle"
	)
	default int bankObjectIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "bankObjectIndicatorColor",
			name = "Color",
			description = "The color of the object indicator.",
			position = 42,
			titleSection = "bankObjectIndicatorTitle"
	)
	default Color bankObjectIndicatorColor() { return Color.BLUE; }

	@ConfigSection(
			keyName = "shortcutSection",
			name = "Shortcut",
			description = "",
			position = 43
	)
	default boolean shortcutSection() { return false; }

	@ConfigTitleSection(
			keyName = "shortcutObjectIndicatorTitle",
			name = "Object Indicator",
			description = "",
			position = 44,
			section = "shortcutSection"
	)
	default Title shortcutObjectIndicatorTitle() { return new Title(); }

	@ConfigItem(
			keyName = "enableShortcutObjectIndicator",
			name = "Enable",
			description = "Enables the object indicator.",
			position = 45,
			titleSection = "shortcutObjectIndicatorTitle"
	)
	default boolean enableShortcutObjectIndicator() { return false; }

	@ConfigItem(
			keyName = "shortcutObjectIndicatorSize",
			name = "Box size",
			description = "The size of the object indicator.",
			position = 46,
			titleSection = "shortcutObjectIndicatorTitle"
	)
	default int shortcutObjectIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "shortcutObjectIndicatorColor",
			name = "Color",
			description = "The color of the object indicator.",
			position = 48,
			titleSection = "shortcutObjectIndicatorTitle"
	)
	default Color shortcutObjectIndicatorColor() { return Color.MAGENTA; }

	@ConfigSection(
			keyName = "rockfallSection",
			name = "Rockfall (experimental)",
			description = "",
			position = 49
	)
	default boolean rockfallSection() { return false; }

	@ConfigTitleSection(
			keyName = "rockfallObjectIndicatorTitle",
			name = "Object Indicator",
			description = "",
			position = 50,
			section = "rockfallSection"
	)
	default Title rockfallObjectIndicatorTitle() { return new Title(); }

	@ConfigItem(
			keyName = "enableRockfallObjectIndicator",
			name = "Enable",
			description = "Enables the object indicator.",
			position = 51,
			titleSection = "rockfallObjectIndicatorTitle"
	)
	default boolean enableRockfallObjectIndicator() { return false; }

	@ConfigItem(
			keyName = "rockfallObjectIndicatorSize",
			name = "Box size",
			description = "The size of the object indicator.",
			position = 52,
			titleSection = "rockfallObjectIndicatorTitle"
	)
	default int rockfallObjectIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "rockfallObjectIndicatorColor",
			name = "Color",
			description = "The color of the object indicator.",
			position = 53,
			titleSection = "rockfallObjectIndicatorTitle"
	)
	default Color rockfallObjectIndicatorColor() { return Color.PINK; }

}