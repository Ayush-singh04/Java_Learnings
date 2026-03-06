package java_learnings.BasicJava;
public class Strings {
   public static void main(String[] args) {
    String s1 = new String("Hello");
    String s2 = new String("Hello");
      
     System.out.println(s1 == s2); // false, because s1 and s2 are different objects in memory
     System.out.println(s1.equals(s2)); // true, because s1 and s2 have the same content
   } 
}
