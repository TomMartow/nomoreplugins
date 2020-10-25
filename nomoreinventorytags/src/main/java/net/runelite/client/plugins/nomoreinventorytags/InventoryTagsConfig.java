/*
 * Copyright (c) 2018 kulers
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
package net.runelite.client.plugins.nomoreinventorytags;

import java.awt.Color;
import lombok.RequiredArgsConstructor;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("inventorytagsAHK")
public interface InventoryTagsConfig extends Config
{
	String GROUP = "inventorytags";

	@RequiredArgsConstructor
	enum amount
	{
		ONE("1"),
		TWO("2"),
		THREE("3"),
		FOUR("4"),
		FIVE("5"),
		SIX("6"),
		SEVEN("7"),
		EIGHT("8"),
		NINE("9"),
		TEN("10"),
		ELEVEN("11"),
		TWELVE("12");

		private final String name;

		@Override
		public String toString()
		{
			return name;
		}

		public int toInt()
		{
			return Integer.parseInt(name);
		}
	}

	@ConfigItem(
		position = 0,
		keyName = "amount",
		name = "Amount of groups",
		description = "The amount of inventory groups"
	)
	default amount getAmount()
	{
		return amount.FOUR;
	}

	@ConfigItem(
		position = 1,
		keyName = "groupColor1",
		name = "Group 1 Color",
		description = "Color of the Tag",
		hidden = true,
		unhide = "amount",
		unhideValue = "1 || 2 || 3 || 4 || 5 || 6 || 7 || 8 || 9 || 10 || 11 || 12"
	)
	default Color getGroup1Color()
	{
		return new Color(255, 0, 0);
	}

	@ConfigItem(
		position = 2,
		keyName = "groupColor2",
		name = "Group 2 Color",
		description = "Color of the Tag",
		hidden = true,
		unhide = "amount",
		unhideValue = "2 || 3 || 4 || 5 || 6 || 7 || 8 || 9 || 10 || 11 || 12"
	)
	default Color getGroup2Color()
	{
		return new Color(0, 255, 0);
	}

	@ConfigItem(
		position = 3,
		keyName = "groupColor3",
		name = "Group 3 Color",
		description = "Color of the Tag",
		hidden = true,
		unhide = "amount",
		unhideValue = "3 || 4 || 5 || 6 || 7 || 8 || 9 || 10 || 11 || 12"
	)
	default Color getGroup3Color()
	{
		return new Color(0, 0, 255);
	}

	@ConfigItem(
		position = 4,
		keyName = "groupColor4",
		name = "Group 4 Color",
		description = "Color of the Tag",
		hidden = true,
		unhide = "amount",
		unhideValue = "4 || 5 || 6 || 7 || 8 || 9 || 10 || 11 || 12"
	)
	default Color getGroup4Color()
	{
		return new Color(255, 0, 255);
	}

	@ConfigItem(
		position = 5,
		keyName = "groupColor5",
		name = "Group 5 Color",
		description = "Color of the Tag",
		hidden = true,
		unhide = "amount",
		unhideValue = "5 || 6 || 7 || 8 || 9 || 10 || 11 || 12"
	)
	default Color getGroup5Color()
	{
		return new Color(0, 255, 246);
	}

	@ConfigItem(
		position = 6,
		keyName = "groupColor6",
		name = "Group 6 Color",
		description = "Color of the Tag",
		hidden = true,
		unhide = "amount",
		unhideValue = "6 || 7 || 8 || 9 || 10 || 11 || 12"
	)
	default Color getGroup6Color()
	{
		return new Color(248, 255, 244);
	}

	@ConfigItem(
		position = 7,
		keyName = "groupColor7",
		name = "Group 7 Color",
		description = "Color of the Tag",
		hidden = true,
		unhide = "amount",
		unhideValue = "7 || 8 || 9 || 10 || 11 || 12"
	)
	default Color getGroup7Color()
	{
		return new Color(0, 0, 0);
	}

	@ConfigItem(
		position = 8,
		keyName = "groupColor8",
		name = "Group 8 Color",
		description = "Color of the Tag",
		hidden = true,
		unhide = "amount",
		unhideValue = "8 || 9 || 10 || 11 || 12"
	)
	default Color getGroup8Color()
	{
		return new Color(104, 105, 255);
	}

	@ConfigItem(
		position = 9,
		keyName = "groupColor9",
		name = "Group 9 Color",
		description = "Color of the Tag",
		hidden = true,
		unhide = "amount",
		unhideValue = "9 || 10 || 11 || 12"
	)
	default Color getGroup9Color()
	{
		return new Color(255, 81, 0);
	}

	@ConfigItem(
		position = 10,
		keyName = "groupColor10",
		name = "Group 10 Color",
		description = "Color of the Tag",
		hidden = true,
		unhide = "amount",
		unhideValue = "10 || 11 || 12"
	)
	default Color getGroup10Color()
	{
		return new Color(255, 107, 229);
	}

	@ConfigItem(
		position = 11,
		keyName = "groupColor11",
		name = "Group 11 Color",
		description = "Color of the Tag",
		hidden = true,
		unhide = "amount",
		unhideValue = "11 || 12"
	)
	default Color getGroup11Color()
	{
		return new Color(121, 255, 157);
	}

	@ConfigItem(
		position = 12,
		keyName = "groupColor12",
		name = "Group 12 Color",
		description = "Color of the Tag",
		hidden = true,
		unhide = "amount",
		unhideValue = "12"
	)
	default Color getGroup12Color()
	{
		return new Color(65, 61, 64);
	}

	@ConfigItem(
			position = 13,
			keyName = "renderStyle",
			name = "Overlay style",
			description = "Choose the style of overlay for the inventory tags."
	)
	default RenderStyle renderStyle()
	{
		return RenderStyle.OUTLINE;
	}

	@ConfigItem(
			position = 14,
			keyName = "hoverDelay",
			name = "Indicator delay",
			description = "How long in milliseconds it will take to display the indicator.",
			titleSection = "overlayTitle"
	)
	default int hoverDelay() { return 1000; }

	// AHK modifications by NoMoreAHK, download them from: https://github.com/TomMartow/openosrsplugins

	// When renderStyle = BOX, display the following settings:

	@ConfigItem(
			position = 15,
			keyName = "boxSize",
			name = "Box size",
			description = "The size of the box displayed in the centre of the marked ground tile.",
			hidden = true,
			unhide = "renderStyle",
			unhideValue = "BOX",
			titleSection = "overlayTitle"
	)
	default int boxSize() { return 4; }

	// end of renderStyle = BOX settings.

	// Hovering config

	@ConfigItem(
			position = 16,
			keyName = "notifyMouseHoverOnTile",
			name = "Mouse hovering indication",
			description = "Displays a indicator if the mouse is hovering over a marked tile."
	)
	default boolean showMouseHoveringOverInventoryIcon()
	{
		return false;
	}

	// When showMouseHoveringOverTile() is true, display the following settings:

	@ConfigItem(
			position = 17,
			keyName = "mouseHoverX",
			name = "Hover x pos:",
			description = "The X coordinate where the indicator will display.",
			hidden = true,
			unhide = "notifyMouseHoverOnTile",
			unhideValue = "true"
	)
	default int mouseHoverX() { return 0; }

	@ConfigItem(
			position = 18,
			keyName = "mouseHoverY",
			name = "Hover y pos:",
			description = "The Y coordinate where the indicator will display.",
			hidden = true,
			unhide = "notifyMouseHoverOnTile",
			unhideValue = "true"
	)
	default int mouseHoverY() { return 0; }

	@ConfigItem(
			position = 19,
			keyName = "mouseHoverIndicatorWidth",
			name = "Hover width",
			description = "Indicator width.",
			hidden = true,
			unhide = "notifyMouseHoverOnTile",
			unhideValue = "true"
	)
	default int mouseHoverIndicatorWidth() { return 10; }

	@ConfigItem(
			position = 20,
			keyName = "mouseHoverIndicatorHeight",
			name = "Hover height",
			description = "Indicator height.",
			hidden = true,
			unhide = "notifyMouseHoverOnTile",
			unhideValue = "true"
	)
	default int mouseHoverIndicatorHeight() { return 10; }

	@ConfigItem(
			position = 21,
			keyName = "hoverIndicatorColor",
			name = "Hover color",
			description = "The color of the indicator when hovering over a marked tile.",
			hidden = true,
			unhide = "notifyMouseHoverOnTile",
			unhideValue = "true"
	)
	default Color mouseHoverIndicatorColour() { return Color.GREEN; }

	// End of when showMouseHoveringOverTile() is true settings.
}
