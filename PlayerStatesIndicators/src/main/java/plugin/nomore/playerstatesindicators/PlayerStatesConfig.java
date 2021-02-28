/*
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

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("playerstatesindicators")
public interface PlayerStatesConfig extends Config {

//  ██╗██████╗ ██╗     ███████╗
//  ██║██╔══██╗██║     ██╔════╝
//  ██║██║  ██║██║     █████╗
//  ██║██║  ██║██║     ██╔══╝
//  ██║██████╔╝███████╗███████╗
//  ╚═╝╚═════╝ ╚══════╝╚══════╝
//

	@ConfigSection(
			keyName = "idleSection",
			name = "Idle",
			description = "",
			position = 1
	)
	String idleSection = "idleSection";
	
	@ConfigItem(
			keyName = "enableIdle",
			name = "Enable Indicator",
			description = "If enabled will display the indicator when conditions match.",
			position = 1,
			section = "idleSection"
	)
	default boolean enableIdle() { return false; }

	@ConfigItem(
			keyName = "idleDelayTime",
			name = "Delay",
			description = "The delay between the player becoming idle and the indicator being displayed.",
			position = 2,
			section = "idleSection"
	)
	default int idleDelayTime() { return 1000; }

	@ConfigItem(
			keyName = "idleLocation",
			name = "Location",
			description = "The location of the indicator.",
			position = 3,
			section = "idleSection"
	)
	default String idleLocation() { return "10:10:10:10"; }

	@ConfigItem(
			keyName = "idleColor",
			name = "Color",
			description = "The location of the indicator.",
			position = 4,
			section = "idleSection"
	)
	default Color idleColor() { return Color.RED; }

//  ██╗  ██╗██╗████████╗██████╗  ██████╗ ██╗███╗   ██╗████████╗███████╗
//  ██║  ██║██║╚══██╔══╝██╔══██╗██╔═══██╗██║████╗  ██║╚══██╔══╝██╔════╝
//  ███████║██║   ██║   ██████╔╝██║   ██║██║██╔██╗ ██║   ██║   ███████╗
//  ██╔══██║██║   ██║   ██╔═══╝ ██║   ██║██║██║╚██╗██║   ██║   ╚════██║
//  ██║  ██║██║   ██║   ██║     ╚██████╔╝██║██║ ╚████║   ██║   ███████║
//  ╚═╝  ╚═╝╚═╝   ╚═╝   ╚═╝      ╚═════╝ ╚═╝╚═╝  ╚═══╝   ╚═╝   ╚══════╝
//
	
	@ConfigSection(
			keyName = "hpSection",
			name = "Hitpoints",
			description = "",
			position = 2
	)
	String hpSection = "hpSection";
	
	@ConfigItem(
			keyName = "enableHP",
			name = "Enable Indicator",
			description = "If enabled will display the indicator when conditions match.",
			position = 1,
			section = "hpSection"
	)
	default boolean enableHP() { return false; }

	@Range(
			max = 120
	)
	@ConfigItem(
			keyName = "hpLevel",
			name = "Level",
			description = "The level to display the indicator.",
			position = 2,
			section = "hpSection"
	)
	default int hpLevel() { return 10; }

	@ConfigItem(
			keyName = "hpLevelLocation",
			name = "Location",
			description = "The location of the indicator.",
			position = 3,
			section = "hpSection"
	)
	default String hpLevelLocation() { return "10:10:10:10"; }

	@ConfigItem(
			keyName = "hpLevelColor",
			name = "Color",
			description = "The location of the indicator.",
			position = 4,
			section = "hpSection"
	)
	default Color hpLevelColor() { return Color.RED; }

//   █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
//  ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
//  ███████║   ██║      ██║   ███████║██║     █████╔╝
//  ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗
//  ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
//  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
//

	@ConfigSection(
			keyName = "attackSection",
			name = "Attack",
			description = "",
			position = 3
	)
	String attackSection = "attackSection";
	
	@ConfigItem(
			keyName = "enableAttack",
			name = "Enable Indicator",
			description = "If enabled will display the indicator when conditions match.",
			position = 5,
			section = "attackSection"
	)
	default boolean enableAttack() { return false; }

	@Range(
			max = 120
	)
	@ConfigItem(
			keyName = "attackLevel",
			name = "Level",
			description = "The level to display the indicator.",
			position = 6,
			section = "attackSection"
	)
	default int attackLevel() { return 10; }

	@ConfigItem(
			keyName = "attackLevelLocation",
			name = "Location",
			description = "The location of the indicator.",
			position = 7,
			section = "attackSection"
	)
	default String attackLevelLocation() { return "10:10:10:10"; }

	@ConfigItem(
			keyName = "attackLevelColor",
			name = "Color",
			description = "The location of the indicator.",
			position = 8,
			section = "attackSection"
	)
	default Color attackLevelColor() { return Color.RED; }

//  ███████╗████████╗██████╗ ███████╗███╗   ██╗ ██████╗████████╗██╗  ██╗
//  ██╔════╝╚══██╔══╝██╔══██╗██╔════╝████╗  ██║██╔════╝╚══██╔══╝██║  ██║
//  ███████╗   ██║   ██████╔╝█████╗  ██╔██╗ ██║██║  ███╗  ██║   ███████║
//  ╚════██║   ██║   ██╔══██╗██╔══╝  ██║╚██╗██║██║   ██║  ██║   ██╔══██║
//  ███████║   ██║   ██║  ██║███████╗██║ ╚████║╚██████╔╝  ██║   ██║  ██║
//  ╚══════╝   ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝ ╚═════╝   ╚═╝   ╚═╝  ╚═╝
//
	
	@ConfigSection(
			keyName = "strengthSection",
			name = "Strength",
			description = "",
			position = 4
	)
	String strengthSection = "strengthSection";

	@ConfigItem(
			keyName = "enableStrength",
			name = "Enable Indicator",
			description = "If enabled will display the indicator when conditions match.",
			position = 5,
			section = "strengthSection"
	)
	default boolean enableStrength() { return false; }

	@Range(
			max = 120
	)
	@ConfigItem(
			keyName = "strengthLevel",
			name = "Level",
			description = "The level to display the indicator.",
			position = 6,
			section = "strengthSection"
	)
	default int strengthLevel() { return 10; }

	@ConfigItem(
			keyName = "strengthLevelLocation",
			name = "Location",
			description = "The location of the indicator.",
			position = 7,
			section = "strengthSection"
	)
	default String strengthLevelLocation() { return "10:10:10:10"; }

	@ConfigItem(
			keyName = "strengthLevelColor",
			name = "Color",
			description = "The location of the indicator.",
			position = 8,
			section = "strengthSection"
	)
	default Color strengthLevelColor() { return Color.RED; }
	
//  ██████╗ ███████╗███████╗███████╗███╗   ██╗ ██████╗███████╗
//  ██╔══██╗██╔════╝██╔════╝██╔════╝████╗  ██║██╔════╝██╔════╝
//  ██║  ██║█████╗  █████╗  █████╗  ██╔██╗ ██║██║     █████╗
//  ██║  ██║██╔══╝  ██╔══╝  ██╔══╝  ██║╚██╗██║██║     ██╔══╝
//  ██████╔╝███████╗██║     ███████╗██║ ╚████║╚██████╗███████╗
//  ╚═════╝ ╚══════╝╚═╝     ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
//

	@ConfigSection(
			keyName = "defenceSection",
			name = "Defence",
			description = "",
			position = 5
	)
	String defenceSection = "defenceSection";

	@ConfigItem(
			keyName = "enableDefence",
			name = "Enable Indicator",
			description = "If enabled will display the indicator when conditions match.",
			position = 5,
			section = "defenceSection"
	)
	default boolean enableDefence() { return false; }

	@Range(
			max = 120
	)
	@ConfigItem(
			keyName = "defenceLevel",
			name = "Level",
			description = "The level to display the indicator.",
			position = 6,
			section = "defenceSection"
	)
	default int defenceLevel() { return 10; }

	@ConfigItem(
			keyName = "defenceLevelLocation",
			name = "Location",
			description = "The location of the indicator.",
			position = 7,
			section = "defenceSection"
	)
	default String defenceLevelLocation() { return "10:10:10:10"; }

	@ConfigItem(
			keyName = "defenceLevelColor",
			name = "Color",
			description = "The location of the indicator.",
			position = 8,
			section = "defenceSection"
	)
	default Color defenceLevelColor() { return Color.RED; }
	
//  ██████╗  █████╗ ███╗   ██╗ ██████╗ ███████╗
//  ██╔══██╗██╔══██╗████╗  ██║██╔════╝ ██╔════╝
//  ██████╔╝███████║██╔██╗ ██║██║  ███╗█████╗
//  ██╔══██╗██╔══██║██║╚██╗██║██║   ██║██╔══╝
//  ██║  ██║██║  ██║██║ ╚████║╚██████╔╝███████╗
//  ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝
//

	@ConfigSection(
			keyName = "rangeSection",
			name = "Range",
			description = "",
			position = 6
	)
	String rangeSection = "rangeSection";

	@ConfigItem(
			keyName = "enableRange",
			name = "Enable Indicator",
			description = "If enabled will display the indicator when conditions match.",
			position = 5,
			section = "rangeSection"
	)
	default boolean enableRange() { return false; }

	@Range(
			max = 120
	)
	@ConfigItem(
			keyName = "rangeLevel",
			name = "Level",
			description = "The level to display the indicator.",
			position = 6,
			section = "rangeSection"
	)
	default int rangeLevel() { return 10; }

	@ConfigItem(
			keyName = "rangeLevelLocation",
			name = "Location",
			description = "The location of the indicator.",
			position = 7,
			section = "rangeSection"
	)
	default String rangeLevelLocation() { return "10:10:10:10"; }

	@ConfigItem(
			keyName = "rangeLevelColor",
			name = "Color",
			description = "The location of the indicator.",
			position = 8,
			section = "rangeSection"
	)
	default Color rangeLevelColor() { return Color.RED; }
	
//  ██████╗ ██████╗  █████╗ ██╗   ██╗███████╗██████╗ 
//  ██╔══██╗██╔══██╗██╔══██╗╚██╗ ██╔╝██╔════╝██╔══██╗
//  ██████╔╝██████╔╝███████║ ╚████╔╝ █████╗  ██████╔╝
//  ██╔═══╝ ██╔══██╗██╔══██║  ╚██╔╝  ██╔══╝  ██╔══██╗
//  ██║     ██║  ██║██║  ██║   ██║   ███████╗██║  ██║
//  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
//                                                   
	
	@ConfigSection(
			keyName = "prayerSection",
			name = "Prayer",
			description = "",
			position = 7
	)
	String prayerSection = "prayerSection";

	@ConfigItem(
			keyName = "enablePrayer",
			name = "Enable Indicator",
			description = "If enabled will display the indicator when conditions match.",
			position = 5,
			section = "prayerSection"
	)
	default boolean enablePrayer() { return false; }

	@Range(
			max = 120
	)
	@ConfigItem(
			keyName = "prayerLevel",
			name = "Level",
			description = "The level to display the indicator.",
			position = 6,
			section = "prayerSection"
	)
	default int prayerLevel() { return 10; }

	@ConfigItem(
			keyName = "prayerLevelLocation",
			name = "Location",
			description = "The location of the indicator.",
			position = 7,
			section = "prayerSection"
	)
	default String prayerLevelLocation() { return "10:10:10:10"; }

	@ConfigItem(
			keyName = "prayerLevelColor",
			name = "Color",
			description = "The location of the indicator.",
			position = 8,
			section = "prayerSection"
	)
	default Color prayerLevelColor() { return Color.RED; }
	
//  ███╗   ███╗ █████╗  ██████╗ ██╗ ██████╗
//  ████╗ ████║██╔══██╗██╔════╝ ██║██╔════╝
//  ██╔████╔██║███████║██║  ███╗██║██║     
//  ██║╚██╔╝██║██╔══██║██║   ██║██║██║     
//  ██║ ╚═╝ ██║██║  ██║╚██████╔╝██║╚██████╗
//  ╚═╝     ╚═╝╚═╝  ╚═╝ ╚═════╝ ╚═╝ ╚═════╝
//   
	
	@ConfigSection(
			keyName = "magicSection",
			name = "Magic",
			description = "",
			position = 8
	)
	String magicSection = "magicSection";

	@ConfigItem(
			keyName = "enableMagic",
			name = "Enable Indicator",
			description = "If enabled will display the indicator when conditions match.",
			position = 5,
			section = "magicSection"
	)
	default boolean enableMagic() { return false; }

	@Range(
			max = 120
	)
	@ConfigItem(
			keyName = "magicLevel",
			name = "Level",
			description = "The level to display the indicator.",
			position = 6,
			section = "magicSection"
	)
	default int magicLevel() { return 10; }

	@ConfigItem(
			keyName = "magicLevelLocation",
			name = "Location",
			description = "The location of the indicator.",
			position = 7,
			section = "magicSection"
	)
	default String magicLevelLocation() { return "10:10:10:10"; }

	@ConfigItem(
			keyName = "magicLevelColor",
			name = "Color",
			description = "The location of the indicator.",
			position = 8,
			section = "magicSection"
	)
	default Color magicLevelColor() { return Color.RED; }

//  ███████╗██████╗ ███████╗ ██████╗██╗ █████╗ ██╗
//  ██╔════╝██╔══██╗██╔════╝██╔════╝██║██╔══██╗██║
//  ███████╗██████╔╝█████╗  ██║     ██║███████║██║
//  ╚════██║██╔═══╝ ██╔══╝  ██║     ██║██╔══██║██║
//  ███████║██║     ███████╗╚██████╗██║██║  ██║███████╗
//  ╚══════╝╚═╝     ╚══════╝ ╚═════╝╚═╝╚═╝  ╚═╝╚══════╝
//

	@ConfigSection(
			keyName = "specialSection",
			name = "Special",
			description = "",
			position = 9
	)
	String specialSection = "specialSection";

	@ConfigItem(
			keyName = "enableSpecial",
			name = "Enable Indicator",
			description = "If enabled will display the indicator when conditions match.",
			position = 5,
			section = "specialSection"
	)
	default boolean enableSpecial() { return false; }

	@Range(
			max = 100
	)
	@ConfigItem(
			keyName = "specialLevel",
			name = "Level",
			description = "The level to display the indicator.",
			position = 6,
			section = "specialSection"
	)
	default int specialLevel() { return 10; }

	@ConfigItem(
			keyName = "specialLevelLocation",
			name = "Location",
			description = "The location of the indicator.",
			position = 7,
			section = "specialSection"
	)
	default String specialLevelLocation() { return "10:10:10:10"; }

	@ConfigItem(
			keyName = "specialLevelColor",
			name = "Color",
			description = "The location of the indicator.",
			position = 8,
			section = "specialSection"
	)
	default Color specialLevelColor() { return Color.RED; }
	
//  ██████╗ ██╗   ██╗███╗   ██╗                        
//  ██╔══██╗██║   ██║████╗  ██║                        
//  ██████╔╝██║   ██║██╔██╗ ██║                        
//  ██╔══██╗██║   ██║██║╚██╗██║                        
//  ██║  ██║╚██████╔╝██║ ╚████║                        
//  ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝                        
//                                                     
//  ███████╗███╗   ██╗███████╗██████╗  ██████╗██╗   ██╗
//  ██╔════╝████╗  ██║██╔════╝██╔══██╗██╔════╝╚██╗ ██╔╝
//  █████╗  ██╔██╗ ██║█████╗  ██████╔╝██║  ███╗╚████╔╝ 
//  ██╔══╝  ██║╚██╗██║██╔══╝  ██╔══██╗██║   ██║ ╚██╔╝  
//  ███████╗██║ ╚████║███████╗██║  ██║╚██████╔╝  ██║   
//  ╚══════╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝ ╚═════╝   ╚═╝   
//                                                     

	@ConfigSection(
			keyName = "runEnergySection",
			name = "Run Energy",
			description = "",
			position = 10
	)
	String runEnergySection = "runEnergySection";

	@ConfigItem(
			keyName = "enableRunEnergy",
			name = "Enable Indicator",
			description = "If enabled will display the indicator when conditions match.",
			position = 5,
			section = "runEnergySection"
	)
	default boolean enableRunEnergy() { return false; }

	@Range(
			max = 100
	)
	@ConfigItem(
			keyName = "runEnergyLevel",
			name = "Level",
			description = "The level to display the indicator.",
			position = 6,
			section = "runEnergySection"
	)
	default int runEnergyLevel() { return 10; }

	@ConfigItem(
			keyName = "runEnergyLevelLocation",
			name = "Location",
			description = "The location of the indicator.",
			position = 7,
			section = "runEnergySection"
	)
	default String runEnergyLevelLocation() { return "10:10:10:10"; }

	@ConfigItem(
			keyName = "runEnergyLevelColor",
			name = "Color",
			description = "The location of the indicator.",
			position = 8,
			section = "runEnergySection"
	)
	default Color runEnergyLevelColor() { return Color.RED; }
}
