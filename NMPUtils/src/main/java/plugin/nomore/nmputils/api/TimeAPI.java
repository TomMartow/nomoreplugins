package plugin.nomore.nmputils.api;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeAPI
{

    public LocalTime getCurrentTime()
    {
        return LocalTime.now();
    }

    public LocalDate getCurrentDate()
    {
        return LocalDate.now();
    }

}
