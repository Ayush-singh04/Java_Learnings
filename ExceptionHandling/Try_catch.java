package java_learnings.ExceptionHandling;

import java.util.Scanner;

public class Try_catch {
    public static void main(String[] args) {
      /*
        int a = 1500;
        int b = 0; // Zero can't be in numerator, so it will throw exception(error).
        
        // So, we will surround it with try and catch block to hadle the exception.
       try{
            int c = a/b;
        System.out.println(c);
        }
        catch(Exception e){
            System.out.print("Sorry! we failed to divide. Reason: ");
            System.out.println(e);
        }
        System.out.println("Process Finished!");
     */

        // Handling specific exception..int try_catch.
    /*
       int [] marks = {31,43,44,56,70}; 
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the index: ");
          int  index = sc.nextInt();

        System.out.println("Enter the no. you want to dived with");
            int numb = sc.nextInt();
        try{
            System.out.println("The marks at "+index+ " is "+marks[index]);
            System.out.println("Division with the number "+numb+ " is "+marks[index]/numb);
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException occured: "+e);
        }
        catch(ArithmeticException e){
            System.out.println(" ArithmeticException occured  "+e);
        }
        catch(Exception e){
            System.out.println("Some other exception occured: "+e);
        }
      */

            // NESTED TRY AND CATCH---
        
        int []numbers={45,46,65,72,27};
       try(Scanner sc = new Scanner(System.in)){
         boolean error = true;
        while (error) {
         System.out.println("Enter the index: ");
          int  ind = sc.nextInt();
          
        try{
            System.out.println("Welcome Home");
        try{
            System.out.println(numbers[ind]);
            error = false;
        }
                catch(ArrayIndexOutOfBoundsException e){
                          System.out.println("Index length is out of bound! in level 2 ");
                         }

                    }catch(Exception e){
                     System.out.println("Exception in level 1 ");
                } 
             }
        }
                     System.out.println("Thanks for using!");
    }
}
