package plugin.nomore.qolclicks.utils.scene;

import com.google.common.base.Strings;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.queries.GameObjectQuery;
import plugin.nomore.qolclicks.utils.automation.Format;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Objects
{

    @Inject Client client;
    @Inject Format format;

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

    public GameObject getClosestGameObject()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGameObjects()
                .stream()
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public GameObject getClosestGameObjectMatching(String gameObjectName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGameObjects()
                .stream()
                .filter(gameObject
                        -> gameObject != null
                        && client.getObjectDefinition(gameObject.getId())
                        .getName()
                        .equalsIgnoreCase(gameObjectName))
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public GameObject getClosestGameObjectMatching(int gameObjectId)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGameObjects()
                .stream()
                .filter(gameObject
                        -> gameObject != null
                        && gameObject.getId() == gameObjectId)
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public GameObject getClosestGameObjectWithAction(String action)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        List<GameObject> gameObjectsList = new ArrayList<>(getGameObjects());
        List<GameObject> gameObjectListToReturn = new ArrayList<>();
        for (GameObject item : gameObjectsList)
        {
            if (item == null)
            {
                continue;
            }
            for (String inventoryAction : client.getObjectDefinition(item.getId()).getActions())
            {
                if (Strings.isNullOrEmpty(inventoryAction))
                {
                    continue;
                }
                if (inventoryAction.equalsIgnoreCase(action))
                {
                    gameObjectListToReturn.add(item);
                }
            }
        }

        return gameObjectListToReturn
                .stream()
                .min(Comparator.comparing(entityType
                -> entityType
                .getLocalLocation()
                .distanceTo(client.getLocalPlayer()
                        .getLocalLocation()))).orElse(null);
    }

    public List<GameObject> getGameObjectsWithAction(String action)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        List<GameObject> gameObjectsList = getGameObjects().stream().min(Comparator.comparing(entityType
                -> entityType
                .getLocalLocation()
                .distanceTo(client.getLocalPlayer()
                        .getLocalLocation()))).stream().collect(Collectors.toList());

        List<GameObject> gameObjectsListToReturn = new ArrayList<>();
        for (GameObject item : gameObjectsList)
        {
            if (item == null)
            {
                continue;
            }
            for (String inventoryAction : client.getObjectDefinition(item.getId()).getActions())
            {
                if (Strings.isNullOrEmpty(inventoryAction))
                {
                    continue;
                }
                if (inventoryAction.equalsIgnoreCase(action))
                {
                    gameObjectsListToReturn.add(item);
                }
            }
        }
        return gameObjectsListToReturn;
    }

    public List<GameObject> getGameObjectsMatching(String... gameObjectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGameObjects()
                .stream()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(gameObjectNames)
                        .anyMatch(s -> format.removeWhiteSpaces(s)
                                .equalsIgnoreCase(format.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId())
                                        .getName()))))
                .collect(Collectors.toList());
    }

    public List<GameObject> getGameObjectsWithNameContaining(String... gameObjectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGameObjects()
                .stream()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(gameObjectNames)
                        .anyMatch(gameObjectName -> client.getObjectDefinition(gameObject.getId())
                                .getName()
                                .toLowerCase()
                                .contains(gameObjectName.toLowerCase())))
                .collect(Collectors.toList());
    }

    public List<GameObject> getGameObjectsMatching(int... gameObjectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGameObjects()
                .stream()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(gameObjectIds)
                        .anyMatch(itemId -> itemId == gameObject.getId()))
                .collect(Collectors.toList());
    }

    public List<GameObject> getMatchingGameObjectsSortedByClosest(String... gameObjectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGameObjects()
                .stream()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(gameObjectNames)
                        .anyMatch(s -> format.removeWhiteSpaces(s)
                                .equalsIgnoreCase(format.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId())
                                        .getName()))))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<GameObject> getMatchingGameObjectsSortedByClosest(int... gameObjectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGameObjects()
                .stream()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(gameObjectIds)
                        .anyMatch(itemId -> itemId == gameObject.getId()))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public GameObject getClosestGameObjectMatching(String... gameObjectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGameObjects()
                .stream()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(gameObjectNames)
                        .anyMatch(s -> format.removeWhiteSpaces(s)
                                .equalsIgnoreCase(format.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId())
                                        .getName())))).min(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public GameObject getClosestGameObjectMatching(int... gameObjectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGameObjects()
                .stream()
                .filter(gameObject -> gameObject != null
                        && Arrays.stream(gameObjectIds)
                        .anyMatch(itemId -> itemId == gameObject.getId()))
                .min(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

}
