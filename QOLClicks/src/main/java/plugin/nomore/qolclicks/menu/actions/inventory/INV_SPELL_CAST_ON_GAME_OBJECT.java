package plugin.nomore.qolclicks.menu.actions.inventory;

import net.runelite.api.Client;
import net.runelite.api.events.MenuOptionClicked;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.menu.scene.GameObj;
import plugin.nomore.qolclicks.menu.scene.Inventory;
import plugin.nomore.qolclicks.menu.scene.Npc;
import plugin.nomore.qolclicks.utils.Utils;

import javax.inject.Inject;

public class INV_SPELL_CAST_ON_GAME_OBJECT
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
