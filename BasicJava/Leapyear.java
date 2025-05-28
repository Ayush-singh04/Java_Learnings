package java_learnings.BasicJava;

import java.util.Scanner;
public class Leapyear{
    public static void main(String[] args) {
       
        System.out.println("Enter the Year :");
         
         try (Scanner sc = new Scanner(System.in)) {
            int year = sc.nextInt();
            
                if( (year%400 == 0)||((year%4 == 0)&&(year%100 != 0))){

                    System.out.println(year + " is a leap year ! ");

             }
                else{

                    System.out.println(" Sorry "+ year + " is not a leap year ");
            }
        }
    }
}