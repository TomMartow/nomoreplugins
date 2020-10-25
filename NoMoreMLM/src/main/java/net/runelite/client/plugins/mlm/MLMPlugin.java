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
package net.runelite.client.plugins.mlm;

import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;

import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import net.runelite.client.plugins.nmutils.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Extension
@PluginDescriptor(
	name = "NoMore MLM",
	description = "Motherload Mine",
	tags = {"tag1", "tag2", "tag3"},
	type = PluginType.UTILITY
)
@Slf4j
@PluginDependency(Utils.class)
public class MLMPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private MLMConfig config;

	@Inject
	private MLMOverlay overlay;

	@Inject
	private Utils utils;

	@Provides
    MLMConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(MLMConfig.class);
	}

	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> lowerObjects = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> upperObjects = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> upperNorthWestVeins = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> upperNorthEastVeins = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> upperSouthVeins = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> lowerNorthVeins = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> lowerEastVeins = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> lowerSouthVeins = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<Tile, TileObject> lowerWestVeins = new HashMap<>();
	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	String currentPlayerArea = null;

	Set<Integer> OBJECT_IDS = Stream.of(ObjectsList.ORE_VEINS, ObjectsList.BANK_CHEST,  ObjectsList.EMPTY_SACK, ObjectsList.FIX_WATER_WHEEL, ObjectsList.LADDERS, ObjectsList.SHORTCUTS, ObjectsList.ROCK_FALL).flatMap(Set::stream).collect(Collectors.toSet());

	@Getter(AccessLevel.PACKAGE)
	int numberOfBrokenWaterWheels = 0;

	@Getter(AccessLevel.PACKAGE)
	TileObject NORTHERN_NORTH_ROCKFALL = null;
	WorldPoint NORTHERN_NORTH_ROCKFALL_WORLDPOINT = new WorldPoint(3731,5683,0);
	@Getter(AccessLevel.PACKAGE)
	TileObject NORTHERN_SOUTH_ROCKFALL = null;
	WorldPoint NORTHERN_SOUTH_ROCKFALL_WORLDPOINT = new WorldPoint(3733,5680,0);
	@Getter(AccessLevel.PACKAGE)
	TileObject EASTERN_ROCKFALL = null;
	WorldPoint EASTERN_ROCKFALL_WORLDPOINT = new WorldPoint(3766,5670,0);
	@Getter(AccessLevel.PACKAGE)
	TileObject SOUTHERN_WEST_ROCKFALL = null;
	WorldPoint SOUTHERN_WEST_ROCKFALL_WORLDPOINT = new WorldPoint(3762,5652,0);
	@Getter(AccessLevel.PACKAGE)
	TileObject SOUTHERN_CENTRE_ROCKFALL = null;
	WorldPoint SOUTHERN_CENTRE_ROCKFALL_WORLDPOINT = new WorldPoint(3766,5647,0);
	@Getter(AccessLevel.PACKAGE)
	TileObject SOUTHERN_SOUTH_ROCKFALL = null;
	WorldPoint SOUTHERN_SOUTH_ROCKFALL_WORLDPOINT = new WorldPoint(3766,5639,0);
	@Getter(AccessLevel.PACKAGE)
	TileObject WESTERN_ROCKFALL = null;
	WorldPoint WESTERN_ROCKFALL_WORLDPOINT = new WorldPoint(3728,5651,0);


	@Override
	protected void startUp() {
		numberOfBrokenWaterWheels = getAmountOfBrokenWaterWheels();
		overlayManager.add(overlay);
		setCurrentPlayerArea("centre");
	}

	@Override
	protected void shutDown() {
		upperObjects.clear();
		lowerObjects.clear();
		upperNorthEastVeins.clear();
		upperNorthWestVeins.clear();
		upperSouthVeins.clear();
		lowerNorthVeins.clear();
		lowerEastVeins.clear();
		lowerSouthVeins.clear();
		lowerWestVeins.clear();
		overlayManager.remove(overlay);
	}

	@Subscribe
	private void on(GameTick event)
	{
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return;
		}
		if (config.enableRockfallObjectIndicator())
		{
			// Centre player location.
			if (utils.isPlayerWithinArea(player, 3732, 5679, 3740, 5673, 0)
					|| utils.isPlayerWithinArea(player, 3757, 5654, 3761, 5651, 0)
					|| utils.isPlayerWithinArea(player, 3728, 5654, 3733, 5649, 0)
					|| utils.isPlayerWithinArea(player, 3757, 5671, 3759, 5668, 0))
			{
				setCurrentPlayerArea("centre");
			}
			// North
				// Between north rocks
				if (utils.isPlayerWithinArea(player, 3729, 5683, 3736, 5680, 0))
				{
					setCurrentPlayerArea("north-betweenrocks");
				}
				// Above north rocks
				if (utils.isPlayerWithinArea(player, 3724, 5688, 3733, 5684, 0))
				{
					setCurrentPlayerArea("north-northofrocks");
				}
			// West
				// West of rocks
				if (utils.isPlayerWithinArea(player, 3723, 5653, 3727, 5649, 0))
				{
					setCurrentPlayerArea("west-westofrocks");
				}
			// East
				// South of east rocks
				if (utils.isPlayerWithinArea(player, 3765, 5669, 3770, 5667,0))
				{
					setCurrentPlayerArea("east-southofrocks");
				}
				// North of east rocks
				if (utils.isPlayerWithinArea(player, 3764, 5673, 3768, 5671,0))
				{
					setCurrentPlayerArea("east-northofrocks");
				}
			// South
				// Between centre and west rock
				if (utils.isPlayerWithinArea(player, 3763, 5653, 3771, 5648,0))
				{
					setCurrentPlayerArea("south-betweencentreandwestrock");
				}
				// Between centre amd south rock
				if (utils.isPlayerWithinArea(player, 3763, 5646, 3768, 5640,0))
				{
					setCurrentPlayerArea("south-betweencentreandsouthrock");
				}
				// South of south rock (mining area)
				if (utils.isPlayerWithinArea(player, 3766, 5639, 3773, 5634,0))
				{
					setCurrentPlayerArea("south-southofsouthrocks");
				}
		}
	}

	private int getAmountOfBrokenWaterWheels()
	{
		if (client.getGameState() == GameState.LOGGED_IN) {
			return utils.getGameObjects(26670).size();
		}
		return 0;
	}

	@Subscribe
	private void on(ConfigChanged event)
	{
		if (!event.getGroup().equals("NoMoreMLM"))
		{
			return;
		}
	}

	@Subscribe
	private void on(GameStateChanged event)
	{
		if (event.getGameState() != GameState.LOGGED_IN)
		{
			upperObjects.clear();
			lowerObjects.clear();
			upperNorthEastVeins.clear();
			upperNorthWestVeins.clear();
			upperSouthVeins.clear();
			lowerNorthVeins.clear();
			lowerEastVeins.clear();
			lowerSouthVeins.clear();
			lowerWestVeins.clear();
		}
		numberOfBrokenWaterWheels = getAmountOfBrokenWaterWheels();
	}

	@Subscribe
	private void on(GameObjectSpawned event)
	{
		getTileObject(event.getTile(), event.getGameObject(), null);
		if (ObjectsList.FIX_WATER_WHEEL.contains(event.getGameObject().getId()))
		{
			numberOfBrokenWaterWheels = getAmountOfBrokenWaterWheels();
		}
	}

	@Subscribe
	private void on(GameObjectChanged event) {
		getTileObject(event.getTile(), event.getGameObject(), event.getPrevious());
		if (ObjectsList.FIX_WATER_WHEEL.contains(event.getGameObject().getId()))
		{
			numberOfBrokenWaterWheels = getAmountOfBrokenWaterWheels();
		}
	}

	@Subscribe
	private void on(GameObjectDespawned event)
	{
		getTileObject(event.getTile(),  null, event.getGameObject());
		if (ObjectsList.FIX_WATER_WHEEL.contains(event.getGameObject().getId()))
		{
			numberOfBrokenWaterWheels = getAmountOfBrokenWaterWheels();
		}
	}

	@Subscribe
	private void on(DecorativeObjectSpawned event)
	{
		getTileObject(event.getTile(), event.getDecorativeObject(), null);
	}

	@Subscribe
	private void on(DecorativeObjectChanged event) { getTileObject(event.getTile(), event.getDecorativeObject(), event.getPrevious()); }

	@Subscribe
	private void on(DecorativeObjectDespawned event)
	{
		getTileObject(event.getTile(), null, event.getDecorativeObject());
	}

	@Subscribe
	private void on(GroundObjectSpawned event)
	{
		getTileObject(event.getTile(), event.getGroundObject(), null);
	}

	@Subscribe
	private void on(GroundObjectChanged event) { getTileObject(event.getTile(), event.getGroundObject(), event.getPrevious()); }

	@Subscribe
	private void on(GroundObjectDespawned event)
	{
		getTileObject(event.getTile(), null, event.getGroundObject());
	}

	@Subscribe
	private void on(WallObjectSpawned event)
	{
		getTileObject(event.getTile(), event.getWallObject(), null);
	}

	@Subscribe
	private void on(WallObjectChanged event) { getTileObject(event.getTile(), event.getWallObject(), event.getPrevious()); }

	@Subscribe
	private void on(WallObjectDespawned event)
	{
		getTileObject(event.getTile(), null, event.getWallObject());
	}

	private void getTileObject(Tile tile, TileObject newObject, TileObject oldObject)
	{
		upperObjects.remove(tile);
		lowerObjects.remove(tile);
		upperNorthEastVeins.remove(tile);
		upperNorthWestVeins.remove(tile);
		upperSouthVeins.remove(tile);
		lowerNorthVeins.remove(tile);
		lowerEastVeins.remove(tile);
		lowerSouthVeins.remove(tile);
		lowerWestVeins.remove(tile);
		removeRockfall(tile);
		if (newObject == null)
		{
			return;
		}
		if (OBJECT_IDS.contains(newObject.getId()))
		{
			LocalPoint lp = newObject.getLocalLocation();
			if (lp == null)
			{
				return;
			}
			if (isUpstairs(lp))
			{
				if (ObjectsList.ORE_VEINS.contains(newObject.getId()))
				{
					checkOreVeinLocation(tile, newObject);
				}
				upperObjects.putIfAbsent(tile, newObject);
			}
			else
			{
				if (ObjectsList.ROCK_FALL.contains(newObject.getId()))
				{
					addRockfall(tile, newObject);
					return;
				}
				if (ObjectsList.ORE_VEINS.contains(newObject.getId()))
				{
					checkOreVeinLocation(tile, newObject);
					return;
				}
				lowerObjects.putIfAbsent(tile, newObject);
			}
		}
	}

	private void removeRockfall(Tile tile)
	{
		if (tile.getWorldLocation().equals(NORTHERN_NORTH_ROCKFALL_WORLDPOINT))
		{
			NORTHERN_NORTH_ROCKFALL = null;
		}
		if (tile.getWorldLocation().equals(NORTHERN_SOUTH_ROCKFALL_WORLDPOINT))
		{
			NORTHERN_SOUTH_ROCKFALL = null;
		}
		if (tile.getWorldLocation().equals(EASTERN_ROCKFALL_WORLDPOINT))
		{
			EASTERN_ROCKFALL = null;
		}
		if (tile.getWorldLocation().equals(SOUTHERN_WEST_ROCKFALL_WORLDPOINT))
		{
			SOUTHERN_WEST_ROCKFALL = null;
		}
		if (tile.getWorldLocation().equals(SOUTHERN_CENTRE_ROCKFALL_WORLDPOINT))
		{
			SOUTHERN_CENTRE_ROCKFALL = null;
		}
		if (tile.getWorldLocation().equals(SOUTHERN_SOUTH_ROCKFALL_WORLDPOINT))
		{
			SOUTHERN_SOUTH_ROCKFALL = null;
		}
		if (tile.getWorldLocation().equals(WESTERN_ROCKFALL_WORLDPOINT))
		{
			WESTERN_ROCKFALL = null;
		}
	}

	private void addRockfall(Tile tile, TileObject tileObject)
	{
		if (tile.getWorldLocation().equals(NORTHERN_NORTH_ROCKFALL_WORLDPOINT))
		{
			NORTHERN_NORTH_ROCKFALL = tileObject;
		}
		if (tile.getWorldLocation().equals(NORTHERN_SOUTH_ROCKFALL_WORLDPOINT))
		{
			NORTHERN_SOUTH_ROCKFALL = tileObject;
		}
		if (tile.getWorldLocation().equals(EASTERN_ROCKFALL_WORLDPOINT))
		{
			EASTERN_ROCKFALL = tileObject;
		}
		if (tile.getWorldLocation().equals(SOUTHERN_WEST_ROCKFALL_WORLDPOINT))
		{
			SOUTHERN_WEST_ROCKFALL = tileObject;
		}
		if (tile.getWorldLocation().equals(SOUTHERN_CENTRE_ROCKFALL_WORLDPOINT))
		{
			SOUTHERN_CENTRE_ROCKFALL = tileObject;
		}
		if (tile.getWorldLocation().equals(SOUTHERN_SOUTH_ROCKFALL_WORLDPOINT))
		{
			SOUTHERN_SOUTH_ROCKFALL = tileObject;
		}
		if (tile.getWorldLocation().equals(WESTERN_ROCKFALL_WORLDPOINT))
		{
			WESTERN_ROCKFALL = tileObject;
		}
	}

	private void checkOreVeinLocation(Tile tile , TileObject tileObject)
	{
		WorldPoint wp = tileObject.getWorldLocation();
		if (wp == null)
		{
			return;
		}
		if (utils.isTileObjectWithinArea(tileObject, 3747, 5684, 3754, 5676, 0))
		{
			upperNorthWestVeins.put(tile, tileObject);
		}
		if (utils.isTileObjectWithinArea(tileObject, 3755, 5685, 3765, 5675, 0))
		{
			upperNorthEastVeins.put(tile, tileObject);
		}
		if (utils.isTileObjectWithinArea(tileObject, 3756, 5674, 3762, 5668, 0))
		{
			upperSouthVeins.put(tile, tileObject);
		}
		if (utils.isTileObjectWithinArea(tileObject, 3731, 5693, 3744, 5686, 0))
		{
			lowerNorthVeins.put(tile, tileObject);
		}
		if (utils.isTileObjectWithinArea(tileObject, 3764, 5668, 3773, 5657, 0))
		{
			lowerEastVeins.put(tile, tileObject);
		}
		if (utils.isTileObjectWithinArea(tileObject, 3765, 5643, 3774, 5634, 0))
		{
			lowerSouthVeins.put(tile, tileObject);
		}
		if (utils.isTileObjectWithinArea(tileObject, 3714, 5662, 3723, 5647, 0))
		{
			lowerWestVeins.put(tile, tileObject);
		}
		return;
	}

	boolean isPlayerUpstairs()
	{
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return false;
		}
		LocalPoint lp = player.getLocalLocation();
		if (lp == null)
		{
			return false;
		}
		return isUpstairs(lp);
	}

	boolean isUpstairs(LocalPoint localPoint)
	{
		return Perspective.getTileHeight(client, localPoint, 0) < -490;
	}
}