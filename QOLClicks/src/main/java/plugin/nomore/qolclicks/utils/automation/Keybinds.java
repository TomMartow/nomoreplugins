package plugin.nomore.qolclicks.utils.automation;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.input.KeyListener;
import plugin.nomore.qolclicks.QOLClicksConfig;
import plugin.nomore.qolclicks.QOLClicksPlugin;

import javax.inject.Inject;
import java.awt.event.KeyEvent;

@Slf4j
public class Keybinds implements KeyListener
{

    @Inject
    ClientThread clientThread;

    @Inject
    QOLClicksPlugin plugin;

    @Inject
    QOLClicksConfig config;

    @Inject
    Automation automation;

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (config.enableKeybinds())
        {
            if (e.getKeyCode() == config.dropMatchingKeybind().getKeyCode())
            {
                clientThread.invoke(() -> automation.dropMatching());
                e.consume();
                return;
            }
            if (e.getKeyCode() == config.dropExceptKeybind().getKeyCode())
            {
                clientThread.invoke(() -> automation.dropExcept());
                e.consume();
                return;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
