package java_learnings.ExceptionHandling;

import java.util.Scanner;

public class Errors {
    public static void main(String[] args) {
        /*  Errors are of three types:
            1- Syntax error (Error in the syntax while writing the programm)
            2- Logical error
            3- Runtime error
        */
            // LOGICAL EORROR--(to print prime nos. less than 10.)
         /*      System.out.println("2");
        for (int i = 1; i < 5; i++) {
            System.out.println(2*i+1); // Here we have written the logic of odd nos. so it's a Logical error.
         }
        */   
  // RUN-TIME ERROR-- (error comes while running the program or crashes the programm)

            int k;
       try(Scanner sc = new Scanner(System.in)){
            k = sc.nextInt();
  //- IF USER ENTERS ANYTHING OTHER THAN INTEGER SO PROGRAMM WILL GET CRASHED AND THROWS A RUN-TIME ERROR
        System.out.println("integer of 1000 divded by "+k+" is "+1000/k);
    }
  }
}
