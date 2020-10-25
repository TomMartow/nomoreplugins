package net.runelite.client.plugins.nomoremenuindicators;

import net.runelite.client.config.*;

import java.awt.Color;


@ConfigGroup("nomoremenuindicators")
public interface NoMoreMenuIndicatorsConfig extends Config
{
	@ConfigTitleSection(
			keyName = "menu",
			name = "Menu tabs",
			description = "",
			position = 1
	)
	default Title nomoreMenu()
	{
		return new Title();
	}

	@ConfigItem(
			position = 2,
			keyName = "boxSize",
			name = "Box Size",
			description = "Set Box Size",
			titleSection = "menu"
	)
	default int boxSize() { return 0; }

	@ConfigItem(
			position = 3,
			keyName = "combat",
			name = "Combat",
			description = "Color",
			titleSection = "menus"
	)
	default Color combatColor() { return new Color(0,255,255); }

	@ConfigItem(
			position = 4,
			keyName = "skills",
			name = "Skills",
			description = "Color",
			titleSection = "menus"
	)
	default Color skillsColor() { return Color.CYAN; }

	@ConfigItem(
			position = 5,
			keyName = "quest",
			name = "Quests",
			description = "Color",
			titleSection = "menus"
	)
	default Color questColor() { return Color.CYAN; }

	@ConfigItem(
			position = 6,
			keyName = "inventory",
			name = "Inventory",
			description = "Color",
			titleSection = "menus"
	)
	default Color inventoryColor() { return Color.CYAN; }

	@ConfigItem(
			position = 7,
			keyName = "equipment",
			name = "Equipment",
			description = "Color",
			titleSection = "menus"
	)
	default Color equipmentColor() { return Color.CYAN; }

	@ConfigItem(
			position = 8,
			keyName = "prayer",
			name = "Prayer",
			description = "Color",
			titleSection = "menus"
	)
	default Color prayerColor() { return Color.CYAN; }

	@ConfigItem(
			position = 9,
			keyName = "magic",
			name = "Magic",
			description = "Color",
			titleSection = "menus"
	)
	default Color magicColor() { return Color.CYAN; }

	@ConfigItem(
			position = 10,
			keyName = "friends",
			name = "Friends",
			description = "Color",
			titleSection = "menus"
	)
	default Color friendsColor() { return Color.CYAN; }

	@ConfigItem(
			position = 11,
			keyName = "account",
			name = "Account",
			description = "Color",
			titleSection = "menus"
	)
	default Color accountColor() { return Color.CYAN; }

	@ConfigItem(
			position = 12,
			keyName = "clan",
			name = "Clan",
			description = "Color",
			titleSection = "menus"
	)
	default Color clanColor() { return Color.CYAN; }

	@ConfigItem(
			position = 13,
			keyName = "logout",
			name = "Logout",
			description = "Color",
			titleSection = "menus"
	)
	default Color logoutColor() { return Color.CYAN; }

	@ConfigItem(
			position = 14,
			keyName = "settings",
			name = "Settings",
			description = "Color",
			titleSection = "menus"
	)
	default Color settingsColor() { return Color.CYAN; }
	@ConfigItem(
			position = 15,
			keyName = "emote",
			name = "Emote",
			description = "Color",
			titleSection = "menus"
	)
	default Color emoteColor() { return Color.CYAN; }

	@ConfigItem(
			position = 16,
			keyName = "music",
			name = "Music",
			description = "Color",
			titleSection = "menus"
	)
	default Color musicColor() { return Color.CYAN; }
}