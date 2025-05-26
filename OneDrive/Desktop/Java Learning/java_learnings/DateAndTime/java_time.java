package java_learnings.DateAndTime;

import java.time.LocalDate;
import java.time.LocalTime;

public class java_time {
    public static void main(String[] args) {
        LocalDate d = LocalDate.now(); 
        LocalTime t = LocalTime.now(); 
        System.out.println(t);
        System.out.println(d);
    }
}
