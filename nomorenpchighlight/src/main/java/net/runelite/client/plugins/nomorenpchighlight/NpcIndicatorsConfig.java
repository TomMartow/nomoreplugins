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
package net.runelite.client.plugins.nomorenpchighlight;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Units;

@ConfigGroup("npcindicators")
public interface NpcIndicatorsConfig extends Config
{
	@ConfigItem(
		position = 0,
		keyName = "highlightStyle",
		name = "Highlight Style",
		description = "Highlight setting"
	)
	default RenderStyle renderStyle()
	{
		return RenderStyle.THIN_OUTLINE;
	}

	@ConfigItem(
		position = 2,
		keyName = "npcToHighlight",
		name = "NPCs to Highlight",
		description = "List of NPC names to highlight"
	)
	default String getNpcToHighlight()
	{
		return "";
	}

	@ConfigItem(
		position = 3,
		keyName = "npcColor",
		name = "Highlight Color",
		description = "Color of the NPC highlight"
	)
	default Color getHighlightColor()
	{
		return Color.CYAN;
	}

	@ConfigItem(
		position = 4,
		keyName = "interactingColor",
		name = "Interacting Color",
		description = "Color of the NPC highlight when targeting local player"
	)
	default Color getInteractingColor()
	{
		return Color.RED;
	}

	@ConfigItem(
		position = 5,
		keyName = "drawNames",
		name = "Draw names above NPC",
		description = "Configures whether or not NPC names should be drawn above the NPC",
		hidden = true
	)
	default boolean drawNames()
	{
		return false;
	}

	@ConfigItem(
		position = 6,
		keyName = "drawInteracting",
		name = "Draw target name above NPC",
		description = "Configures whether the name of the NPC's target is drawn above it's head",
		hidden = true
	)
	default boolean drawInteracting()
	{
		return false;
	}

	@ConfigItem(
		position = 7,
		keyName = "drawMinimapNames",
		name = "Draw names on minimap",
		description = "Configures whether or not NPC names should be drawn on the minimap",
		hidden = true
	)
	default boolean drawMinimapNames()
	{
		return false;
	}

	@ConfigItem(
		position = 8,
		keyName = "highlightMenuNames",
		name = "Highlight menu names",
		description = "Highlight NPC names in right click menu",
		hidden = true
	)
	default boolean highlightMenuNames()
	{
		return false;
	}

	@ConfigItem(
		position = 9,
		keyName = "showRespawnTimer",
		name = "Show respawn timer",
		description = "Show respawn timer of tagged NPCs",
			hidden = true
	)
	default boolean showRespawnTimer()
	{
		return false;
	}

	@ConfigItem(
		position = 10,
		keyName = "notifyOnRespawn",
		name = "Notify on Respawn",
		description = "Enable notification on respawn",
			hidden = true
	)
	default boolean getNotifyOnRespawn()
	{
		return false;
	}

	@ConfigItem(
		position = 11,
		keyName = "notifyOnRespawnDelay",
		name = "Notification Delay",
		description = "Notify when NPC is x ms from respawning",
			hidden = true
	)
	@Units(Units.MILLISECONDS)
	default int getNotifyOnRespawnDelay()
	{
		return -1;
	}

	// AHK modifications by NoMoreAHK, download them from: https://github.com/TomMartow/openosrsplugins

	// When renderStyle = BOX, display the following settings:

	@ConfigItem(
			position = 1,
			keyName = "boxSize",
			name = "Box size",
			description = "The size of the box displayed in the centre of the marked ground tile.",
			hidden = true,
			unhide = "highlightStyle",
			unhideValue = "BOX"
	)
	default int boxSize() { return 4; }

	// end of renderStyle = BOX settings.

	// Hovering config

	@ConfigItem(
			position = 12,
			keyName = "notifyHover",
			name = "Mouse hovering indication",
			description = "Displays a indicator if the mouse is hovering over a marked NPC."
	)
	default boolean showMouseHover()
	{
		return false;
	}

	// When showMouseHoveringOverTile() is true, display the following settings:

	@ConfigItem(
			position = 13,
			keyName = "mouseHoverX",
			name = "Hover x pos:",
			description = "The X coordinate where the indicator will display.",
			hidden = true,
			unhide = "notifyHover",
			unhideValue = "true"
	)
	default int mouseHoverX() { return 0; }

	@ConfigItem(
			position = 14,
			keyName = "mouseHoverY",
			name = "Hover y pos:",
			description = "The Y coordinate where the indicator will display.",
			hidden = true,
			unhide = "notifyHover",
			unhideValue = "true"
	)
	default int mouseHoverY() { return 0; }

	@ConfigItem(
			position = 15,
			keyName = "mouseHoverIndicatorWidth",
			name = "Hover width",
			description = "Indicator width.",
			hidden = true,
			unhide = "notifyHover",
			unhideValue = "true"
	)
	default int mouseHoverIndicatorWidth() { return 10; }

	@ConfigItem(
			position = 16,
			keyName = "mouseHoverIndicatorHeight",
			name = "Hover height",
			description = "Indicator height.",
			hidden = true,
			unhide = "notifyHover",
			unhideValue = "true"
	)
	default int mouseHoverIndicatorHeight() { return 10; }

	@ConfigItem(
			position = 17,
			keyName = "hoverIndicatorColor",
			name = "Hover color",
			description = "The color of the indicator when hovering over a marked tile.",
			hidden = true,
			unhide = "notifyHover",
			unhideValue = "true"
	)
	default Color mouseHoverIndicatorColour() { return Color.GREEN; }

	// End of when showMouseHoveringOverTile() is true settings.

	@ConfigItem(
			position = 18,
			keyName = "hideNPCInCombat",
			name = "Hide NPC's in combat",
			description = "Hides the overlay on an NPC if the NPC is currently in combat."
	)
	default boolean hideNPCInCombat() { return false; }

	@ConfigItem(
			position = 19,
			keyName = "showInteraction",
			name = "Interaction indicator",
			description = "Displays an indicator if the player is currently interacting with a tagged NPC."
	)
	default boolean isInteractingIndicator() { return false; }

	// When isInteractingIndicator() is true, display the following settings:

	@ConfigItem(
			position = 20,
			keyName = "interactionIndicatorX",
			name = "Interaction x pos:",
			description = "The X coordinate where the indicator will display.",
			hidden = true,
			unhide = "showInteraction",
			unhideValue = "true"
	)
	default int interactingIndicatorX() { return 10; }

	@ConfigItem(
			position = 21,
			keyName = "interactionIndicatorY",
			name = "Interaction y pos:",
			description = "The Y coordinate where the indicator will display.",
			hidden = true,
			unhide = "showInteraction",
			unhideValue = "true"
	)
	default int interactingIndicatorY() { return 0; }

	@ConfigItem(
			position = 22,
			keyName = "interactionIndicatorWidth",
			name = "Interaction width",
			description = "Indicator width.",
			hidden = true,
			unhide = "showInteraction",
			unhideValue = "true"
	)
	default int interactingIndicatorWidth() { return 10; }

	@ConfigItem(
			position = 23,
			keyName = "interactionIndicatorHeight",
			name = "Interaction height",
			description = "Indicator height.",
			hidden = true,
			unhide = "showInteraction",
			unhideValue = "true"
	)
	default int interactingIndicatorHeight() { return 10; }

	@ConfigItem(
			position = 24,
			keyName = "interactionIndicatorColor",
			name = "Interaction color",
			description = "The color of the indicator when interacting with an NPC.",
			hidden = true,
			unhide = "showInteraction",
			unhideValue = "true"
	)
	default Color interactingIndicatorColour() { return Color.GREEN; }

	@ConfigItem(
			position = 24,
			keyName = "hideDeadNpcs",
			name = "Ignore dead npcs",
			description = "Ignores NPC's that are dead."
	)
	default boolean ignoreDeadNpcs() { return true; }

	// End of when isInteractingIndicator() is true settings.
}