package plugin.nomore.nmputils.api;

import javax.inject.Inject;

public class SleepAPI
{

    @Inject
    private DebugAPI debug;

    public void forXTicks(int delay)
    {
        try
        {
            Thread.sleep((int) (delay * 0.6));
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void forXMillis(int delay)
    {
        try
        {
            debug.log("Sleeping for: " + delay + " milliseconds.");
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
