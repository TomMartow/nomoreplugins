package plugin.nomore.qolclicks.skills.prayer.types;

import com.google.common.collect.ImmutableSet;
import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.Npcs;
import plugin.nomore.qolclicks.utils.Inventory;

import javax.inject.Inject;
import java.util.Set;

public class Unnote
{

    @Inject
    Client client;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    QOLClicksConfig config;

    @Inject
    Inventory inventory;

    @Inject
    Npcs npcs;

    private static final Set<Integer> NOTED_BONES_ID = ImmutableSet.of(
            537
    );

    public boolean menuOptionClicked(MenuEntry event)
    {
        WidgetItem widgetItem = inventory.getItem(event.getIdentifier());
        if (widgetItem == null)
        {
            return false;
        }
        NPC npc = npcs.getClosestMatchingName("Phials");
        if (npc == null)
        {
            return false;
        }
        plugin.setSelected(WidgetInfo.INVENTORY, widgetItem.getIndex(), widgetItem.getId());
        event.setOption("Unnote");
        event.setTarget("<col=ff9040>" + client.getItemDefinition(widgetItem.getId()).getName() + "<col=ffffff> -> <col=ffff00>" + client.getNpcDefinition(npc.getId()).getName());
        event.setIdentifier(npc.getIndex());
        event.setOpcode(MenuOpcode.ITEM_USE_ON_NPC.getId());
        event.setParam0(0);
        event.setParam1(0);
        event.setForceLeftClick(false);
        return true;
    }

    public void menuEntryAdded(MenuEntryAdded event)
    {
        if (client == null || event.isForceLeftClick())
        {
            return;
        }
        if (!inventory.contains(event.getIdentifier()))
        {
            return;
        }
        WidgetItem widgetItem = inventory.getItem(event.getIdentifier());
        NPC npc = npcs.getClosestMatchingName("Phials");
        if (npc == null)
        {
            return;
        }
        event.setOption("Unnote");
        event.setTarget("<col=ffff00>" + client.getItemDefinition(widgetItem.getId()).getName());
        plugin.insertMenuEntry(event, true);
    }

}
