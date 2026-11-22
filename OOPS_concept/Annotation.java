package java_learnings.OOPS_concept;
 @FunctionalInterface // This Annotation is used for single abstract method, we can't use multiple methods inside it.
     interface myfunctionalInterface {
      void  method1();
      //void method2(); // -- Throws error as only one method can be run insdie functional interface
    }
class tag{
    public void time(){
        System.out.println("Time is 8AM");
    }
}
class tag2 extends tag{
    @Override  // HERE OVERRIDE IS AN ANNOTATION.
    public void time(){
        System.out.println("Time is 8PM");
    }
    @Deprecated // Annotation of Deprecation
    public int sum(int a , int b){
        return a+b;
    }
   
}
public class Annotation {
    @SuppressWarnings("deprication") // Annotation to suppress any warnings
    //Annotations are the tag's above the code.
   public static void main(String[] args) {
    tag2 t = new tag2();
        t.time();
        t.sum(4, 6);
   }
}
