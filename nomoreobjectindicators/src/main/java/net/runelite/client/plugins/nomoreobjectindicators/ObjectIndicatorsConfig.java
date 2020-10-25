/*
 * Copyright (c) 2019, Owain van Brakel <https://github.com/Owain94>
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

package net.runelite.client.plugins.nomoreobjectindicators;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigTitleSection;
import net.runelite.client.config.Range;
import net.runelite.client.config.Title;
import net.runelite.client.config.Units;

@ConfigGroup("objectindicators")
public interface ObjectIndicatorsConfig extends Config
{
	@ConfigTitleSection(
		keyName = "overlayTitle",
		name = "Overlay Style",
		description = "",
		position = 0
	)
	default Title overlayTitle()
	{
		return new Title();
	}

	@ConfigItem(
		position = 1,
		keyName = "objectMarkerRenderStyle",
		name = "Highlight Style",
		description = "Highlight setting",
		titleSection = "overlayTitle"
	)
	default RenderStyle objectMarkerRenderStyle()
	{
		return RenderStyle.OUTLINE;
	}


	@ConfigItem(
		position = 3,
		keyName = "objectMarkerOutlineRenderStyle",
		name = "Outline Style",
		description = "Highlight outline setting",
		titleSection = "overlayTitle",
		hidden = true,
		unhide = "objectMarkerRenderStyle",
		unhideValue = "OUTLINE"
	)
	default OutlineRenderStyle objectMarkerOutlineRenderStyle()
	{
		return OutlineRenderStyle.NORMAL_OUTLINE;
	}

	@ConfigTitleSection(
		keyName = "colorTitle",
		name = "Colors",
		description = "",
		position = 3
	)
	default Title colorTitle()
	{
		return new Title();
	}

	@ConfigItem(
		position = 4,
		keyName = "markerColor",
		name = "Marker color",
		description = "Configures the outer color of object marker",
		titleSection = "colorTitle"
	)
	default Color objectMarkerColor()
	{
		return Color.YELLOW;
	}

	@Range(
		max = 100
	)
	@ConfigItem(
		position = 5,
		keyName = "objectMarkerAlpha",
		name = "Alpha",
		description = "Configures the opacity/alpha of object marker",
		titleSection = "colorTitle"
	)
	@Units(Units.PERCENT)
	default int objectMarkerAlpha()
	{
		return 100;
	}

	@ConfigItem(
		position = 6,
		keyName = "rememberObjectColors",
		name = "Remember color per object",
		description = "Color objects using the color from time of marking",
		titleSection = "colorTitle"
	)
	default boolean rememberObjectColors()
	{
		return false;
	}

	// AHK modifications by NoMoreAHK, download them from: https://github.com/TomMartow/openosrsplugins

	// When renderStyle = BOX, display the following settings:

	@ConfigItem(
			position = 2,
			keyName = "boxSize",
			name = "Box size",
			description = "The size of the box displayed in the centre of the marked ground tile.",
			hidden = true,
			unhide = "objectMarkerRenderStyle",
			unhideValue = "BOX",
			titleSection = "overlayTitle"
	)
	default int boxSize() { return 4; }

	// end of renderStyle = BOX settings.

	// Hovering config

	@ConfigItem(
			position = 8,
			keyName = "notifyHover",
			name = "Mouse hovering indication",
			description = "Displays a indicator if the mouse is hovering over a marked object."
	)
	default boolean showMouseHover() { return false; }

	// When showMouseHoveringOverTile() is true, display the following settings:

	@ConfigItem(
			position = 9,
			keyName = "mouseHoverX",
			name = "Hover x pos:",
			description = "The X coordinate where the indicator will display.",
			hidden = true,
			unhide = "notifyHover",
			unhideValue = "true"
	)
	default int mouseHoverX() { return 0; }

	@ConfigItem(
			position = 10,
			keyName = "mouseHoverY",
			name = "Hover y pos:",
			description = "The Y coordinate where the indicator will display.",
			hidden = true,
			unhide = "notifyHover",
			unhideValue = "true"
	)
	default int mouseHoverY() { return 0; }

	@ConfigItem(
			position = 11,
			keyName = "mouseHoverIndicatorWidth",
			name = "Hover width",
			description = "Indicator width.",
			hidden = true,
			unhide = "notifyHover",
			unhideValue = "true"
	)
	default int mouseHoverIndicatorWidth() { return 10; }

	@ConfigItem(
			position = 12,
			keyName = "mouseHoverIndicatorHeight",
			name = "Hover height",
			description = "Indicator height.",
			hidden = true,
			unhide = "notifyHover",
			unhideValue = "true"
	)
	default int mouseHoverIndicatorHeight() { return 10; }

	@ConfigItem(
			position = 13,
			keyName = "hoverIndicatorColor",
			name = "Hover color",
			description = "The color of the indicator when hovering over a marked tile.",
			hidden = true,
			unhide = "notifyHover",
			unhideValue = "true"
	)
	default Color mouseHoverIndicatorColour() { return Color.GREEN; }

	// End of when showMouseHoveringOverTile() is true settings.
}