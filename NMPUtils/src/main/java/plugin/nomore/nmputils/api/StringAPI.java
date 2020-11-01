package plugin.nomore.nmputils.api;

import org.apache.commons.lang3.StringUtils;

public class StringAPI
{

    public String removeCharactersFromString(String string)
    {
        return string.toLowerCase().replaceAll("\\D", "");
    }

    public String removeNumbersFromString(String string)
    {
        return string.toLowerCase().replaceAll("[0-9]", "");
    }

    public String removeWhiteSpaces(String string)
    {
        return string.toLowerCase().replaceAll("\\s+", "");
    }

    public boolean containsNumbers(String string) { return StringUtils.containsAny(string, "[0-9]+"); }

}