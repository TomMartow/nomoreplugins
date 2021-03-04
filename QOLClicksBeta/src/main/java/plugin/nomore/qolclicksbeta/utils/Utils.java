package plugin.nomore.qolclicksbeta.utils;

import joptsimple.internal.Strings;
import net.runelite.api.mixins.Inject;
import plugin.nomore.qolclicksbeta.QOLClicksBetaConfig;

public class Utils
{

    @Inject
    private QOLClicksBetaConfig config;

    public String rws(String string)
    {
        return string.toLowerCase().replaceAll("\\s+", "");
    }

    public String removeCharactersFromString(String string)
    {
        return string.toLowerCase().replaceAll("\\D", "");
    }

    public int getConfigInt(int configIteration, String configString)
    {
        String[] splitString = configString.split(":");
        String[] sortedString = new String[3];
        if (Strings.isNullOrEmpty(configString))
        {
            sortedString[0] = "0";
            sortedString[1] = "0";
            sortedString[2] = "0";
        }
        if (splitString.length == 1)
        {
            sortedString[0] = splitString[0];
            sortedString[1] = "0";
            sortedString[2] = "0";
        }
        if (splitString.length == 2)
        {
            sortedString[0] = splitString[0];
            sortedString[1] = splitString[1];
            sortedString[2] = "0";
        }
        if (splitString.length == 3)
        {
            sortedString[0] = splitString[0];
            sortedString[1] = splitString[1];
            sortedString[2] = splitString[2];
        }
        System.out.println(sortedString[0] + " : " + sortedString[1] + " : " + sortedString[2]);
        return Integer.parseInt(sortedString[configIteration]);
    }

    public String getConfigString(int configIteration, String configString)
    {
        String[] splitString = configString.split(":");
        String[] sortedString = new String[3];
        if (Strings.isNullOrEmpty(configString))
        {
            sortedString[0] = "0";
            sortedString[1] = "0";
            sortedString[2] = "0";
        }
        if (splitString.length == 1)
        {
            sortedString[0] = splitString[0];
            sortedString[1] = "0";
            sortedString[2] = "0";
        }
        if (splitString.length == 2)
        {
            sortedString[0] = splitString[0];
            sortedString[1] = splitString[1];
            sortedString[2] = "0";
        }
        if (splitString.length == 3)
        {
            sortedString[0] = splitString[0];
            sortedString[1] = splitString[1];
            sortedString[2] = splitString[2];
        }
        System.out.println(sortedString[0] + " : " + sortedString[1] + " : " + sortedString[2]);
        return sortedString[configIteration];
    }
}
