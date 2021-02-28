/*
 * Copyright (c) 2019-2020, ganom <https://github.com/Ganom>
 * All rights reserved.
 * Licensed under GPL3, see LICENSE for the full scope.
 */
package plugin.nomore.nmputils;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Extension
@PluginDescriptor(
		name = "NMPUtils",
		description = "Plugin required for numerous plugins created by NoMore."
)
@Slf4j
@SuppressWarnings("unused")
@Singleton
public class NMPUtils extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private NMPConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ConfigManager configManager;

	@Provides
	NMPConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(NMPConfig.class);
	}

	@Override
	protected void startUp()
	{
	}

	@Override
	protected void shutDown()
	{
	}

	//███╗   ██╗██████╗  ██████╗
	//████╗  ██║██╔══██╗██╔════╝
	//██╔██╗ ██║██████╔╝██║
	//██║╚██╗██║██╔═══╝ ██║
	//██║ ╚████║██║     ╚██████╗
	//╚═╝  ╚═══╝╚═╝      ╚═════╝

	public static final List<NPC> npcList = new ArrayList<>();

	public List<NPC> getNpcs()
	{
		return npcList;
	}

	@Subscribe
	private void on(NpcSpawned event)
	{
		npcList.add(event.getNpc());
	}

	@Subscribe
	private void on(NpcDespawned event)
	{
		npcList.remove(event.getNpc());
	}



}
