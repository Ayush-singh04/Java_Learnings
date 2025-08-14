package java_learnings.Recursion;

public class recursion_exponential_power {
     public static int power(int x,int n){
         // to calculate power we use x^n = x*x^n-1 .
            if (n==0) {
                return 1;
            }                  
            if (x==0) {
                return 0;
            }
            int x_power2 = power(x ,n-1); // for n-1
            int x_power1 = x*x_power2;
             return x_power1;
        }
           
    public static void main(String[] args) {
       System.out.println(power(2, 3));
    }
}
