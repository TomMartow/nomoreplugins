package plugin.nomore.qolclicks.menu.scene;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.queries.GameObjectQuery;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.QOLClicksPlugin;
import plugin.nomore.qolclicks.utils.Utils;

import javax.inject.Inject;
import java.util.List;

public class GameObj
{

    @Inject
    private Client client;

    @Inject
    private QOLClicksConfig config;

    @Inject
    private QOLClicksPlugin plugin;

    @Inject
    private Inventory inventory;

    @Inject
    private Utils utils;

    public GameObject getClosestGameObject(List<Integer> ids)
    {
        if (client.getLocalPlayer() == null)
        {
            return null;
        }
        return new GameObjectQuery()
                .idEquals(ids)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    public GameObject getClosestGameObject(int id)
    {
        if (client.getLocalPlayer() == null)
        {
            return null;
        }
        return new GameObjectQuery()
                .idEquals(id)
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

}
