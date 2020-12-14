package plugin.nomore.nmputils.api;

import net.runelite.api.*;
import net.runelite.api.events.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectAPI
{

    @Inject
    private Client client;

    @Inject
    private StringAPI string;

    public static List<TileObject> tileObjects = new ArrayList<>();
    public static List<GameObject> gameObjects = new ArrayList<>();
    public static List<GroundObject> groundObjects = new ArrayList<>();
    public static List<WallObject> wallObjects = new ArrayList<>();
    public static List<DecorativeObject> decorativeObjects = new ArrayList<>();

    //  ████████╗██╗██╗     ███████╗
    //  ╚══██╔══╝██║██║     ██╔════╝
    //     ██║   ██║██║     █████╗
    //     ██║   ██║██║     ██╔══╝
    //     ██║   ██║███████╗███████╗
    //     ╚═╝   ╚═╝╚══════╝╚══════╝
    //   ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗
    //  ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝
    //  ██║   ██║██████╔╝     ██║█████╗  ██║        ██║
    //  ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║
    //  ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║
    //   ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝

    private void onTileObject(TileObject newObject, TileObject oldObject)
    {
        tileObjects.remove(oldObject);
        if (newObject == null)
        {
            return;
        }
        tileObjects.add(newObject);
    }

    public List<TileObject> get()
    {
        return tileObjects;
    }

    public TileObject getClosest()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public TileObject getClosestMatching(String tileObjectName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .filter(tileObject
                        -> tileObject != null
                        && client.getObjectDefinition(tileObject.getId())
                        .getName()
                        .equalsIgnoreCase(tileObjectName))
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public TileObject getClosestMatching(int tileObjectId)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .filter(tileObject
                        -> tileObject != null
                        && tileObject.getId() == tileObjectId)
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public List<TileObject> getMatching(String... tileObjectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .filter(tileObject -> tileObject != null
                        && Arrays.stream(tileObjectNames)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(tileObject.getId())
                                        .getName()))))
                .collect(Collectors.toList());
    }

    public List<TileObject> getMatching(int... tileObjectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .filter(tileObject -> tileObject != null
                        && Arrays.stream(tileObjectIds)
                        .anyMatch(itemId -> itemId == tileObject.getId()))
                .collect(Collectors.toList());
    }

    public List<TileObject> getMatchingSortedByClosest(String... tileObjectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .filter(tileObject -> tileObject != null
                        && Arrays.stream(tileObjectNames)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(tileObject.getId())
                                        .getName()))))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<TileObject> getMatchingSortedByClosest(int... tileObjectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return get()
                .stream()
                .filter(tileObject -> tileObject != null
                        && Arrays.stream(tileObjectIds)
                        .anyMatch(itemId -> itemId == tileObject.getId()))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    //   ██████╗  █████╗ ███╗   ███╗███████╗
    //  ██╔════╝ ██╔══██╗████╗ ████║██╔════╝
    //  ██║  ███╗███████║██╔████╔██║█████╗
    //  ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝
    //  ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗
    //   ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝
    //   ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗
    //  ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝
    //  ██║   ██║██████╔╝     ██║█████╗  ██║        ██║
    //  ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║
    //  ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║
    //   ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝
    //

    public void onGameObjectSpawned(GameObjectSpawned event)
    {
        onGameObject(event.getGameObject(), null);
    }
    
    public void onGameObjectChanged(GameObjectChanged event) { onGameObject(event.getGameObject(), event.getPrevious()); }

    public void onGameObjectDespawned(GameObjectDespawned event)
    {
        onGameObject(null, event.getGameObject());
    }
    
    private void onGameObject(GameObject newObject, GameObject oldObject)
    {
        gameObjects.remove(oldObject);
        onTileObject(newObject, oldObject);
        if (newObject == null)
        {
            return;
        }
        gameObjects.add(newObject);
    }

    public List<GameObject> getGameObjects()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return gameObjects;
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
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId())
                                        .getName()))))
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
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(gameObject.getId())
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

    //   ██████╗ ██████╗  ██████╗ ██╗   ██╗███╗   ██╗██████╗
    //  ██╔════╝ ██╔══██╗██╔═══██╗██║   ██║████╗  ██║██╔══██╗
    //  ██║  ███╗██████╔╝██║   ██║██║   ██║██╔██╗ ██║██║  ██║
    //  ██║   ██║██╔══██╗██║   ██║██║   ██║██║╚██╗██║██║  ██║
    //  ╚██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
    //   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝
    //   ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗
    //  ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝
    //  ██║   ██║██████╔╝     ██║█████╗  ██║        ██║
    //  ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║
    //  ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║
    //   ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝
    //

    public void onGroundObjectSpawned(GroundObjectSpawned event)
    {
        onGroundObject(event.getGroundObject(), null);
    }

    public void onGroundObjectChanged(GroundObjectChanged event) { onGroundObject(event.getGroundObject(), event.getPrevious()); }

    public void onGroundObjectDespawned(GroundObjectDespawned event)
    {
        onGroundObject(null, event.getGroundObject());
    }

    private void onGroundObject(GroundObject newObject, GroundObject oldObject)
    {
        groundObjects.remove(oldObject);
        onTileObject(newObject, oldObject);
        if (newObject == null)
        {
            return;
        }
        groundObjects.add(newObject);
    }

    public List<GroundObject> getGroundObjects()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return groundObjects;
    }

    public GroundObject getClosestGroundObject()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundObjects()
                .stream()
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public GroundObject getClosestGroundObjectMatching(String groundObjectName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundObjects()
                .stream()
                .filter(groundObject
                        -> groundObject != null
                        && client.getObjectDefinition(groundObject.getId())
                        .getName()
                        .equalsIgnoreCase(groundObjectName))
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public GroundObject getClosestGroundObjectMatching(int groundObjectId)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundObjects()
                .stream()
                .filter(groundObject
                        -> groundObject != null
                        && groundObject.getId() == groundObjectId)
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public List<GroundObject> getGroundObjectsMatching(String... groundObjectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundObjects()
                .stream()
                .filter(groundObject -> groundObject != null
                        && Arrays.stream(groundObjectNames)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(groundObject.getId())
                                        .getName()))))
                .collect(Collectors.toList());
    }

    public List<GroundObject> getGroundObjectsMatching(int... groundObjectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundObjects()
                .stream()
                .filter(groundObject -> groundObject != null
                        && Arrays.stream(groundObjectIds)
                        .anyMatch(itemId -> itemId == groundObject.getId()))
                .collect(Collectors.toList());
    }

    public List<GroundObject> getMatchingGroundObjectsSortedByClosest(String... groundObjectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundObjects()
                .stream()
                .filter(groundObject -> groundObject != null
                        && Arrays.stream(groundObjectNames)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(groundObject.getId())
                                        .getName()))))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<GroundObject> getMatchingGroundObjectsSortedByClosest(int... groundObjectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getGroundObjects()
                .stream()
                .filter(groundObject -> groundObject != null
                        && Arrays.stream(groundObjectIds)
                        .anyMatch(itemId -> itemId == groundObject.getId()))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    //  ██╗    ██╗ █████╗ ██╗     ██╗
    //  ██║    ██║██╔══██╗██║     ██║
    //  ██║ █╗ ██║███████║██║     ██║
    //  ██║███╗██║██╔══██║██║     ██║
    //  ╚███╔███╔╝██║  ██║███████╗███████╗
    //   ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚══════╝
    //   ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗
    //  ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝
    //  ██║   ██║██████╔╝     ██║█████╗  ██║        ██║
    //  ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║
    //  ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║
    //   ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝
    //

    public void onWallObjectSpawned(WallObjectSpawned event)
    {
        onWallObject(event.getWallObject(), null);
    }

    public void onWallObjectChanged(WallObjectChanged event) { onWallObject(event.getWallObject(), event.getPrevious()); }

    public void onWallObjectDespawned(WallObjectDespawned event)
    {
        onWallObject(null, event.getWallObject());
    }

    private void onWallObject(WallObject newObject, WallObject oldObject)
    {
        wallObjects.remove(oldObject);
        onTileObject(newObject, oldObject);
        if (newObject == null)
        {
            return;
        }
        wallObjects.add(newObject);
    }

    public List<WallObject> getWallObjects()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return wallObjects;
    }

    public WallObject getClosestWallObject()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getWallObjects()
                .stream()
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public WallObject getClosestWallObjectMatching(String wallObjectName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getWallObjects()
                .stream()
                .filter(wallObject
                        -> wallObject != null
                        && client.getObjectDefinition(wallObject.getId())
                        .getName()
                        .equalsIgnoreCase(wallObjectName))
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public WallObject getClosestWallObjectMatching(int wallObjectId)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getWallObjects()
                .stream()
                .filter(wallObject
                        -> wallObject != null
                        && wallObject.getId() == wallObjectId)
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public List<WallObject> getWallObjectsMatching(String... wallObjectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getWallObjects()
                .stream()
                .filter(wallObject -> wallObject != null
                        && Arrays.stream(wallObjectNames)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(wallObject.getId())
                                        .getName()))))
                .collect(Collectors.toList());
    }

    public List<WallObject> getWallObjectsMatching(int... wallObjectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getWallObjects()
                .stream()
                .filter(wallObject -> wallObject != null
                        && Arrays.stream(wallObjectIds)
                        .anyMatch(itemId -> itemId == wallObject.getId()))
                .collect(Collectors.toList());
    }

    public List<WallObject> getMatchingWallObjectsSortedByClosest(String... wallObjectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getWallObjects()
                .stream()
                .filter(wallObject -> wallObject != null
                        && Arrays.stream(wallObjectNames)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(wallObject.getId())
                                        .getName()))))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<WallObject> getMatchingWallObjectsSortedByClosest(int... wallObjectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getWallObjects()
                .stream()
                .filter(wallObject -> wallObject != null
                        && Arrays.stream(wallObjectIds)
                        .anyMatch(itemId -> itemId == wallObject.getId()))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    //  ██████╗ ███████╗ ██████╗ ██████╗ ██████╗  █████╗ ████████╗██╗██╗   ██╗███████╗
    //  ██╔══██╗██╔════╝██╔════╝██╔═══██╗██╔══██╗██╔══██╗╚══██╔══╝██║██║   ██║██╔════╝
    //  ██║  ██║█████╗  ██║     ██║   ██║██████╔╝███████║   ██║   ██║██║   ██║█████╗
    //  ██║  ██║██╔══╝  ██║     ██║   ██║██╔══██╗██╔══██║   ██║   ██║╚██╗ ██╔╝██╔══╝
    //  ██████╔╝███████╗╚██████╗╚██████╔╝██║  ██║██║  ██║   ██║   ██║ ╚████╔╝ ███████╗
    //  ╚═════╝ ╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═══╝  ╚══════╝
    //   ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗
    //  ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝
    //  ██║   ██║██████╔╝     ██║█████╗  ██║        ██║
    //  ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║
    //  ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║
    //   ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝
    //

    public void onDecorativeObjectSpawned(DecorativeObjectSpawned event)
    {
        onDecorativeObject(event.getDecorativeObject(), null);
    }

    public void onDecorativeObjectChanged(DecorativeObjectChanged event)
    {
        onDecorativeObject(event.getDecorativeObject(), event.getPrevious());
    }

    public void onDecorativeObjectDespawned(DecorativeObjectDespawned event)
    {
        onDecorativeObject(null, event.getDecorativeObject());
    }

    private void onDecorativeObject(DecorativeObject newObject, DecorativeObject oldObject)
    {
        decorativeObjects.remove(oldObject);
        onTileObject(newObject, oldObject);
        if (newObject == null)
        {
            return;
        }
        decorativeObjects.add(newObject);
    }

    public List<DecorativeObject> getDecorativeObjects()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return decorativeObjects;
    }

    public DecorativeObject getClosestDecorativeObject()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getDecorativeObjects()
                .stream()
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public DecorativeObject getClosestDecorativeObjectMatching(String decorativeObjectName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getDecorativeObjects()
                .stream()
                .filter(decorativeObject
                        -> decorativeObject != null
                        && client.getObjectDefinition(decorativeObject.getId())
                        .getName()
                        .equalsIgnoreCase(decorativeObjectName))
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public DecorativeObject getClosestDecorativeObjectMatching(int decorativeObjectId)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getDecorativeObjects()
                .stream()
                .filter(decorativeObject
                        -> decorativeObject != null
                        && decorativeObject.getId() == decorativeObjectId)
                .min(Comparator.comparing(entityType
                        -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .orElse(null);
    }

    public List<DecorativeObject> getDecorativeObjectsMatching(String... decorativeObjectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getDecorativeObjects()
                .stream()
                .filter(decorativeObject -> decorativeObject != null
                        && Arrays.stream(decorativeObjectNames)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(decorativeObject.getId())
                                        .getName()))))
                .collect(Collectors.toList());
    }

    public List<DecorativeObject> getDecorativeObjectsMatching(int... decorativeObjectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getDecorativeObjects()
                .stream()
                .filter(decorativeObject -> decorativeObject != null
                        && Arrays.stream(decorativeObjectIds)
                        .anyMatch(itemId -> itemId == decorativeObject.getId()))
                .collect(Collectors.toList());
    }

    public List<DecorativeObject> getMatchingDecorativeObjectsSortedByClosest(String... decorativeObjectNames)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getDecorativeObjects()
                .stream()
                .filter(decorativeObject -> decorativeObject != null
                        && Arrays.stream(decorativeObjectNames)
                        .anyMatch(s -> string.removeWhiteSpaces(s)
                                .equalsIgnoreCase(string.removeWhiteSpaces(client.getObjectDefinition(decorativeObject.getId())
                                        .getName()))))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }

    public List<DecorativeObject> getMatchingDecorativeObjectsSortedByClosest(int... decorativeObjectIds)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getDecorativeObjects()
                .stream()
                .filter(decorativeObject -> decorativeObject != null
                        && Arrays.stream(decorativeObjectIds)
                        .anyMatch(itemId -> itemId == decorativeObject.getId()))
                .sorted(Comparator.comparing(entityType -> entityType
                        .getLocalLocation()
                        .distanceTo(client.getLocalPlayer()
                                .getLocalLocation())))
                .collect(Collectors.toList());
    }
    
}
