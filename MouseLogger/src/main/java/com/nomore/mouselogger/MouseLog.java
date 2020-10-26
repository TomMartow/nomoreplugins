package com.nomore.mouselogger;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.input.MouseListener;
import net.runelite.client.input.MouseManager;

import javax.inject.Inject;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MouseLog implements MouseListener {

    public long clickStart = 0;
    public long clickEnd = 0;
    public long lastClickStart = 0;
    public long lastClickEnd = 0;

    private final FileManager fileManager;
    private final Client client;
    private final AMiscPlugin plugin;
    private final MouseManager mouseManager;
    private final AMiscConfig config;

    @Inject
    public MouseLog(final Client client,
                    final FileManager fileManager,
                    final AMiscPlugin plugin,
                    final MouseManager mouseManager,
                    final AMiscConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.fileManager = fileManager;
        this.mouseManager = mouseManager;
        this.config = config;
    }

    private boolean recordMouse = false;

    @Override
    public MouseEvent mouseClicked(MouseEvent mouseEvent) {
        log(mouseEvent);
        return mouseEvent;
    }

    @Override
    public MouseEvent mousePressed(MouseEvent mouseEvent) {
        if (client.getGameState() == GameState.LOGGED_IN) {
            clickStart = System.currentTimeMillis();
            lastClickEnd = clickStart;
        }
        log(mouseEvent);
        return mouseEvent;
    }

    @Override
    public MouseEvent mouseReleased(MouseEvent mouseEvent) {
        if (client.getGameState() == GameState.LOGGED_IN) {
            clickEnd = System.currentTimeMillis();
            String mouseButton = null;
            if (mouseEvent.getButton() == 1) {
                mouseButton = "l";
            }
            if (mouseEvent.getButton() == 2) {
                mouseButton = "m";
            }
            if (mouseEvent.getButton() == 3) {
                mouseButton = "r";
            }
            if ((mouseEvent.getX() != 0 || mouseEvent.getY() != 0) && mouseButton != null) {
                if (config.clickLog()) {
                    if (config.separateInventoryLog()) {
                        int x = mouseEvent.getX();
                        int y = mouseEvent.getY();
                        int cWidth = client.getCanvasWidth();
                        int cHeight = client.getCanvasHeight();
                        int invX = cWidth - 204;
                        int invY = cHeight - 275 - 72;
                        int invWidth = invX + 204;
                        int invHeight = invY + 275;
                        if (x >= invX && x <= invWidth &&
                                y >= invY && y <= invHeight) {
                            x = mouseEvent.getX() - invX;
                            y = mouseEvent.getY() - invY;
                            if (config.invertYClickLog()) {
                                fileManager.writeToFile(fileManager.getInventoryFile(), mouseButton + ", " + x + ", -" + y + ", " + (clickEnd - clickStart) + ", " + (lastClickEnd - lastClickStart));
                            } else {
                                fileManager.writeToFile(fileManager.getInventoryFile(), mouseButton + ", " + x + ", " + y + ", " + (clickEnd - clickStart) + ", " + (lastClickEnd - lastClickStart));
                            }
                        }
                    }
                    if (config.invertYClickLog()) {
                        fileManager.writeToFile(fileManager.getClickFile(), mouseButton + ", " + mouseEvent.getX() + ", -" + mouseEvent.getY() + ", " + (clickEnd - clickStart) + ", " + (lastClickEnd - lastClickStart));
                    } else {
                        fileManager.writeToFile(fileManager.getClickFile(), mouseButton + ", " + mouseEvent.getX() + ", " + mouseEvent.getY() + ", " + (clickEnd - clickStart) + ", " + (lastClickEnd - lastClickStart));
                    }
                }
            }
            lastClickStart = clickEnd;
        }
        log(mouseEvent);
        return mouseEvent;
    }

    @Override
    public MouseEvent mouseEntered(MouseEvent mouseEvent) {
        recordMouse = true;
        log(mouseEvent);
        return mouseEvent;
    }

    @Override
    public MouseEvent mouseExited(MouseEvent mouseEvent) {
        recordMouse = false;
        log(mouseEvent);
        return mouseEvent;
    }

    @Override
    public MouseEvent mouseDragged(MouseEvent mouseEvent) {
        return mouseEvent;
    }

    @Override
    public MouseEvent mouseMoved(MouseEvent mouseEvent) {
        if (client.getGameState() == GameState.LOGGED_IN) {
            if (recordMouse) {
                if (config.positionLog()) {
                    if (config.invertYPositionLog()) {
                        fileManager.writeToFile(fileManager.getPositionFile(), mouseEvent.getX() + ", -" + mouseEvent.getY());
                    } else {
                        fileManager.writeToFile(fileManager.getPositionFile(), mouseEvent.getX() + ", " + mouseEvent.getY());
                    }
                }
            }
        }
        return mouseEvent;
    }

    public void log(MouseEvent e)
    {
        if (!config.sysPrintMouseClick())
        {
            return;
        }
        System.out.println("-------------------------"
                + "\n" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:S").format(new Date())
                + "\ngetLocationOnScreen: " + e.getLocationOnScreen()
                + "\ngetXOnScreen: " + e.getXOnScreen()
                + "\ngetYOnScreen: " + e.getYOnScreen()
                + "\ngetModifiersEx: " + e.getModifiersEx()
                + "\ngetX: " + e.getX()
                + "\ngetY: " + e.getY()
                + "\ngetPoint: " + e.getPoint()
                + "\ngetClickCount: " + e.getClickCount()
                + "\ngetButton: " + e.getButton()
                + "\nisPopupTrigger: " + e.isPopupTrigger()
                + "\nparamString: " + e.paramString());
    }
}
