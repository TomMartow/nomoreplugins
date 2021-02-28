package plugin.nomore.qolclicksbeta;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.util.ImageUtil;
import plugin.nomore.qolclicksbeta.builds.BuiltNPC;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class QOLClicksBetaOverlay extends Overlay
{

    @Inject
    private Client client;

    @Inject
    private QOLClicksBetaPlugin plugin;

    @Inject
    private QOLClicksBetaConfig config;

    @Inject
    public QOLClicksBetaOverlay() {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    static final BufferedImage arrow = ImageUtil.loadImageResource(QOLClicksBetaPlugin.class, "/arrow.png");
    static final BufferedImage background = ImageUtil.loadImageResource(QOLClicksBetaPlugin.class, "/background1.png");
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
        if (System.currentTimeMillis() < builtNPC.getSystemTime() + 500)
        {
            renderIcon(graphics, builtNPC.getNpc().getConvexHull(), arrow, 0, -20);
        }
        else if (System.currentTimeMillis() < builtNPC.getSystemTime() + 1000)
        {
            renderIcon(graphics, builtNPC.getNpc().getConvexHull(), arrow, 0, -20);
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
