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
package plugin.nomore.upordown;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;

import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.util.*;

@Extension
@PluginDescriptor(
		name = "Up or Down",
		description = "Highlights everything that goes up or down.",
		tags = {"ladder", "highlight", "tag3"}
)
@Slf4j
public class UpOrDownPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private UpOrDownOverlay overlay;

	public List<ElevationObject> elevationObjects = new ArrayList<>();

	private static final Set<String> ACTIONS_DOWN = ImmutableSet.of(
			"Climb-down", "Climb down"
	);
	private static final Set<String> ACTIONS_UP = ImmutableSet.of(
			"Climb-up", "Climb up"
	);

	@Provides
	UpOrDownConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(UpOrDownConfig.class);
	}

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	private void on(GameObjectSpawned e) { checkObject(e.getGameObject(), null, e.getTile()); }

	@Subscribe
	private void on(GameObjectChanged e) { checkObject(e.getGameObject(), e.getPrevious(), e.getTile()); }

	@Subscribe
	private void on(GameObjectDespawned e) { checkObject(null, e.getGameObject(), e.getTile()); }

	@Subscribe
	private void on(GameStateChanged e)
	{
		if (client.getGameState() == GameState.LOADING) {
			elevationObjects.clear();
		}
	}

	private void checkObject(GameObject newObj, GameObject oldObj, Tile tile)
	{
		if (oldObj != null)
		{
			ElevationObject eObjToRemove = createElevationObject(oldObj, tile);
			if (elevationObjects.contains(eObjToRemove))
			{
				assert eObjToRemove != null;
				System.out.println("Removing object: " + client.getObjectDefinition(eObjToRemove.getGameObject().getId()).getName());
				elevationObjects.remove(eObjToRemove);
			}
		}
		if (newObj == null)
		{
			return;
		}
		ElevationObject elevationObject = createElevationObject(newObj, tile);
		if (elevationObject == null)
		{
			return;
		}
		elevationObjects.add(elevationObject);
		//System.out.println(client.getObjectDefinition(elevationObject.getGameObject().getId()).getName() + ", " + elevationObject.getTile().getWorldLocation() + ", Up: " + elevationObject.isUp() + ", Down: " + elevationObject.isDown());
	}

	private ElevationObject createElevationObject(GameObject obj, Tile tile)
	{
		ObjectComposition def = client.getObjectDefinition(obj.getId());
		if (def == null)
		{
			return null;
		}
		ElevationObject elevationObject = null;
		for (String action : def.getActions())
		{
			if (ACTIONS_UP.contains(action) || ACTIONS_DOWN.contains(action))
			{
				elevationObject = ElevationObject.builder()
						.gameObject(obj)
						.tile(tile)
						.up(ACTIONS_UP.contains(action))
						.down(ACTIONS_DOWN.contains(action))
						.build();
			}
		}
		if (elevationObject == null)
		{
			return null;
		}
		if (elevationObject.isUp() || elevationObject.isDown())
		{
			return elevationObject;
		}
		return null;
	}

	public List<ElevationObject> getElevationObjects()
	{
		return elevationObjects;
	}
	
}