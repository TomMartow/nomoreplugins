package plugin.nomore.qolclicks.utils;

import javax.inject.Inject;

public class Sleep
{

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
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}