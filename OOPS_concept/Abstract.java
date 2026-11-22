package java_learnings.OOPS_concept;

// Making an abstract class here..

    abstract class Parent{
        Parent(){
             System.out.println("It's an abstract class..");
        }
        abstract public void greet(); // These are the abstract methods..
        abstract public void greet2();
    }

        //This is a concrete class and its object can be made.
        // It's using the abstract methods

    class Child extends Parent{
        public void greet(){
            System.out.println("Good Morning! ");
        }                                               
        public void greet2(){
            System.out.println("Good Afternoon!");
        }
    }
    
        // This class is an abstract class so its object can't be made.

    abstract class Child2 extends Parent{
    public void age(){
         System.out.println("Hello I'm 18 year old ");
    }
    }
public class Abstract{
    public static void main(String[] args){
     //   Parent father = new Parent(); // It won't be working for abstract class.
              Child boy = new Child();
     //      Child2 girl = new Child2();   // It won't be working for abstract class.

            boy.greet();
            boy.greet2();

    }
}