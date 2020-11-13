package plugin.nomore.nmputils.api;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.queries.GameObjectQuery;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NPCAPI
{

    @Inject
    private Client client;

    @Inject
    private StringAPI string;

    public List<GameObject> getGameObjects()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .result(client)
                .list;
    }

    public List<GameObject> getGameObjectsSortedByDistance()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .result(client)
                .stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public GameObject getClosestGameObjectMatching(String objectName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .filter(gameObject -> gameObject != null
                        && string.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId())
                        .getName())
                        .equalsIgnoreCase(string.removeWhiteSpaces(objectName)))
                .result(client)
                .stream().min(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer().getLocalLocation())))
                .orElse(null);
    }

    public GameObject getClosestGameObjectMatching(int objectId)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .filter(gameObject -> gameObject != null
                        && gameObject.getId() == objectId)
                .result(client)
                .stream().min(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer().getLocalLocation())))
                .orElse(null);
    }

    public List<GameObject> getGameObjectsMatching(String... objectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(objectNames)
                        .anyMatch(objectName -> string.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId())
                                .getName())
                                .equalsIgnoreCase(string.removeWhiteSpaces(objectName))))
                .result(client)
                .stream().sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<GameObject> getClosestGameObjectsMatching(int... objectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(objectIds)
                        .anyMatch(objectId -> objectId == gameObject.getId()))
                .result(client)
                .stream().sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<GameObject> getGameObjectsNotMatching(String... objectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(objectNames)
                        .noneMatch(objectName -> string.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId())
                                .getName())
                                .equalsIgnoreCase(string.removeWhiteSpaces(objectName))))
                .result(client)
                .stream().sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<GameObject> getClosestGameObjectsNotMatching(int... objectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(objectIds)
                        .noneMatch(objectId -> objectId == gameObject.getId()))
                .result(client)
                .stream().sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public GameObject getClosestGameObjectWithinDistanceTo(String objectName, WorldPoint comparisonTile, int maxTileDistance)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .isWithinDistance(comparisonTile, maxTileDistance)
                .result(client)
                .stream()
                .filter(gameObject -> gameObject != null
                        && string.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId()).getName())
                        .equalsIgnoreCase(string.removeWhiteSpaces(objectName)))
                .findFirst()
                .orElse(null);
    }

    public GameObject getClosestGameObjectWithinDistanceTo(int objectId, WorldPoint comparisonTile, int maxTileDistance)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .isWithinDistance(comparisonTile, maxTileDistance)
                .result(client)
                .stream()
                .filter(gameObject -> gameObject != null
                        && gameObject.getId() == objectId)
                .findFirst()
                .orElse(null);
    }

    public List<GameObject> getGameObjectsWithinDistanceTo(String[] objectNames, WorldPoint comparisonTile, int maxTileDistance)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .isWithinDistance(comparisonTile, maxTileDistance)
                .result(client)
                .stream()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(objectNames)
                        .anyMatch(objectName -> string.removeWhiteSpaces(objectName)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId())
                                        .getName()))))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<GameObject> getGameObjectsWithinDistanceTo(Integer[] objectIds, WorldPoint comparisonTile, int maxTileDistance)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return new GameObjectQuery()
                .isWithinDistance(comparisonTile, maxTileDistance)
                .result(client)
                .stream()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(objectIds)
                        .anyMatch(ObjectId -> ObjectId == gameObject.getId()))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

}
