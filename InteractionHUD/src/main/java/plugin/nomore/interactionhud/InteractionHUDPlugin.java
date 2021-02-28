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
package plugin.nomore.interactionhud;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuOpcode;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.queries.NPCQuery;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;
import plugin.nomore.interactionhud.builds.BuiltNPC;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.util.Set;

@Extension
@PluginDescriptor(
		name = "Interaction HUD",
		description = "Display's a marker over what you are interacting with.",
		tags = {"nomore", "interaction", "hud"},
		type = PluginType.UTILITY
)
@Slf4j
public class InteractionHUDPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private InteractionHUDOverlay overlay;

	@Inject
	private ItemManager itemManager;

	@Provides
	InteractionHUDConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(InteractionHUDConfig.class);
	}

	Set<MenuOpcode> npcMenuOpcodes = ImmutableSet.of(
			MenuOpcode.NPC_FIRST_OPTION,
			MenuOpcode.NPC_SECOND_OPTION,
			MenuOpcode.NPC_THIRD_OPTION,
			MenuOpcode.NPC_FOURTH_OPTION,
			MenuOpcode.NPC_FIFTH_OPTION,
			MenuOpcode.EXAMINE_NPC
	);

	Set<String> fishingOptions = ImmutableSet.of(
			"Use-rod",
			"Cage",
			"Lure"
	);

	@Getter(AccessLevel.PACKAGE)@Setter(AccessLevel.PACKAGE)
	BuiltNPC builtNPC = null;

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
		log.info("Plugin started.");
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		log.info("Plugin finished.");
	}

	@Subscribe
	private void on(MenuOptionClicked event)
	{
		if (npcMenuOpcodes.contains(event.getMenuOpcode()))
		{
			if (fishingOptions.contains(event.getOption()))
			{
				setBuiltNPC(BuiltNPC.builder()
						.npc(getNpc(event))
						.systemTime(System.currentTimeMillis())
						.build());
			}
		}
	}

	private NPC getNpc(MenuOptionClicked event)
	{
		return new NPCQuery()
				.result(client)
				.stream()
				.filter(npc1 -> npc1 != null
						&& npc1.getIndex() == event.getIdentifier())
				.findFirst()
				.orElse(null);
	}

	public BufferedImage getImage(int id)
	{
		return itemManager.getImage(id);
	}

}