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
package net.runelite.client.plugins.test;

import net.runelite.client.config.*;

import java.awt.Color;


@ConfigGroup("test")
public interface TestConfig extends Config
{
	@ConfigTitleSection(
			keyName = "testTitle",
			name = "Item on Item",
			description = "",
			position = 1
	)
	default Title firstTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "combineItem1Name",
			name = "Item 1 name",
			description = "",
			position = 2,
			titleSection = "testTitle"
	)
	default String combineItem1Name() { return ""; }

	@ConfigItem(
			keyName = "combineItem2Name",
			name = "Item 2 name",
			description = "",
			position = 3,
			titleSection = "testTitle"
	)
	default String combineItem2Name() { return ""; }

	@ConfigTitleSection(
			keyName = "itemOnGameObject",
			name = "Item on GameObject",
			description = "",
			position = 4
	)
	default Title itemOnGameObject()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "itemOnGameObjectItemName",
			name = "Item 1 name",
			description = "",
			position = 5,
			titleSection = "itemOnGameObject"
	)
	default String itemOnGameObjectItemName() { return ""; }

	@ConfigItem(
			keyName = "itemOnGameObjectName",
			name = "Item 2 name",
			description = "",
			position = 6,
			titleSection = "itemOnGameObject"
	)
	default String itemOnGameObjectName() { return ""; }

}