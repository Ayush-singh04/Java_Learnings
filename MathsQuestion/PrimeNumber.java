package java_learnings.MathsQuestion;

public class PrimeNumber {
    static boolean isPrime(int n){
        if (n <= 1) {
            return false ;
        }
    // we used "sqrt" function because after square root of a number the division process will repeat so to stop that we used it. 
       // for (int i = 2; i <= Math.sqrt(n) ; i++) { // i = 2 cause every numbers is divisible to 1 so, check from 2.
        for (int i = 2; i*i <= n ; i++) { // if not to use "sqrt" function just check i^2 is less than the "n"
                if (n % i == 0) {
                    return false ;
                }
        }
        return true ;
    }
    public static void main(String[] args) {
        int n = 13 ;
        for (int i = 1; i <= n; i++) {
           System.out.println(i + " " + isPrime(i));
        }
    
    }
}
