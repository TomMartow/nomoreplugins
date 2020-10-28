/*
 * Copyright (c) 2018, James Swindle <wilingua@gmail.com>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package plugin.nomore.interfacemarking;

import java.awt.*;
import java.util.regex.Pattern;
import javax.inject.Inject;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class TheOverlay extends Overlay
{

    private final Client client;
    private final ThePlugin plugin;
    private final TheConfig config;

    @Inject
    TheOverlay(final Client client, final ThePlugin plugin, final TheConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Widget bank = client.getWidget(WidgetInfo.BANK_CONTAINER);
        if (bank != null && !bank.isHidden())
        {
            if (config.bankOpen())
            {
                renderG(graphics, config.bankColor(), config.bankOpenLocation().split(Pattern.quote(":")));
            }
            Widget[] bankWidgets = client.getWidget(12, 2).getChildren();
            //close button
            if (bankWidgets[11] != null && config.bankClose())
            {
                displayOverWidget(graphics, bankWidgets[11], config.bankCloseColor());
            }
            //withdraw item
            Widget bankWithdrawItem = client.getWidget(12, 21);
            if (bankWithdrawItem != null && config.bankItem())
            {
                displayOverWidget(graphics, bankWithdrawItem, config.bankItemColor());
            }
            //withdraw note
            Widget bankWithdrawNote = client.getWidget(12, 23);
            if (bankWithdrawNote != null && config.bankNote())
            {
                displayOverWidget(graphics, bankWithdrawNote, config.bankNoteColor());
            }
            //quantity 1
            Widget bankWithdraw1 = client.getWidget(12, 27);
            if (bankWithdraw1 != null && config.bankQuantity1())
            {
                displayOverWidget(graphics, bankWithdraw1, config.bankQuantity1Color());
            }
            //quantity 5
            Widget bankWithdraw5 = client.getWidget(12, 29);
            if (bankWithdraw5 != null && config.bankQuantity5())
            {
                displayOverWidget(graphics, bankWithdraw5, config.bankQuantity5Color());
            }
            //quantity 10
            Widget bankWithdraw10 = client.getWidget(12, 31);
            if (bankWithdraw10 != null && config.bankQuantity10())
            {
                displayOverWidget(graphics, bankWithdraw10, config.bankQuantity10Color());
            }
            //quantity x
            Widget bankWithdrawX = client.getWidget(12, 33);
            if (bankWithdrawX != null && config.bankQuantityX())
            {
                displayOverWidget(graphics, bankWithdrawX, config.bankQuantityXColor());
            }
            //quantity all
            Widget bankWithdrawAll = client.getWidget(12, 35);
            if (bankWithdrawAll != null && config.bankQuantityAll())
            {
                displayOverWidget(graphics, bankWithdrawAll, config.bankQuantityAllColor());
            }
            //deposit items
            Widget bankDepositItems = client.getWidget(WidgetInfo.BANK_DEPOSIT_INVENTORY);
            if (bankDepositItems != null && config.bankDepositInventory())
            {
                displayOverWidget(graphics, bankDepositItems, config.bankDepositInventoryColor());
            }
            //deposit gear
            Widget bankDepositEquipment = client.getWidget(WidgetInfo.BANK_DEPOSIT_EQUIPMENT);
            if (bankDepositEquipment != null && config.bankDepositEquipment())
            {
                displayOverWidget(graphics, bankDepositEquipment, config.bankDepositEquipmentColor());
            }
        }
        if (config.displayDeposit()) // test
        {
            Widget deposit = client.getWidget(WidgetInfo.DEPOSIT_BOX_INVENTORY_ITEMS_CONTAINER);
            if (deposit != null && !deposit.isHidden())
            {
                renderG(graphics, config.depositColor(), config.depositLocation().split(Pattern.quote(":")));
            }
        }
        if (config.displayChatboxMake())
        {
            Widget chatboxMakeAll = client.getWidget(270, 12);
            if (chatboxMakeAll != null && !chatboxMakeAll.isHidden())
            {
                renderG(graphics, config.makeColor(), config.makeLocation().split(Pattern.quote(":")));
            }
        }
        if (!config.bankOpen())
        {
            //test
        }
        return null;
    }

    private void displayOverWidget(Graphics2D graphics, Widget widget, Color color)
    {
        Rectangle bounds = widget.getBounds();
        if (bounds == null)
        {
            return;
        }
        graphics.setColor(color);
        graphics.fillRect((int) bounds.getCenterX() - 2, (int) bounds.getCenterY() - 2, 4, 4);
    }

    private void renderG(Graphics2D graphics, Color color, String[] s)
    {
        graphics.setColor(color);
        graphics.fillRect(getParsedInt(s,0),
                getParsedInt(s,1),
                getParsedInt(s,2),
                getParsedInt(s,3));
    }

    private int getParsedInt(String[] strings, int number)
    {
        return Integer.parseInt(strings[number]);
    }
}
