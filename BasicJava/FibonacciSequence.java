package java_learnings.BasicJava;

import java.util.Scanner;

public class FibonacciSequence {
     // Finding nth term using Reccusion:
    // public static void fib(int a , int b , int num){
    //     if(num == 0){
    //      return;   
    //     }
    //     int c = b + a ;
    //     System.out.print(c + " ");
    //     fib(b, c, num-1);
    // }

    // static void changename(String naam){
    //     naam = "rahul";
    //    System.out.println(naam);
       
    // }
    public static void main(String[] args) {
     System.out.print("Enter the term : ");
        Scanner sc = new Scanner(System.in);
            int n = sc.nextInt();
         int first = 0;
         int now = 1;
        if (n == 1) {
            System.out.println("Fib no. at " + n +" is :" + first);
        }
        else if (n == 2) {
              System.out.println("Fib no. at " + n +" is :" + now);
            }else{
        for (int i = 3; i <=n; i++) {
            int temp = first + now ;
            first = now ;
            now = temp ;
        }
        System.out.println("Fib no. at " + n +" is :" + now);
       }

          //  Finding all the term till nth using Reccusion:
      
        //   int a = 0 , b = 1 ;
        //   System.out.print(a + " " + b + " ");
        //   int num = 7;
        //   fib(a, b, num -2); // Cause we have already printed first two elements

        // String name = "kunal";
        // changename(name);
        //  System.out.println(name);
    
    }
}
 