package java_learnings.BasicJava;

import java.util.Scanner;
public class VotingRight 
{
 public static void main(String[]args){
         
     System.out.println("Enter your age :");
       
       try (Scanner sc = new Scanner(System.in)) {
          int age=sc.nextInt();
     if(age>=18){
          System.out.println("You are elegible to VOTE !");
     }
     else{
          System.out.println( " You are not elegible to VOTE !");

    }
     }

  }

}