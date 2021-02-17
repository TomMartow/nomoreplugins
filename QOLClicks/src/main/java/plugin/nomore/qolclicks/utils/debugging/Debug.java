package plugin.nomore.qolclicks.utils.debugging;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.api.events.MenuOptionClicked;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.QOLClicksPlugin;

import javax.inject.Inject;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class Debug
{

    @Inject private QOLClicksPlugin plugin;
    @Inject private QOLClicksConfig config;

    public void clickedEntry(MenuEntry o, MenuOptionClicked e)
    {
        if (!config.enableDebugging())
        {
            return;
        }
        if (e.getOpcode() == MenuOpcode.WALK.getId())
        {
            return;
        }
        System.out.println(
                "\n" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:S").format(new Date())
                        + "\nOrig: Point: " + plugin.getPointClicked().getX() + ", " + plugin.getPointClicked().getY()
                        + "\nOrig: Option: " + o.getOption() + "   ||   Mod: Option: " + e.getOption()
                        + "\nOrig: Target: " + o.getTarget() + "   ||   Mod: Target: " + e.getTarget()
                        + "\nOrig: Identifier: " + o.getIdentifier() + "   ||   Mod: Identifier: " + e.getIdentifier()
                        + "\nOrig: Opcode: " + e.getMenuOpcode() + "   ||   Mod: Opcode: "	+ e.getMenuOpcode()
                        + "\nOrig: Param0: " + o.getParam0() + "   ||   Mod: Param0: " + e.getParam0()
                        + "\nOrig: Param1: " + o.getParam1() + "   ||   Mod: Param1: " + e.getParam1()
                        + "\nOrig: forceLeftClick: " + o.isForceLeftClick() + "   ||   Mod: forceLeftClick: " 	+ e.isForceLeftClick()
                        + "\nEvent consumed: " + e.isConsumed()
        );
        if (config.enableWriteToClipboard())
        {
            writeTextToClipboard(
                    "```\n"
                            + "\nOrig: Point: " + plugin.getPointClicked().getX() + ", " + plugin.getPointClicked().getY()
                            + "\nOrig: Option: " + o.getOption() + "   ||   Mod: Option: " + e.getOption()
                            + "\nOrig: Target: " + o.getTarget() + "   ||   Mod: Target: " + e.getTarget()
                            + "\nOrig: Identifier: " + o.getIdentifier() + "   ||   Mod: Identifier: " + e.getIdentifier()
                            + "\nOrig: Opcode: " + e.getMenuOpcode() + "   ||   Mod: Opcode: "	+ e.getMenuOpcode()
                            + "\nOrig: Param0: " + o.getParam0() + "   ||   Mod: Param0: " + e.getParam0()
                            + "\nOrig: Param1: " + o.getParam1() + "   ||   Mod: Param1: " + e.getParam1()
                            + "\nOrig: forceLeftClick: " + o.isForceLeftClick() + "   ||   Mod: forceLeftClick: " 	+ e.isForceLeftClick()
                            + "\nEvent consumed: " + e.isConsumed()
                            + "\n```");
        }
    }

    public void writeTextToClipboard(String s)
    {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(s);
        clipboard.setContents(transferable, null);
    }

}
