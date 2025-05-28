package java_learnings.OOPS_concept;

interface Sample{ // Sample is not a class but we made an object using it
    void meth1();
    void meth2();
}
@FunctionalInterface
interface Sample2 {
    void meth3();   
  //  void meth4();
}

public class Anonymous_class {
    public static void main(String[] args) {
        Sample obj = new Sample(){ // Here Sample is an anonymous class 
 
            @Override
            public void meth1() {
                System.out.println("Applying Method 1st ");  
            }

            @Override
            public void meth2() {
                System.out.println("Applying Method 2nd ");
            }

        };
        obj.meth1();

    //     LAMDA EXPRESSIONS   
       Sample2 obj2 = ()->{System.out.println("I'm Method 3 "  );};
            obj2.meth3();
    }
}
