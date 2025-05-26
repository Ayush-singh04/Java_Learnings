package java_learnings.recursion;

public class recursion{

    // factorial(1) =1, fact.(0)=1
    // factorial(n) = n*n-1.....*1
    // factorial(5) = 5*4*3*2*1 = 120
    // factorial(n) = n* factorial(n-1)

    static int factorial(int n){
        
        if(n==0 || n==1){
            return 1;
        } else{
            return n* factorial(n-1);
        }
    }
    public static void print(int n){
        if (n==0) {
          return;  
        }
            System.out.println(n);
            print(n-1); // calling the same funtion again.
            return; 
    }
    public static void numsum(int i,int n,int sum){
        // sum of First n natural numbers--
        if (i==n) {
            sum +=i;
            System.out.println(sum);
            return;
        }
            sum +=i;
            numsum(i+1,n,sum);
            return;
    }
     
    
    public static void main(String[] args) {

       // int n = 5;
       // System.out.println("The value of factorial "+ n+ " is: "+ factorial(n));
       // print(n);
            

           numsum(1,5,0);
       
           
    }
}