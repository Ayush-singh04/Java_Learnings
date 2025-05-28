package java_learnings.DateAndTime;

import java.util.Calendar;
import java.util.TimeZone;

public class Calender_class {

    // The Calender class is an abstract class.
    public static void main(String[] args) {
     //   Calendar c =  Calendar.getInstance();
      //  System.out.println(c.getCalendarType()); // gregorian calender is the normal calender used everywhere.
       // System.out.println(c.getTimeZone());

       Calendar c =  Calendar.getInstance(TimeZone.getTimeZone("Asia/China"));
       // Calendar c =  Calendar.getInstance();
        System.out.println(c.getTimeZone().getID());
        System.out.println(c.getTime());
        
        System.out.println(c.get(Calendar.DATE));
        System.out.println(c.get(Calendar.DAY_OF_MONTH));
        System.out.println(c.get(Calendar.HOUR));
        System.out.println(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)+":"+ c.get(Calendar.SECOND));
    

    }       

}
