package plugin.nomore.qolclicks.utils.menu;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.api.events.MenuOpened;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.utils.scene.Objects;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Opened
{

    @Inject private Client client;
    @Inject private QOLClicksConfig config;
    @Inject private Objects objects;

    public void addDropMenu(MenuOpened e)
    {
        MenuEntry[] origE = e.getMenuEntries();
        MenuEntry firstEntry = e.getFirstEntry();

        MenuEntry dropMatching = new MenuEntry("Drop-Matching",
                "<col=ffff00>Drop list",
                firstEntry.getIdentifier(),
                MenuOpcode.ITEM_USE.getId(),
                firstEntry.getParam0(),
                firstEntry.getParam1(),
                firstEntry.isForceLeftClick());

        MenuEntry dropExcept = new MenuEntry("Drop-Except",
                "<col=ffff00>Ignore list",
                firstEntry.getIdentifier(), MenuOpcode.ITEM_USE.getId(),
                firstEntry.getParam0(),
                firstEntry.getParam1(),
                firstEntry.isForceLeftClick());

        if (!config.enableDropExcept() && config.enableDropMatching())
        {
            if (origE.length == 3)
            {
                e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropMatching, origE[2]});
            }
            if (origE.length == 4)
            {
                e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropMatching, origE[2], origE[3]});
            }
            if (origE.length == 5)
            {
                e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropMatching, origE[2], origE[3], origE[4]});
            }
            if (origE.length == 6)
            {
                e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropMatching, origE[2], origE[3], origE[4], origE[5]});
            }
        }
        if (config.enableDropExcept() && !config.enableDropMatching())
        {
            if (origE.length == 3)
            {
                e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, origE[2]});
            }
            if (origE.length == 4)
            {
                e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, origE[2], origE[3]});
            }
            if (origE.length == 5)
            {
                e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, origE[2], origE[3], origE[4]});
            }
            if (origE.length == 6)
            {
                e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, origE[2], origE[3], origE[4], origE[5]});
            }
        }
        if (config.enableDropExcept() && config.enableDropMatching())
        {
            if (origE.length == 3)
            {
                e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, dropMatching, origE[2]});
            }
            if (origE.length == 4)
            {
                e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, dropMatching, origE[2], origE[3]});
            }
            if (origE.length == 5)
            {
                e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, dropMatching, origE[2], origE[3], origE[4]});
            }
            if (origE.length == 6)
            {
                e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], dropExcept, dropMatching, origE[2], origE[3], origE[4], origE[5]});
            }
        }
    }

    public void addBankMenu(MenuOpened e)
    {
        MenuEntry[] origE = e.getMenuEntries();

        List<GameObject> gameObjectList = objects.getGameObjectsMatching("Bank chest", "Bank booth");
        if (gameObjectList.size() == 0)
        {
            return;
        }
        GameObject object = gameObjectList.size() > 2
                ? gameObjectList.get(new Random().nextInt(2))
                : gameObjectList.get(new Random().nextInt(gameObjectList.size()));
        if (object == null)
        {
            return;
        }
        MenuEntry openBank = new MenuEntry(
                Arrays.stream(client.getObjectDefinition(object.getId()).getActions()).findFirst().orElse("Bank"),
                "<col=ffff00>" + client.getObjectDefinition(object.getId()).getName(),
                object.getId(),
                MenuOpcode.GAME_OBJECT_FIRST_OPTION.getId(),
                object.getSceneMinLocation().getX(),
                object.getSceneMinLocation().getY(),
                false);

        e.setMenuEntries(new MenuEntry[]{origE[0], origE[1], openBank});
    }

}
