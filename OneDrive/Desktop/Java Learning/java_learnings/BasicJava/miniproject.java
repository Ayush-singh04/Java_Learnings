package java_learnings.BasicJava;


import java.util.Scanner;
 public class miniproject {

public static void main(String[]args) {
   
    
    try (Scanner sc = new Scanner(System.in)) {
      int numb= (int)(Math.random()*100);
        int usernumb = 0;
do{
      System.out.println("Guess the no. between 0-100");
        usernumb = sc.nextInt();
        
         if (usernumb==numb){
          System.out.println("You guessed it right");
           break;  
   }
         else if(usernumb > numb){
          System.out.println("your number is too large ");
   } 
         else{
          System.out.println("your number is too small ");
   }
 }  
   while(usernumb >=0);
      System.out.print("My number was : ");
      System.out.println(numb);
    }

  } 
    
}






