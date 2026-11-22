package java_learnings.Recursion;

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
    static int sumOfDigits(int n){
        if (n == 0) {
            return 0 ;
        }
        return n % 10 + sumOfDigits(n / 10) ; // In this step "n%10" will give the last digit to add then "n/10" will make the number one digit less.
    }
    static int productOfDigits(int n){
        if (n % 10 == n) {
            return n ;
        }
        return n % 10 * productOfDigits(n / 10) ; // In this step "n%10" will give the last digit to add then "n/10" will make the number one digit less.
    }
    
    public static void main(String[] args) {

       // int n = 5;
       // System.out.println("The value of factorial "+ n+ " is: "+ factorial(n));
       // print(n);
            

        //    numsum(1,5,0);

      // Find the sum of the digits of a number.
    
      int ans = sumOfDigits(1234);
      int ans2 = productOfDigits(1234);
        // System.out.println(ans);
        System.out.println(ans2);
       
           
    }
}