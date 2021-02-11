package plugin.nomore.qolclicks.misc.inventory;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.MenuEntry;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.utils.Inventory;

import javax.inject.Inject;

@Slf4j
public class DropSimilar
{
    @Inject
    QOLClicksConfig config;
    @Inject
    Inventory inventory;

    public boolean menuOptionClicked(MenuEntry event)
    {
        if (config.enableDropSimilar())
        {
            inventory.setItemsToDrop(inventory.getItems(event.getIdentifier()));
            for (WidgetItem item : inventory.getItemsToDrop())
            {
                if (item == null)
                {
                    continue;
                }
                log.info(item.getId() + ", " + item.getIndex());
            }
            return inventory.getItemsToDrop() != null;
        }
        return true;
    }

}
