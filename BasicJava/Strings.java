package java_learnings.BasicJava;
public class Strings {
   public static void main(String[] args) {
    String s1 = new String("Hello");
    String s2 = new String("Hello");
      
     System.out.println(s1 == s2); // false, because s1 and s2 are different objects in memory
     System.out.println(s1.equals(s2)); // true, because s1 and s2 have the same content
     String s3 = "Hello";
     System.out.println(s1 == s3);

     String s4 = "Hello";
     System.out.println(s3 == s4); 

     String s5 = "Hel" + "lo";
     System.out.println(s4 == s5);

     String s6 = "Hel";
     String s7 = s6 + "lo";
     System.out.println(s6 == s7);

     final String s8 = "Hel";
     String s9 = s8 + "lo";
     System.out.println(s4 == s9);
   } 
}
