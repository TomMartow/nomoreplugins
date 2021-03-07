/*
 * Copyright (c) 2018, James Swindle <wilingua@gmail.com>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package plugin.nomore.npchighlightingextended;

import com.google.common.base.Strings;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.queries.NPCQuery;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;
import plugin.nomore.npchighlightingextended.builder.ConfigObject;
import plugin.nomore.npchighlightingextended.builder.HighlightingObject;
import plugin.nomore.npchighlightingextended.utils.StringFormat;

import javax.inject.Inject;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Extension
@PluginDescriptor(
		name = "Ex: NPC Highlighting",
		description = "NPC Highlighting, but with more options.",
		tags = {"npc", "highlighting", "nomore"}
)
@Slf4j
public class NPCHighlightingExtendedPlugin extends Plugin implements KeyListener
{

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private KeyManager keyManager;

	@Inject
	private NPCHighlightingExtendedConfig config;

	@Inject
	private NPCHighlightingExtendedOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private StringFormat stringFormat;

	@Provides
	NPCHighlightingExtendedConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NPCHighlightingExtendedConfig.class);
	}

	private static final List<HighlightingObject> npcsToHighlight = new ArrayList<>();
	private final List<ConfigObject> configObjects = new ArrayList<>();

	private static final String UNMARK = "Un-tag";
	private static final String MARK = "Tag";
	private boolean isShiftKeyPressed = false;

	@Override
	protected void startUp()
	{
		keyManager.registerKeyListener(this);
		overlayManager.add(overlay);
		getConfigTextField();
		if (client.getLocalPlayer() == null)
		{
			return;
		}
		clientThread.invoke(() ->
		{
			getAllNpcs();
			getAllPlayers();
		});
	}

	@Override
	protected void shutDown()
	{
		keyManager.unregisterKeyListener(this);
		overlayManager.remove(overlay);
		npcsToHighlight.clear();
	}

	@Subscribe
	private void on(MenuOpened e)
	{
		if (!isShiftKeyPressed)
		{
			client.setMenuEntries(e.getMenuEntries());
			return;
		}

		System.out.println("Hotkey pressed.");
		MenuEntry objectEntry = Arrays.stream(e.getMenuEntries()).filter(entry -> entry.getMenuAction() == MenuAction.EXAMINE_NPC).findFirst().orElse(null);

		if (objectEntry == null)
		{
			return;
		}

		final NPC npc = findNpc(objectEntry.getIdentifier());

		if (npc == null)
		{
			return;
		}

		MenuEntry[] menuEntries = e.getMenuEntries();
		menuEntries = Arrays.copyOf(menuEntries, menuEntries.length + 1);
		MenuEntry menuEntry = menuEntries[2] = new MenuEntry();

		menuEntry.setOption(getNpcsToHighlight().stream().anyMatch(o -> o.getNpc() == npc) ? UNMARK : MARK);
		menuEntry.setTarget(objectEntry.getTarget());
		menuEntry.setParam0(objectEntry.getParam0());
		menuEntry.setParam1(objectEntry.getParam1());
		menuEntry.setIdentifier(objectEntry.getIdentifier());
		menuEntry.setOpcode(objectEntry.getMenuAction().getId());
		client.setMenuEntries(menuEntries);
	}

	@Subscribe
	private void on(MenuOptionClicked e)
	{
		if (e.getMenuOption().equalsIgnoreCase(MARK))
		{
			final NPC npc = findNpc(e.getId());

			if (npc == null)
			{
				return;
			}

			compareNPC(npc, true);
			e.consume();
		}
		if (e.getMenuOption().equalsIgnoreCase(UNMARK))
		{
			final NPC npc = findNpc(e.getId());

			if (npc == null)
			{
				return;
			}

			npcsToHighlight.removeIf(hObj -> hObj.getNpc() == npc);
			e.consume();
		}
	}

	private NPC findNpc(int index)
	{
		return new NPCQuery()
				.result(client)
				.stream()
				.filter(npc -> npc != null && npc.getIndex() == index)
				.findFirst()
				.orElse(null);
	}

	@Subscribe
	private void on(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOADING)
		{
			npcsToHighlight.clear();
			getConfigTextField();
		}
	}

	@Subscribe
	private void on(NpcSpawned event)
	{
		NPC npc = event.getNpc();
		if (npc == null)
		{
			return;
		}
		compareNPC(npc, false);
	}

	@Subscribe
	private void on(NpcDespawned event)
	{
		NPC npc = event.getNpc();
		if (npc == null)
		{
			return;
		}
		npcsToHighlight.removeIf(HighlightingObject -> HighlightingObject.getNpc() == createHighlightingObject(npc).getNpc());
	}

	private void getAllNpcs()
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
			compareNPC(npc, false);
		}
	}

	private void compareNPC(NPC npc, boolean forceTag)
	{
		HighlightingObject highlightingObject = createHighlightingObject(npc);
		if (highlightingObject == null)
		{
			return;
		}
		if (forceTag)
		{
			npcsToHighlight.add(highlightingObject);
		}
		String npcName = highlightingObject.getName();
		int npcId = npc.getId();
		for (ConfigObject configObject : configObjects)
		{
			if (!Strings.isNullOrEmpty(configObject.getName()))
			{
				if (configObject.getName()
						.equalsIgnoreCase(stringFormat.rws(npcName))
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
		NPCComposition cDef = client.getNpcDefinition(npc.getId());
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
		String configTextString = stringFormat.rws(config.npcConfigTextString());
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
				if (stringFormat.containsNumbers(colonSplit[0]))
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
				if (stringFormat.containsNumbers(fakeSplit[0]))
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
		String charRemoved = stringFormat.removeCharactersFromString(stringNum);
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
		Color actualConfigColor;
		try
		{
			actualConfigColor = Color.decode("#" + configNpcColor);
		}
		catch (NumberFormatException nfe)
		{
			actualConfigColor = config.npcDefaultHighlightColor();
		}
		ConfigObject configObject = ConfigObject.builder()
				.name(configNpcName)
				.id(configNpcId)
				.color(actualConfigColor)
				.build();
		configObjects.add(configObject);
	}

	@Subscribe
	private void on(ConfigChanged event)
	{
		if (!event.getGroup().equals("npchighlightingextended"))
		{
			return;
		}
		if (event.getKey().equals("configNPCTextField"))
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

	private void getAllPlayers()
	{
		if (client.getLocalPlayer() == null)
		{
			return;
		}
		assert client.isClientThread();

		List<Player> players = client.getPlayers();
		for (Player player : players)
		{
			if (player == null)
			{
				continue;
			}
			otherPlayersList.add(player);
		}
	}

	@Subscribe
	private void on(PlayerSpawned event)
	{
		if (event.getPlayer() == null)
		{
			return;
		}
		otherPlayersList.add(event.getPlayer());
	}

	@Subscribe
	private void on(PlayerDespawned event)
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

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 16)
		{
			isShiftKeyPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 16)
		{
			isShiftKeyPressed = false;
		}
	}

}