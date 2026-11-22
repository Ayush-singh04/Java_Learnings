package java_learnings.DateAndTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date_time_formatter {
    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.now();
        System.out.println(dt);

    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //This is the format we want to show the date
            String myDate = df.format(dt); // Here we gave refrence to the formater of the current date.
                    System.out.println(myDate);
                // Here are some diffrent methods to format Date and time--
    DateTimeFormatter df2 = DateTimeFormatter.ISO_LOCAL_DATE;
           String myDate2 = dt.format(df2);
                    System.out.println(myDate2); 
    }
}
