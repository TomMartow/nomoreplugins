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
package plugin.nomore.aiomarkers;

import net.runelite.client.config.*;
import plugin.nomore.aiomarkers.item.ground.GroundItemRenderStyle;
import plugin.nomore.aiomarkers.item.inventory.InventoryItemRenderStyle;
import plugin.nomore.aiomarkers.npc.NPCRenderStyle;
import plugin.nomore.aiomarkers.object.ObjectRenderStyle;

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
			name = "for:",
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
			keyName = "npcHighlightTitle",
			name = "NPC Highlight Options",
			description = "",
			position = 10,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title npcHighlightTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableNPCHighlighting",
			name = "Enable NPC Highlight",
			description = "",
			position = 11,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default boolean enableNPCHighlighting() { return false; }

	@ConfigItem(
			keyName = "npcRenderStyle",
			name = "Render style",
			description = "The type of marker.",
			position = 12,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default NPCRenderStyle npcRenderStyle() { return NPCRenderStyle.BOX; }

	@ConfigItem(
			keyName = "npcIndicatorSize",
			name = "Indicator size",
			description = "The size of the marker.",
			position = 13,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default int npcIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "configNPCTextField",
			name = "NPC Name / ID:Hex Color",
			description = "Example: \"Goblin:00FF00,\".",
			position = 14,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default String npcConfigTextString() { return "Banker, 3010"; }

	@ConfigItem(
			keyName = "npcColorTitle",
			name = "Indicator Interaction Color Options",
			description = "",
			position = 15,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title npcColorTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "npcDefaultHighlightColor",
			name = "Default Marker color",
			description = "The default color for the NPC indicator.",
			position = 16,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Color npcDefaultHighlightColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "npcEnableNPCDefaultColorOverrideWithNPCInteractingWithPlayer",
			name = "Enable NPC -> Player Indicator",
			description = "Enable the override of the default NPC highlighting color should the NPC be attacking your player.",
			position = 17,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default boolean npcEnableNPCDefaultColorOverrideWithNPCInteractingWithPlayer() { return false; }

	@ConfigItem(
			keyName = "npcInteractingWithPlayerColor",
			name = "NPC -> Player color",
			description = "The color of the indicator.",
			position = 18,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Color npcInteractingWithPlayerColor() { return Color.YELLOW; }

	@ConfigItem(
			keyName = "npcEnableNPCDefaultColorOverrideWithPlayersInteractingWithPlayer",
			name = "Enable Players -> NPC Indicator",
			description = "Enable the override of the default NPC highlighting color should another player be attacking an NPC that is currently marked.",
			position = 19,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default boolean npcEnableNPCDefaultColorOverrideWithPlayersInteractingWithPlayer() { return false; }

	@ConfigItem(
			keyName = "npcPlayersInteractingWithNPCColor",
			name = "Players -> NPC color",
			description = "The color of the indicator.",
			position = 20,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Color npcPlayersInteractingWithNPCColor() { return Color.RED; }

	@ConfigItem(
			keyName = "npcEnableHighlightingMenuItemForMarkedNPCS",
			name = "Enable Menu Highlighting",
			description = "Enable the highlighting of the NPC's name context menu.",
			position = 21,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default boolean npcEnableHighlightingMenuItemForMarkedNPCS() { return false; }

	@ConfigItem(
			keyName = "npcMenuItemColorForMarkedNPCS",
			name = "Menu Item Color",
			description = "The color of the menu item.",
			position = 22,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Color npcMenuItemColorForMarkedNPCS() { return Color.MAGENTA; }

	@ConfigItem(
			keyName = "npc10",
			name = "",
			description = "",
			position = 23,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title npc11() { return new Title(); }

	@ConfigItem(
			keyName = "npcMiscOptionsTitle",
			name = "Miscellaneous Options",
			description = "",
			position = 24,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title npcMiscOptionsTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "npc10",
			name = "",
			description = "",
			position = 25,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default Title npc10()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "npcLineOfSight",
			name = "Only show NPC's in line of sight",
			description = "Only show NPC's your player (tile: diagonally or straight) can \"see\"",
			position = 26,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default boolean npcLineOfSight() { return false; }

	@ConfigItem(
			keyName = "npcDisplayMouseHoveringIndicator",
			name = "Mouse hovering indicator",
			description = "If enabled will display an indicator if hovering over the npc.",
			position = 27,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC_HIGHLIGHTING"
	)
	default boolean npcDisplayMouseHoveringIndicator() { return false; }

	@ConfigItem(
			keyName = "npcMouseHoveringIndicatorLocation",
			name = "X:Y:Width:Height indicator location",
			description = "If enabled will display an indicator if hovering over the npc.",
			position = 28,
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
			keyName = "objectMarkerTitle",
			name = "Object Marker Options",
			description = "",
			position = 29,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Title objectMarkerTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableObjectMarkers",
			name = "Enable Object Markers",
			description = "",
			position = 30,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default boolean enableObjectMarkers() { return false; }

	@ConfigItem(
			keyName = "objectRenderStyle",
			name = "Render style",
			description = "The type of marker.",
			position = 31,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default ObjectRenderStyle objectRenderStyle() { return ObjectRenderStyle.BOX; }

	@ConfigItem(
			keyName = "objectIndicatorSize",
			name = "Indicator size",
			description = "The size of the marker.",
			position = 32,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default int objectIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "configObjectTextField",
			name = "Object Name / ID:Hex Color",
			description = "Example: \"Bank booth:00FF00,\".",
			position = 33,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default String objectConfigTextString() { return "Bank booth, 10529"; }

	@ConfigItem(
			keyName = "objectColorTitle",
			name = "Indicator Interaction Color Options",
			description = "",
			position = 34,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Title objectColorTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "objectDefaultHighlightColor",
			name = "Default Marker color",
			description = "The default color for the Object indicator.",
			position = 35,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Color objectDefaultHighlightColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "objectMiscOptionsTitle",
			name = "Miscellaneous Options",
			description = "",
			position = 36,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default Title objectMiscOptionsTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "objectLineOfSight",
			name = "Only show Objects in line of sight",
			description = "Only show Objects your player (tile: diagonally or straight) can \"see\"",
			position = 37,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default boolean objectLineOfSight() { return false; }

	@ConfigItem(
			keyName = "objectDisplayMouseHoveringIndicator",
			name = "Mouse hovering indicator",
			description = "If enabled will display an indicator if hovering over the object.",
			position = 38,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default boolean objectDisplayMouseHoveringIndicator() { return false; }

	@ConfigItem(
			keyName = "objectMouseHoveringIndicatorLocation",
			name = "X:Y:Width:Height indicator location",
			description = "If enabled will display an indicator if hovering over the object.",
			position = 39,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "OBJECT_MARKERS"
	)
	default String objectMouseHoveringIndicatorLocation() { return "10:10:10:10"; }

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
			name = "Ground Item Highlighting Options",
			description = "",
			position = 29,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default Title groundItemHighlightingTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableGroundItemHighlighting",
			name = "Enable Ground Item Highlighting",
			description = "",
			position = 30,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default boolean enableGroundItemHighlighting() { return false; }

	@ConfigItem(
			keyName = "groundItemRenderStyle",
			name = "Render style",
			description = "The type of marker.",
			position = 31,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default GroundItemRenderStyle groundItemRenderStyle() { return GroundItemRenderStyle.BOX; }

	@ConfigItem(
			keyName = "groundItemIndicatorSize",
			name = "Indicator size",
			description = "The size of the marker.",
			position = 32,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default int groundItemIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "configGroundItemTextField",
			name = "Item Name / ID:Hex Color",
			description = "Example: \"Bones:00FF00,\".",
			position = 33,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default String groundItemConfigTextString() { return "Bones, 590"; }

	@ConfigItem(
			keyName = "groundItemColorTitle",
			name = "Indicator Interaction Color Options",
			description = "",
			position = 34,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default Title groundItemColorTitle() { return new Title(); }

	@ConfigItem(
			keyName = "groundItemDefaultHighlightColor",
			name = "Default Marker color",
			description = "The default color for the Object indicator.",
			position = 35,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default Color groundItemDefaultHighlightColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "groundItemMiscOptionsTitle",
			name = "Miscellaneous Options",
			description = "",
			position = 36,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default Title groundItemMiscOptionsTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "groundItemLineOfSight",
			name = "Only show Objects in line of sight",
			description = "Only show Objects your player (tile: diagonally or straight) can \"see\"",
			position = 37,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default boolean groundItemLineOfSight() { return false; }

	@ConfigItem(
			keyName = "groundItemDisplayMouseHoveringIndicator",
			name = "Mouse hovering indicator",
			description = "If enabled will display an indicator if hovering over the groundItem.",
			position = 38,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default boolean groundItemDisplayMouseHoveringIndicator() { return false; }

	@ConfigItem(
			keyName = "groundItemMouseHoveringIndicatorLocation",
			name = "X:Y:Width:Height indicator location",
			description = "If enabled will display an indicator if hovering over the groundItem.",
			position = 39,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "GROUND_ITEM_HIGHLIGHTING"
	)
	default String groundItemMouseHoveringIndicatorLocation() { return "10:10:10:10"; }

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
			keyName = "inventoryItemHighlightingTitle",
			name = "Inventory Item Highlighting Options",
			description = "",
			position = 29,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title inventoryItemHighlightingTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "enableInventoryItemHighlighting",
			name = "Enable Inventory Item Highlighting",
			description = "",
			position = 30,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default boolean enableInventoryItemHighlighting() { return false; }

	@ConfigItem(
			keyName = "inventoryItemRenderStyle",
			name = "Render style",
			description = "The type of marker.",
			position = 31,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default InventoryItemRenderStyle inventoryItemRenderStyle() { return InventoryItemRenderStyle.BOX; }

	@ConfigItem(
			keyName = "inventoryItemIndicatorSize",
			name = "Indicator size",
			description = "The size of the marker.",
			position = 32,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default int inventoryItemIndicatorSize() { return 4; }

	@ConfigItem(
			keyName = "configInventoryItemTextField",
			name = "Item Name / ID:Hex Color",
			description = "Example: \"Bones:00FF00,\".",
			position = 33,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default String inventoryItemConfigTextString() { return "Bones, 590"; }

	@ConfigItem(
			keyName = "inventoryItemColorTitle",
			name = "Indicator Interaction Color Options",
			description = "",
			position = 34,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title inventoryItemColorTitle() { return new Title(); }

	@ConfigItem(
			keyName = "inventoryItemDefaultHighlightColor",
			name = "Default Marker color",
			description = "The default color for the Object indicator.",
			position = 35,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Color inventoryItemDefaultHighlightColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "inventoryItemMiscOptionsTitle",
			name = "Miscellaneous Options",
			description = "",
			position = 36,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default Title inventoryItemMiscOptionsTitle() { return new Title(); }

	@ConfigItem(
			keyName = "inventoryItemDisplayMouseHoveringIndicator",
			name = "Mouse hovering indicator",
			description = "If enabled will display an indicator if hovering over the inventoryItem.",
			position = 38,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default boolean inventoryItemDisplayMouseHoveringIndicator() { return false; }

	@ConfigItem(
			keyName = "inventoryItemMouseHoveringIndicatorLocation",
			name = "X:Y:Width:Height indicator location",
			description = "If enabled will display an indicator if hovering over the inventoryItem.",
			position = 39,
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "INVENTORY_TAGGING"
	)
	default String inventoryItemMouseHoveringIndicatorLocation() { return "10:10:10:10"; }

}