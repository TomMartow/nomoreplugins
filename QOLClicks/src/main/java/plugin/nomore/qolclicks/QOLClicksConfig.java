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
package plugin.nomore.qolclicks;

import net.runelite.client.config.*;

@ConfigGroup("qolclicks")
public interface QOLClicksConfig extends Config
{

	// https://www.patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=Type%20Something%20

	@ConfigSection(
			keyName = "cookingSection",
			name = "Cooking",
			description = "",
			position = 2
	)
	String cookingSection = "cookingSection";

	@ConfigItem(
			keyName = "enableFire",
			name = "Fire",
			description = "Add a menu option \"Cook\" to raw food items. The menu option will only show if a fire is nearby.",
			position = 1,
			section = "cookingSection"
	)
	default boolean enableFire() { return false; }

	@ConfigItem(
			keyName = "enableRange",
			name = "Range",
			description = "Add a menu option \"Cook\" to raw food items. The menu option will only show if a range is nearby.",
			position = 1,
			section = "cookingSection"
	)
	default boolean enableRange() { return false; }

	@ConfigSection(
			keyName = "fishingSection",
			name = "Fishing",
			description = "",
			position = 3
	)
	String fishingSection = "fishingSection";

	@ConfigItem(
			keyName = "enableBarbarianRod",
			name = "Barbarian rod",
			description = "Add a menu option \"Use-rod\" to the barbarian rod. The menu option will only show if a barbarian rod fishing spot is nearby.",
			position = 1,
			section = "fishingSection"
	)
	default boolean enableBarbarianRod() { return false; }

	@ConfigItem(
			keyName = "enableCutOffcuts",
			name = "  Offcuts",
			description = "Add a menu option \"Cut\" to the knife. The menu option will only show if a leaping trout, salmon or sturgeon is in the inventory.",
			position = 2,
			section = "fishingSection"
	)
	default boolean enableCutOffcuts() { return false; }

	@ConfigItem(
			keyName = "enableCutOffcutsAutoCut",
			name = "    Auto-cut fishes",
			description = "Auto cuts fish into offcuts.",
			position = 3,
			section = "fishingSection",
			hidden = true,
			unhide = "enableCutOffcuts",
			unhideValue = "true"
	)
	default boolean enableCutOffcutsAutoCut() { return false; }

	@ConfigItem(
			keyName = "enableCutOffcutsAutoDrop",
			name = "    Auto-drop offcuts",
			description = "Auto drops the offcuts.",
			position = 4,
			section = "fishingSection",
			hidden = true,
			unhide = "enableCutOffcuts",
			unhideValue = "true"
	)
	default boolean enableCutOffcutsAutoDrop() { return false; }

	@ConfigItem(
			keyName = "enableLobsterPot",
			name = "Lobster pot",
			description = "Add a menu option \"Cage\" to the lobster pot. The menu option will only show if a cage fishing spot is nearby.",
			position = 5,
			section = "fishingSection"
	)
	default boolean enableLobsterPot() { return false; }

	@ConfigItem(
			keyName = "enableFishingRod",
			name = "Lure",
			description = "Add a menu option \"Lure\" to the fly fishing rod. The menu option will only show if a lure fishing spot it nearby.",
			position = 6,
			section = "fishingSection"
	)
	default boolean enableFishingRod() { return false; }

	@ConfigSection(
			keyName = "firemakingSection",
			name = "Firemaking",
			description = "",
			position = 4
	)
	String firemakingSection = "firemakingSection";

	@ConfigItem(
			keyName = "enableFiremaking",
			name = "Burn",
			description = "Add a menu option \"Burn\" to all logs. The menu option will only show if a tinderbox is in the inventory.",
			position = 1,
			section = "firemakingSection"
	)
	default boolean enableFiremaking() { return false; }

	@ConfigSection(
			keyName = "prayerSection",
			name = "Prayer",
			description = "",
			position = 5
	)
	String prayerSection = "prayerSection";

	@ConfigItem(
			keyName = "enableUnnoteBones",
			name = "Unnote Bones",
			description = "Add a menu option \"Unnote\" to all bones. The menu option will only show if the npc \"Phials\" is nearby.",
			position = 1,
			section = "prayerSection"
	)
	default boolean enableUnnoteBones() { return false; }

	@ConfigSection(
			keyName = "bankingSection",
			name = "Banking",
			description = "",
			position = 6
	)
	String bankingSection = "bankingSection";

	@ConfigItem(
			keyName = "enableBanking",
			name = "Bank",
			description = "Add a menu option \"Bank\" to the inventory tab. The menu option will only show if a bank is nearby.",
			position = 1,
			section = "bankingSection"
	)
	default boolean enableBanking() { return false; }

	@ConfigSection(
			keyName = "dropSection",
			name = "Inventory Dropping",
			description = "",
			position = 7
	)
	String dropSection = "dropSection";

	@ConfigItem(
			keyName = "enableDropMatching",
			name = "Drop-Matching",
			description = "Add a menu option \"Drop-Matching\" to the inventory tab. When clicked, it will drop all items in your inventory that match the names in the \"Matching\" text box.",
			position = 1,
			section = "dropSection"
	)
	default boolean enableDropMatching() { return false; }

	@ConfigItem(
			keyName = "matchingTextBox",
			name = "  Drop list",
			description = "Add the name of the item that you wish to drop here.",
			position = 2,
			section = "dropSection"
	)
	default String dropMatchingTextBox() { return "Bones,"; }

	@ConfigItem(
			keyName = "dropMatchingKeybind",
			name = "  D-M Keybind",
			description = "Drops the items above.",
			position = 3,
			section = "dropSection"
	)
	default Keybind dropMatchingKeybind() { return Keybind.NOT_SET; }

	@ConfigItem(
			keyName = "enableDropExcept",
			name = "Drop-Except",
			description = "Add a menu option \"Drop-Except\" to the inventory tab. When clicked, it will drop all items in your inventory that do not match the names in the \"Not matching\" text box.",
			position = 4,
			section = "dropSection"
	)
	default boolean enableDropExcept() { return false; }

	@ConfigItem(
			keyName = "itemsToDrop",
			name = "  Ignore list",
			description = "Add the name of the item that you wish to ignore here.",
			position = 5,
			section = "dropSection"
	)
	default String dropExceptTextBox() { return "Coins,"; }

	@ConfigItem(
			keyName = "dropExceptKeybind",
			name = "  D-E Keybind",
			description = "Drops everything except the items above.",
			position = 6,
			section = "dropSection"
	)
	default Keybind dropExceptKeybind() { return Keybind.NOT_SET; }

	@ConfigTitle(
			keyName = "automationTitle",
			name = "Automation",
			description = "The minimum amount of time it will take to drop an item.",
			position = 8
	)
	String automationTitle = "automationTitle";

	@ConfigItem(
			keyName = "dropAllMinTime",
			name = "Min time (millis)",
			description = "The minimum amount of time it will take to drop an item.",
			position = 1,
			title = "automationTitle"
	)
	default int dropMinTime() { return 250; }

	@ConfigItem(
			keyName = "dropAllMaxTime",
			name = "Max time (millis)",
			description = "The maximum amount of time it will take to drop an item.",
			position = 2,
			title = "automationTitle"
	)
	default int dropMaxTime() { return 1000; }

	@ConfigItem(
			keyName = "dropOrder",
			name = "Drop order",
			description = "The order that the items in the inventory will be dropped.",
			position = 3,
			title = "automationTitle"
	)
	default String dropOrder() { return "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27"; }

	@ConfigTitle(
			keyName = "selectionTitle",
			name = "Miscellaneous Options",
			description = "",
			position = 9
	)
	String selectionTitle = "";

	@ConfigItem(
			keyName = "findClosest",
			name = "Find closest",
			description = "Instead of randomising the selection of an NPC / Object / Item, when this option is enabled you will always select the closest / first.",
			position = 1,
			title = "selectionTitle"
	)
	default boolean findClosest() { return false; }

	@ConfigItem(
			keyName = "enableKeybinds",
			name = "Enable keybinds",
			description = "Enable the use of keybinds.",
			position = 2,
			title = "selectionTitle"
	)
	default boolean enableKeybinds() { return false; }

	@ConfigTitle(
			keyName = "debugTitle",
			name = "Developer",
			description = "",
			position = 100
	)
	String debugTitle = "debugTitle";

	@ConfigItem(
			keyName = "enableDebugMessages",
			name = "Debug Messages",
			description = "Enable debug messages (dev only).",
			position = 101,
			title = "debugTitle"
	)
	default boolean enableDebugging() { return false; }

	@ConfigItem(
			keyName = "enableWriteToClipboard",
			name = "Copy to clipboard",
			description = "Copies the menu entry to your clipboard to provide suggestions.",
			hidden = true,
			unhide = "enableDebugMessages",
			unhideValue = "true",
			position = 102,
			title = "debugTitle"
	)
	default boolean enableWriteToClipboard() { return false; }

}