package java_learnings.DateAndTime;

import java.util.Date;

public class Date_and_time {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis()); // Shows the time from 1/jan/1970 in millis.
    
 //      System.out.println(System.currentTimeMillis()/1000);// to check the seconds from 1970.
  //     System.out.println(System.currentTimeMillis()/1000/60);// to check the minutes from 1970.
   //    System.out.println(System.currentTimeMillis()/1000/3600);// to check the hours from 1970.
    //   System.out.println(System.currentTimeMillis()/1000/3600/24);// to check the Days  from 1970.
     //  System.out.println(System.currentTimeMillis()/1000/3600/24/365);// to check the years from 1970.

        Date d = new Date(); // Date Class
        System.out.println(d);
       // System.out.println(d.getTime());
       
        
        
    }
    
}
