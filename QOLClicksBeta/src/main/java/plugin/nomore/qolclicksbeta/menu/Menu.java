package plugin.nomore.qolclicksbeta.menu;

import net.runelite.api.MenuAction;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.MenuOptionClicked;
import plugin.nomore.qolclicksbeta.highlighting.Arrow;

import javax.inject.Inject;

public class Menu
{

    @Inject
    private Arrow arrow;

    public void onOpen(MenuOpened e)
    {

    }

    public void onAdded(MenuEntryAdded e)
    {

    }

    public void onClicked(MenuOptionClicked e)
    {
        arrow.draw(e);

        debugMessage(e);
    }

    private void debugMessage(MenuOptionClicked e)
    {
        if (e.getMenuAction() == MenuAction.WALK)
        {
            return;
        }
        System.out.println("Option: " + e.getMenuOption() + "\n" +
                "Target: " + e.getMenuTarget() + "\n" +
                "ID: " + e.getId() + "\n" +
                "MenuAction: " + e.getMenuAction() + "\n" +
                "ActionParam: " + e.getActionParam() + "\n" +
                "WidgetID: " + e.getWidgetId() + "\n"
        );
    }

}
