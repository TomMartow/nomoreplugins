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
package plugin.nomore.objectmarkersextended;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("objectmarkersextended")
public interface ObjectMarkersExtendedConfig extends Config
{

    @ConfigTitle(
            keyName = "objectMarkerTitle",
            name = "Overlay Options",
            description = "",
            position = 2
    )
    String objectMarkerTitle = "objectMarkerTitle";

    @ConfigItem(
            keyName = "objectRenderStyle",
            name = "Render style",
            description = "The type of marker.",
            position = 5,
            title = "objectMarkerTitle"
    )
    default ObjectRenderStyle objectRenderStyle() { return ObjectRenderStyle.BOX; }

    @ConfigItem(
            keyName = "objectIndicatorSize",
            name = "Indicator size",
            description = "The size of the marker.",
            position = 6,
            hidden = true,
            unhide = "objectRenderStyle",
            unhideValue = "BOX",
            title = "objectMarkerTitle"
    )
    default int objectIndicatorSize() { return 4; }

    @ConfigItem(
            keyName = "configObjectTextField",
            name = "Name / ID:Hex Color",
            description = "Example: \"Bank booth:00FF00,\".",
            position = 7,
            title = "objectMarkerTitle"
    )
    default String configObjectTextField() { return "Bank booth, 10529"; }

    @ConfigTitle(
            keyName = "objectColorTitle",
            name = "Color Options",
            description = "",
            position = 9
    )
    String objectColorTitle = "objectColorTitle";

    @ConfigItem(
            keyName = "objectDefaultHighlightColor",
            name = "Default Marker color",
            description = "The default color for the Object indicator.",
            position = 11,
            title = "objectColorTitle"
    )
    default Color objectDefaultHighlightColor() { return Color.GREEN; }

    @ConfigTitle(
            keyName = "objectMiscOptionsTitle",
            name = "Miscellaneous Options",
            description = "",
            position = 13
    )
    String objectMiscOptionsTitle = "objectMiscOptionsTitle";

    @ConfigItem(
            keyName = "objectLineOfSight",
            name = "Only show Objects in line of sight",
            description = "Only show Objects your player (tile: diagonally or straight) can \"see\"",
            position = 15,
            title = "objectMiscOptionsTitle"
    )
    default boolean objectLineOfSight() { return false; }

    @ConfigItem(
            keyName = "objectDisplayMouseHoveringIndicator",
            name = "Mouse hovering indicator",
            description = "If enabled will display an indicator if hovering over the object.",
            position = 16,
            title = "objectMiscOptionsTitle"
    )
    default boolean objectDisplayMouseHoveringIndicator() { return false; }

    @ConfigItem(
            keyName = "objectMouseHoveringIndicatorLocation",
            name = "X:Y:Width:Height indicator location",
            description = "If enabled will display an indicator if hovering over the object.",
            position = 17,
            title = "objectMiscOptionsTitle"
    )
    default String objectMouseHoveringIndicatorLocation() { return "10:10:10:10"; }
    
}