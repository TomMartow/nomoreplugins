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
package plugin.nomore.newplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Extension
@PluginDescriptor(
		name = "JSON",
		description = "JSON JSON JSON",
		tags = {"tag1", "tag2", "tag3"}
)
@Slf4j
public class JSONPlugin extends Plugin
{

	@Inject
	private Client client;

	Gson gson;
	List<NPCs> npcs = new ArrayList<>();

	@Provides
	JSONConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(JSONConfig.class);
	}

	@Override
	protected void startUp()
	{
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	@Override
	protected void shutDown()
	{
		gson = null;
	}

	@Subscribe
	private void on(NpcSpawned e)
	{
		NPC npc = e.getNpc();
		if (npc == null)
		{
			return;
		}

		NPCComposition def = client.getNpcDefinition(npc.getId());
		NPCs builtNPC = NPCs.builder().npc(npc).id(npc.getId()).name(def.getName()).build();
		if (builtNPC == null)
		{
			return;
		}
		npcs.add(builtNPC);
	}

	@Subscribe
	private void on(NpcDespawned e)
	{
		NPC npc = e.getNpc();
		if (npc == null)
		{
			return;
		}

		NPCComposition def = client.getNpcDefinition(npc.getId());
		NPCs builtNPC = NPCs.builder().npc(npc).id(npc.getId()).name(def.getName()).build();
		if (builtNPC == null)
		{
			return;
		}
		npcs.removeIf(npcs -> npcs.getNpc() == builtNPC.getNpc());
	}

}