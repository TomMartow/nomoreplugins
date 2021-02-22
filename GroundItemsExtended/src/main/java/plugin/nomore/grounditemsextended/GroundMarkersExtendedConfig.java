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
package plugin.nomore.grounditemsextended;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("grounditemsextended")
public interface GroundMarkersExtendedConfig extends Config
{

    @ConfigTitleSection(
            keyName = "groundItemHighlightingTitle",
            name = "Overlay Options",
            description = "",
            position = 1
    )
    default Title groundItemHighlightingTitle() { return new Title(); }

    @ConfigItem(
            keyName = "groundItemRenderStyle",
            name = "  Render style",
            description = "The type of marker.",
            position = 5,
            titleSection = "groundItemHighlightingTitle"
    )
    default GroundItemRenderStyle groundItemRenderStyle() { return GroundItemRenderStyle.BOX; }

    @ConfigItem(
            keyName = "groundItemIndicatorSize",
            name = "  Indicator size",
            description = "The size of the marker.",
            position = 6,
            hidden = true,
            unhide = "groundItemRenderStyle",
            unhideValue = "BOX",
            titleSection = "groundItemHighlightingTitle"
    )
    default int groundItemIndicatorSize() { return 4; }

    @ConfigItem(
            keyName = "configGroundItemTextField",
            name = "  Name / ID:Hex Color",
            description = "Example: \"Bones:00FF00,\".",
            position = 7,
            titleSection = "groundItemHighlightingTitle"
    )
    default String groundItemConfigTextString() { return "Bones, 590"; }

    @ConfigTitleSection(
            keyName = "groundItemColorTitle",
            name = "Color Options",
            description = "",
            position = 9
    )
    default Title groundItemColorTitle() { return new Title(); }

    @ConfigItem(
            keyName = "groundItemDefaultHighlightColor",
            name = "  Default Marker color",
            description = "The default color for the ground item indicator.",
            position = 11,
            titleSection = "groundItemColorTitle"
    )
    default Color groundItemDefaultHighlightColor() { return Color.GREEN; }

    @ConfigTitleSection(
            keyName = "groundItemMiscOptionsTitle",
            name = "Miscellaneous Options",
            description = "",
            position = 13
    )
    default Title groundItemMiscOptionsTitle() { return new Title(); }

    @ConfigItem(
            keyName = "groundItemLineOfSight",
            name = "  Only show Objects in line of sight",
            description = "Only show Objects your player (tile: diagonally or straight) can \"see\"",
            position = 15,
            titleSection = "groundItemMiscOptionsTitle"
    )
    default boolean groundItemLineOfSight() { return false; }

    @ConfigItem(
            keyName = "groundItemDisplayMouseHoveringIndicator",
            name = "  Mouse hovering indicator",
            description = "If enabled will display an indicator if hovering over the groundItem.",
            position = 16,
            titleSection = "groundItemMiscOptionsTitle"
    )
    default boolean groundItemDisplayMouseHoveringIndicator() { return false; }

    @ConfigItem(
            keyName = "groundItemMouseHoveringIndicatorLocation",
            name = "  X:Y:Width:Height indicator location",
            description = "If enabled will display an indicator if hovering over the groundItem.",
            position = 17,
            titleSection = "groundItemMiscOptionsTitle"
    )
    default String groundItemMouseHoveringIndicatorLocation() { return "10:10:10:10"; }
    
}