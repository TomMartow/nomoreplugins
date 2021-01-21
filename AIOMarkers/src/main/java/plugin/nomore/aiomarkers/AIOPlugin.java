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
package plugin.nomore.aiomarkers;

import com.google.inject.Provides;

import javax.inject.Inject;

import plugin.nomore.aiomarkers.npc.NPCHighlightingOverlay;
import plugin.nomore.aiomarkers.npc.NPCMethods;
import plugin.nomore.aiomarkers.object.ObjectHighlightingOverlay;
import plugin.nomore.aiomarkers.object.ObjectMethods;
import plugin.nomore.nmputils.NMPUtils;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;

import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;
import plugin.nomore.nmputils.api.StringAPI;

@Extension
@PluginDescriptor(
		name = "AIO Markers",
		description = "An AIO Marker plugin for Player's, NPC's, Game Objects, Inventory Items and Ground Items.",
		tags = {"marker", "indicator", "overlay"},
		type = PluginType.UTILITY
)
@Slf4j
@PluginDependency(NMPUtils.class)
public class AIOPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private AIOConfig config;

	@Inject
	private NMPUtils utils;

	@Inject
	private StringAPI stringAPI;

	@Inject
	private KeyManager keyManager;

	@Inject
	private NPCHighlightingOverlay npcHighlightingOverlay;

	@Inject
	private ObjectHighlightingOverlay objectHighlightingOverlay;

	@Inject
	private KeyboardListener keyboardListener;

	@Inject
	private NPCMethods npcMethods;

	@Inject
	private ObjectMethods objectMethods;

	@Provides
    AIOConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AIOConfig.class);
	}

	@Override
	protected void startUp()
	{
		keyManager.registerKeyListener(keyboardListener);
		// NPC Highlighting
		overlayManager.add(npcHighlightingOverlay);
		npcMethods.startUp();
		// Object Markers
		overlayManager.add(objectHighlightingOverlay);
		objectMethods.startUp();
	}

	@Override
	protected void shutDown()
	{
		keyManager.unregisterKeyListener(keyboardListener);
		// NPC Highlighting
		overlayManager.remove(npcHighlightingOverlay);
		npcMethods.shutDown();
		// Object Markers
		overlayManager.remove(objectHighlightingOverlay);
		objectMethods.shutDown();
	}

	@Subscribe
	private void on(GameTick event)
	{
	}
	/*
	@Subscribe
	private void onFocusChanged(FocusChanged event) { npcMethods.onFocusChanged(event); }

	@Subscribe
	private void on(GameStateChanged event)
	{
		npcMethods.onGameStateChanged(event);
	}

	 */

	@Subscribe
	private void on(ConfigChanged event)
	{
		npcMethods.onConfigChanged(event);
		objectMethods.onConfigChanged(event);
	}

	@Subscribe
	private void on(NpcSpawned event) { npcMethods.onNPCSpawned(event); }

	@Subscribe
	private void on(NpcDespawned event)
	{
		npcMethods.onNPCDespawned(event);
	}

	@Subscribe
	private void on(PlayerSpawned event)
	{
		npcMethods.onPlayerSpawned(event);
	}

	@Subscribe
	private void on(PlayerDespawned event)
	{
		npcMethods.onPlayerDespawned(event);
	}

	@Subscribe
	private void on(GameObjectSpawned event) { objectMethods.onGameObjectSpawned(event); }

	@Subscribe
	private void on(GameObjectChanged event) { objectMethods.onGameObjectChanged(event); }

	@Subscribe
	private void on(GameObjectDespawned event) { objectMethods.onGameObjectDespawned(event); }

	@Subscribe
	private void on(GroundObjectSpawned event) { objectMethods.onGroundObjectSpawned(event); }

	@Subscribe
	private void on(GroundObjectChanged event) { objectMethods.onGroundObjectChanged(event); }

	@Subscribe
	private void on(GroundObjectDespawned event) { objectMethods.onGroundObjectDespawned(event); }

	@Subscribe
	private void on(DecorativeObjectSpawned event) { objectMethods.onDecorativeObjectSpawned(event); }

	@Subscribe
	private void on(DecorativeObjectChanged event) { objectMethods.onDecorativeObjectChanged(event); }

	@Subscribe
	private void on(DecorativeObjectDespawned event) { objectMethods.onDecorativeObjectDespawned(event); }

	@Subscribe
	private void on(WallObjectSpawned event) { objectMethods.onWallObjectSpawned(event); }

	@Subscribe
	private void on(WallObjectChanged event) { objectMethods.onWallObjectChanged(event); }

	@Subscribe
	private void on(WallObjectDespawned event) { objectMethods.onWallObjectDespawned(event); }

	/*

	@Subscribe
	private void onMenuEntryAdded(MenuEntryAdded event)
	{
		npcMethods.onMenuEntryAdded(event);
	}

	@Subscribe
	private void onMenuOptionClicked(MenuOptionClicked event)
	{
		npcMethods.onMenuOptionClicked(event);
	}

	 */

}
