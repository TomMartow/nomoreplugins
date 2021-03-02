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

@ConfigGroup("qolclicks")
public interface QOLClicksConfig extends Config
{

    @ConfigTitle(
            keyName = "configOptionsTitle",
            name = "Configuration Options",
            description = "",
            position = 1
    )
    String configOptionsTitle = "configOptionsTitle";

    @ConfigItem(
            keyName = "configEnum",
            name = "Show options for",
            description = "",
            position = 2,
            title = "configOptionsTitle"
    )
    default ConfigOptions configOptions() { return ConfigOptions.INV_ITEM_ON_ITEM; }

//  ██╗████████╗███████╗███╗   ███╗
//  ██║╚══██╔══╝██╔════╝████╗ ████║
//  ██║   ██║   █████╗  ██╔████╔██║
//  ██║   ██║   ██╔══╝  ██║╚██╔╝██║
//  ██║   ██║   ███████╗██║ ╚═╝ ██║
//  ╚═╝   ╚═╝   ╚══════╝╚═╝     ╚═╝
//
//   ██████╗ ███╗   ██╗
//  ██╔═══██╗████╗  ██║
//  ██║   ██║██╔██╗ ██║
//  ██║   ██║██║╚██╗██║
//  ╚██████╔╝██║ ╚████║
//   ╚═════╝ ╚═╝  ╚═══╝
//
//  ██╗████████╗███████╗███╗   ███╗
//  ██║╚══██╔══╝██╔════╝████╗ ████║
//  ██║   ██║   █████╗  ██╔████╔██║
//  ██║   ██║   ██╔══╝  ██║╚██╔╝██║
//  ██║   ██║   ███████╗██║ ╚═╝ ██║
//  ╚═╝   ╚═╝   ╚══════╝╚═╝     ╚═╝
//

    @ConfigItem(
            keyName = "enableItemOnItem",
            name = "Enable",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "INV_ITEM_ON_ITEM"
    )
    default boolean enableItemOnItem() { return false; }

    @ConfigItem(
            keyName = "itemOnItemTarget",
            name = "Clicked Item ID : Selected Item ID",
            description = "Replace \"Clicked item\" with the id of the item you want to click on. Replace \"Selected item\" with the item you want to be selected.",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "INV_ITEM_ON_ITEM"
    )
    default String itemOnItemIds() { return "1519:590"; }

//  ██╗   ██╗███████╗███████╗
//  ██║   ██║██╔════╝██╔════╝
//  ██║   ██║███████╗█████╗
//  ██║   ██║╚════██║██╔══╝
//  ╚██████╔╝███████║███████╗
//   ╚═════╝ ╚══════╝╚══════╝
//
//  ██╗████████╗███████╗███╗   ███╗
//  ██║╚══██╔══╝██╔════╝████╗ ████║
//  ██║   ██║   █████╗  ██╔████╔██║
//  ██║   ██║   ██╔══╝  ██║╚██╔╝██║
//  ██║   ██║   ███████╗██║ ╚═╝ ██║
//  ╚═╝   ╚═╝   ╚══════╝╚═╝     ╚═╝
//
//   ██████╗ ███╗   ██╗
//  ██╔═══██╗████╗  ██║
//  ██║   ██║██╔██╗ ██║
//  ██║   ██║██║╚██╗██║
//  ╚██████╔╝██║ ╚████║
//   ╚═════╝ ╚═╝  ╚═══╝
//
//  ███╗   ██╗██████╗  ██████╗
//  ████╗  ██║██╔══██╗██╔════╝
//  ██╔██╗ ██║██████╔╝██║
//  ██║╚██╗██║██╔═══╝ ██║
//  ██║ ╚████║██║     ╚██████╗
//  ╚═╝  ╚═══╝╚═╝      ╚═════╝
//

    @ConfigItem(
            keyName = "enableItemOnNpc",
            name = "Enable",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "NPC_USE_ITEM_ON"
    )
    default boolean enableItemOnNpc() { return false; }

    @ConfigItem(
            keyName = "useItemOnNpcIds",
            name = "Clicked Item ID : NPC ID",
            description = "Replace \"Clicked item\" with the id of the item you want to click on. Replace \"NPC ID\" with the npc you want to use the item on.",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "NPC_USE_ITEM_ON"
    )
    default String useItemOnNpcIds() { return "1519:590"; }
    
//  ███╗   ██╗██████╗  ██████╗                      
//  ████╗  ██║██╔══██╗██╔════╝                      
//  ██╔██╗ ██║██████╔╝██║                           
//  ██║╚██╗██║██╔═══╝ ██║                           
//  ██║ ╚████║██║     ╚██████╗                      
//  ╚═╝  ╚═══╝╚═╝      ╚═════╝                      
//                                                  
//  ███████╗██╗██████╗ ███████╗████████╗            
//  ██╔════╝██║██╔══██╗██╔════╝╚══██╔══╝            
//  █████╗  ██║██████╔╝███████╗   ██║               
//  ██╔══╝  ██║██╔══██╗╚════██║   ██║               
//  ██║     ██║██║  ██║███████║   ██║               
//  ╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝               
//                                                  
//   ██████╗ ██████╗ ████████╗██╗ ██████╗ ███╗   ██╗
//  ██╔═══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
//  ██║   ██║██████╔╝   ██║   ██║██║   ██║██╔██╗ ██║
//  ██║   ██║██╔═══╝    ██║   ██║██║   ██║██║╚██╗██║
//  ╚██████╔╝██║        ██║   ██║╚██████╔╝██║ ╚████║
//   ╚═════╝ ╚═╝        ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
//      
    
    @ConfigItem(
            keyName = "enableNPCFirstOption",
            name = "Enable",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "NPC_FIRST_OPTION"
    )
    default boolean enableNPCFirstOption() { return false; }

    @ConfigItem(
            keyName = "npcOption",
            name = "Option",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "NPC_FIRST_OPTION"
    )
    default String npcOption() { return "Use-rod"; }

    @ConfigItem(
            keyName = "npcFirstOptionTarget",
            name = "Clicked Item ID : NPC ID",
            description = "Replace \"Clicked item\" with the id of the item you want to click on. Replace \"NPC ID\" with the npc you want to interact with.",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "NPC_FIRST_OPTION"
    )
    default String npcFirstOptionIds() { return "11323:1542"; }

//   ██████╗  █████╗ ███╗   ███╗███████╗ ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗
//  ██╔════╝ ██╔══██╗████╗ ████║██╔════╝██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝
//  ██║  ███╗███████║██╔████╔██║█████╗  ██║   ██║██████╔╝     ██║█████╗  ██║        ██║   
//  ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝  ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║   
//  ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║   
//   ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝ ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   
//                                                                                        
//  ███████╗██╗██████╗ ███████╗████████╗                                                  
//  ██╔════╝██║██╔══██╗██╔════╝╚══██╔══╝                                                  
//  █████╗  ██║██████╔╝███████╗   ██║                                                     
//  ██╔══╝  ██║██╔══██╗╚════██║   ██║                                                     
//  ██║     ██║██║  ██║███████║   ██║                                                     
//  ╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝                                                     
//                                                                                        
//   ██████╗ ██████╗ ████████╗██╗ ██████╗ ███╗   ██╗                                      
//  ██╔═══██╗██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║                                      
//  ██║   ██║██████╔╝   ██║   ██║██║   ██║██╔██╗ ██║                                      
//  ██║   ██║██╔═══╝    ██║   ██║██║   ██║██║╚██╗██║                                      
//  ╚██████╔╝██║        ██║   ██║╚██████╔╝██║ ╚████║                                      
//   ╚═════╝ ╚═╝        ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝                                      
//                                                                                        

    @ConfigItem(
            keyName = "enableGameObjectFirstOption",
            name = "Enable",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "OBJECT_FIRST_OPTION"
    )
    default boolean enableGameObjectFirstOption() { return false; }

    @ConfigItem(
            keyName = "gameObjectOption",
            name = "Option",
            description = "",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "OBJECT_FIRST_OPTION"
    )
    default String gameObjectOption() { return "Chop down"; }

    @ConfigItem(
            keyName = "gameObjectFirstOptionTarget",
            name = "Clicked Item ID : GameObject ID",
            description = "Replace \"Clicked item\" with the id of the item you want to click on. Replace \"GameObject ID\" with the game object you want to interact with.",
            position = 5,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "OBJECT_FIRST_OPTION"
    )
    default String gameObjectFirstOptionIds() { return "1353:3511"; }

//  ██╗   ██╗███████╗███████╗
//  ██║   ██║██╔════╝██╔════╝
//  ██║   ██║███████╗█████╗
//  ██║   ██║╚════██║██╔══╝
//  ╚██████╔╝███████║███████╗
//   ╚═════╝ ╚══════╝╚══════╝
//
//  ██╗████████╗███████╗███╗   ███╗
//  ██║╚══██╔══╝██╔════╝████╗ ████║
//  ██║   ██║   █████╗  ██╔████╔██║
//  ██║   ██║   ██╔══╝  ██║╚██╔╝██║
//  ██║   ██║   ███████╗██║ ╚═╝ ██║
//  ╚═╝   ╚═╝   ╚══════╝╚═╝     ╚═╝
//
//   ██████╗ ███╗   ██╗
//  ██╔═══██╗████╗  ██║
//  ██║   ██║██╔██╗ ██║
//  ██║   ██║██║╚██╗██║
//  ╚██████╔╝██║ ╚████║
//   ╚═════╝ ╚═╝  ╚═══╝
//
//   ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗
//  ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝
//  ██║   ██║██████╔╝     ██║█████╗  ██║        ██║
//  ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║
//  ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║
//   ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝
//

    @ConfigItem(
            keyName = "enableItemOnObject",
            name = "Enable",
            description = "",
            position = 3,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "OBJECT_USE_ITEM_ON"
    )
    default boolean enableItemOnObject() { return false; }

    @ConfigItem(
            keyName = "useItemOnObjectIds",
            name = "Clicked Item ID : NPC ID",
            description = "Replace \"Clicked item\" with the id of the item you want to click on. Replace \"Object ID\" with the object you want to use the item on.",
            position = 4,
            title = "configOptionsTitle",
            hidden = true,
            unhide = "configEnum",
            unhideValue = "OBJECT_USE_ITEM_ON"
    )
    default String useItemOnObjectIds() { return "1519:590"; }

//   █████╗ ██╗   ██╗████████╗ ██████╗ ███╗   ███╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
//  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗████╗ ████║██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
//  ███████║██║   ██║   ██║   ██║   ██║██╔████╔██║███████║   ██║   ██║██║   ██║██╔██╗ ██║
//  ██╔══██║██║   ██║   ██║   ██║   ██║██║╚██╔╝██║██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
//  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║ ╚═╝ ██║██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
//  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
//

    @ConfigTitle(
            keyName = "automationTitle",
            name = "Automation Options",
            description = "",
            position = 2
    )
    String automationTitle = "automationTitle";

    @ConfigItem(
            keyName = "droppingKeybind",
            name = "Keybind",
            description = "If enabled, when the drop-matching hotkey is pressed, all items that match in the box below will be dropped.",
            position = 3,
            title = "automationTitle"
    )
    default Keybind dropKeybind() { return Keybind.NOT_SET; }

    @ConfigItem(
            keyName = "dropMatching",
            name = "Drop matching",
            description = "If enabled, when the drop-matching hotkey is pressed, all items that match in the box below will be dropped.",
            position = 4,
            title = "automationTitle"
    )
    default boolean dropMatching() { return false; }

    @ConfigItem(
            keyName = "matchingList",
            name = "Matching list",
            description = "If enabled, when the drop-matching hotkey is pressed, all items that match in the box below will be dropped.",
            position = 5,
            title = "automationTitle"
    )
    default String matchingList() { return "Bones"; }

    @ConfigItem(
            keyName = "dropExcept",
            name = "Drop except",
            description = "If enabled, when the drop-matching hotkey is pressed, all items that match in the box below will be dropped.",
            position = 6,
            title = "automationTitle"
    )
    default boolean dropExcept() { return false; }

    @ConfigItem(
            keyName = "exceptList",
            name = "Except list",
            description = "If enabled, when the drop-matching hotkey is pressed, all items that don't match in the box below will be dropped.",
            position = 7,
            title = "automationTitle"
    )
    default String exceptList() { return "Bones"; }

    @ConfigItem(
            keyName = "listOrder",
            name = "Order",
            description = "The iteration order.",
            position = 8,
            title = "automationTitle"
    )
    default String listOrder() { return "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27"; }

    @ConfigItem(
            keyName = "minTime",
            name = "Min (millis)",
            description = "The minimum time between dropping.",
            position = 9,
            title = "automationTitle"
    )
    default String minTime() { return "250"; }

    @ConfigItem(
            keyName = "maxTime",
            name = "Max (millis)",
            description = "The maximum time between dropping.",
            position = 10,
            title = "automationTitle"
    )
    default String maxTime() { return "1000"; }
}