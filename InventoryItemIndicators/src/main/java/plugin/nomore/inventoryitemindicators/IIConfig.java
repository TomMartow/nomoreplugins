package plugin.nomore.inventoryitemindicators;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("inventoryitemindicators")
public interface IIConfig extends Config {

    @ConfigTitleSection(
            keyName = "firstTitle",
            name = "Inventory Indicators",
            description = "",
            position = 1
    )
    default Title clickLogTitle() { return new Title(); }

    @ConfigItem(
            keyName = "inventoryEnum",
            name = "",
            description = "",
            position = 2,
            titleSection = "firstTitle"
    )
    default InventoryEnum inventoryEnum() { return InventoryEnum.FULL; }

    @ConfigItem(
            keyName = "displayFull",
            name = "Enable indicator",
            description = "",
            position = 3,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "FULL",
            titleSection = "firstTitle"
    )
    default boolean displayFull() { return false; }

    @ConfigItem(
            keyName = "fullLocation",
            name = "Indicator location",
            description = "Format: X:Y:WIDTH:HEIGHT | \"10:10:20:20\"",
            position = 4,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "FULL",
            titleSection = "firstTitle"
    )
    default String fullLocation() { return "100:0:5:5"; }

    @ConfigItem(
            keyName = "fullColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 5,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "FULL",
            titleSection = "firstTitle"
    )
    default Color fullColor() { return Color.RED; }

    @ConfigTitleSection(
            keyName = "secondTitle",
            name = "Inventory",
            description = "",
            position = 7,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS"
    )
    default Title secondTitle() { return new Title(); }

    @ConfigItem(
            keyName = "containNames",
            name = "Name : Stack-Amount : Hex Color",
            description = "Highlights the following items, use the following format: \"Coins:1000:00FF00, Ash,\"",
            position = 8,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "secondTitle"
    )
    default String containName() { return "Coins:1000, \nAsh:1:00FF00, \n*bones,"; }

    @ConfigItem(
            keyName = "highlightItems",
            name = "Highlight Items",
            description = "Highlight items based on a color, default: RED.",
            position = 9,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "secondTitle"
    )
    default boolean highlightItems() { return false; }

    @ConfigItem(
            keyName = "defaultHighlightColor",
            name = "Default highlight color",
            description = "highlight color",
            position = 10,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "secondTitle"
    )
    default Color defaultHighlightColor() { return Color.RED; }

    @ConfigTitleSection(
            keyName = "thirdTitle",
            name = "Indicator",
            description = "",
            position = 11,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS"
    )
    default Title thirdTitle() { return new Title(); }

    @ConfigItem(
            keyName = "displayContain",
            name = "Display indicator",
            description = "",
            position = 12,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "thirdTitle"
    )
    default boolean displayContain() { return false; }

    @ConfigItem(
            keyName = "containLocation",
            name = "X : Y : Width : Height",
            description = "Format: X:Y:WIDTH:HEIGHT | \"10:10:20:20\"",
            position = 13,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "thirdTitle"
    )
    default String containLocation() { return "105:0:5:5"; }

    @ConfigItem(
            keyName = "containColor",
            name = "Indicator color",
            description = "Indicator color",
            position = 14,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "thirdTitle"
    )
    default Color containColor() { return Color.RED; }

    @ConfigItem(
            keyName = "containSize",
            name = "Marker size",
            description = "",
            position = 15,
            hidden = true,
            unhide = "inventoryEnum",
            unhideValue = "CONTAINS",
            titleSection = "thirdTitle"
    )
    default int containMarkerSize() { return 4; }

}
