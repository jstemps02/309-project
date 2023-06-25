package backend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarHandler {
    public static String CalendarToString(Calendar cal){
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return format.format(cal.getTime());
    }
    //DON'T KNOW WHERE TO PUT********
    public Calendar StringtoCalendar(String str) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        cal.setTime(sdf.parse(str));// all done
        return cal;
    }
}
