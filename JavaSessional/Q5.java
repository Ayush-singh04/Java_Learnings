package java_learnings.JavaSessional;
import java.util.Scanner;

public class Q5 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter first number: ");
            int a = sc.nextInt();

            System.out.print("Enter second number: ");
            int b = sc.nextInt();

            int result = a / b;   
            int arr[] = {10, 20, 30};
            System.out.println("Array element: " + arr[5]); 

            System.out.println("Result: " + result);
        }

        catch (ArithmeticException e) {
            System.out.println("Error: Division by zero is not allowed.");
        }

        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Array index is out of bounds.");
        }

        catch (Exception e) {
            System.out.println("General Exception occurred.");
        }

        finally {
            System.out.println("Finally block executed. Program finished.");
        }

    }
}