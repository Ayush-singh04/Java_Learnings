package java_learnings.BasicJava;

import java.util.Scanner;
public class oddeven {

 public static void main(String[]args)

  {
    System.out.println("Enter the number:");   
     try (Scanner sc = new Scanner(System.in)) {
      // checking 'Odd and Even'
      int num=sc.nextInt();
        
      if(num%2==0){
       System.out.println("You have entered an EVEN number.");
 } 
      else{
       System.out.println("You have entered a ODD number .");
 }
    } 
}


}
