package java_learnings.BasicJava;


import java.util.Scanner;

public class userinput
{
  public static void main(String[]args)
  {
  
    System.out.println("Adding two numbers");
     
     try (Scanner $ = new Scanner(System.in)) {
      System.out.println("enter the first number:");
      int A=$.nextInt();

      System.out.println("enter the second number:");
      int B=$.nextInt();

      System.out.println("The sum of these number's is: " + (A+B) );
    }
  }  
}
