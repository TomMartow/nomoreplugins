package plugin.nomore.nmputils.api;

import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import plugin.nomore.nmputils.NMPConfig;

import javax.inject.Inject;

public class DebugAPI
{

    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Inject
    private NMPConfig config;

    @Inject
    private ChatMessageManager chatMessageManager;

    // API Injects

    @Inject
    private TimeAPI time;

    public void log(String string)
    {
        System.out.println("[" + time.getCurrentTime() + "]: " + string);
    }
}
