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
package plugin.nomore.nomorewintertodt;

import net.runelite.client.config.*;

import java.awt.Color;


@ConfigGroup("nomorewintertodt")
public interface NoMoreWintertodtConfig extends Config
{
	@ConfigTitle(
			keyName = "overlaySettingsTitle",
			name = "Overlay Settings",
			description = "",
			position = 1
	)
	String highlightStyleTitle = "overlaySettingsTitle";

	@ConfigItem(
			position = 2,
			keyName = "objectHighlightStyle",
			name = "Highlight style",
			description = "Choose the rendering style for the objects.",
			title = "overlaySettingsTitle"
	)
	default RenderStyle renderStyle() { return RenderStyle.CLICKBOX; }

	@ConfigItem(
			position = 3,
			keyName = "locationOverlay",
			name = "Overlay side",
			description = "Displays the overlays on the chosen side.",
			title = "overlaySettingsTitle"
	)
	default Location locationSide() { return Location.EAST; }

	@ConfigTitle(
			keyName = "objectsTitle",
			name = "Individual Settings",
			description = "",
			position = 4
	)
	String objectsTitle = "objectsTitle";

	@ConfigTitle(
			keyName = "brumaRootTitle",
			name = "Bruma Root",
			description = "",
			position = 5
	)
	String brumaRootTitle = "brumaRootTitle";

	@ConfigItem(
			keyName = "displayBruma",
			name = "Display overlay",
			description = "Display the overlay.",
			position = 6,
			title = "brumaRootTitle"
	)
	default boolean displayBruma()
	{
		return true;
	}

	@ConfigItem(
			keyName = "brumaColor",
			name = "Overlay color",
			description = "The color of the overlay.",
			position = 7,
			title = "brumaRootTitle"
	)
	default Color brumaColor()
	{
		return Color.MAGENTA;
	}

	@ConfigItem(
			keyName = "brumaBoxSize",
			name = "Box size",
			description = "The size of the box overlay.",
			position = 8,
			title = "brumaRootTitle",
			hidden = true,
			unhide = "objectHighlightStyle",
			unhideValue = "BOX"
	)
	default int brumaBoxSize()
	{
		return 4;
	}

	@ConfigTitle(
			keyName = "litBrazierTitle",
			name = "Lit Brazier",
			description = "",
			position = 9
	)
	String litBrazierTitle = "litBrazierTitle";
	
	@ConfigItem(
			keyName = "displayLitBrazier",
			name = "Display overlay",
			description = "Display the overlay.",
			position = 10,
			title = "litBrazierTitle"
	)
	default boolean displayLitBrazier()
	{
		return true;
	}

	@ConfigItem(
			keyName = "litBrazierColor",
			name = "Overlay color",
			description = "The overlay color.",
			position = 11,
			title = "litBrazierTitle"
	)
	default Color litBrazierColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "litBrazierBoxSize",
			name = "Box size",
			description = "The size of the box overlay.",
			position = 12,
			title = "litBrazierTitle",
			hidden = true,
			unhide = "objectHighlightStyle",
			unhideValue = "BOX"
	)
	default int litBrazierBoxSize()
	{
		return 4;
	}

	@ConfigTitle(
			keyName = "unlitBrazierTitle",
			name = "Unlit Brazier",
			description = "",
			position = 13
	)
	String unlitBrazierTitle = "unlitBrazierTitle";

	@ConfigItem(
			keyName = "displayUnlitBrazier",
			name = "Display overlay",
			description = "Display the overlay.",
			position = 14,
			title = "unlitBrazierTitle"
	)
	default boolean displayUnlitBrazier()
	{
		return true;
	}

	@ConfigItem(
			keyName = "unlitBrazierColor",
			name = "Overlay color",
			description = "The overlay color.",
			position = 15,
			title = "unlitBrazierTitle"
	)
	default Color unlitBrazierColor() { return Color.RED; }

	@ConfigItem(
			keyName = "unlitBrazierBoxSize",
			name = "Box size",
			description = "The size of the box overlay.",
			position = 16,
			title = "unlitBrazierTitle",
			hidden = true,
			unhide = "objectHighlightStyle",
			unhideValue = "BOX"
	)
	default int unlitBrazierBoxSize()
	{
		return 4;
	}

	@ConfigTitle(
			keyName = "brokenBrazierTitle",
			name = "Broken Brazier",
			description = "",
			position = 17
	)
	String brokenBrazierTitle = "brokenBrazierTitle";

	@ConfigItem(
			keyName = "displayBrokenBrazier",
			name = "Display overlay",
			description = "Display the overlay.",
			position = 18,
			title = "brokenBrazierTitle"
	)
	default boolean displayBrokenBrazier() { return true; }

	@ConfigItem(
			keyName = "brokenBrazierColor",
			name = "Overlay color",
			description = "The overlay color.",
			position = 19,
			title = "brokenBrazierTitle"
	)
	default Color brokenBrazierColor() { return Color.ORANGE; }

	@ConfigItem(
			keyName = "brokenBrazierBoxSize",
			name = "Box size",
			description = "The size of the box overlay.",
			position = 20,
			title = "brokenBrazierTitle",
			hidden = true,
			unhide = "objectHighlightStyle",
			unhideValue = "BOX"
	)
	default int brokenBrazierBoxSize()
	{
		return 4;
	}

	@ConfigTitle(
			keyName = "pyroTitle",
			name = "Pyromancer",
			description = "",
			position = 21
	)
	String pyroTitle = "pyroTitle";

	@ConfigItem(
			keyName = "displayPyro",
			name = "Display overlay",
			description = "Display the overlay.",
			position = 22,
			title = "pyroTitle"
	)
	default boolean displayPyroBrazier() { return true; }

	@ConfigItem(
			keyName = "pyroColor",
			name = "Overlay color",
			description = "The overlay color.",
			position = 23,
			title = "pyroTitle"
	)
	default Color pyroColor() { return Color.CYAN; }

	@ConfigItem(
			keyName = "pyroBoxSize",
			name = "Box size",
			description = "The size of the box overlay.",
			position = 24,
			title = "pyroTitle",
			hidden = true,
			unhide = "objectHighlightStyle",
			unhideValue = "BOX"
	)
	default int pyroBoxSize()
	{
		return 4;
	}

	@ConfigTitle(
			keyName = "miscTitle",
			name = "Misc",
			description = "",
			position = 25
	)
	String miscTitle = "miscTitle";

	@ConfigItem(
			keyName = "displayMinigameStatus",
			name = "Display minigame status",
			description = "Display's a coloured overlay dependent on the minigame status.",
			position = 26,
			title = "miscTitle"
	)
	default boolean displayMinigameStatus()
	{
		return true;
	}

	@ConfigItem(
			keyName = "wintertodtHUD",
			name = "Wintertodt HUD",
			description = "Hides the Wintertodt widgets.",
			position = 27,
			title = "miscTitle"
	)
	default WidgetStyle wintertotdHUD()
	{
		return WidgetStyle.MIXED;
	}

	@ConfigItem(
			keyName = "displayPoints",
			name = "Display points",
			description = "Display's the points gained in the current game.",
			position = 28,
			title = "miscTitle"
	)
	default boolean displayPoints()
	{
		return true;
	}

}