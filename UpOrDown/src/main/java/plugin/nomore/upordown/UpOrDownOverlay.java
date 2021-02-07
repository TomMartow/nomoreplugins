package plugin.nomore.upordown;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.TileObject;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/*

<div>Icons made by <a href="https://www.flaticon.com/authors/pixel-perfect" title="Pixel perfect">Pixel perfect</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>

 */

public class UpOrDownOverlay extends Overlay
{

    @Inject
    private Client client;

    @Inject
    private UpOrDownPlugin plugin;

    @Inject
    public UpOrDownOverlay() {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        List<ElevationObject> elObjs = plugin.getElevationObjects();
        for (ElevationObject elObj : elObjs)
        {
            if (elObj.getGameObject() == null)
            {
                plugin.getElevationObjects().remove(elObj);
                continue;
            }
            renderObject(graphics, elObj);
        }
        return null;
    }

    private void renderObject(Graphics2D graphics, ElevationObject elObj)
    {
        Player player = client.getLocalPlayer();
        if (player == null)
        {
            return;
        }
        if (player.getWorldLocation().getPlane() != elObj.getTile().getPlane())
        {
            return;
        }
        Shape clickbox = elObj.getGameObject().getClickbox();
        if (clickbox == null)
        {
            return;
        }
        if (elObj.isDown() && elObj.isUp())
        {
            renderIcon(graphics, elObj.getGameObject(), ImageUtil.getResourceStreamFromClass(UpOrDownPlugin.class, "/up20.png"), 0, -15);
            renderIcon(graphics, elObj.getGameObject(), ImageUtil.getResourceStreamFromClass(UpOrDownPlugin.class, "/down20.png"), 0, +15);
        }
        else if (elObj.isUp())
        {
            renderIcon(graphics, elObj.getGameObject(), ImageUtil.getResourceStreamFromClass(UpOrDownPlugin.class, "/up20.png"), 0, 0);
        }
        else if (elObj.isDown())
        {
            renderIcon(graphics, elObj.getGameObject(), ImageUtil.getResourceStreamFromClass(UpOrDownPlugin.class, "/down20.png"), 0, 0);
        }
    }

    private void renderIcon(Graphics2D graphics, TileObject obj, BufferedImage icon, int x, int y)
    {
        if (obj == null)
        {
            return;
        }
        Shape shape = obj.getClickbox();
        if (shape == null)
        {
            return;
        }
        Rectangle bounds = shape.getBounds();
        int cX = (int) bounds.getCenterX();
        int cY = (int) bounds.getCenterY();
        graphics.drawImage(icon, cX + x - icon.getWidth() / 2, cY + y - icon.getHeight() / 2, null);
        //OverlayUtil.renderTextLocation(graphics, new Point((int) bounds.getMinX(), cY + y + icon.getHeight() + 5), client.getObjectDefinition(obj.getId()).getName(), Color.WHITE);
    }

}
