package plugin.nomore.nmputils.api;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.PlayerDespawned;
import net.runelite.api.events.PlayerSpawned;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PlayersAPI
{

    @Inject
    private Client client;

    @Inject
    private StringAPI string;

    public static List<Player> playerList = new ArrayList<>();

    public void onPlayerSpawned(PlayerSpawned event)
    {
        if (event.getPlayer() != null)
        {
            playerList.add(event.getPlayer());
        }
    }

    public void onPlayerDespawned(PlayerDespawned event)
    {
        playerList.remove(event.getPlayer());
    }

    public boolean isIdle()
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return false;
        }

        return client.getLocalPlayer().getAnimation() == -1;
    }

    public List<Player> getPlayers()
    {
        return playerList;
    }

    public Player getPlayer(String playerName)
    {
        assert client.isClientThread();

        if (client.getLocalPlayer() == null)
        {
            return null;
        }

        return getPlayers()
                .stream()
                .filter(otherPlayer
                        -> otherPlayer != null
                        && otherPlayer.getName() != null
                        && string.removeWhiteSpaces(otherPlayer.getName())
                        .equalsIgnoreCase(playerName))
                .findFirst()
                .orElse(null);
    }

}
