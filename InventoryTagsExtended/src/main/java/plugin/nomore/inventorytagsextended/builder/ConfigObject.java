package plugin.nomore.inventorytagsextended.builder;

import lombok.Builder;
import lombok.Data;

import java.awt.*;

@Builder
@Data
public class ConfigObject
{
    String name;
    int id;
    int quantity;
    int gePrice;
    Color color;
}
