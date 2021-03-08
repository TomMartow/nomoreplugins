package plugin.nomore.qolclicksbeta;

public enum QOLClickWidgetItemInteractionType
{

    DROP_ITEM("Left Click Item -> Drop"),
    DEFAULT_CLICK("Left Click Item -> Default");

    public final String enumString;

    QOLClickWidgetItemInteractionType(String enumString) { this.enumString = enumString; }

    public String toString() {
        return this.enumString;
    }
}