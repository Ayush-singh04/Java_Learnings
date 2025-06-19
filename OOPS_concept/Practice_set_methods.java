package java_learnings.OOPS_concept;

public class Practice_set_methods{
    static void table(int n){       // for Q1
        System.out.println("The Table of " + n+ " is: ");
        for(int i=1;i<=10;i++){
           
          System.out.println(n+" X "+i+ " = "+n*i);                    

        } 
    }
         static void pattern(int n){    // for Q2
            for(int i=1;i<=n;i++){
               for(int j=1;j<=i;j++){
                System.out.print(" * ");
               }
               System.out.println(" "); 
            }
         }
         static int Sum(int n){
            if(n==1){
                return 1;           // for Q3
            }else{
                return n+Sum(n-1);
            }
          // int Addition=0;           
            //for(int i=1;i<=n;i++){
              //  Addition = Addition+   i; 
            //}
            //return Addition;
         } 

         static int fib(int n){
            //Fibonacci sequence - 0, 1, 1, 2, 3, 5, 8, 13, 21, 34
            if(n==1){
                return 0;
                                // for Q4
            }else if(n==2){
                return 1;

            }else{
                return fib(n-1) + fib(n-2);
            }
         }

         static float num(int ...set){
           float avg=0;
            float sum=0;
            for(int element:set){       // for Q5
                sum = sum +element;
               avg= sum/set.length;
                
         }

                return avg;

      }
    public static void main(String[] args) {
        // Q1- write a mutil. table of no. N.
         
            //  table(10);

        // Q2- make star pattern.

          //  pattern(4);

        // Q3- write a recursive function to find the sum of first n natural nos.

          //  System.out.println(" The sum of first "+ 6+" Natural no. is : "+ Sum(6)); 
             //   System.out.println(" The sum of first "+ 1+" Natural no. is : "+ Sum(1)); // it is for second type.
        
        // Q4- find the nth term of Fibonacci sequence.
             System.out.println(" The 7th"+" Fibonacci sequence no. is : "+ fib(7));  
            

        // Q5- write the function to find the avg. of a set of nos. passed as arguments.
                //  System.out.print(" The avg of these numbers is: ");
                // System.out.println(  num(1,2,3,4));

    }       

}