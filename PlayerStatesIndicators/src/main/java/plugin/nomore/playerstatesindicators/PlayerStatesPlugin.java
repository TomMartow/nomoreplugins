/*
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
package plugin.nomore.playerstatesindicators;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import java.time.LocalDateTime;

@Extension
@PluginDescriptor(
		name = "Player States Indicators",
		description = "Displays indicators based on the players current state and skill levels.",
		tags = {"idle", "skills", "state", "nomore"}
)
@Slf4j
public class PlayerStatesPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private PlayerStatesConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private PlayerStatesOverlay overlay;

	@Inject
	private ItemManager itemManager;

	@Provides
	PlayerStatesConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(PlayerStatesConfig.class);
	}

	// Idle
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	LocalDateTime timeLastIdle = null;
	int tickToggle = 0;
	WorldPoint lastPosition = null;
	WorldPoint currentPosition = null;
	boolean movementCheck = false;

	//Energy
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	boolean specLow;
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	boolean runEnergyLow;

	//Skills
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	boolean hpLow;
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	boolean prayerLow;
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	boolean attackLow;
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	boolean strengthLow;
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	boolean defenceLow;
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	boolean rangeLow;
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	boolean magicLow;

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
	private void on(GameTick e)
	{
		Player player = client.getLocalPlayer();
		if (player == null)
		{
			return;
		}
		idle(player);
		skill();
		energy();
	}

	@Subscribe
	private void on(AnimationChanged e)
	{
		if (e.getActor() != client.getLocalPlayer())
		{
			return;
		}
		if (e.getActor().getAnimation() == AnimationID.IDLE)
		{
			timeLastIdle = LocalDateTime.now();
		}
		else
		{
			timeLastIdle = null;
		}
	}

	private void idle(Player player)
	{
		if (player.getAnimation() != AnimationID.IDLE)
		{
			return;
		}
		if (tickToggle == 0)
		{
			currentPosition = player.getWorldLocation();
			tickToggle++;
		}
		else if (tickToggle == 1)
		{
			lastPosition = player.getWorldLocation();
			if (currentPosition.getX() == lastPosition.getX()
					&& currentPosition.getY() == lastPosition.getY()
					&& currentPosition.getPlane() == lastPosition.getPlane())
			{
				if (!movementCheck)
				{
					movementCheck = true;
					timeLastIdle = LocalDateTime.now();
				}
			}
			else
			{
				log.info("not idle: " + lastPosition + ", " + currentPosition);
				movementCheck = false;
				timeLastIdle = null;
				currentPosition = null;
				lastPosition = null;
			}
			tickToggle--;
		}
	}

	private void skill()
	{
		setHpLow(client.getBoostedSkillLevel(Skill.HITPOINTS) <= config.hpLevel());
		setAttackLow(client.getBoostedSkillLevel(Skill.ATTACK) <= config.attackLevel());
		setStrengthLow(client.getBoostedSkillLevel(Skill.STRENGTH) <= config.strengthLevel());
		setDefenceLow(client.getBoostedSkillLevel(Skill.DEFENCE) <= config.defenceLevel());
		setRangeLow(client.getBoostedSkillLevel(Skill.RANGED) <= config.rangeLevel());
		setPrayerLow(client.getBoostedSkillLevel(Skill.PRAYER) <= config.prayerLevel());
		setMagicLow(client.getBoostedSkillLevel(Skill.MAGIC) <= config.magicLevel());
	}

	private void energy()
	{
		setRunEnergyLow(client.getEnergy() <= config.runEnergyLevel());
		setSpecLow(client.getVar(VarPlayer.SPECIAL_ATTACK_PERCENT) <= config.specialLevel() * 10);
	}

}
