package java_learnings.OOPS_concept;

class one{
    public void meth(){
         System.out.println(" Method of one class");
    }
    public void meth1(){
         System.out.println(" Checking the Dynamic Dispatch"); // for Dynamic Dispatch.
    }
}
class two extends one{
    @Override
    public void meth(){
        System.out.println(" Method of one class but in two.");
    }
    public void meth2(){
        System.out.println(" Method of two class ");
    }
    public void meth1(){
        System.out.println(" Checking the Dynamic Dispatch in  class two."); // for Dynamic Dispatch.
    }
}
public class Method_overriding{
    public static void main(String[] args){
      /*   one a=new one();
        a.meth();
        two b=new two();
        b.meth();
        //b.meth2();
     */

    // Here we will know about Dynamic Dispatch..
        
        one obj = new two(); // we can use sub-class to make obj. with super-class in it.
                          //  two B = new one(); // we can't use revrse of the above condition..
        obj.meth1();
         obj.meth();
    }
}
