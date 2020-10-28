package plugin.nomore.aiomarkers.NPC;

import plugin.nomore.aiomarkers.AIOConfig;
import plugin.nomore.aiomarkers.AIOPlugin;
import plugin.nomore.nmputils.NMPUtils;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.util.Text;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.util.ColorUtil;

import javax.inject.Inject;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import static net.runelite.api.MenuOpcode.MENU_ACTION_DEPRIORITIZE_OFFSET;

public class NPCMethods
{

    @Inject
    private Client client;

    @Inject
    private AIOConfig config;

    @Inject
    private AIOPlugin plugin;

    @Inject
    private NMPUtils utils;

    List<String> configNameList = new ArrayList<>();
    List<Integer> configIdList = new ArrayList<>();

    private static final String TAG = "Add-Tag";
    private static final String UNTAG = "Remove-Tag";

    private static final Set<MenuOpcode> NPC_MENU_ACTIONS = Set.of(
            MenuOpcode.NPC_FIRST_OPTION,
            MenuOpcode.NPC_SECOND_OPTION,
            MenuOpcode.NPC_THIRD_OPTION,
            MenuOpcode.NPC_FOURTH_OPTION,
            MenuOpcode.NPC_FIFTH_OPTION
    );

    public void startUp()
    {
        getConfigTextField();
        getAllNPCS();
        Stream.of(client.getPlayers()).forEach(plugin.getNpcOtherPlayersList()::addAll);
    }

    public void shutDown()
    {
        plugin.getNpcHighlightingList().clear();
        configNameList.clear();
        configIdList.clear();
    }

    public void onFocusChanged(FocusChanged event)
    {
        if (!event.isFocused())
        {
            plugin.setNpcHotKeyPressed(false);
        }
    }

    public void onGameStateChanged(GameStateChanged event)
    {
        if (event.getGameState() != GameState.LOGGED_IN)
        {
            return;
        }
        plugin.getNpcHighlightingList().clear();
    }

    public void onMenuEntryAdded(MenuEntryAdded event)
    {
        int type = event.getOpcode();

        if (type >= MENU_ACTION_DEPRIORITIZE_OFFSET)
        {
            type -= MENU_ACTION_DEPRIORITIZE_OFFSET;
        }

        if (config.npcEnableHighlightingMenuItemForMarkedNPCS() &&
                NPC_MENU_ACTIONS.contains(MenuOpcode.of(type)) &&
                plugin.getNpcHighlightingList().stream().anyMatch(npc -> npc.getIndex() == event.getIdentifier()))
        {
            final String target = ColorUtil.prependColorTag(Text.removeTags(event.getTarget()), config.npcMenuItemColorForMarkedNPCS());
            event.setTarget(target);
            event.setModified();
        }
        else if (plugin.isNpcHotKeyPressed() && type == MenuOpcode.EXAMINE_NPC.getId())
        {
            // Add tag option
            client.insertMenuItem(
                    plugin.getNpcHighlightingList().stream().anyMatch(npc -> npc.getIndex() == event.getIdentifier()) ? UNTAG : TAG,
                    event.getTarget(),
                    MenuOpcode.RUNELITE.getId(),
                    event.getIdentifier(),
                    event.getParam0(),
                    event.getParam1(),
                    false
            );
        }
    }

    public void onMenuOptionClicked(MenuOptionClicked click)
    {
        if (click.getMenuOpcode() != MenuOpcode.RUNELITE ||
                !(click.getOption().equals(TAG) || click.getOption().equals(UNTAG)))
        {
            return;
        }

        final int id = click.getIdentifier();
        final NPC[] cachedNPCs = client.getCachedNPCs();
        final NPC npc = cachedNPCs[id];

        if (npc == null || npc.getName() == null)
        {
            return;
        }

        if (click.getOption().equals(TAG)) {
            plugin.getNpcHighlightingList().add(npc);
        } else {
            plugin.getNpcHighlightingList().remove(npc);
        }

        click.consume();
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
        plugin.getNpcHighlightingList().remove(npc);
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
            plugin.getNpcHighlightingList().clear();
            configNameList.clear();
            configIdList.clear();
            getConfigTextField();
            getAllNPCS();
        }
    }

    public void onPlayerSpawned(PlayerSpawned event)
    {
        if (plugin.getNpcOtherPlayersList().contains(event.getPlayer()))
        {
            return;
        }
        plugin.getNpcOtherPlayersList().add(event.getPlayer());
    }

    public void onPlayerDespawned(PlayerDespawned event)
    {
        plugin.getNpcOtherPlayersList().remove(event.getPlayer());
    }

    private void compareNPC(NPC npc)
    {
        if (npc == null)
        {
            return;
        }
        String npcName = npc.getName();
        if (npcName == null)
        {
            return;
        }
        npcName = npcName.toLowerCase().replace(" ", "");
        for (String s : configNameList) {
            if (npcName.contains(s))
            {
                if (!plugin.getNpcHighlightingList().contains(npc))
                {
                    plugin.getNpcHighlightingList().add(npc);
                }
            }
        }
        int npcId = npc.getId();
        for (Integer i : configIdList)
        {
            if (npcId == i)
            {
                if (!plugin.getNpcHighlightingList().contains(npc))
                {
                    plugin.getNpcHighlightingList().add(npc);
                }
            }
        }
    }

    private void getConfigTextField()
    {
        if (config.npcConfigTextString() == null || config.npcConfigTextString().isEmpty())
        {
            return;
        }
        String[] names = config.npcConfigTextString().toLowerCase().replace(" ", "").split(",");
        for (String string : names)
        {
            if (string == null)
            {
                return;
            }
            if (string.matches(".*\\d.*"))
            {
                getConfigNPCId(string);
            }
            else
            {
                getConfigNameList(string);
            }
        }
        System.out.println("Config NPC Names: " + configNameList);
        System.out.println("Config NPC IDs: " + configIdList);
    }

    private void getConfigNameList(String string)
    {
        if (string == null)
        {
            return;
        }
        string = string.toLowerCase();
        string = string.replaceAll(" ", "");
        if (!configNameList.contains(string))
        {
            configNameList.add(string);
        }
    }

    private void getConfigNPCId(String string)
    {
        if (string.isEmpty())
        {
            return;
        }
        string = string.toLowerCase();
        string = string.replaceAll(" ", "");
        string = string.replaceAll("\\D+", "");
        if (!configNameList.contains(string))
        {
            configIdList.add(Integer.parseInt(string));
        }
    }

    private void getAllNPCS()
    {
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
