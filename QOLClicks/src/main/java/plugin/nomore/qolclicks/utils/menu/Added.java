package plugin.nomore.qolclicks.utils.menu;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.scene.Objects;

import javax.inject.Inject;

@Slf4j
public class Added
{

    @Inject private Client client;
    @Inject private QOLClicksPlugin plugin;
    @Inject private Added menuClicked;
    @Inject private Objects objects;

    public void useItemOnItem(String option, WidgetItem itemClicked, WidgetItem itemToBeUsedOn, MenuEntry e)
    {
        if (client == null || e.isForceLeftClick())
        {
            return;
        }
        if (itemClicked == null || itemToBeUsedOn == null)
        {
            return;
        }
        e.setOption(option);
        e.setTarget("<col=ffff00>" + client.getItemDefinition(itemClicked.getId()).getName());
        plugin.insertMenuEntry(e, true);
    }

    public void interactWithNPC(String option, NPC npcToInteractWith, MenuEntry e)
    {
        if (client == null || e.isForceLeftClick())
        {
            return;
        }
        if (npcToInteractWith == null)
        {
            return;
        }
        e.setOption(option);
        e.setTarget("<col=ffff00>" + client.getNpcDefinition(npcToInteractWith.getId()).getName());
        plugin.insertMenuEntry(e, true);
    }

    public void useItemOnNPC(String option, WidgetItem itemClicked, NPC npcToUseItemOn, MenuEntry e)
    {
        if (client == null || e.isForceLeftClick())
        {
            return;
        }
        if (itemClicked == null)
        {

        }
        if (npcToUseItemOn == null)
        {
            return;
        }
        e.setOption(option);
        e.setTarget("<col=ffff00>" + client.getNpcDefinition(npcToUseItemOn.getId()).getName());
        plugin.insertMenuEntry(e, true);
    }

}
