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
package plugin.nomore.qolclicksbeta;

import com.google.inject.Provides;
import joptsimple.internal.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.queries.GameObjectQuery;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;
import plugin.nomore.qolclicksbeta.builds.BuiltNPC;
import plugin.nomore.qolclicksbeta.builds.BuiltObject;
import plugin.nomore.qolclicksbeta.highlighting.Arrow;

import javax.inject.Inject;

@Extension
@PluginDescriptor(
		name = "QOL Clicks (Beta)",
		description = "QOL fixes that should be implemented.",
		tags = {"nomore", "qol", "click"}
)
@Slf4j
public class QOLClicksBetaPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private QOLClicksBetaOverlay overlay;

	@Inject
	private ItemManager itemManager;

	@Inject
	private Arrow arrow;

	@Provides
	QOLClicksBetaConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(QOLClicksBetaConfig.class);
	}

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	BuiltNPC builtNPC = null;
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	BuiltObject builtObject = null;

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
	private void on(MenuOptionClicked e)
	{
		arrow.draw(e);

		debugMessage(e);
	}

	private void debugMessage(MenuOptionClicked e)
	{
		System.out.println("Option: " + e.getMenuOption() +
				"Target: " + e.getMenuOption() +
				"ID: " + e.getMenuOption() +
				"MenuAction: " + e.getMenuOption() +
				"ActionParam: " + e.getMenuOption() +
				"WidgetID: " + e.getMenuOption()
		);

		GameObject gameObject = new GameObjectQuery()
				.result(client)
				.stream()
				.filter(object -> object != null
						&& object.getId() == e.getId())
				.findFirst()
				.orElse(null);
		if (gameObject == null)
		{
			System.out.println("GameObject is null.");
			return;
		}
		System.out.println("GameObject is not null.");
		System.out.println(gameObject.getSceneMinLocation().getX() + ", " + gameObject.getSceneMinLocation().getY());
	}

}