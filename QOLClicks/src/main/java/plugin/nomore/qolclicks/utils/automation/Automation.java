package plugin.nomore.qolclicks.utils.automation;

import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.utils.scene.Inventory;
import plugin.nomore.qolclicks.utils.scene.builds.InventoryItem;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Automation
{

    @Inject
    Client client;
    @Inject
    private QOLClicksConfig config;

    @Inject
    private Format format;
    @Inject
    Inventory inventory;

    public void dropMatching()
    {
        List<InventoryItem> dropList = new ArrayList<>();
        String[] configMatchingTextBoxStrings = format.string(config.dropMatchingTextBox()).split(",");
        for (WidgetItem item : inventory.getItems())
        {
            if (item != null)
            {
                if (Arrays.stream(configMatchingTextBoxStrings)
                        .anyMatch(cIN
                                -> format.string(client.getItemDefinition(item.getId()).getName())
                                .equalsIgnoreCase(cIN)))
                {
                    dropList.add(InventoryItem.builder()
                            .item(item)
                            .name(client.getItemDefinition(item.getId()).getName())
                            .build());
                }
                else
                {
                    dropList.add(null);
                }
            }
        }
        inventory.dropItems(inventory.sortDropListOrder(dropList));
    }

    public void dropExcept()
    {
        List<InventoryItem> dropList = new ArrayList<>();
        String[] configExceptTextBoxStrings = format.string(config.dropExceptTextBox()).split(",");
        for (WidgetItem item : inventory.getItems())
        {
            if (item != null)
            {
                if (Arrays.stream(configExceptTextBoxStrings)
                        .noneMatch(cIN
                                -> format.string(client.getItemDefinition(item.getId()).getName())
                                .equalsIgnoreCase(cIN)))
                {
                    dropList.add(InventoryItem.builder()
                            .item(item)
                            .name(client.getItemDefinition(item.getId()).getName())
                            .build());
                }
                else
                {
                    dropList.add(null);
                }
            }
        }
        inventory.dropItems(inventory.sortDropListOrder(dropList));
    }

}
