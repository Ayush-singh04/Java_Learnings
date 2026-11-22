package java_learnings.BitWiseOperators;
public class SumOfNumbers {
   static int sumofnum(int a , int b){
        int sum = a ^ b;
        int carry = a & b;
        while (carry != 0){
            a = sum ; b = carry << 1;
            sum = a ^ b;
            carry = a & b;
        }

        return sum;
        }
    public static void main(String[] args) {
        System.out.println(sumofnum(4, 5));

    }
}
