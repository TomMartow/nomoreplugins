package plugin.nomore.qolclicks.utils.automation;

public class Format
{

    public String removeCharactersFromString(String string)
    {
        return string.toLowerCase().replaceAll("\\D", "");
    }

    public String removeNumbersFromString(String string)
    {
        return string.toLowerCase().replaceAll("[0-9]", "");
    }

    public String string(String string)
    {
        return string.toLowerCase().replaceAll("\\s+", "");
    }

    public boolean containsNumbers(String string) { return org.apache.commons.lang3.StringUtils.containsAny(string, "[0-9]+"); }

}