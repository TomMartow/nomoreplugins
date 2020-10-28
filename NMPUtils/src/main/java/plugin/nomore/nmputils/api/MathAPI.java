package plugin.nomore.nmputils.api;

import java.util.concurrent.ThreadLocalRandom;

public class MathAPI
{

    public int getRandomInt(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
