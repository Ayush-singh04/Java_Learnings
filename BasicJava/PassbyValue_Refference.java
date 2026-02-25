package java_learnings.BasicJava;

public class PassbyValue_Refference {
    static void change(int a){
        a = 98; 
        System.out.println("The value of a after is: "+a); // This will print 98 because it is pass by value.
    }

    public static void main(String[] args) {
        int a = 45;
        System.out.println("The value of a before changing is: "+a); 
         change(a);
    }
}
