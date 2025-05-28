package java_learnings.BasicJava;

import java.util.Scanner;
public class Unitconvertor{
    public static void main(String[] args) {
    
         System.out.println("Enter the length in Cm :");
           try (Scanner sc = new Scanner(System.in)) {
            double CM = sc.nextDouble();
         double M = CM/100;
          System.out.println(CM + "cm have "+ M + " meters in it ! ");
          }


    }
}