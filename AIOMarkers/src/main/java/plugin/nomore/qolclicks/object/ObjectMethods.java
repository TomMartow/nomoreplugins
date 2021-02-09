package plugin.nomore.qolclicks.object;

import joptsimple.internal.Strings;
import net.runelite.api.events.*;
import net.runelite.api.queries.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.events.ConfigChanged;
import plugin.nomore.qolclicks.AIOConfig;
import plugin.nomore.qolclicks.KeyboardListener;
import net.runelite.api.*;
import plugin.nomore.qolclicks.object.builder.ConfigObject;
import plugin.nomore.qolclicks.object.builder.HighlightingObject;
import plugin.nomore.nmputils.api.StringAPI;

import javax.inject.Inject;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ObjectMethods
{

    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Inject
    private AIOConfig config;

    @Inject
    private StringAPI stringAPI;

    @Inject
    private KeyboardListener keyboardListener;

    private static final List<HighlightingObject> objectToHighlight = new ArrayList<>();
    private final List<ConfigObject> configObjects = new ArrayList<>();

    public void startUp()
    {
        getConfigTextField();
        clientThread.invoke(this::getAllObjects);
    }

    public void shutDown()
    {
        objectToHighlight.clear();
    }

    public void onGameStateChanged(GameStateChanged event)
    {
        switch (event.getGameState())
        {
            case LOADING:
                objectToHighlight.clear();
                getConfigTextField();
                break;
        }
    }

    public void onGameObjectSpawned(GameObjectSpawned event)
    {
        TileObject object = event.getGameObject();
        Tile tile = event.getTile();
        if (object == null || tile == null)
        {
            return;
        }
        compareObject(object, tile);
    }

    public void onGameObjectChanged(GameObjectChanged event)
    {
        removePrevious(event.getGameObject());
        TileObject newObject = event.getGameObject();
        Tile tile = event.getTile();
        if (newObject == null || tile == null)
        {
            return;
        }
        compareObject(newObject, tile);
    }

    public void onGameObjectDespawned(GameObjectDespawned event)
    {
        TileObject object = event.getGameObject();
        Tile tile = event.getTile();
        if (object == null || tile == null)
        {
            return;
        }
        objectToHighlight.removeIf(HighlightingObject -> HighlightingObject.equals(createHighlightingObject(object, tile)));
    }

    public void onGroundObjectSpawned(GroundObjectSpawned event)
    {
        TileObject object = event.getGroundObject();
        Tile tile = event.getTile();
        if (object == null || tile == null)
        {
            return;
        }
        compareObject(object, tile);
    }

    public void onGroundObjectChanged(GroundObjectChanged event)
    {
        removePrevious(event.getPrevious());
        TileObject newObject = event.getGroundObject();
        Tile tile = event.getTile();
        if (newObject == null || tile == null)
        {
            return;
        }
        compareObject(newObject, tile);
    }

    public void onGroundObjectDespawned(GroundObjectDespawned event)
    {
        TileObject object = event.getGroundObject();
        Tile tile = event.getTile();
        if (object == null || tile == null)
        {
            return;
        }
        objectToHighlight.removeIf(HighlightingObject -> HighlightingObject.equals(createHighlightingObject(object, tile)));
    }

    public void onDecorativeObjectSpawned(DecorativeObjectSpawned event)
    {
        TileObject object = event.getDecorativeObject();
        Tile tile = event.getTile();
        if (object == null || tile == null)
        {
            return;
        }
        compareObject(object, tile);
    }

    public void onDecorativeObjectChanged(DecorativeObjectChanged event)
    {
        removePrevious(event.getPrevious());
        TileObject newObject = event.getDecorativeObject();
        Tile tile = event.getTile();
        if (newObject == null || tile == null)
        {
            return;
        }
        compareObject(newObject, tile);
    }

    public void onDecorativeObjectDespawned(DecorativeObjectDespawned event)
    {
        TileObject object = event.getDecorativeObject();
        Tile tile = event.getTile();
        if (object == null || tile == null)
        {
            return;
        }
        objectToHighlight.removeIf(HighlightingObject -> HighlightingObject.equals(createHighlightingObject(object, tile)));
    }

    public void onWallObjectSpawned(WallObjectSpawned event)
    {
        TileObject object = event.getWallObject();
        Tile tile = event.getTile();
        if (object == null || tile == null)
        {
            return;
        }
        compareObject(object, tile);
    }

    public void onWallObjectChanged(WallObjectChanged event)
    {
        removePrevious(event.getPrevious());
        TileObject newObject = event.getWallObject();
        Tile tile = event.getTile();
        if (newObject == null || tile == null)
        {
            return;
        }
        compareObject(newObject, tile);
    }

    public void onWallObjectDespawned(WallObjectDespawned event)
    {
        TileObject object = event.getWallObject();
        Tile tile = event.getTile();
        if (object == null || tile == null)
        {
            return;
        }
        objectToHighlight.removeIf(HighlightingObject -> HighlightingObject.equals(createHighlightingObject(object, tile)));
    }

    private void removePrevious(TileObject previousObject)
    {
        if (previousObject == null)
        {
            return;
        }
        HighlightingObject previousHighlightingObject = createHighlightingObject(previousObject, null);
        if (previousHighlightingObject != null)
        {
            objectToHighlight.removeIf(HighlightingObject
                    -> HighlightingObject.getObject() == previousHighlightingObject.getObject());
        }
    }

    public void compareObject(TileObject object, Tile tile)
    {
        HighlightingObject highlightingObject = createHighlightingObject(object, tile);
        if (highlightingObject == null)
        {
            return;
        }
        String objectName = highlightingObject.getName();
        int objectId = highlightingObject.getId();
        for (ConfigObject configObject : configObjects)
        {
            // configObject.getName() may literally equal "null" rather than being null.
            if (!configObject.getName().equalsIgnoreCase("null"))
            {
                if (configObject.getName()
                        .equalsIgnoreCase(stringAPI.removeWhiteSpaces(objectName)))
                {
                    highlightingObject.setColor(configObject.getColor());
                    objectToHighlight.add(highlightingObject);
                }
            }
            if (configObject.getId() == objectId)
            {
                highlightingObject.setColor(configObject.getColor());
                objectToHighlight.add(highlightingObject);
            }
        }
    }

    public void getAllObjects()
    {
        if (client.getLocalPlayer() == null)
        {
            return;
        }
        assert client.isClientThread();

        for (GameObject object : new GameObjectQuery().result(client).list)
        {
            if (object == null)
            {
                continue;
            }
            compareObject(object, null);
        }
        for (DecorativeObject object : new DecorativeObjectQuery().result(client).list)
        {
            if (object == null)
            {
                continue;
            }
            compareObject(object, null);
        }
        for (WallObject object : new WallObjectQuery().result(client).list)
        {
            if (object == null)
            {
                continue;
            }
            compareObject(object, null);
        }
        for (GroundObject object : new GroundObjectQuery().result(client).list)
        {
            if (object == null)
            {
                continue;
            }
            compareObject(object, null);
        }
    }

    private HighlightingObject createHighlightingObject(TileObject object, Tile tile)
    {
        ObjectDefinition cDef = client.getObjectDefinition(object.getId());
        if (cDef == null)
        {
            return null;
        }
        return HighlightingObject.builder()
                .name(cDef.getName())
                .id(object.getId())
                .object(object)
                .tile(tile)
                .plane(object.getPlane())
                .build();
    }

    // ██████╗ ██████╗ ███╗   ██╗███████╗██╗ ██████╗
    //██╔════╝██╔═══██╗████╗  ██║██╔════╝██║██╔════╝
    //██║     ██║   ██║██╔██╗ ██║█████╗  ██║██║  ███╗
    //██║     ██║   ██║██║╚██╗██║██╔══╝  ██║██║   ██║
    //╚██████╗╚██████╔╝██║ ╚████║██║     ██║╚██████╔╝
    // ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝     ╚═╝ ╚═════╝

    //██████╗ ███████╗██╗      █████╗ ████████╗███████╗██████╗
    //██╔══██╗██╔════╝██║     ██╔══██╗╚══██╔══╝██╔════╝██╔══██╗
    //██████╔╝█████╗  ██║     ███████║   ██║   █████╗  ██║  ██║
    //██╔══██╗██╔══╝  ██║     ██╔══██║   ██║   ██╔══╝  ██║  ██║
    //██║  ██║███████╗███████╗██║  ██║   ██║   ███████╗██████╔╝
    //╚═╝  ╚═╝╚══════╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═════╝

    private void getConfigTextField()
    {
        String configTextString = stringAPI.removeWhiteSpaces(config.configObjectTextField());
        if (Strings.isNullOrEmpty(configTextString))
        {
            return;
        }
        String[] stringsSplitByComma = configTextString.split(",");
        for (String commaSplit : stringsSplitByComma)
        {
            if (commaSplit.contains(":"))
            {
                String[] colonSplit;
                String[] colonToAdd = commaSplit.split(":");
                if (colonToAdd.length == 0)
                {
                    return;
                }
                if (colonToAdd.length == 1)
                {
                    colonSplit = new String[]{colonToAdd[0], ""};
                }
                else
                {
                    colonSplit = new String[]{colonToAdd[0], colonToAdd[1]};
                }
                if (stringAPI.containsNumbers(colonSplit[0]))
                {
                    createConfigObject(null, checkInt(colonSplit[0]), colonSplit[1]);
                }
                else
                {
                    createConfigObject(colonSplit[0], -1, colonSplit[1]);
                }
            }
            else
            {
                String[] fakeSplit = {commaSplit, null, null};
                if (stringAPI.containsNumbers(fakeSplit[0]))
                {
                    createConfigObject(null, checkInt(fakeSplit[0]), null);
                }
                else
                {
                    createConfigObject(fakeSplit[0], -1, null);
                }
            }
        }
    }

    private int checkInt(String stringNum)
    {
        String charRemoved = stringAPI.removeCharactersFromString(stringNum);
        if (Strings.isNullOrEmpty(charRemoved))
        {
            return -1;
        }
        int number;
        if (charRemoved.length() > 8)
        {
            number = Integer.parseInt(charRemoved.substring(0, 8));
        }
        else
        {
            number = Integer.parseInt(charRemoved);
        }
        return number;
    }

    private void createConfigObject(String configObjectName, int configObjectId, String configObjectColor)
    {
        if (Strings.isNullOrEmpty(configObjectName))
        {
            configObjectName = "null";
        }
        if (Strings.isNullOrEmpty(configObjectColor))
        {
            configObjectColor = "null";
        }
        try
        {
            if (configObjectColor.length() != 6)
            {
                configObjectColor = "00FF00";
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            if (Strings.isNullOrEmpty(configObjectColor))
            {
                configObjectColor = "00FF00";
            }
        }
        Color actualConfigColor = config.objectDefaultHighlightColor();
        try
        {
            actualConfigColor = Color.decode("#" + configObjectColor);
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("Error decoding color for " + configObjectColor);
        }
        ConfigObject configObject = ConfigObject.builder()
                .name(configObjectName)
                .id(configObjectId)
                .color(actualConfigColor)
                .build();
        configObjects.add(configObject);
    }


    public void onConfigChanged(ConfigChanged event)
    {
        if (!event.getGroup().equals("aiomarkers"))
        {
            return;
        }
        if (event.getKey().equals("configObjectTextField")
                && config.enableObjectMarkers())
        {
            objectToHighlight.clear();
            configObjects.clear();
            getConfigTextField();
            clientThread.invoke(this::getAllObjects);
        }
    }

    // ██████╗ ███████╗████████╗████████╗███████╗██████╗ ███████╗
    //██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
    //██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝███████╗
    //██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗╚════██║
    //╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║███████║
    //╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

    public List<HighlightingObject> getObjectToHighlight()
    {
        return objectToHighlight;
    }

    //███╗   ███╗██╗███████╗ ██████╗
    //████╗ ████║██║██╔════╝██╔════╝
    //██╔████╔██║██║███████╗██║
    //██║╚██╔╝██║██║╚════██║██║
    //██║ ╚═╝ ██║██║███████║╚██████╗
    //╚═╝     ╚═╝╚═╝╚══════╝ ╚═════╝

    public boolean doesPlayerHaveALineOfSightToObject(Player player, TileObject object)
    {
        if (player == null)
        {
            return false;
        }
        if (object == null)
        {
            return false;
        }
        return player.getWorldArea().hasLineOfSightTo(client, object.getWorldLocation().toWorldArea());
    }
}
