package plugin.nomore.qolclicks.utils;

import joptsimple.internal.Strings;
import net.runelite.api.mixins.Inject;
import plugin.nomore.qolclicks.QOLClicksConfig;

public class Utils
{

    @Inject
    private QOLClicksConfig config;

    public String rws(String string)
    {
        return string.toLowerCase().replaceAll("\\s+", "");
    }

    public String removeCharactersFromString(String string)
    {
        return string.toLowerCase().replaceAll("\\D", "");
    }

    public int getConfigArg(int configIteration, String configString)
    {
        if (Strings.isNullOrEmpty(configString))
        {
            return -1;
        }
        String[] splitString = configString.split(":");
        String[] sortedString = new String[2];
        if (splitString.length == 1)
        {
            sortedString[0] = splitString[0];
            sortedString[1] = "0";
        }
        else
        {
            sortedString[0] = splitString[0];
            sortedString[1] = splitString[1];
        }
        return Integer.parseInt(sortedString[configIteration]);
    }
}
