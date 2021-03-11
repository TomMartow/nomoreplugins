package plugin.nomore.qolclicksbeta.menu.actions.inventory;

import net.runelite.api.Client;
import net.runelite.api.events.MenuOptionClicked;
import plugin.nomore.qolclicksbeta.QOLClicksConfig;
import plugin.nomore.qolclicksbeta.QOLClicksPlugin;
import plugin.nomore.qolclicksbeta.menu.scene.GameObj;
import plugin.nomore.qolclicksbeta.menu.scene.Inventory;
import plugin.nomore.qolclicksbeta.menu.scene.Npc;
import plugin.nomore.qolclicksbeta.utils.Utils;

import javax.inject.Inject;

public class INV_SPELL_CAST_ON_GROUND_ITEM
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
    GameObj gameObj;

    @Inject
    Npc npc;

    @Inject
    Utils utils;

    public void check(MenuOptionClicked e)
    {
    }
}
