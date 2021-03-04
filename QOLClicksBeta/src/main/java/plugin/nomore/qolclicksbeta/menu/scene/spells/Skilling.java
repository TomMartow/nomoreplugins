package plugin.nomore.qolclicksbeta.menu.scene.spells;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.widgets.WidgetInfo;

@Getter
@AllArgsConstructor
public enum Skilling
{
    LOW_ALCH("Low Alch", WidgetInfo.SPELL_LOW_LEVEL_ALCHEMY),
    HIGH_ALCH("High Alch", WidgetInfo.SPELL_HIGH_LEVEL_ALCHEMY);

    private String name;
    private WidgetInfo spell;

    @Override
    public String toString()
    {
        return getName();
    }
}