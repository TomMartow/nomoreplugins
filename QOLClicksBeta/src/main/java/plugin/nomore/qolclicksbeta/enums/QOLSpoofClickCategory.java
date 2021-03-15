package plugin.nomore.qolclicksbeta.enums;

public enum QOLSpoofClickCategory
{

    // Click item in inventory to perform action.

    CUSTOM("Custom X:Y:Width:Height"),
    FULL_CLIENT("The Full Client");

    public final String enumString;

    QOLSpoofClickCategory(String enumString) {
        this.enumString = enumString;
    }

    public String toString() {
        return this.enumString;
    }
}
