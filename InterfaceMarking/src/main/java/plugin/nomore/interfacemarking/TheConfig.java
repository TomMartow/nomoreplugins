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
package plugin.nomore.interfacemarking;

import net.runelite.client.config.*;

import java.awt.Color;


@ConfigGroup("interfaceindicators")
public interface TheConfig extends Config
{
	@ConfigTitle(
			keyName = "firstTitle",
			name = "First Title",
			description = "",
			position = 1
	)
	String firstTitle = "firstTitle";

	@ConfigItem(
			keyName = "configType",
			name = "Configuration Options",
			description = "",
			position = 2,
			title = "firstTitle"
	)
	default Options options() { return Options.BANK; }

	@ConfigItem(
			keyName = "bankOpen",
			name = "Open",
			description = "Displays the an indicator if the bank interface is open.",
			position = 3,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean bankOpen() { return false; }

	@ConfigItem(
			keyName = "bankOpenLocation",
			name = "Indicator Location",
			description = "Indicator location, format to use: x:y:width:height e.g 100:100:10:10",
			position = 4,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default String bankOpenLocation() { return "150:0:5:5"; }

	@ConfigItem(
			keyName = "bankOpenColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 5,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color bankColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "bankClose",
			name = "Close Button",
			description = "Displays the an indicator over the bank close button.",
			position = 6,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean bankClose() { return false; }

	@ConfigItem(
			keyName = "bankCloseColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 7,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color bankCloseColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "bankItem",
			name = "Withdraw / Deposit Item Button",
			description = "Displays the an indicator over the withdraw / deposit item button.",
			position = 8,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean bankItem() { return false; }

	@ConfigItem(
			keyName = "bankItemColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 9,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color bankItemColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "bankNote",
			name = "Withdraw / Deposit Note Button",
			description = "Displays the an indicator over the withdraw / deposit note button.",
			position = 10,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean bankNote() { return false; }

	@ConfigItem(
			keyName = "bankItemColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 11,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color bankNoteColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "bankQuantity1",
			name = "Quantity 1 Button",
			description = "Displays the an indicator over the quantity 1 button.",
			position = 12,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean bankQuantity1() { return false; }

	@ConfigItem(
			keyName = "bankQuantity1Color",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 13,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color bankQuantity1Color() { return Color.GREEN; }

	@ConfigItem(
			keyName = "bankQuantity5",
			name = "Quantity 5 Button",
			description = "Displays the an indicator over the quantity 5 button.",
			position = 14,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean bankQuantity5() { return false; }

	@ConfigItem(
			keyName = "bankQuantity5Color",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 15,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color bankQuantity5Color() { return Color.GREEN; }

	@ConfigItem(
			keyName = "bankQuantity10",
			name = "Quantity 10 Button",
			description = "Displays the an indicator over the quantity 10 button.",
			position = 16,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean bankQuantity10() { return false; }

	@ConfigItem(
			keyName = "bankQuantity10Color",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 17,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color bankQuantity10Color() { return Color.GREEN; }

	@ConfigItem(
			keyName = "bankQuantityX",
			name = "Quantity X Button",
			description = "Displays the an indicator over the quantity X button.",
			position = 18,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean bankQuantityX() { return false; }

	@ConfigItem(
			keyName = "bankQuantityXColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 19,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color bankQuantityXColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "bankQuantityAll",
			name = "Quantity All Button",
			description = "Displays the an indicator over the quantity All button.",
			position = 20,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean bankQuantityAll() { return false; }

	@ConfigItem(
			keyName = "bankQuantityAllColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 21,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color bankQuantityAllColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "bankDepositInventory",
			name = "Deposit All Items Button",
			description = "Displays the an indicator over the deposit all items button.",
			position = 22,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean bankDepositInventory() { return false; }

	@ConfigItem(
			keyName = "bankDepositInventoryColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 23,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color bankDepositInventoryColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "bankDepositEquipment",
			name = "Deposit All Equipment Button",
			description = "Displays the an indicator over the deposit all equipment button.",
			position = 24,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean bankDepositEquipment() { return false; }

	@ConfigItem(
			keyName = "bankDepositEquipmentColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 25,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color bankDepositEquipmentColor() { return Color.GREEN; }





	@ConfigItem(
			keyName = "depositIndicator",
			name = "Deposit",
			description = "Displays the an indicator if the deposit inventory interface is open.",
			position = 100,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean displayDeposit() { return false; }

	@ConfigItem(
			keyName = "depositIndicatorLocation",
			name = "Indicator location",
			description = "Indicator location, format to use: x:y:width:height e.g 100:100:10:10",
			position = 100,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default String depositLocation() { return "150:0:5:5"; }

	@ConfigItem(
			keyName = "depositIndicatorColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 100,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color depositColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "chatboxMakeIndicator",
			name = "Make / Create",
			description = "Displays the an indicator if the deposit inventory interface is open.",
			position = 100,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "CHATBOX"
	)
	default boolean displayChatboxMake() { return false; }

	@ConfigItem(
			keyName = "chatboxMakeIndicatorLocation",
			name = "Indicator location",
			description = "Indicator location, format to use: x:y:width:height e.g 100:100:10:10",
			position = 100,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "CHATBOX"
	)
	default String makeLocation() { return "155:0:5:5"; }

	@ConfigItem(
			keyName = "chatboxMakeIndicatorColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 100,
			title = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "CHATBOX"
	)
	default Color makeColor() { return Color.GREEN; }

}