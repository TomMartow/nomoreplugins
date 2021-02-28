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
package plugin.nomore.statrandomiser;

import com.google.inject.Provides;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;

import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import java.util.*;

@Extension
@PluginDescriptor(
	name = "Stat Randomiser",
	description = "Randomises the players skill levels (client side only).",
	tags = {"stat", "level", "nomore"}
)
@Slf4j
public class ThePlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private TheConfig config;

	@Provides
    TheConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TheConfig.class);
	}

	@Override
	protected void startUp()
	{
		scrambleStats();
	}

	@Override
	protected void shutDown()
	{
	}

	@Subscribe
	private void on(GameTick event)
	{
		scrambleStats();
	}

	private void scrambleStats()
	{
		for (int i = 1; i < 24; i++)
		{
			Widget skillWidget = client.getWidget(320, i);
			if (skillWidget == null)
			{
				continue;
			}
			int skillLevel = Integer.parseInt(client.getWidget(320, i).getChild(4).getText());
			if (skillLevel <= 9)
			{
				skillWidget.getChild(3).setText(String.valueOf(randomNumber(0,9)));
				skillWidget.getChild(4).setText(String.valueOf(randomNumber(0,9)));
			}
			if (skillLevel <= 99 && skillLevel >= 10)
			{
				skillWidget.getChild(3).setText(Integer.toString(skillLevel).substring(0,1) + "" + randomNumber(0,9));
				skillWidget.getChild(4).setText(Integer.toString(skillLevel).substring(0,1) + "" + randomNumber(0,9));
			}
		}
		Widget total = client.getWidget(320, 27);
		if (total == null)
		{
			return;
		}
		int totalLevel = Integer.parseInt(total.getText().replaceAll("\\D+",""));
		if (totalLevel <= 99)
		{
			total.setText("Total Level: " + Integer.toString(totalLevel).substring(0, 1) + "" + randomNumber(0, 9));
		}
		if (totalLevel >= 100 && totalLevel <= 999)
		{
			total.setText("Total Level: " + Integer.toString(totalLevel).substring(0, 1) + "" + randomNumber(0,9) + "" + randomNumber(0,9));
		}
		if (totalLevel >= 1000 && totalLevel <= 9999)
		{
			total.setText("Total Level: " + Integer.toString(totalLevel).substring(0, 1) + "" + randomNumber(0,9) + "" + randomNumber(0,9) + "" + randomNumber(0,9));
		}
	}

	private int randomNumber(int min, int max)
	{
		return new Random().nextInt((max - min) + 1) + min;
	}
}
