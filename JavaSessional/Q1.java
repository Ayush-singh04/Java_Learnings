package java_learnings.JavaSessional;


interface Operation {
    int calculate(int a, int b, int c);
}

public class Q1 {

    public static void main(String[] args) {

        // Lambda expression with three arguments
        Operation sum = (a, b, c) -> a + b + c;

        int result = sum.calculate(10, 20, 20);

        System.out.println("Sum of three numbers: " + result);
    }
}
