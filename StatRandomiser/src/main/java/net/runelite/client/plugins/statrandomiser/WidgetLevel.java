package net.runelite.client.plugins.statrandomiser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.widgets.Widget;

@AllArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class WidgetLevel {
    Widget boosted;
    Widget real;
}
