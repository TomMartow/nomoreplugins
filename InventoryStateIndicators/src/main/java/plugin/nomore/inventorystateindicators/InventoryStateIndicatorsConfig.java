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
package plugin.nomore.inventorystateindicators;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

import java.awt.*;

@ConfigGroup("InventoryStateIndicators")
public interface InventoryStateIndicatorsConfig extends Config
{

    @ConfigSection(
            keyName = "inventoryFullSection",
            name = "Inventory Full",
            description = "",
            position = 1
    )
    String inventoryFullSection = "inventoryFullSection";

    @ConfigItem(
            keyName = "enableInventoryFullIndicator",
            name = "Enable Indicator",
            description = "If enabled will display the indicator when conditions match.",
            position = 1,
            section = "inventoryFullSection"
    )
    default boolean enableInventoryFullIndicator() { return false; }

    @ConfigItem(
            keyName = "enableInventoryFullSlot28Override",
            name = "Last slot override",
            description = "If enabled will display the inventory full indicator if an item is in slot 28.",
            position = 1,
            section = "inventoryFullSection"
    )
    default boolean enableInventoryFullSlot28Override() { return false; }

    @ConfigItem(
            keyName = "inventoryFullLocation",
            name = "Location",
            description = "The location of the indicator.",
            position = 2,
            section = "inventoryFullSection"
    )
    default String inventoryFullLocation() { return "10:10:10:10"; }

    @ConfigItem(
            keyName = "inventoryFullColor",
            name = "Color",
            description = "The location of the indicator.",
            position = 3,
            section = "inventoryFullSection"
    )
    default Color inventoryFullColor() { return Color.RED; }

    @ConfigSection(
            keyName = "inventoryContainsSection",
            name = "Inventory Contain",
            description = "",
            position = 2
    )
    String inventoryContainsSection = "inventoryContainsSection";

    @ConfigItem(
            keyName = "enableInventoryContainsIndicator",
            name = "Enable Indicator",
            description = "If enabled will display the indicator when conditions match.",
            position = 1,
            section = "inventoryContainsSection"
    )
    default boolean enableInventoryContainsIndicator() { return false; }

    @ConfigItem(
            keyName = "inventoryContainsItemIDs",
            name = "Item IDs",
            description = "If enabled will display the indicator when conditions match.",
            position = 2,
            section = "inventoryContainsSection"
    )
    default String inventoryContainsItemIDs() { return ""; }

    @ConfigItem(
            keyName = "inventoryContainsLocation",
            name = "Location",
            description = "The location of the indicator.",
            position = 3,
            section = "inventoryContainsSection"
    )
    default String inventoryContainsLocation() { return "10:10:10:10"; }

    @ConfigItem(
            keyName = "inventoryContainsColor",
            name = "Color",
            description = "The location of the indicator.",
            position = 4,
            section = "inventoryContainsSection"
    )
    default Color inventoryContainsColor() { return Color.RED; }

    @ConfigSection(
            keyName = "inventoryDoesNotContainsSection",
            name = "Inventory Does Not Contain",
            description = "",
            position = 2
    )
    String inventoryDoesNotContainsSection = "inventoryDoesNotContainsSection";

    @ConfigItem(
            keyName = "enableInventoryDoesNotContainsIndicator",
            name = "Enable Indicator",
            description = "If enabled will display the indicator when conditions match.",
            position = 1,
            section = "inventoryDoesNotContainsSection"
    )
    default boolean enableInventoryDoesNotContainsIndicator() { return false; }

    @ConfigItem(
            keyName = "inventoryDoesNotContainsItemIDs",
            name = "Item IDs",
            description = "If enabled will display the indicator when conditions match.",
            position = 2,
            section = "inventoryDoesNotContainsSection"
    )
    default String inventoryDoesNotContainsItemIDs() { return ""; }

    @ConfigItem(
            keyName = "inventoryDoesNotContainsLocation",
            name = "Location",
            description = "The location of the indicator.",
            position = 3,
            section = "inventoryDoesNotContainsSection"
    )
    default String inventoryDoesNotContainsLocation() { return "10:10:10:10"; }

    @ConfigItem(
            keyName = "inventoryDoesNotContainsColor",
            name = "Color",
            description = "The location of the indicator.",
            position = 4,
            section = "inventoryDoesNotContainsSection"
    )
    default Color inventoryDoesNotContainsColor() { return Color.RED; }

}