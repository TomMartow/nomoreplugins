package plugin.nomore.qolclicksbeta.menu.scene;

import net.runelite.api.*;
import net.runelite.api.queries.GameObjectQuery;
import plugin.nomore.qolclicksbeta.QOLClicksBetaConfig;
import plugin.nomore.qolclicksbeta.QOLClicksBetaPlugin;
import plugin.nomore.qolclicksbeta.utils.Utils;

import javax.inject.Inject;
import java.util.List;

public class GameObj
{

    @Inject
    private Client client;

    @Inject
    private QOLClicksBetaConfig config;

    @Inject
    private QOLClicksBetaPlugin plugin;

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
