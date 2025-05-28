package java_learnings.ExceptionHandling;

import java.util.Scanner;
class Max_try extends Exception{
 public String getMessage(){ 
        return "You reached the Max. Level!";
       }
}
public class Practise_set_exception  {
    
    public static int marks(int i) throws Max_try{
         if(i>=5){
            throw new Max_try();
         }
         return  0;
    }
    
    public static void main(String[] args)  {
        
        // Q1- Write a programm to demonstrate [Syntax , Logical, Run-time errors]

                             //Sol-1.1
                /*int a = 10  // no semicolon (Syntax error)
                  b = 11; */  // no declearation of variable (Syntax error)
                
                            //Sol-1.2
             /*    for (int i = 1; i <=5; i++) {   // Here we want to get odd nos but we wrote the logic of even nos
                    System.out.println(2*i); } */

                            //Sol-1.3
         /*   try (Scanner sc = new Scanner(System.in)){
                System.out.println("Enter the no.");
                int x = sc.nextInt();
                System.out.println(100/x); // Here  if user entered an invalid no. so the programm will crash at run-time
            }*/ 

        // Q2- Write a program using arithmatic and illegal exception..
                        // Sol-3
       /*   try {
                int x = 555/0;
            } catch (ArithmeticException e) {
               System.out.println("HaHa");
            }catch(IllegalArgumentException e){
                System.out.println("HeHe");
            } */  
        
        // Q3- Write a java programm which acces an array till the correct index entered  
          //- and after 5 retries given Error.   
                    // Sol-3 , Sol-4
                int [] marks ={100,95,65,68,80};
                try (Scanner sc = new Scanner(System.in)){
                 for (int i = 1; i <=5; i++) {
                   System.out.println("Enter the index:");
                    int index =sc.nextInt(); 
                 try {
                         marks(i);
                } catch (Max_try e) {
                    System.out.println(e.getMessage());
                } 
                try{
                    System.out.println("Marks at "+index+" are "+marks[index]);
                    break;
                }catch(Exception e){
                    if (i<5) {
                    System.out.println("Invalid Index - Try Again..");
                    }
               }
               
                if (i>=5) {
                     System.out.println("Time over ");
                    }
                  
              }
            }
            finally{
                System.out.println("Process finished!");
            }  
            
        // Q4- Throw a custom exception in Q3 for max tries..
    }
}
