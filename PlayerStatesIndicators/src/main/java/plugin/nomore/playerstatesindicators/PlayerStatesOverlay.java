/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Cas <https://github.com/casvandongen>
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

import java.awt.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.Graphics2D;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import net.runelite.client.ui.overlay.*;
import net.runelite.api.Client;
import plugin.nomore.playerstatesindicators.utils.Utils;

@Singleton
class PlayerStatesOverlay extends Overlay
{

	@Inject
	private Client client;

	@Inject
	private PlayerStatesPlugin plugin;

	@Inject
	private PlayerStatesConfig config;

	@Inject
	private Utils utils;

	@Inject
	private PlayerStatesOverlay(final Client client, final PlayerStatesConfig config, final PlayerStatesPlugin plugin)
	{
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.LOW);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		idle(graphics);
		skills(graphics);
		energy(graphics);
		return null;
	}

	private void idle(Graphics2D graphics)
	{
		if (config.enableIdle()
				&& plugin.getTimeLastIdle() != null
				&& LocalDateTime.now().isAfter(plugin.getTimeLastIdle().plus(config.idleDelayTime(), ChronoUnit.MILLIS)))
		{
			utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.idleLocation()), config.idleColor());
		}
	}

	private void skills(Graphics2D graphics)
	{
		if (config.enableHP()
				&& plugin.isHpLow())
		{
			utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.hpLevelLocation()), config.hpLevelColor());
		}
		if (config.enableAttack()
				&& plugin.isAttackLow())
		{
			utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.attackLevelLocation()), config.attackLevelColor());
		}
		if (config.enableStrength()
				&& plugin.isStrengthLow())
		{
			utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.strengthLevelLocation()), config.strengthLevelColor());
		}
		if (config.enableDefence()
				&& plugin.isDefenceLow())
		{
			utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.defenceLevelLocation()), config.defenceLevelColor());
		}
		if (config.enableRange()
				&& plugin.isRangeLow())
		{
			utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.rangeLevelLocation()), config.rangeLevelColor());
		}
		if (config.enablePrayer()
				&& plugin.isPrayerLow())
		{
			utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.prayerLevelLocation()), config.prayerLevelColor());
		}
		if (config.enableMagic()
				&& plugin.isMagicLow())
		{
			utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.magicLevelLocation()), config.magicLevelColor());
		}
	}

	private void energy(Graphics2D graphics)
	{
		if (config.enableSpecial()
				&& plugin.isSpecLow())
		{
			utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.specialLevelLocation()), config.specialLevelColor());
		}
		if (config.enableRunEnergy()
				&& plugin.isRunEnergyLow())
		{
			utils.canvasIndicator(graphics, utils.getCanvasIndicatorLocation(config.runEnergyLevelLocation()), config.runEnergyLevelColor());
		}
	}

}