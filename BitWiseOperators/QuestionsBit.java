package java_learnings.BitWiseOperators;

public class QuestionsBit {
    //For Q1

    static boolean isOdd(int n){
        return (n & 1) == 1 ; // When we &(and) Any number with one it gives same number back so if the last bit of binary number is (1) that means the number is odd .
    }
    // For Q2

    static int findUnique(int [] arr){
        int unique = 0 ; // Here we took unique value as 0 

            for (int n : arr) { // We'll move to all the elements one by one using this loop.
                unique = unique^n ; // Important Step (Here we are doing XOR with each element with unique and again storing it as unique)
            }

        return unique ; // In the last the element which will remain that will be the real unique element.
    }

    // For Q3

    static int magicNumber(int n){
        int ans = 0 ;
        int base = 5 ;
            while (n > 0) {
                int last = n & 1 ; // This step finds the last bit(digit) of a binary number.
                                            /* 1 in binary = 0 0 1 (So where one is there would be 5^1 = 5).
                                             * 2 in binary = 0 1 0 (So here one is at 2nd place there would be 5^2 = 25).
                                             * 3 in binary = 0 1 1 (So here one is at 1st and 2nd place there would be 5^1 + 5^2 = 30).
                                             */
                n = n >> 1 ; // we have to right shift the number so that the calculation could be performed 
                ans = ans + (last * base) ;
                base = base * 5;
            }

        return ans;
    }
    public static void main(String[] args) {
    // Q1 - Check the number is Odd or Even. 
        int n = 67;
        // System.out.println(isOdd(n));

    // Q2 - In the given Array every number is appearing twice only one number is appearing once,Find the number.

        int arr [] = {2, 3, 3, 4, 2, 6, 4};
        // System.out.println(findUnique(arr));

    // Q3 - Find the nth Magic number (Asked in amazon) {We know magic number for 1 is 5 and it multiplies by itself as it grows.}
        System.out.println(magicNumber(5));
    }
}
