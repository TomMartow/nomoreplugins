package net.runelite.client.plugins.nomoremenuindicators;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class NoMoreMenuIndicatorsOverlay extends Overlay {
    //combat , levels , quest , inventory , equipment , prayer , magic , friends , account , clan , settings , emote , music
    private final Client client;
    private final NoMoreMenuIndicatorsPlugin plugin;
    private final NoMoreMenuIndicatorsConfig config;


    @Inject
    NoMoreMenuIndicatorsOverlay(final Client client, final NoMoreMenuIndicatorsPlugin plugin, final NoMoreMenuIndicatorsConfig config) {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        Player player = client.getLocalPlayer();
        int boxSize = config.boxSize();
        if(client.isResized()){
        renderBox(graphics, client.getWidget(164,44).getBounds(), config.clanColor(),boxSize);
        renderBox(graphics, client.getWidget(164,45).getBounds(), config.accountColor(),boxSize);
        renderBox(graphics, client.getWidget(164,46).getBounds(), config.friendsColor(),boxSize);
        renderBox(graphics, client.getWidget(164,47).getBounds(), config.settingsColor(),boxSize);
        renderBox(graphics, client.getWidget(164,48).getBounds(), config.emoteColor(),boxSize);
        renderBox(graphics, client.getWidget(164,49).getBounds(), config.musicColor(),boxSize);
        renderBox(graphics, client.getWidget(164,60).getBounds(), config.combatColor(),boxSize);
        renderBox(graphics, client.getWidget(164,61).getBounds(), config.skillsColor(),boxSize);
        renderBox(graphics, client.getWidget(164,62).getBounds(), config.questColor(),boxSize);
        renderBox(graphics, client.getWidget(164,63).getBounds(), config.inventoryColor(),boxSize);
        renderBox(graphics, client.getWidget(164,64).getBounds(), config.equipmentColor(),boxSize);
        renderBox(graphics, client.getWidget(164,65).getBounds(), config.prayerColor(),boxSize);
        renderBox(graphics, client.getWidget(164,66).getBounds(), config.magicColor(),boxSize);
        renderBox(graphics, client.getWidget(164,32).getBounds(), config.logoutColor(),boxSize);
        renderBox(graphics, client.getWidget(182,8).getBounds(), config.logoutColor(),boxSize);}
        else{
            renderBox(graphics, client.getWidget(548,41).getBounds(), config.clanColor(),boxSize);
            renderBox(graphics, client.getWidget(548,42).getBounds(), config.accountColor(),boxSize);
            renderBox(graphics, client.getWidget(548,43).getBounds(), config.friendsColor(),boxSize);
            renderBox(graphics, client.getWidget(548,44).getBounds(), config.logoutColor(),boxSize);
            renderBox(graphics, client.getWidget(548,45).getBounds(), config.settingsColor(),boxSize);
            renderBox(graphics, client.getWidget(548,46).getBounds(), config.emoteColor(),boxSize);
            renderBox(graphics, client.getWidget(548,47).getBounds(), config.musicColor(),boxSize);
            renderBox(graphics, client.getWidget(548,58).getBounds(), config.combatColor(),boxSize);
            renderBox(graphics, client.getWidget(548,59).getBounds(), config.skillsColor(),boxSize);
            renderBox(graphics, client.getWidget(548,60).getBounds(), config.questColor(),boxSize);
            renderBox(graphics, client.getWidget(548,61).getBounds(), config.inventoryColor(),boxSize);
            renderBox(graphics, client.getWidget(548,62).getBounds(), config.equipmentColor(),boxSize);
            renderBox(graphics, client.getWidget(548,63).getBounds(), config.prayerColor(),boxSize);
            renderBox(graphics, client.getWidget(548,64).getBounds(), config.magicColor(),boxSize);
        }
        return null;
    }


    public void renderBox(Graphics2D graphics, Rectangle shapeClickBox, Color color, int boxSize) {
        if (shapeClickBox != null) {
            int x = (int) shapeClickBox.getCenterX() - boxSize / 2;
            int y = (int) shapeClickBox.getCenterY() - boxSize / 2;
            graphics.setColor(color);
            graphics.fillRect(x, y, boxSize, boxSize);
        }
    }
}
