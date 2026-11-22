package java_learnings.BasicJava;

public class PassbyValue_Refference {
    static int change(int num){
       return num = num + 10; 
        // System.out.println("The value of a after is: "+a); // This will print 98 because it is pass by value.
    }

    public static void main(String[] args) {
        int a = 5;
        System.out.println("The value of a before changing is: "+a); 
         System.out.println("The value of a after changing is: "+change(a)); // This will print 15 because it is pass by value.
    }
}
