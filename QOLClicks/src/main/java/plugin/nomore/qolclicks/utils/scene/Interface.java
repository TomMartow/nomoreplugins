package plugin.nomore.qolclicks.utils.scene;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;

public class Interface
{

    @Inject private Client client;

    public int getInventoryTabID()
    {
        Widget fixedInvTab = client.getWidget(WidgetInfo.FIXED_VIEWPORT_INVENTORY_TAB);
        if (fixedInvTab != null
                && !fixedInvTab.isHidden())
        {
            return fixedInvTab.getId();
        }
        Widget resizedClassicInvTab = client.getWidget(WidgetInfo.RESIZABLE_VIEWPORT_INVENTORY_TAB);
        if (resizedClassicInvTab != null
                && !resizedClassicInvTab.isHidden())
        {
            return resizedClassicInvTab.getId();
        }
        Widget residedModernInvTab = client.getWidget(WidgetInfo.RESIZABLE_VIEWPORT_BOTTOM_LINE_INVENTORY_TAB);
        if (residedModernInvTab != null
                && !residedModernInvTab.isHidden())
        {
            return residedModernInvTab.getId();
        }
        return 0;
    }
}
