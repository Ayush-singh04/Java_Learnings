package java_learnings.MathsQuestion;

public class Seive {
    // False in the Array we'll take that number as Prime. Or True Means that number already exists so can't be prime.
    static void seive(int n , boolean [] primes){
        for (int i = 2; i*i <= n; i++) {
            if (primes[i] == false ){
                for (int j = i*2; j <= n ; j = j+i) {
                    primes[j] = true ;
                }
            }
        }
        for (int i = 2; i <= n; i++) {
            if (primes[i] == false) {
                System.out.print(i + " ");
            }
        }
    }
    public static void main(String[] args) {
        // This Question basically means to find the prime numbers till Nth number.
            int n = 40 ;
        boolean[] primes = new boolean[n+1] ; // Arrays Starts from 0 so we have to include one space more than N.
            seive(n , primes);
    }
}
