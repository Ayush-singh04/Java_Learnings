package java_learnings.DateAndTime;


import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Gregorian_calender {
    public static void main(String[] args) {
        GregorianCalendar cal = new GregorianCalendar();
            System.out.println(cal.isLeapYear(2020));
            System.out.println(TimeZone.getAvailableIDs()[0]);
            System.out.println(TimeZone.getAvailableIDs()[1]);
            System.out.println(TimeZone.getAvailableIDs()[100]);
           // System.out.println(TimeZone.getDefault());
    }
}
