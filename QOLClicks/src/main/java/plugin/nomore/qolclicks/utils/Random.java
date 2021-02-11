package plugin.nomore.qolclicks.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Random
{

    public int getIntBetween(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}