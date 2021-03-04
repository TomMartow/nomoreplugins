package plugin.nomore.qolclicksbeta.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.Point;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import plugin.nomore.qolclicksbeta.QOLClicksBetaConfig;
import plugin.nomore.qolclicksbeta.builds.BuiltInventoryItem;
import plugin.nomore.qolclicksbeta.menu.scene.Inventory;

import javax.inject.Inject;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Automation
{

    @Inject
    Client client;

    @Inject
    QOLClicksBetaConfig config;

    @Inject
    Utils utils;

    @Inject
    Inventory inventory;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    boolean busy = false;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    boolean readyToDrop = false;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    MenuEntry targetMenu = null;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    Point clickedPoint = new Point(0,0);

    public void dropItems()
    {
        if (config.dropMatching())
        {
            dropMatching();
        }
        if (config.dropExcept())
        {
            dropExcept();
        }
    }

    private void dropMatching()
    {
        List<BuiltInventoryItem> dropList = new ArrayList<>();
        String[] configExceptTextBoxStrings = utils.rws(config.matchingList()).split(",");
        for (WidgetItem item : inventory.getItems())
        {
            if (item != null)
            {
                if (Arrays.stream(configExceptTextBoxStrings)
                        .anyMatch(cIN
                                -> utils.rws(client.getItemDefinition(item.getId()).getName())
                                .equalsIgnoreCase(cIN)))
                {
                    dropList.add(BuiltInventoryItem.builder()
                            .widgetItem(item)
                            .name(client.getItemDefinition(item.getId()).getName())
                            .build());
                }
                else
                {
                    dropList.add(null);
                }
            }
        }
        dropItems(sortDropListOrder(dropList));
    }

    private void dropExcept()
    {
        List<BuiltInventoryItem> dropList = new ArrayList<>();
        String[] configExceptTextBoxStrings = utils.rws(config.exceptList()).split(",");
        for (WidgetItem item : inventory.getItems())
        {
            if (item != null)
            {
                if (Arrays.stream(configExceptTextBoxStrings)
                        .noneMatch(cIN
                                -> utils.rws(client.getItemDefinition(item.getId()).getName())
                                .equalsIgnoreCase(cIN)))
                {
                    dropList.add(BuiltInventoryItem.builder()
                            .widgetItem(item)
                            .name(client.getItemDefinition(item.getId()).getName())
                            .build());
                }
                else
                {
                    dropList.add(null);
                }
            }
        }
        dropItems(sortDropListOrder(dropList));
    }

    public void dropItems(List<BuiltInventoryItem> items)
    {
        setBusy(true);
        new Thread(() ->
        {
            for (BuiltInventoryItem inventoryItem : items)
            {
                setTargetMenu(new MenuEntry(
                        "Drop",
                        "<col=ff9040>" + inventoryItem.getName(),
                        inventoryItem.getWidgetItem().getId(),
                        MenuAction.ITEM_FIFTH_OPTION.getId(),
                        inventoryItem.getWidgetItem().getIndex(),
                        WidgetInfo.INVENTORY.getId(),
                        false
                ));
                clickC(inventoryItem.getWidgetItem().getCanvasBounds());
                try
                {
                    Thread.sleep(getRandomIntBetweenRange(Integer.parseInt(config.minTime()), Integer.parseInt(config.maxTime())));
                }
                catch (InterruptedException e)
                {
                    setBusy(false);
                    setReadyToDrop(false);
                    e.printStackTrace();
                }
            }
            setBusy(false);
            setReadyToDrop(false);
        }).start();
    }

    public List<BuiltInventoryItem> sortDropListOrder(List<BuiltInventoryItem> items)
    {
        String[] dropOrder = utils.rws(config.listOrder()).split(",");
        List<BuiltInventoryItem> sortedDropItems = new ArrayList<>();
        for (int i = 0; i < dropOrder.length; i++)
        {
            try
            {
                BuiltInventoryItem item = items.get(Integer.parseInt(dropOrder[i]));
                if (item == null)
                {
                    continue;
                }
                sortedDropItems.add(item);
            }
            catch (IndexOutOfBoundsException ignored)
            {

            }
        }
        return sortedDropItems;
    }

    public int getCentreX(WidgetItem item)
    {
        return (int) item.getCanvasBounds().getCenterX();
    }

    public int getCentreY(WidgetItem item)
    {
        return (int) item.getCanvasBounds().getCenterY();
    }

    public Point getRandomPointWithinBounds(Rectangle rect)
    {
        if (rect == null)
        {
            return null;
        }

        int x = ThreadLocalRandom.current().nextInt((int) rect.getX(), (int) rect.getX() + (int) rect.getWidth());
        int y = ThreadLocalRandom.current().nextInt((int) rect.getY(), (int) rect.getY() + (int) rect.getHeight());
        return new Point(x, y);
    }

    public Point getRandomPointFromCentre(Rectangle rect, int pixelSize)
    {

        if (rect == null)
        {
            return null;
        }

        int x1 = (int) rect.getCenterX() - pixelSize;
        int y1 = (int) rect.getCenterY() - pixelSize;
        int x2 = (int) rect.getCenterX() + pixelSize;
        int y2 = (int) rect.getCenterY() + pixelSize;

        return new Point(ThreadLocalRandom.current().nextInt(x1, x2), ThreadLocalRandom.current().nextInt(y1, y2));
    }

    public Point getClickPoint(Rectangle rect)
    {

        final int x = (int) (rect.getX() + ThreadLocalRandom.current().nextInt((int) rect.getWidth() / 6 * -1, (int) rect.getWidth() / 6) + rect.getWidth() / 2);
        final int y = (int) (rect.getY() + ThreadLocalRandom.current().nextInt((int) rect.getHeight() / 6 * -1, (int) rect.getHeight() / 6) + rect.getHeight() / 2);

        return new Point(x, y);
    }

    public Point getClientBounds()
    {
        int x = client.getCanvas().getX();
        int y = client.getCanvas().getY();
        int width = client.getCanvasWidth();
        int height = client.getCanvasHeight();
        return new Point(ThreadLocalRandom.current().nextInt(x, x + width), ThreadLocalRandom.current().nextInt(y, y + height));
    }

    public Point getRandomPoint(Rectangle rect)
    {
        final int x = getRandomIntBetweenRange((int) rect.getX(), (int) rect.getX() + (int) rect.getWidth());
        final int y = getRandomIntBetweenRange((int) rect.getY(), (int) rect.getY() + (int) rect.getHeight());

        return new Point(x, y);
    }

    public int getRandomIntBetweenRange(int min, int max)
    {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

    public void clickC(Rectangle rectangle)
    {
        assert !client.isClientThread();
        Point point = getClickPoint(rectangle);
        click(point);
    }

    public void clickR(Rectangle rectangle)
    {
        assert !client.isClientThread();
        Point point = getRandomPoint(rectangle);
        click(point);
    }

    public void click(Point p)
    {
        assert !client.isClientThread();

        setClickedPoint(p);

        if (client.isStretchedEnabled())
        {
            final Dimension stretched = client.getStretchedDimensions();
            final Dimension real = client.getRealDimensions();
            final double width = (stretched.width / real.getWidth());
            final double height = (stretched.height / real.getHeight());
            final net.runelite.api.Point point = new Point((int) (p.getX() * width), (int) (p.getY() * height));
            mouseEvent(501, point);
            mouseEvent(502, point);
            mouseEvent(500, point);
            return;
        }
        mouseEvent(501, p);
        mouseEvent(502, p);
        mouseEvent(500, p);
    }

    private void mouseEvent(int id, Point point)
    {
        MouseEvent e = new MouseEvent(
                client.getCanvas(), id,
                System.currentTimeMillis(),
                0, point.getX(), point.getY(),
                1, false, 1
        );

        client.getCanvas().dispatchEvent(e);
    }

}
