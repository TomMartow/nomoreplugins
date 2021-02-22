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
package plugin.nomore.inventorytagsextended;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("inventorytagsextended")
public interface InventoryTagsExtendedConfig extends Config
{

    @ConfigTitleSection(
            keyName = "inventoryItemHighlightingTitle",
            name = "Overlay Options",
            description = "",
            position = 2
    )
    default Title inventoryItemHighlightingTitle() { return new Title(); }

    @ConfigItem(
            keyName = "inventoryItemRenderStyle",
            name = "  Render style",
            description = "The type of marker.",
            position = 5,
            titleSection = "inventoryItemHighlightingTitle"
    )
    default ItemRenderStyle inventoryItemRenderStyle() { return ItemRenderStyle.BOX; }

    @ConfigItem(
            keyName = "inventoryItemIndicatorSize",
            name = "  Indicator size",
            description = "The size of the marker.",
            position = 6,
            hidden = true,
            unhide = "inventoryItemRenderStyle",
            unhideValue = "BOX",
            titleSection = "inventoryItemHighlightingTitle"
    )
    default int inventoryItemIndicatorSize() { return 4; }

    @ConfigItem(
            keyName = "configInventoryItemTextField",
            name = "  Item Name / ID:Hex Color:Amount",
            description = "Example: \"Bones:00FF00,\".",
            position = 7,
            titleSection = "inventoryItemHighlightingTitle"
    )
    default String inventoryItemConfigTextString() { return "Bones, Coins:000000:100, Ash:ffffff,"; }

    @ConfigTitleSection(
            keyName = "inventoryItemColorTitle",
            name = "Color Options",
            description = "",
            position = 9
    )
    default Title inventoryItemColorTitle() { return new Title(); }

    @ConfigItem(
            keyName = "inventoryItemDefaultHighlightColor",
            name = "  Default Marker color",
            description = "The default color for the Object indicator.",
            position = 11,
            titleSection = "inventoryItemColorTitle"
    )
    default Color inventoryItemDefaultHighlightColor() { return Color.GREEN; }

    @ConfigItem(
            keyName = "inventoryItemMiscOptionsTitle",
            name = "Miscellaneous Options",
            description = "",
            position = 13
    )
    default Title inventoryItemMiscOptionsTitle() { return new Title(); }

    @ConfigItem(
            keyName = "inventoryItemDisplayMouseHoveringIndicator",
            name = "  Mouse hovering indicator",
            description = "If enabled will display an indicator if hovering over the inventoryItem.",
            position = 15,
            titleSection = "inventoryItemMiscOptionsTitle"
    )
    default boolean inventoryItemDisplayMouseHoveringIndicator() { return false; }

    @ConfigItem(
            keyName = "inventoryItemMouseHoveringIndicatorLocation",
            name = "  X:Y:Width:Height indicator location",
            description = "If enabled will display an indicator if hovering over the inventoryItem.",
            position = 16,
            titleSection = "inventoryItemMiscOptionsTitle"
    )
    default String inventoryItemMouseHoveringIndicatorLocation() { return "10:10:10:10"; }
    
}