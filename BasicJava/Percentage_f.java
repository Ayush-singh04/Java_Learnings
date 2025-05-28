package java_learnings.BasicJava;

import java.util.Scanner;
public class Percentage_f{
    public static void main(String[] args) {
    
        System.out.println("Find The percentage of 5 numbers : ");

          try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the 1st no.");
              int a = sc.nextInt();
               System.out.println("Enter the 2nd no.");
                 int b = sc.nextInt();
                  System.out.println("Enter the 3rd no.");
                int c = sc.nextInt();
               System.out.println("Enter the 4th no.");
              int d = sc.nextInt();
            System.out.println("Enter the 5th no.");
     int e = sc.nextInt();

       int sum = a+b+c+d+e;
   
       float prc= sum*100/500f;
   
      System.out.println("Your percentage is : "+prc);
          }


    }
}