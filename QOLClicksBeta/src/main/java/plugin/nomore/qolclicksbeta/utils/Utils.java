package plugin.nomore.qolclicksbeta.utils;

import joptsimple.internal.Strings;
import net.runelite.api.mixins.Inject;
import plugin.nomore.qolclicksbeta.QOLClicksBetaConfig;

import java.awt.*;

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
        return sortedString[configIteration];
    }

    public void canvasIndicator(Graphics2D graphics, int[] indicatorLocation, Color color)
    {
        if (color == null)
        {
            color = Color.RED;
        }
        graphics.setColor(color);
        graphics.fillRect(indicatorLocation[0],
                indicatorLocation[1],
                indicatorLocation[2],
                indicatorLocation[3]);
    }

    public void canvasIndicator(Graphics2D graphics, int x, int y, int width, int height, Color color)
    {
        if (color == null)
        {
            color = Color.RED;
        }
        graphics.setColor(color);
        graphics.fillRect(x, y, width, height);
    }

    public int[] getCanvasIndicatorLocation(String string)
    {
        int[] location = {0,0,5,5};
        if (string.isEmpty())
        {
            return location;
        }
        String[] parts = rws(string).split(":");
        for (int i = 0; i < 4; i++)
        {
            String part = removeCharactersFromString(parts[i]);
            if (part.isEmpty())
            {
                break;
            }
            location[i] = Integer.parseInt(part);
        }
        return location;
    }
}
