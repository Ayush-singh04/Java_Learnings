package java_learnings.BitWiseOperators;

public class onesCompliment {
    public static void main(String[] args) {
        int a = -26; 
        int onesComplement = ~a; 
        
        System.out.println("Original number: " + a);
        System.out.println("One's complement: " + onesComplement);
    }
}