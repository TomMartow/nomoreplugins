/*
 * Copyright (c) 2019-2020, ganom <https://github.com/Ganom>
 * All rights reserved.
 * Licensed under GPL3, see LICENSE for the full scope.
 */
package plugin.nomore.nmputils;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import javax.inject.Inject;
import javax.inject.Singleton;

@Extension
@PluginDescriptor(
		name = "NMPUtils",
		description = "Plugin required for numerous plugins created by NoMore.",
		type = PluginType.UTILITY
		//hidden = true
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

}
