package plugin.nomore.qolclicks;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.util.ImageUtil;
import plugin.nomore.qolclicks.builds.BuiltNPC;
import plugin.nomore.qolclicks.builds.BuiltObject;
import plugin.nomore.qolclicks.highlighting.Arrow;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class QOLClicksOverlay extends Overlay
{

    @Inject
    private Client client;

    @Inject
    private QOLClicksPlugin plugin;

    @Inject
    private QOLClicksConfig config;

    @Inject
    private Arrow arrow;

    @Inject
    public QOLClicksOverlay() {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    static final BufferedImage arrowIcon = ImageUtil.loadImageResource(QOLClicksPlugin.class, "/arrow.png");

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (arrow.getBuiltNPC() != null)
        {
            renderNPC(graphics, arrow.getBuiltNPC());
        }
        if (arrow.getBuiltObject() != null)
        {
            renderObject(graphics, arrow.getBuiltObject());
        }
        return null;
    }

    private void renderNPC(Graphics2D graphics, BuiltNPC builtNPC)
    {
        long timeNow = System.currentTimeMillis();
        long npcTime = builtNPC.getSystemTime();
        if (timeNow < npcTime + 500 || (timeNow < npcTime + 1500 && timeNow > npcTime + 1000))
        {
            renderIcon(graphics, builtNPC.getNpc().getConvexHull(), arrowIcon, 0, (int) - (builtNPC.getNpc().getModelHeight() * 0.5));
        }
        if (timeNow > npcTime + 1500)
        {
            arrow.setBuiltNPC(null);
        }
    }

    private void renderObject(Graphics2D graphics, BuiltObject builtObject)
    {
        long timeNow = System.currentTimeMillis();
        long objectTime = builtObject.getSystemTime();
        if (timeNow < objectTime + 500 || (timeNow < objectTime + 1500 && timeNow > objectTime + 1000))
        {
            renderIcon(graphics, builtObject.getGameObject().getClickbox(), arrowIcon, 0, -25);
        }
        if (timeNow > objectTime + 1500)
        {
            arrow.setBuiltObject(null);
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
