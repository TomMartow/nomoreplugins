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
package net.runelite.client.plugins.aiomarkers;

import net.runelite.client.config.*;

import java.awt.Color;

@ConfigGroup("aiomarkers")
public interface AIOConfig extends Config
{
	@ConfigTitleSection(
			keyName = "configurationOptions",
			name = "Configuration Options",
			description = "",
			position = 1
	)
	default Title configurationOptions()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "markerConfiguration",
			name = "for:",
			description = "",
			position = 2,
			titleSection = "configurationOptions"
	)
	default ConfigurationOptions markerConfiguration() { return ConfigurationOptions.NPC_HIGHLIGHTING; }

	//███╗   ██╗██████╗  ██████╗
	//████╗  ██║██╔══██╗██╔════╝
	//██╔██╗ ██║██████╔╝██║
	//██║╚██╗██║██╔═══╝ ██║
	//██║ ╚████║██║     ╚██████╗
	//╚═╝  ╚═══╝╚═╝      ╚═════╝

	@ConfigTitleSection(
			keyName = "npcHighlightTitle",
			name = "NPC Highlight Options",
			description = "",
			position = 10
	)
	default Title npcHighlightTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableNPCHighlighting",
			name = "Enable NPC Highlight",
			description = "",
			position = 11,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING",
			titleSection = "npcHighlightTitle"
	)
	default boolean enableNPCHighlighting() { return false; }

	@ConfigItem(
			keyName = "npcIndicatorSize",
			name = "Indicator size",
			description = "The size of the marker.",
			position = 12,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING",
			titleSection = "npcHighlightTitle"
	)
	default int npcIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "configNPCTextField",
			name = "NPC Name / ID",
			description = "Use , to split up NPC names and IDs.",
			position = 13,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING",
			titleSection = "npcHighlightTitle"
	)
	default String npcConfigTextString() { return "Banker, 3010"; }

	@ConfigTitleSection(
			keyName = "npcColorTitle",
			name = "Indicator Interaction Color Options",
			description = "",
			position = 14
	)
	default Title npcColorTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "npcDefaultHighlightColor",
			name = "Default Marker color",
			description = "The default color for the NPC indicator.",
			position = 15,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING",
			titleSection = "npcColorTitle"
	)
	default Color npcDefaultHighlightColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "npcEnableNPCDefaultColorOverrideWithNPCInteractingWithPlayer",
			name = "Enable NPC -> Player Indicator",
			description = "Enable the override of the default NPC highlighting color should the NPC be attacking your player.",
			position = 16,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING",
			titleSection = "npcColorTitle"
	)
	default boolean npcEnableNPCDefaultColorOverrideWithNPCInteractingWithPlayer() { return false; }

	@ConfigItem(
			keyName = "npcInteractingWithPlayerColor",
			name = "NPC -> Player color",
			description = "The color of the indicator.",
			position = 17,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING",
			titleSection = "npcColorTitle"
	)
	default Color npcInteractingWithPlayerColor() { return Color.YELLOW; }

	@ConfigItem(
			keyName = "npcEnableNPCDefaultColorOverrideWithPlayersInteractingWithPlayer",
			name = "Enable Players -> NPC Indicator",
			description = "Enable the override of the default NPC highlighting color should another player be attacking an NPC that is currently marked.",
			position = 18,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING",
			titleSection = "npcColorTitle"
	)
	default boolean npcEnableNPCDefaultColorOverrideWithPlayersInteractingWithPlayer() { return false; }

	@ConfigItem(
			keyName = "npcPlayersInteractingWithNPCColor",
			name = "Players -> NPC color",
			description = "The color of the indicator.",
			position = 19,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING",
			titleSection = "npcColorTitle"
	)
	default Color npcPlayersInteractingWithNPCColor() { return Color.RED; }

	@ConfigItem(
			keyName = "npcEnableHighlightingMenuItemForMarkedNPCS",
			name = "Enable Menu Highlighting",
			description = "Enable the highlighting of the NPC's name context menu.",
			position = 20,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING",
			titleSection = "npcColorTitle"
	)
	default boolean npcEnableHighlightingMenuItemForMarkedNPCS() { return false; }

	@ConfigItem(
			keyName = "npcMenuItemColorForMarkedNPCS",
			name = "Menu Item Color",
			description = "The color of the menu item.",
			position = 21,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING",
			titleSection = "npcColorTitle"
	)
	default Color npcMenuItemColorForMarkedNPCS() { return Color.MAGENTA; }

	@ConfigTitleSection(
			keyName = "npcMiscOptionsTitle",
			name = "Miscellaneous Options",
			description = "",
			position = 22,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title npcMiscOptionsTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "npcLineOfSight",
			name = "Only show NPC's you can \"see\"",
			description = "",
			position = 23,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING",
			titleSection = "npcMiscOptionsTitle"
	)
	default boolean npcLineOfSight() { return false; }

}