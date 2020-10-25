/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
package net.runelite.client.plugins.pluginbase;

import net.runelite.client.config.*;

import java.awt.Color;


@ConfigGroup("aplugintutorial")
public interface TheConfig extends Config
{
	@ConfigTitleSection(
			keyName = "firstTitle",
			name = "First Title",
			description = "",
			position = 1
	)
	default Title firstTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "unhideOption",
			name = "Unhide checkbox",
			description = "When enabled, it unhides a checkbox.",
			position = 2,
			titleSection = "firstTitle"
	)
	default boolean unhideOption() { return false; }

	@ConfigItem(
			keyName = "hiddenOption",
			name = "Hidden option",
			description = "Does nothing, but it hides based on the unhide checkbox.",
			position = 3,
			titleSection = "firstTitle",
			hidden = true,
			unhide = "unhideOption",
			unhideValue = "true"
	)
	default boolean hiddenOption() { return false; }

	@ConfigTitleSection(
			keyName = "secondTitle",
			name = "Graphics tutorial",
			description = "",
			position = 4
	)
	default Title secondTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "displayGraphics",
			name = "Display graphics?",
			description = "When enabled, it will display a graphic.",
			position = 5,
			titleSection = "secondTitle"
	)
	default boolean displayGraphics() { return false; }

	@ConfigItem(
			keyName = "graphicColor",
			name = "Color",
			description = "The color of the graphic.",
			position = 6,
			titleSection = "secondTitle",
			hidden = true,
			unhide = "displayGraphics",
			unhideValue = "true"
	)
	default Color graphicColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "xPos",
			name = "X pos",
			description = "The x position of the graphic.",
			position = 7,
			titleSection = "secondTitle",
			hidden = true,
			unhide = "displayGraphics",
			unhideValue = "true"
	)
	default int xPos() { return 100; }

	@ConfigItem(
			keyName = "yPos",
			name = "Y pos",
			description = "The y position of the graphic.",
			position = 8,
			titleSection = "secondTitle",
			hidden = true,
			unhide = "displayGraphics",
			unhideValue = "true"
	)
	default int yPos() { return 100; }

	@Range(
			max = 200
	)
	@ConfigItem(
			keyName = "size",
			name = "Size",
			description = "The size of the graphic.",
			position = 9,
			titleSection = "secondTitle",
			hidden = true,
			unhide = "displayGraphics",
			unhideValue = "true"
	)
	default int size() { return 10; }

	@ConfigItem(
			keyName = "displayPlayer",
			name = "On player instead?",
			description = "When enabled, it will ignore the above x and y and render the graphic over the player.",
			position = 10,
			titleSection = "secondTitle",
			hidden = true,
			unhide = "displayGraphics",
			unhideValue = "true"
	)
	default boolean displayPlayer() { return false; }

}