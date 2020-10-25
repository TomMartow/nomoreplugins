package net.runelite.client.plugins.nmutils;

import net.runelite.client.config.*;

@ConfigGroup("nmutils")
public interface UtilsConfig extends Config {

    @ConfigTitleSection(
            keyName = "playerUtils",
            name = "Player Configurations",
            description = "",
            position = 1
    )
    default Title playerUtils()
    {
        return new Title();
    }

    @ConfigItem(
            keyName = "playerIdleTime",
            name = "Animation Idle Delay",
            description = "The time it will take for the player to be classed as Idle after an animation.",
            position = 1,
            titleSection = "playerUtils"
    )
    default int playerIdleTime()
    {
        return 1000;
    }

}
