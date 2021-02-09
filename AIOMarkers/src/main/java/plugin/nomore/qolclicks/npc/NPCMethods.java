package plugin.nomore.qolclicks.npc;

import joptsimple.internal.Strings;
import net.runelite.api.events.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.events.ConfigChanged;
import plugin.nomore.qolclicks.AIOConfig;
import plugin.nomore.qolclicks.KeyboardListener;
import net.runelite.api.*;
import plugin.nomore.qolclicks.npc.builder.ConfigObject;
import plugin.nomore.qolclicks.npc.builder.HighlightingObject;
import plugin.nomore.nmputils.api.StringAPI;

import javax.inject.Inject;
import java.awt.*;
import java.util.*;
import java.util.List;

public class NPCMethods
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

    private static final List<HighlightingObject> npcsToHighlight = new ArrayList<>();
    private final List<ConfigObject> configObjects = new ArrayList<>();

    public void startUp()
    {
        getConfigTextField();
        clientThread.invoke(this::getAllNpcs);
    }

    public void shutDown()
    {
        npcsToHighlight.clear();
    }

    public void onGameStateChanged(GameStateChanged event)
    {
        switch (event.getGameState())
        {
            case LOADING:
                npcsToHighlight.clear();
                getConfigTextField();
                break;
        }
    }

    public void onNPCSpawned(NpcSpawned event)
    {
        NPC npc = event.getNpc();
        if (npc == null)
        {
            return;
        }
        compareNPC(npc);
    }

    public void onNPCDespawned(NpcDespawned event)
    {
        NPC npc = event.getNpc();
        if (npc == null)
        {
            return;
        }
        npcsToHighlight.removeIf(HighlightingObject -> HighlightingObject.equals(createHighlightingObject(npc)));
    }

    public void getAllNpcs()
    {
        if (client.getLocalPlayer() == null)
        {
            return;
        }
        assert client.isClientThread();

        List<NPC> npcs = client.getNpcs();
        for (NPC npc : npcs)
        {
            if (npc == null)
            {
                continue;
            }
            compareNPC(npc);
        }
    }

    private void compareNPC(NPC npc)
    {
        HighlightingObject highlightingObject = createHighlightingObject(npc);
        if (highlightingObject == null)
        {
            return;
        }
        String npcName = highlightingObject.getName();
        int npcId = npc.getId();
        for (ConfigObject configObject : configObjects)
        {
            if (!Strings.isNullOrEmpty(configObject.getName()))
            {
                if (configObject.getName()
                        .equalsIgnoreCase(stringAPI.removeWhiteSpaces(npcName))
                        || npcId == configObject.getId())
                {
                    highlightingObject.setColor(configObject.getColor());
                    npcsToHighlight.add(highlightingObject);
                }
            }
            if (npcId == configObject.getId())
            {
                highlightingObject.setColor(configObject.getColor());
                npcsToHighlight.add(highlightingObject);
            }
        }
    }

    private HighlightingObject createHighlightingObject(NPC npc)
    {
        NPCDefinition cDef = client.getNpcDefinition(npc.getId());
        if (cDef == null)
        {
            return null;
        }
        return HighlightingObject.builder()
                .name(cDef.getName())
                .id(npc.getId())
                .npc(npc)
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
        String configTextString = stringAPI.removeWhiteSpaces(config.npcConfigTextString());
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

    private void createConfigObject(String configNpcName, int configNpcId, String configNpcColor)
    {
        if (Strings.isNullOrEmpty(configNpcName))
        {
            configNpcName = "null";
        }
        if (Strings.isNullOrEmpty(configNpcColor))
        {
            configNpcColor = "null";
        }
        try
        {
            if (configNpcColor.length() != 6)
            {
                configNpcColor = "00FF00";
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            if (Strings.isNullOrEmpty(configNpcColor))
            {
                configNpcColor = "00FF00";
            }
        }
        Color actualConfigColor = config.npcDefaultHighlightColor();
        try
        {
            actualConfigColor = Color.decode("#" + configNpcColor);
        }
        catch (NumberFormatException nfe)
        {
            System.out.println("Error decoding color for " + configNpcColor);
        }
        ConfigObject configObject = ConfigObject.builder()
                .name(configNpcName)
                .id(configNpcId)
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
        if (event.getKey().equals("configNPCTextField")
                && config.enableNPCHighlighting())
        {
            npcsToHighlight.clear();
            configObjects.clear();
            getConfigTextField();
            clientThread.invoke(this::getAllNpcs);
        }
    }

    // ██████╗ ████████╗██╗  ██╗███████╗██████╗
    //██╔═══██╗╚══██╔══╝██║  ██║██╔════╝██╔══██╗
    //██║   ██║   ██║   ███████║█████╗  ██████╔╝
    //██║   ██║   ██║   ██╔══██║██╔══╝  ██╔══██╗
    //╚██████╔╝   ██║   ██║  ██║███████╗██║  ██║
    //╚═════╝    ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
    //
    //██████╗ ██╗      █████╗ ██╗   ██╗███████╗██████╗ ███████╗
    //██╔══██╗██║     ██╔══██╗╚██╗ ██╔╝██╔════╝██╔══██╗██╔════╝
    //██████╔╝██║     ███████║ ╚████╔╝ █████╗  ██████╔╝███████╗
    //██╔═══╝ ██║     ██╔══██║  ╚██╔╝  ██╔══╝  ██╔══██╗╚════██║
    //██║     ███████╗██║  ██║   ██║   ███████╗██║  ██║███████║
    //╚═╝     ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

    private static final List<Player> otherPlayersList = new ArrayList<>();

    public void onPlayerSpawned(PlayerSpawned event)
    {
        if (event.getPlayer() == null)
        {
            return;
        }
        otherPlayersList.add(event.getPlayer());
    }

    public void onPlayerDespawned(PlayerDespawned event)
    {
        if (event.getPlayer() == null)
        {
            return;
        }
        otherPlayersList.remove(event.getPlayer());
    }

    // ██████╗ ███████╗████████╗████████╗███████╗██████╗ ███████╗
    //██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
    //██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝███████╗
    //██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗╚════██║
    //╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║███████║
    //╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

    public List<HighlightingObject> getNpcsToHighlight()
    {
        return npcsToHighlight;
    }

    public List<Player> getOtherPlayersList()
    {
        return otherPlayersList;
    }

    //███╗   ███╗██╗███████╗ ██████╗
    //████╗ ████║██║██╔════╝██╔════╝
    //██╔████╔██║██║███████╗██║
    //██║╚██╔╝██║██║╚════██║██║
    //██║ ╚═╝ ██║██║███████║╚██████╗
    //╚═╝     ╚═╝╚═╝╚══════╝ ╚═════╝

    public boolean doesPlayerHaveALineOfSightToNPC(Player player, NPC npc)
    {
        if (player == null)
        {
            return false;
        }
        if (npc == null)
        {
            return false;
        }
        return player.getWorldArea().hasLineOfSightTo(client, npc.getWorldArea());
    }
}
