package java_learnings.JAVASessional;
public class Q6 {

    public static String merge(String a, String b) {
        StringBuilder result = new StringBuilder();

        int i = 0;

       
        while (i < a.length() && i < b.length()) {
            result.append(a.charAt(i));
            result.append(b.charAt(i));
            i++;
        }

       
        if (i < a.length()) {
            result.append(a.substring(i));
        }

        if (i < b.length()) {
            result.append(b.substring(i));
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String a = "Hello";
        String b = "World";

        String output = merge(a, b);
        System.out.println(output);
    }
}
