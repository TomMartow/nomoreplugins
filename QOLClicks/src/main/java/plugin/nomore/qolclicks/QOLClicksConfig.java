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

	@ConfigTitleSection(
			keyName = "skillingMenuSwapsTitle",
			name = "Skilling Menu Swaps",
			description = "",
			position = 1
	)
	default Title skillingMenuSwapsTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "skillConfiguration",
			name = "Display options for:",
			description = "",
			position = 2,
			titleSection = "skillingMenuSwapsTitle"
	)
	default SkillOptions skillConfiguration() { return SkillOptions.COOKING; }

	//   ██████╗ ██████╗  ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗
	//  ██╔════╝██╔═══██╗██╔═══██╗██║ ██╔╝██║████╗  ██║██╔════╝
	//  ██║     ██║   ██║██║   ██║█████╔╝ ██║██╔██╗ ██║██║  ███╗
	//  ██║     ██║   ██║██║   ██║██╔═██╗ ██║██║╚██╗██║██║   ██║
	//  ╚██████╗╚██████╔╝╚██████╔╝██║  ██╗██║██║ ╚████║╚██████╔╝
	//   ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝
	//

	@ConfigItem(
			keyName = "enableCooking",
			name = "Cook",
			description = "Add a menu option \"Cook\" to raw food items. The menu option will only show if a fire or range is nearby.",
			position = 3,
			hidden = true,
			unhide = "skillConfiguration",
			unhideValue = "COOKING",
			titleSection = "skillingMenuSwapsTitle"
	)
	default boolean enableCooking() { return false; }

	@ConfigItem(
			keyName = "enableFishing",
			name = "Fly Fishing Rod",
			description = "Add a menu option \"Lure\" to the fly fishing rod. The menu option will only show if a lure fishing spot it nearby.",
			position = 4,
			hidden = true,
			unhide = "skillConfiguration",
			unhideValue = "FISHING",
			titleSection = "skillingMenuSwapsTitle"
	)
	default boolean enableFishingRod() { return false; }

	@ConfigItem(
			keyName = "enableLobsterPot",
			name = "Lobster Cage",
			description = "Add a menu option \"Cage\" to the lobster pot. The menu option will only show if a cage fishing spot is nearby.",
			position = 5,
			hidden = true,
			unhide = "skillConfiguration",
			unhideValue = "FISHING",
			titleSection = "skillingMenuSwapsTitle"
	)
	default boolean enableLobsterPot() { return false; }

	@ConfigItem(
			keyName = "enableBarbarianRod",
			name = "Barbarian Rod",
			description = "Add a menu option \"Fish\" to the barbarian rod. The menu option will only show if a barbarian rod fishing spot is nearby.",
			position = 6,
			hidden = true,
			unhide = "skillConfiguration",
			unhideValue = "FISHING",
			titleSection = "skillingMenuSwapsTitle"
	)
	default boolean enableBarbarianRod() { return false; }

	@ConfigItem(
			keyName = "enableFiremaking",
			name = "Burn",
			description = "Add a menu option \"Burn\" to all logs. The menu option will only show if a tinderbox is in the inventory.",
			position = 6,
			hidden = true,
			unhide = "skillConfiguration",
			unhideValue = "FIREMAKING",
			titleSection = "skillingMenuSwapsTitle"
	)
	default boolean enableFiremaking() { return false; }

	@ConfigItem(
			keyName = "enableUnnoteBones",
			name = "Unnote Bones",
			description = "Add a menu option \"Unnote\" to all bones. The menu option will only show if the npc \"Phials\" is nearby.",
			position = 7,
			hidden = true,
			unhide = "skillConfiguration",
			unhideValue = "PRAYER",
			titleSection = "skillingMenuSwapsTitle"
	)
	default boolean enableUnnoteBones() { return false; }

	@ConfigTitleSection(
			keyName = "miscMenuSwapsTitle",
			name = "Misc Menu Swaps",
			description = "",
			position = 8
	)
	default Title miscMenuSwapsTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableBanking",
			name = "Bank",
			description = "Add a menu option \"Bank\" to the inventory tab. The menu option will only show if a bank is nearby.",
			position = 9,
			titleSection = "miscMenuSwapsTitle"
	)
	default boolean enableBanking() { return false; }

	@ConfigTitleSection(
			keyName = "debugTitle",
			name = "Developer",
			description = "",
			position = 100
	)
	default Title debugTitle() { return new Title(); }

	@ConfigItem(
			keyName = "enableDebugMessages",
			name = "Debug Messages",
			description = "Enable debug messages (dev only).",
			position = 101
	)
	default boolean enableDebugging() { return false; }

	@ConfigItem(
			keyName = "enableWriteToClipboard",
			name = "Copy to clipboard",
			description = "Copies the menu entry to your clipboard to provide suggestions.",
			hidden = true,
			unhide = "enableDebugMessages",
			unhideValue = "true",
			position = 102
	)
	default boolean enableWriteToClipboard() { return false; }

}