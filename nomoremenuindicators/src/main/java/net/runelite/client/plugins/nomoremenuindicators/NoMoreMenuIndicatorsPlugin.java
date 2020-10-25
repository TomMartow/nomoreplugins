package net.runelite.client.plugins.nomoremenuindicators;

import com.google.inject.Provides;

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
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import java.util.HashMap;
import java.util.Map;

@Extension
@PluginDescriptor(
	name = "NoMore Menu Indicators",
	description = "Adds Indicators to Menu Buttons.",
	tags = {"newplugin", "nomoreahk"},
	type = PluginType.UTILITY
)
@Slf4j
public class NoMoreMenuIndicatorsPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private NoMoreMenuIndicatorsConfig config;

	@Inject
	private NoMoreMenuIndicatorsOverlay overlay;

	@Inject
	private Notifier notifier;

	@Provides
	NoMoreMenuIndicatorsConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(NoMoreMenuIndicatorsConfig.class);
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
	private void onGameStateChanged(GameStateChanged event) {
	}

	@Subscribe
	private void onGameObjectSpawned(GameObjectSpawned event) {
		onTileObject(event.getTile(), null, event.getGameObject());
	}

	@Subscribe
	private void onGameObjectChanged(GameObjectChanged event) {
		onTileObject(event.getTile(), event.getPrevious(), event.getGameObject());
	}

	@Subscribe
	private void onGameObjectDespawned(GameObjectDespawned event) {
		onTileObject(event.getTile(), event.getGameObject(), null);
	}

	private void onTileObject(Tile tile, TileObject oldObject, TileObject newObject) {

		if (newObject == null) {
			return;
		}

	}
}
