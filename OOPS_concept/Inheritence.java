package java_learnings.OOPS_concept;



class Base{             // This is the parent class and other classes will be derived from it.
    int x;
    
    public int getX() {
        System.out.println("Setting base X now: ");
          return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public Base(){     // constructor inheritence...
        System.out.println("I'm a constructor ");
    }
    public Base(int a){
        System.out.println("I'm constructor- "+a);
    }   
}

class Derived extends Base{ // it is the derived class of base class, any changes in parents class will also be applicable here. 
    int y;                  // we usee "extends" command to derive a class

    public int getY() {
        System.out.println("Setting Derived Y now: ");
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Derived(){
        System.out.println("I'm a derived constructor ");
    }
    public Derived(int a, int b){
        super(b);       // it checks the availabilty of given refrence and moves to Base class.. 
        System.out.println("I'm constructor no. "+b);
        // System.out.println("I'm constructor no. "+a);
    }

}

public class Inheritence{
    public static void main(String[] args) {
        //Base b = new Base();
       // b.setX(5);
       // System.out.println(b.getX()); 
       Derived d = new Derived(5,7);
         d.setY(100);
    //   System.out.println(d.getY());
        
    }
}