package java_learnings.BasicJava;

public class StringPreformance {
    public static void main(String[] args) {
        // Write Complete Alphabets in java.

        for (int i = 0; i < 26; i++) {
            char ch = (char)('a' + i); // In this step i will be working Alphabets index like ('a'+0 = 'a' , 'a'+1 = 'b')
            System.out.print(ch + " ");
        }
    }
}
