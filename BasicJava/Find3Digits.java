package java_learnings.BasicJava;
import java.util.Scanner;
class Find3Digits {
    public static void main(String[] args) {
        System.out.print("Enter a number: ");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int count = 0;

        while (num > 0) {
            num = num / 10; // Remove the last digit
            count++;
        }
        if (count == 3) {
            System.out.println("The number contains exactly 3 digits.");
        } else {
            System.out.println("The number does not contain exactly 3 digits.");
        }
    }
}