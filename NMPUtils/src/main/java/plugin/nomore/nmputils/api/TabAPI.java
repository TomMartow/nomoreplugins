package plugin.nomore.nmputils.api;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;

public class TabAPI
{

    @Inject
    private Client client;

    public static int COMBAT = 0;
    public static int SKILLS = 1;
    public static int QUESTS = 2;
    public static int IVENTORY = 3;
    public static int EQUIPMENT = 4;
    public static int PRAYER = 5;
    public static int SPELLBOOK = 6;
    public static int FRIENDS = 7;
    public static int CLAN = 8;
    public static int ACCOUNT_MANAGEMENT = 9;
    public static int LOGOUT = 10;
    public static int OPTIONS = 11;
    public static int EMOTES = 12;
    public static int MUSIC = 13;

    public void open(int tab)
    {
        if (client == null || client.getGameState() != GameState.LOGGED_IN)
        {
            return;
        }
        client.runScript(915, tab);
    }

    public boolean isInventoryOpen()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return false;
        }

        return !client.getWidget(WidgetInfo.INVENTORY).isHidden();
    }

    public boolean isSpellbookOpen()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return false;
        }

        return !client.getWidget(WidgetInfo.SPELLBOOK).isHidden();
    }

}
