package plugin.nomore.qolclicksbeta.highlighting;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.MenuAction;
import net.runelite.api.NPC;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.queries.GameObjectQuery;
import net.runelite.api.queries.NPCQuery;
import plugin.nomore.qolclicksbeta.builds.BuiltNPC;
import plugin.nomore.qolclicksbeta.builds.BuiltObject;

import javax.inject.Inject;
import java.util.Set;

public class Arrow
{

    @Inject
    Client client;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    static BuiltNPC builtNPC = null;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    static BuiltObject builtObject = null;

    Set<MenuAction> npcMenuActions = ImmutableSet.of(
            MenuAction.NPC_FIRST_OPTION,
            MenuAction.NPC_SECOND_OPTION,
            MenuAction.NPC_THIRD_OPTION,
            MenuAction.NPC_FOURTH_OPTION,
            MenuAction.NPC_FIFTH_OPTION,
            MenuAction.EXAMINE_NPC,
            MenuAction.ITEM_USE_ON_NPC,
            MenuAction.SPELL_CAST_ON_NPC
    );

    Set<MenuAction> objectMenuActions = ImmutableSet.of(
            MenuAction.GAME_OBJECT_FIRST_OPTION,
            MenuAction.GAME_OBJECT_SECOND_OPTION,
            MenuAction.GAME_OBJECT_THIRD_OPTION,
            MenuAction.GAME_OBJECT_FOURTH_OPTION,
            MenuAction.GAME_OBJECT_FIFTH_OPTION,
            MenuAction.EXAMINE_OBJECT,
            MenuAction.ITEM_USE_ON_GAME_OBJECT,
            MenuAction.SPELL_CAST_ON_GAME_OBJECT
    );

    public void draw(MenuOptionClicked event)
    {
        if (npcMenuActions.contains(event.getMenuAction()))
        {
            setBuiltObject(null);
            setBuiltNPC(BuiltNPC.builder()
                    .npc(getNpc(event))
                    .systemTime(System.currentTimeMillis())
                    .build());
        }
        if (objectMenuActions.contains(event.getMenuAction()))
        {
            setBuiltNPC(null);
            setBuiltObject(BuiltObject.builder()
                    .gameObject(getGameObject(event))
                    .systemTime(System.currentTimeMillis())
                    .build());
        }
    }

    private NPC getNpc(MenuOptionClicked e)
    {
        return new NPCQuery()
                .result(client)
                .stream()
                .filter(npc1 -> npc1 != null
                        && npc1.getIndex() == e.getId())
                .findFirst()
                .orElse(null);
    }

    private GameObject getGameObject(MenuOptionClicked e)
    {
        return new GameObjectQuery()
                .result(client)
                .stream()
                .filter(object -> object != null
                        && object.getId() == e.getId()
                        && object.getSceneMinLocation().getX() == e.getActionParam()
                        && object.getSceneMinLocation().getY() == e.getWidgetId())
                .findFirst()
                .orElse(null);
    }
}
