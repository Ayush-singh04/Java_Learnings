package java_learnings.OOPS_concept;

interface first{

    default void greet(){
        System.out.println("It's a default method in the interface..");
        name(); // We are calling the private method in the default method of the interface.
    } 

    private void name(){
        System.out.println("It's a private method in the interface..");
    }

    static void age(){
        System.out.println("It's a static method in the interface..");
    }
}
class second implements first{
   public void city(){
        System.out.println("It's a static method in the inherited class..");
    }
}


public class InterfaceExample {
    public static void main(String[] args) {
        second obj = new second();
        obj.greet();
        first.age();
        // obj.name(); // It won't be working for private method in the interface.
        obj.city(); 

        
    }
}
