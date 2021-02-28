package plugin.nomore.npchighlightingextended;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import plugin.nomore.npchighlightingextended.builder.HighlightingObject;
import plugin.nomore.npchighlightingextended.utils.Rendering;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;

public class NPCHighlightingExtendedOverlay extends Overlay
{

    @Inject
    private Client client;

    @Inject
    private NPCHighlightingExtendedPlugin plugin;

    @Inject
    private NPCHighlightingExtendedConfig config;

    @Inject
    private Rendering render;

    @Inject
    public NPCHighlightingExtendedOverlay()
    {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Player player = client.getLocalPlayer();
        if (player == null)
        {
            return null;
        }
        renderNPC(graphics, player);
        return null;
    }

    private void renderNPC(Graphics2D graphics, Player player)
    {
        List<HighlightingObject> npcsToHighlight = plugin.getNpcsToHighlight();
        for (HighlightingObject HIghlightingObject : npcsToHighlight)
        {
            NPC npc = HIghlightingObject.getNpc();
            Color color = HIghlightingObject.getColor();
            if (npc == null)
            {
                continue;
            }

            if (config.npcLineOfSight()
                    && !plugin.doesPlayerHaveALineOfSightToNPC(player, npc))
            {
                return;
            }

            switch (config.npcRenderStyle())
            {
                case BOX:
                    render.renderNPCCentreBox(graphics, npc, getNPCColor(npc, player, color), config.npcIndicatorSize());
                    showMouseOverlay(graphics, npc, color);
                    break;
                case HULL:
                    render.clickbox(graphics, client.getMouseCanvasPosition(), npc.getConvexHull(), getNPCColor(npc, player, color));
                    showMouseOverlay(graphics, npc, color);
                    break;
                case CLICKBOX:
                    render.hull(graphics, npc.getConvexHull(), getNPCColor(npc, player, color));
                    showMouseOverlay(graphics, npc, color);
                    break;
                case FILL:
                    render.fill(graphics, npc.getConvexHull(), getNPCColor(npc, player, color));
                    showMouseOverlay(graphics, npc, color);
                    break;
                case TILE_OUTLINE:
                    render.outline(graphics, npc.getConvexHull().getBounds(), client.getMouseCanvasPosition(), getNPCColor(npc, player, color));
                    showMouseOverlay(graphics, npc, color);
                    break;
                case TILE_FILLED:
                    render.fill(graphics, npc.getCanvasTilePoly(), getNPCColor(npc, player, color));
                    showMouseOverlay(graphics, npc, color);
                    break;
            }

        }
    }

    private Color getNPCColor(NPC npc, Player player, Color color)
    {
        if (config.npcEnableNPCDefaultColorOverrideWithNPCInteractingWithPlayer()
                && npc.getInteracting() != null
                && npc.getInteracting() == player)
        {
            return config.npcInteractingWithPlayerColor();
        }
        if (config.npcEnableNPCDefaultColorOverrideWithPlayersInteractingWithPlayer())
        {
            for (Player otherPlayer : plugin.getOtherPlayersList())
            {
                if (otherPlayer == null)
                {
                    continue;
                }
                if (player.getInteracting() != npc
                        && otherPlayer.getInteracting() != null
                        && otherPlayer.getInteracting() == npc)
                {
                    return config.npcPlayersInteractingWithNPCColor();
                }
            }
        }
        return color;
    }

    private void showMouseOverlay(Graphics2D graphics, NPC npc, Color color)
    {
        if (config.npcDisplayMouseHoveringIndicator()
                && render.isMouseHoveringOver(npc.getConvexHull(), client.getMouseCanvasPosition()))
        {
            render.canvasIndicator(graphics, render.getCanvasIndicatorLocation(config.npcMouseHoveringIndicatorLocation()), color);
        }
    }

}
