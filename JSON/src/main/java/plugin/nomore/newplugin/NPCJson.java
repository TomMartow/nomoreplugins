package plugin.nomore.newplugin;

import lombok.Getter;

@Getter
public class NPCJson
{

    private int id;
    private String name;
    private String[] actions;

    public NPCJson(int id, String name, String[] actions)
    {
        this.id = id;
        this.name = name;
        this.actions = actions;
    }
}
