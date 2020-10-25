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
package net.runelite.client.plugins.nomorewintertodt;

import net.runelite.client.config.*;

import java.awt.Color;


@ConfigGroup("nomorewintertodt")
public interface NoMoreWintertodtConfig extends Config
{
	@ConfigTitleSection(
			keyName = "overlaySettingsTitle",
			name = "Overlay Settings",
			description = "",
			position = 1
	)
	default Title highlightStyleTitle()
	{
		return new Title();
	}

	@ConfigItem(
			position = 2,
			keyName = "objectHighlightStyle",
			name = "Highlight style",
			description = "Choose the rendering style for the objects.",
			titleSection = "overlaySettingsTitle"
	)
	default RenderStyle renderStyle() { return RenderStyle.CLICKBOX; }

	@ConfigItem(
			position = 3,
			keyName = "locationOverlay",
			name = "Overlay side",
			description = "Displays the overlays on the chosen side.",
			titleSection = "overlaySettingsTitle"
	)
	default Location locationSide() { return Location.EAST; }

	@ConfigTitleSection(
			keyName = "objectsTitle",
			name = "Individual Settings",
			description = "",
			position = 4
	)
	default Title objectsTitle()
	{
		return new Title();
	}

	@ConfigTitleSection(
			keyName = "brumaRootTitle",
			name = "Bruma Root",
			description = "",
			position = 5
	)
	default Title brumaRootTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "displayBruma",
			name = "Display overlay",
			description = "Display the overlay.",
			position = 6,
			titleSection = "brumaRootTitle"
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
			titleSection = "brumaRootTitle"
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
			titleSection = "brumaRootTitle",
			hidden = true,
			unhide = "objectHighlightStyle",
			unhideValue = "BOX"
	)
	default int brumaBoxSize()
	{
		return 4;
	}

	@ConfigTitleSection(
			keyName = "litBrazierTitle",
			name = "Lit Brazier",
			description = "",
			position = 9
	)
	default Title litBrazierTitle()
	{
		return new Title();
	}
	
	@ConfigItem(
			keyName = "displayLitBrazier",
			name = "Display overlay",
			description = "Display the overlay.",
			position = 10,
			titleSection = "litBrazierTitle"
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
			titleSection = "litBrazierTitle"
	)
	default Color litBrazierColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "litBrazierBoxSize",
			name = "Box size",
			description = "The size of the box overlay.",
			position = 12,
			titleSection = "litBrazierTitle",
			hidden = true,
			unhide = "objectHighlightStyle",
			unhideValue = "BOX"
	)
	default int litBrazierBoxSize()
	{
		return 4;
	}

	@ConfigTitleSection(
			keyName = "unlitBrazierTitle",
			name = "Unlit Brazier",
			description = "",
			position = 13
	)
	default Title unlitBrazierTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "displayUnlitBrazier",
			name = "Display overlay",
			description = "Display the overlay.",
			position = 14,
			titleSection = "unlitBrazierTitle"
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
			titleSection = "unlitBrazierTitle"
	)
	default Color unlitBrazierColor() { return Color.RED; }

	@ConfigItem(
			keyName = "unlitBrazierBoxSize",
			name = "Box size",
			description = "The size of the box overlay.",
			position = 16,
			titleSection = "unlitBrazierTitle",
			hidden = true,
			unhide = "objectHighlightStyle",
			unhideValue = "BOX"
	)
	default int unlitBrazierBoxSize()
	{
		return 4;
	}

	@ConfigTitleSection(
			keyName = "brokenBrazierTitle",
			name = "Broken Brazier",
			description = "",
			position = 17
	)
	default Title brokenBrazierTitle() { return new Title(); }

	@ConfigItem(
			keyName = "displayBrokenBrazier",
			name = "Display overlay",
			description = "Display the overlay.",
			position = 18,
			titleSection = "brokenBrazierTitle"
	)
	default boolean displayBrokenBrazier() { return true; }

	@ConfigItem(
			keyName = "brokenBrazierColor",
			name = "Overlay color",
			description = "The overlay color.",
			position = 19,
			titleSection = "brokenBrazierTitle"
	)
	default Color brokenBrazierColor() { return Color.ORANGE; }

	@ConfigItem(
			keyName = "brokenBrazierBoxSize",
			name = "Box size",
			description = "The size of the box overlay.",
			position = 20,
			titleSection = "brokenBrazierTitle",
			hidden = true,
			unhide = "objectHighlightStyle",
			unhideValue = "BOX"
	)
	default int brokenBrazierBoxSize()
	{
		return 4;
	}

	@ConfigTitleSection(
			keyName = "pyroTitle",
			name = "Pyromancer",
			description = "",
			position = 21
	)
	default Title pyroTitle() { return new Title(); }

	@ConfigItem(
			keyName = "displayPyro",
			name = "Display overlay",
			description = "Display the overlay.",
			position = 22,
			titleSection = "pyroTitle"
	)
	default boolean displayPyroBrazier() { return true; }

	@ConfigItem(
			keyName = "pyroColor",
			name = "Overlay color",
			description = "The overlay color.",
			position = 23,
			titleSection = "pyroTitle"
	)
	default Color pyroColor() { return Color.CYAN; }

	@ConfigItem(
			keyName = "pyroBoxSize",
			name = "Box size",
			description = "The size of the box overlay.",
			position = 24,
			titleSection = "pyroTitle",
			hidden = true,
			unhide = "objectHighlightStyle",
			unhideValue = "BOX"
	)
	default int pyroBoxSize()
	{
		return 4;
	}

	@ConfigTitleSection(
			keyName = "miscTitle",
			name = "Misc",
			description = "",
			position = 25
	)
	default Title miscTitle() { return new Title(); }

	@ConfigItem(
			keyName = "displayMinigameStatus",
			name = "Display minigame status",
			description = "Display's a coloured overlay dependent on the minigame status.",
			position = 26,
			titleSection = "miscTitle"
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
			titleSection = "miscTitle"
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
			titleSection = "miscTitle"
	)
	default boolean displayPoints()
	{
		return true;
	}

}