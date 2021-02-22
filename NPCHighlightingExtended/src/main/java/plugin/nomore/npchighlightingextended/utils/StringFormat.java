package plugin.nomore.npchighlightingextended.utils;

import org.apache.commons.lang3.StringUtils;

public class StringFormat
{

    public String removeCharactersFromString(String string)
    {
        return string.toLowerCase().replaceAll("\\D", "");
    }

    public String removeNumbersFromString(String string)
    {
        return string.toLowerCase().replaceAll("[0-9]", "");
    }

    public String rws(String string)
    {
        return string.toLowerCase().replaceAll("\\s+", "");
    }

    public boolean containsNumbers(String string) { return StringUtils.containsAny(string, "[0-9]+"); }

}