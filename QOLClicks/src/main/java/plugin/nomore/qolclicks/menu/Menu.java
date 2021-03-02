package plugin.nomore.qolclicks.menu;

import net.runelite.api.MenuAction;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.MenuOptionClicked;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.highlighting.Arrow;
import plugin.nomore.qolclicks.menu.inventory.GameObj;
import plugin.nomore.qolclicks.menu.inventory.Inventory;
import plugin.nomore.qolclicks.menu.inventory.Npc;
import plugin.nomore.qolclicks.utils.Utils;

import javax.inject.Inject;

public class Menu
{

    @Inject
    private QOLClicksConfig config;

    @Inject
    private Arrow arrow;

    @Inject
    private Inventory inventory;

    @Inject
    private Npc npc;

    @Inject
    private GameObj gameObj;

    @Inject
    private Utils utils;

    public void onOpen(MenuOpened e)
    {

    }

    public void onAdded(MenuEntryAdded e)
    {

    }

    public void onClicked(MenuOptionClicked e)
    {
        arrow.draw(e);

        if (e.getMenuAction() == MenuAction.ITEM_USE
                && config.enableItemOnItem())
        {
            inventory.useItemOnItem(e);
        }

        if (e.getMenuAction() == MenuAction.ITEM_USE
                && config.enableNPCFirstOption())
        {
            npc.interactWithNPC(e);
        }

        if (e.getMenuAction() == MenuAction.ITEM_USE
                && config.enableItemOnNpc())
        {
            npc.useItemOnNPC(e);
        }

        if (e.getMenuAction() == MenuAction.ITEM_USE
                && config.enableGameObjectFirstOption())
        {
            gameObj.interactWithGameObject(e);
        }

        if (e.getMenuAction() == MenuAction.ITEM_USE
                && config.enableItemOnObject())
        {
            gameObj.useItemOnGameObject(e);
        }

    }

}
