package plugin.nomore.interactionhud;

import com.google.common.graph.Graph;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Perspective;
import net.runelite.api.TileObject;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.util.ImageUtil;
import org.jetbrains.annotations.NotNull;
import plugin.nomore.interactionhud.builds.BuiltNPC;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;

public class InteractionHUDOverlay extends Overlay
{

    @Inject
    private Client client;

    @Inject
    private InteractionHUDPlugin plugin;

    @Inject
    private InteractionHUDConfig config;

    @Inject
    public InteractionHUDOverlay() {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    static final BufferedImage background = ImageUtil.getResourceStreamFromClass(InteractionHUDPlugin.class, "/background1.png");
    static final int backgroundHeight = 60;
    static final int backgroundWidth = 45;

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (plugin.getBuiltNPC() != null)
        {
            renderNPC(graphics, plugin.getBuiltNPC());
        }
        return null;
    }

    private void renderNPC(Graphics2D graphics, BuiltNPC builtNPC)
    {
        if (System.currentTimeMillis() < builtNPC.getSystemTime() + 2500)
        {
            renderIcon(graphics, builtNPC.getNpc().getConvexHull(), background, 0, - (int) (backgroundHeight * 0.5));
            renderIcon(graphics, builtNPC.getNpc().getConvexHull(), plugin.getImage(11328), 0, - (int) (backgroundHeight * 0.5));
        }
    }

    private void renderIcon(Graphics2D graphics, Shape shape, BufferedImage icon, int x, int y)
    {
        if (shape == null)
        {
            return;
        }
        renderIcon(graphics, shape.getBounds(), icon, x, y);
    }

    private void renderIcon(Graphics2D graphics, Rectangle bounds, BufferedImage icon, int x, int y)
    {
        if (bounds == null)
        {
            return;
        }
        int cX = (int) bounds.getCenterX();
        int cY = (int) bounds.getCenterY();
        graphics.drawImage(icon, cX + x - icon.getWidth() / 2, cY + y - icon.getHeight() / 2, null);
    }

}
