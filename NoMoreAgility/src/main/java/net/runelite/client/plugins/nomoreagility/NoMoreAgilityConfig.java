package net.runelite.client.plugins.nomoreagility;

import net.runelite.client.config.*;
import net.runelite.client.plugins.nomoreagility.render.EnumMarks;
import net.runelite.client.plugins.nomoreagility.render.EnumObstacles;

import java.awt.*;

@ConfigGroup("NoMoreAgility")
public interface NoMoreAgilityConfig extends Config {

    @ConfigTitleSection(
            keyName = "obstacleTitle",
            name = "Obstacles",
            description = "",
            position = 1
    )
    default Title obstacleTitle()
    {
        return new Title();
    }

    @ConfigItem(
            position = 2,
            keyName = "obstacleHighlightStyle",
            name = "Highlight style",
            description = "How the obstacle will render.",
            titleSection = "obstacleTitle"
    )
    default EnumObstacles obstacleRenderStyle()
    {
        return EnumObstacles.CLICKBOX;
    }

    @ConfigItem(
            position = 3,
            keyName = "obstacleColor",
            name = "Highlight color",
            description = "Highlight color",
            titleSection = "obstacleTitle"
    )
    default Color obstacleColor()
    {
        return Color.GREEN;
    }

    @ConfigItem(
            position = 4,
            keyName = "obstacleBoxSize",
            name = "Box size",
            description = "Size of the box",
            hidden = true,
            unhide = "obstacleHighlightStyle",
            unhideValue = "BOX",
            titleSection = "obstacleTitle"
    )
    default int obstacleBoxSize()
    {
        return 4;
    }

    // MARK OF GRACE

    @ConfigTitleSection(
            keyName = "marksTitle",
            name = "Marks of Grace",
            description = "",
            position = 5
    )
    default Title marksTitle()
    {
        return new Title();
    }

    @ConfigItem(
            position = 6,
            keyName = "marksHighlightStyle",
            name = "Highlight style",
            description = "How the marks of grace will render.",
            titleSection = "marksTitle"
    )
    default EnumMarks marksRenderStyle()
    {
        return EnumMarks.CLICKBOX;
    }

    @ConfigItem(
            position = 7,
            keyName = "marksColor",
            name = "Highlight color",
            description = "Highlight color",
            titleSection = "marksTitle"
    )
    default Color marksColor()
    {
        return Color.RED;
    }

    @ConfigItem(
            position = 8,
            keyName = "marksBoxSize",
            name = "Box size",
            description = "Size of the box",
            hidden = true,
            unhide = "marksHighlightStyle",
            unhideValue = "BOX",
            titleSection = "marksTitle"
    )
    default int marksBoxSize()
    {
        return 4;
    }

    @ConfigItem(
            position = 9,
            keyName = "markDistance",
            name = "Mark distance",
            description = "Display a overlay over the mark of grace when within x amount of tiles.",
            titleSection = "marksTitle"
    )
    default int markDistance()
    {
        return 5;
    }
}
