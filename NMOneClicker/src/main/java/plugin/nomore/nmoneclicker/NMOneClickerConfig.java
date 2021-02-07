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
package plugin.nomore.nmoneclicker;

import lombok.AllArgsConstructor;
import net.runelite.client.config.*;

import java.awt.*;
import java.util.EnumSet;

@ConfigGroup("nmoneclicker")
public interface NMOneClickerConfig extends Config
{

	// https://www.patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=Type%20Something%20

	@ConfigTitleSection(
			keyName = "skillingOptions",
			name = "Skilling",
			description = "",
			position = 1
	)
	default Title configurationOptions()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableFiremaking",
			name = "Firemaking",
			description = "If a Tinderbox is present in the inventory, clicking on a log will burn that log.",
			titleSection = "skillingOptions",
			position = 2
	)
	default boolean enableFiremaking() { return false; }

	@ConfigItem(
			keyName = "enableCooking",
			name = "Cooking",
			description = "If a fire is present, clicking on any raw food will take you to the nearest fire to cook it on.",
			titleSection = "skillingOptions",
			position = 3
	)
	default boolean enableCooking() { return false; }

	@ConfigItem(
			keyName = "enableFishing",
			name = "Fishing",
			description = "Enable faster fishing by clicking on the relevant inventory fishing item.",
			titleSection = "skillingOptions",
			position = 5
	)
	default boolean enableFishing() { return false; }

	@ConfigTitleSection(
			keyName = "miscellaneousTitle",
			name = "Miscellaneous",
			description = "",
			position = 10
	)
	default Title miscellaneousTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableBanking",
			name = "Banking",
			description = "Enable added a menu option to the Inventory tab bag to open the bank.",
			titleSection = "miscellaneousTitle",
			position = 11
	)
	default boolean enableBanking() { return false; }

	@ConfigTitleSection(
			keyName = "debugTitle",
			name = "Developer",
			description = "",
			position = 100
	)
	default Title debugTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableDebugMessages",
			name = "Debug Messages",
			description = "Enable debug messages (dev only).",
			titleSection = "debugTitle",
			position = 100
	)
	default boolean enableDebugging() { return false; }

}