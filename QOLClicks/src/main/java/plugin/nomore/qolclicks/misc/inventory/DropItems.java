package plugin.nomore.qolclicks.misc.inventory;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.Inventory;

import javax.inject.Inject;
import java.util.List;

@Slf4j
public class DropItems
{
    @Inject
    QOLClicksConfig config;
    @Inject
    Inventory inventory;

    public boolean menuOptionClicked(MenuEntry event)
    {
        if (config.enableDropItems())
        {
            inventory.setItemsToDrop(inventory.getWidgetItemsThatMatchesConfigItemName());
            return inventory.getItemsToDrop() != null;
        }
        return true;
    }

}
