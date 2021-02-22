package plugin.nomore.npchighlightingextended.builder;

import lombok.Builder;
import lombok.Data;

import java.awt.*;

@Builder
@Data
public class ConfigObject
{
    String name;
    int id;
    Color color;
}
