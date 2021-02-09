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
package plugin.nomore.qolclicks;

import net.runelite.client.config.*;
import plugin.nomore.qolclicks.item.ground.GroundItemRenderStyle;
import plugin.nomore.qolclicks.item.inventory.InventoryItemRenderStyle;
import plugin.nomore.qolclicks.npc.NPCRenderStyle;
import plugin.nomore.qolclicks.object.ObjectRenderStyle;

import java.awt.Color;

@ConfigGroup("aiomarkers")
public interface AIOConfig extends Config
{

	// https://www.patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=Type%20Something%20

	@ConfigTitleSection(
			keyName = "configurationOptions",
			name = "Configuration Options",
			description = "",
			position = 1
	)
	default Title configurationOptions()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "markerConfiguration",
			name = "",
			description = "",
			position = 2,
			titleSection = "configurationOptions"
	)
	default ConfigurationOptions markerConfiguration() { return ConfigurationOptions.NPC_HIGHLIGHTING; }

	//███╗   ██╗██████╗  ██████╗
	//████╗  ██║██╔══██╗██╔════╝
	//██╔██╗ ██║██████╔╝██║
	//██║╚██╗██║██╔═══╝ ██║
	//██║ ╚████║██║     ╚██████╗
	//╚═╝  ╚═══╝╚═╝      ╚═════╝

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 10,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title test1()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "npcHighlightTitle",
			name = "Overlay Options",
			description = "",
			position = 11,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title npcHighlightTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 12,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title test2()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableNPCHighlighting",
			name = "  Enable NPC Highlight",
			description = "",
			position = 13,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default boolean enableNPCHighlighting() { return false; }

	@ConfigItem(
			keyName = "npcRenderStyle",
			name = "  Render style",
			description = "The type of marker.",
			position = 14,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default NPCRenderStyle npcRenderStyle() { return NPCRenderStyle.BOX; }

	@ConfigItem(
			keyName = "npcIndicatorSize",
			name = "  Indicator size",
			description = "The size of the marker.",
			position = 15,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default int npcIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "configNPCTextField",
			name = "  Name / ID:Color",
			description = "Example: \"Goblin:00FF00,\".",
			position = 16,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default String npcConfigTextString() { return "Banker,\nMan:00ffff"; }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 17,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title test3()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "npcColorTitle",
			name = "Indicator Interaction Color Options",
			description = "",
			position = 18,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title npcColorTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 19,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title test4()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "npcDefaultHighlightColor",
			name = "  Default Marker color",
			description = "The default color for the NPC indicator.",
			position = 20,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Color npcDefaultHighlightColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "npcEnableNPCDefaultColorOverrideWithNPCInteractingWithPlayer",
			name = "  Enable NPC -> Player Indicator",
			description = "Enable the override of the default NPC highlighting color should the NPC be attacking your player.",
			position = 21,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default boolean npcEnableNPCDefaultColorOverrideWithNPCInteractingWithPlayer() { return false; }

	@ConfigItem(
			keyName = "npcInteractingWithPlayerColor",
			name = "  NPC -> Player color",
			description = "The color of the indicator.",
			position = 22,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Color npcInteractingWithPlayerColor() { return Color.YELLOW; }

	@ConfigItem(
			keyName = "npcEnableNPCDefaultColorOverrideWithPlayersInteractingWithPlayer",
			name = "  Enable Players -> NPC Indicator",
			description = "Enable the override of the default NPC highlighting color should another player be attacking an NPC that is currently marked.",
			position = 23,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default boolean npcEnableNPCDefaultColorOverrideWithPlayersInteractingWithPlayer() { return false; }

	@ConfigItem(
			keyName = "npcPlayersInteractingWithNPCColor",
			name = "  Players -> NPC color",
			description = "The color of the indicator.",
			position = 24,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Color npcPlayersInteractingWithNPCColor() { return Color.RED; }

	@ConfigItem(
			keyName = "npcEnableHighlightingMenuItemForMarkedNPCS",
			name = "  Enable Menu Highlighting",
			description = "Enable the highlighting of the NPC's name context menu.",
			position = 25,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default boolean npcEnableHighlightingMenuItemForMarkedNPCS() { return false; }

	@ConfigItem(
			keyName = "npcMenuItemColorForMarkedNPCS",
			name = "  Menu Item Color",
			description = "The color of the menu item.",
			position = 26,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Color npcMenuItemColorForMarkedNPCS() { return Color.MAGENTA; }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 27,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title test5()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "npcMiscOptionsTitle",
			name = "Miscellaneous Options",
			description = "",
			position = 28,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title npcMiscOptionsTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 29,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title test6()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "npcLineOfSight",
			name = "  Only show NPC's in line of sight",
			description = "Only show NPC's your player (tile: diagonally or straight) can \"see\"",
			position = 30,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default boolean npcLineOfSight() { return false; }

	@ConfigItem(
			keyName = "npcDisplayMouseHoveringIndicator",
			name = "  Mouse hovering indicator",
			description = "If enabled will display an indicator if hovering over the npc.",
			position = 31,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default boolean npcDisplayMouseHoveringIndicator() { return false; }

	@ConfigItem(
			keyName = "npcMouseHoveringIndicatorLocation",
			name = "  X:Y:Width:Height indicator location",
			description = "If enabled will display an indicator if hovering over the npc.",
			position = 32,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default String npcMouseHoveringIndicatorLocation() { return "10:10:10:10"; }


	//   ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗
	//  ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝
	//  ██║   ██║██████╔╝     ██║█████╗  ██║        ██║
	//  ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║
	//  ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║
	//   ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 1,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Title test7()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "objectMarkerTitle",
			name = "Overlay Options",
			description = "",
			position = 2,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Title objectMarkerTitle() { return new Title(); }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 3,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Title test8()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableObjectMarkers",
			name = "  Enable Object Markers",
			description = "",
			position = 4,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default boolean enableObjectMarkers() { return false; }

	@ConfigItem(
			keyName = "objectRenderStyle",
			name = "  Render style",
			description = "The type of marker.",
			position = 5,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default ObjectRenderStyle objectRenderStyle() { return ObjectRenderStyle.BOX; }

	@ConfigItem(
			keyName = "objectIndicatorSize",
			name = "  Indicator size",
			description = "The size of the marker.",
			position = 6,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default int objectIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "configObjectTextField",
			name = "  Name / ID:Hex Color",
			description = "Example: \"Bank booth:00FF00,\".",
			position = 7,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default String configObjectTextField() { return "Bank booth, 10529"; }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 8,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Title test9()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "objectColorTitle",
			name = "Color Options",
			description = "",
			position = 9,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Title objectColorTitle() { return new Title(); }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 10,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Title test10()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "objectDefaultHighlightColor",
			name = "  Default Marker color",
			description = "The default color for the Object indicator.",
			position = 11,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Color objectDefaultHighlightColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 12,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Title test11()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "objectMiscOptionsTitle",
			name = "Miscellaneous Options",
			description = "",
			position = 13,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Title objectMiscOptionsTitle() { return new Title(); }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 14,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Title test12()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "objectLineOfSight",
			name = "  Only show Objects in line of sight",
			description = "Only show Objects your player (tile: diagonally or straight) can \"see\"",
			position = 15,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default boolean objectLineOfSight() { return false; }

	@ConfigItem(
			keyName = "objectDisplayMouseHoveringIndicator",
			name = "  Mouse hovering indicator",
			description = "If enabled will display an indicator if hovering over the object.",
			position = 16,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default boolean objectDisplayMouseHoveringIndicator() { return false; }

	@ConfigItem(
			keyName = "objectMouseHoveringIndicatorLocation",
			name = "X:Y:Width:Height indicator location",
			description = "If enabled will display an indicator if hovering over the object.",
			position = 17,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default String objectMouseHoveringIndicatorLocation() { return "10:10:10:10"; }

	//  ██╗███╗   ██╗██╗   ██╗███████╗███╗   ██╗████████╗ ██████╗ ██████╗ ██╗   ██╗
	//  ██║████╗  ██║██║   ██║██╔════╝████╗  ██║╚══██╔══╝██╔═══██╗██╔══██╗╚██╗ ██╔╝
	//  ██║██╔██╗ ██║██║   ██║█████╗  ██╔██╗ ██║   ██║   ██║   ██║██████╔╝ ╚████╔╝
	//  ██║██║╚██╗██║╚██╗ ██╔╝██╔══╝  ██║╚██╗██║   ██║   ██║   ██║██╔══██╗  ╚██╔╝
	//  ██║██║ ╚████║ ╚████╔╝ ███████╗██║ ╚████║   ██║   ╚██████╔╝██║  ██║   ██║
	//  ╚═╝╚═╝  ╚═══╝  ╚═══╝  ╚══════╝╚═╝  ╚═══╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝   ╚═╝
	//
	//  ████████╗ █████╗  ██████╗  ██████╗ ██╗███╗   ██╗ ██████╗
	//  ╚══██╔══╝██╔══██╗██╔════╝ ██╔════╝ ██║████╗  ██║██╔════╝
	//     ██║   ███████║██║  ███╗██║  ███╗██║██╔██╗ ██║██║  ███╗
	//     ██║   ██╔══██║██║   ██║██║   ██║██║██║╚██╗██║██║   ██║
	//     ██║   ██║  ██║╚██████╔╝╚██████╔╝██║██║ ╚████║╚██████╔╝
	//     ╚═╝   ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚═════╝
	//

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 1,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title test13()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "inventoryItemHighlightingTitle",
			name = "Overlay Options",
			description = "",
			position = 2,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title inventoryItemHighlightingTitle() { return new Title(); }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 3,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title test14()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableInventoryItemHighlighting",
			name = "  Enable Inventory Item Highlighting",
			description = "",
			position = 4,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default boolean enableInventoryItemHighlighting() { return false; }

	@ConfigItem(
			keyName = "inventoryItemRenderStyle",
			name = "  Render style",
			description = "The type of marker.",
			position = 5,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default InventoryItemRenderStyle inventoryItemRenderStyle() { return InventoryItemRenderStyle.BOX; }

	@ConfigItem(
			keyName = "inventoryItemIndicatorSize",
			name = "  Indicator size",
			description = "The size of the marker.",
			position = 6,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default int inventoryItemIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "configInventoryItemTextField",
			name = "  Item Name / ID:Hex Color",
			description = "Example: \"Bones:00FF00,\".",
			position = 7,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default String inventoryItemConfigTextString() { return "Bones, 590"; }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 8,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title test15()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "inventoryItemColorTitle",
			name = "Color Options",
			description = "",
			position = 9,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title inventoryItemColorTitle() { return new Title(); }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 10,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title test16()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "inventoryItemDefaultHighlightColor",
			name = "  Default Marker color",
			description = "The default color for the Object indicator.",
			position = 11,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Color inventoryItemDefaultHighlightColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 12,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title test17()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "inventoryItemMiscOptionsTitle",
			name = "Miscellaneous Options",
			description = "",
			position = 13,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title inventoryItemMiscOptionsTitle() { return new Title(); }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 14,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title test18()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "inventoryItemDisplayMouseHoveringIndicator",
			name = "  Mouse hovering indicator",
			description = "If enabled will display an indicator if hovering over the inventoryItem.",
			position = 15,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default boolean inventoryItemDisplayMouseHoveringIndicator() { return false; }

	@ConfigItem(
			keyName = "inventoryItemMouseHoveringIndicatorLocation",
			name = "  X:Y:Width:Height indicator location",
			description = "If enabled will display an indicator if hovering over the inventoryItem.",
			position = 16,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default String inventoryItemMouseHoveringIndicatorLocation() { return "10:10:10:10"; }

	//   ██████╗ ██████╗  ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
	//  ██╔════╝ ██╔══██╗██╔═══██╗██║   ██║████╗  ██║██╔══██╗
	//  ██║  ███╗██████╔╝██║   ██║██║   ██║██╔██╗ ██║██║  ██║
	//  ██║   ██║██╔══██╗██║   ██║██║   ██║██║╚██╗██║██║  ██║
	//  ╚██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
	//   ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
	//                                                       
	//  ██╗████████╗███████╗███╗   ███╗                      
	//  ██║╚══██╔══╝██╔════╝████╗ ████║                      
	//  ██║   ██║   █████╗  ██╔████╔██║                      
	//  ██║   ██║   ██╔══╝  ██║╚██╔╝██║                      
	//  ██║   ██║   ███████╗██║ ╚═╝ ██║                      
	//  ╚═╝   ╚═╝   ╚══════╝╚═╝     ╚═╝                      
	//                                                       
	
	@ConfigItem(
			keyName = "groundItemHighlightingTitle",
			name = "Overlay Options",
			description = "",
			position = 1,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default Title groundItemHighlightingTitle() { return new Title(); }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 2,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title test19()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableGroundItemHighlighting",
			name = "  Enable Ground Item Highlighting",
			description = "",
			position = 3,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default boolean enableGroundItemHighlighting() { return false; }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 4,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title test20()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "groundItemRenderStyle",
			name = "  Render style",
			description = "The type of marker.",
			position = 5,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default GroundItemRenderStyle groundItemRenderStyle() { return GroundItemRenderStyle.BOX; }

	@ConfigItem(
			keyName = "groundItemIndicatorSize",
			name = "  Indicator size",
			description = "The size of the marker.",
			position = 6,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default int groundItemIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "configGroundItemTextField",
			name = "  Name / ID:Hex Color",
			description = "Example: \"Bones:00FF00,\".",
			position = 7,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default String groundItemConfigTextString() { return "Bones, 590"; }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 8,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title test21()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "groundItemColorTitle",
			name = "Color Options",
			description = "",
			position = 9,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default Title groundItemColorTitle() { return new Title(); }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 10,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title test22()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "groundItemDefaultHighlightColor",
			name = "  Default Marker color",
			description = "The default color for the ground item indicator.",
			position = 11,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default Color groundItemDefaultHighlightColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 12,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title test23() { return new Title(); }

	@ConfigItem(
			keyName = "groundItemMiscOptionsTitle",
			name = "Miscellaneous Options",
			description = "",
			position = 13,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default Title groundItemMiscOptionsTitle() { return new Title(); }

	@ConfigItem(
			keyName = "",
			name = "",
			description = "",
			position = 14,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title test24() { return new Title(); }

	@ConfigItem(
			keyName = "groundItemLineOfSight",
			name = "  Only show Objects in line of sight",
			description = "Only show Objects your player (tile: diagonally or straight) can \"see\"",
			position = 15,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default boolean groundItemLineOfSight() { return false; }

	@ConfigItem(
			keyName = "groundItemDisplayMouseHoveringIndicator",
			name = "  Mouse hovering indicator",
			description = "If enabled will display an indicator if hovering over the groundItem.",
			position = 16,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default boolean groundItemDisplayMouseHoveringIndicator() { return false; }

	@ConfigItem(
			keyName = "groundItemMouseHoveringIndicatorLocation",
			name = "  X:Y:Width:Height indicator location",
			description = "If enabled will display an indicator if hovering over the groundItem.",
			position = 17,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default String groundItemMouseHoveringIndicatorLocation() { return "10:10:10:10"; }

}