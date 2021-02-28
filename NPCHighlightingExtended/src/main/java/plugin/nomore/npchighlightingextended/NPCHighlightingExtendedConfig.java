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
package plugin.nomore.npchighlightingextended;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("npchighlightingextended")
public interface NPCHighlightingExtendedConfig extends Config
{

    @ConfigTitle(
            keyName = "npcHighlightTitle",
            name = "Overlay Options",
            description = "",
            position = 11
    )
    String npcHighlightTitle = "npcHighlightTitle";

    @ConfigItem(
            keyName = "npcRenderStyle",
            name = "  Render style",
            description = "The type of marker.",
            position = 14,
            title = "npcHighlightTitle"
    )
    default NPCRenderStyle npcRenderStyle() { return NPCRenderStyle.BOX; }

    @ConfigItem(
            keyName = "npcIndicatorSize",
            name = "  Indicator size",
            description = "The size of the marker.",
            position = 15,
            hidden = true,
            unhide = "npcRenderStyle",
            unhideValue = "BOX",
            title = "npcHighlightTitle"
    )
    default int npcIndicatorSize() { return 4; }

    @ConfigItem(
            keyName = "configNPCTextField",
            name = "  Name / ID:Color",
            description = "Example: \"Goblin:00FF00,\".",
            position = 16,
            title = "npcHighlightTitle"
    )
    default String npcConfigTextString() { return "Banker,\nMan:00ffff"; }

    @ConfigTitle(
            keyName = "npcColorTitle",
            name = "Indicator Interaction Color Options",
            description = "",
            position = 18
    )
    String npcColorTitle = "npcColorTitle";

    @ConfigItem(
            keyName = "npcDefaultHighlightColor",
            name = "  Default Marker color",
            description = "The default color for the NPC indicator.",
            position = 20,
            title = "npcColorTitle"
    )
    default Color npcDefaultHighlightColor() { return Color.GREEN; }

    @ConfigItem(
            keyName = "npcEnableNPCDefaultColorOverrideWithNPCInteractingWithPlayer",
            name = "  Enable NPC -> Player Indicator",
            description = "Enable the override of the default NPC highlighting color should the NPC be attacking your player.",
            position = 21,
            title = "npcColorTitle"
    )
    default boolean npcEnableNPCDefaultColorOverrideWithNPCInteractingWithPlayer() { return false; }

    @ConfigItem(
            keyName = "npcInteractingWithPlayerColor",
            name = "  NPC -> Player color",
            description = "The color of the indicator.",
            position = 22,
            title = "npcColorTitle"
    )
    default Color npcInteractingWithPlayerColor() { return Color.YELLOW; }

    @ConfigItem(
            keyName = "npcEnableNPCDefaultColorOverrideWithPlayersInteractingWithPlayer",
            name = "  Enable Players -> NPC Indicator",
            description = "Enable the override of the default NPC highlighting color should another player be attacking an NPC that is currently marked.",
            position = 23,
            title = "npcColorTitle"
    )
    default boolean npcEnableNPCDefaultColorOverrideWithPlayersInteractingWithPlayer() { return false; }

    @ConfigItem(
            keyName = "npcPlayersInteractingWithNPCColor",
            name = "  Players -> NPC color",
            description = "The color of the indicator.",
            position = 24,
            title = "npcColorTitle"
    )
    default Color npcPlayersInteractingWithNPCColor() { return Color.RED; }

    @ConfigItem(
            keyName = "npcEnableHighlightingMenuItemForMarkedNPCS",
            name = "  Enable Menu Highlighting",
            description = "Enable the highlighting of the NPC's name context menu.",
            position = 25,
            title = "npcColorTitle"
    )
    default boolean npcEnableHighlightingMenuItemForMarkedNPCS() { return false; }

    @ConfigItem(
            keyName = "npcMenuItemColorForMarkedNPCS",
            name = "  Menu Item Color",
            description = "The color of the menu item.",
            position = 26,
            title = "npcColorTitle"
    )
    default Color npcMenuItemColorForMarkedNPCS() { return Color.MAGENTA; }

    @ConfigTitle(
            keyName = "npcMiscOptionsTitle",
            name = "Miscellaneous Options",
            description = "",
            position = 28
    )
    String npcMiscOptionsTitle = "npcMiscOptionsTitle";

    @ConfigItem(
            keyName = "npcLineOfSight",
            name = "  Only show NPC's in line of sight",
            description = "Only show NPC's your player (tile: diagonally or straight) can \"see\"",
            position = 30,
            title = "npcMiscOptionsTitle"
    )
    default boolean npcLineOfSight() { return false; }

    @ConfigItem(
            keyName = "npcDisplayMouseHoveringIndicator",
            name = "  Mouse hovering indicator",
            description = "If enabled will display an indicator if hovering over the npc.",
            position = 31,
            title = "npcMiscOptionsTitle"
    )
    default boolean npcDisplayMouseHoveringIndicator() { return false; }

    @ConfigItem(
            keyName = "npcMouseHoveringIndicatorLocation",
            name = "  X:Y:Width:Height indicator location",
            description = "If enabled will display an indicator if hovering over the npc.",
            position = 32,
            title = "npcMiscOptionsTitle"
    )
    default String npcMouseHoveringIndicatorLocation() { return "10:10:10:10"; }
    
}