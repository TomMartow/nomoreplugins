package plugin.nomore.nmoneclicker.menu;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.api.queries.GameObjectQuery;

import javax.inject.Inject;

public class Object
{

    @Inject
    Client client;

    public void useItemOnObject(MenuEntry event)
    {
        event.setOpcode(MenuOpcode.ITEM_USE_ON_GAME_OBJECT.getId());
    }

    public void setParams(MenuEntry event, GameObject object)
    {
        if (object == null)
        {
            return;
        }
        event.setParam0(object.getSceneMinLocation().getX());
        event.setParam1(object.getSceneMinLocation().getY());
    }

    public GameObject getGameObject(int... ids)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .idEquals(ids)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

}
