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
package plugin.nomore.nomorewintertodt;

import com.google.inject.Provides;

import java.util.*;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;

import net.runelite.api.events.*;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import static net.runelite.api.ObjectID.*;
import static net.runelite.api.ObjectID.BRAZIER_29313;

@Extension
@PluginDescriptor(
	name = "NoMore Wintertodt",
	description = "Displays markers over Wintertodt related objects and npcs.",
	tags = {"wintertodt", "ahk", "nomoreahk"}
)
@Slf4j
public class NoMoreWintertodtPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private NoMoreWintertodtConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private NoMoreWintertodtOverlay overlay;

	@Inject
	private Notifier notifier;

	@Getter(AccessLevel.PACKAGE)
	private final List<NPC> pyromancerNPC = new ArrayList<>();
	@Getter(AccessLevel.PACKAGE)
	private final List<TileObject> objects = new ArrayList<>();

	@Getter(AccessLevel.PACKAGE)
	private boolean minigameActive = false;

	private final static int pyromancerID = 7371;
	private final static List<Integer> wintertodtObjectIDS = Arrays.asList(BRUMA_ROOTS, BURNING_BRAZIER_29314, BRAZIER_29312, BRAZIER_29313);

	@Provides
	NoMoreWintertodtConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NoMoreWintertodtConfig.class);
	}

	@Override
	protected void startUp() {
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() {
		overlayManager.remove(overlay);
	}

	@Subscribe
	private void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() != GameState.LOGGED_IN)
		{
			objects.clear();
		}
	}

	@Subscribe
	void onVarbitChanged(VarbitChanged varbitChanged)
	{
		minigameActive = client.getVar(Varbits.WINTERTODT_TIMER) <= 0;
	}

	@Subscribe
	private void onGameObjectSpawned(GameObjectSpawned event)
	{
		onTileObject(event.getTile(), null, event.getGameObject());
	}

	@Subscribe
	private void onGameObjectChanged(GameObjectChanged event)
	{
		onTileObject(event.getTile(), event.getPrevious(), event.getGameObject());
	}

	@Subscribe
	private void onGameObjectDespawned(GameObjectDespawned event)
	{
		onTileObject(event.getTile(), event.getGameObject(), null);
	}

	@Subscribe
	private void onNpcSpawned(NpcSpawned event)
	{
		NPC npc = event.getNpc();
		if (npc.getId() == pyromancerID)
		{
			pyromancerNPC.add(npc);
		}
	}

	@Subscribe
	private void onNpcDespawned(NpcDespawned event)
	{
		NPC npc = event.getNpc();
		if (npc.getId() == pyromancerID)
		{
			pyromancerNPC.remove(npc);
		}
	}

	private void onTileObject(Tile tile, TileObject oldObject, TileObject newObject)
	{
		objects.remove(oldObject);
		if (newObject == null)
		{
			return;
		}
		if (wintertodtObjectIDS.contains(newObject.getId()))
		{
			objects.add(newObject);
		}
	}
}
