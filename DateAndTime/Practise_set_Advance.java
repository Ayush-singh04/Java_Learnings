package java_learnings.DateAndTime;

import java.util.Calendar;
/*import java.util.TimeZone;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
*/
public class Practise_set_Advance {
    public static void main(String[] args) {
       // Q1- Create an arraylist and store 10 students name in it and print the with the help of for-each-loop.
      //  Q2- Try to insert duplicate numbers inside a set and varify only no. is stored.
    //    Q3- Use date class to print time in "21:47:02" Format.
     //   Q4- repeat Q3 using calender class.
     //   Q5- repeat Q3 using java.time API.


                    // Sol-1
      /*   ArrayList <String> Students = new ArrayList<>();
            Students.add("Nakul");
             Students.add("Pankaj");
              Students.add("Rohan");
               Students.add("Dheeraj");
                Students.add("Shubham");
                Students.add("Jojo");
               Students.add("Tinku");
              Students.add("Rahul");
             Students.add("Vaibhav");
            Students.add("Suresh");

        for (String Names : Students) {
            System.out.println(Names);
        }*/
    
             // Sol-2
     /*     
            HashSet <Integer> numbers = new HashSet<>();
                numbers.add(10);
                 numbers.add(10);
                numbers.add(8);
            System.out.println(numbers);
         */ 

            // Sol-3
        /*   
            Date d = new Date();
            System.out.println(d.getHours()+ ":"+d.getMinutes()+":"+d.getSeconds());
         */

           // Sol-4
        
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)+":"+ cal.get(Calendar.SECOND));

            // Sol-5
         /* 
         LocalTime dt = LocalTime.now();
        DateTimeFormatter T = DateTimeFormatter.ISO_LOCAL_TIME;
            String Time = dt.format(T);
                System.out.println(Time);
          */ 
    }
}
