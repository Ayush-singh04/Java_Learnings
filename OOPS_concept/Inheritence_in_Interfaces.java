package java_learnings.OOPS_concept;

interface Sample{
    void meth1();
    void meth2();
}
interface Sample2 extends Sample{
    void meth3();
    void meth4();
}
class MySample implements Sample2{
    public void meth1(){
         System.out.println("Applying Method 1st ");
    }
    public void meth2(){
         System.out.println("Applying Method 2nd ");
    }
    public void meth3(){
         System.out.println("Applying Method 3rd ");
    }
    public void meth4(){
         System.out.println("Applying Method 4th ");
    }

}
public class Inheritence_in_Interfaces{
    public static void main(String[] args){
        MySample obj = new MySample();
            obj.meth1();
            obj.meth2();
            obj.meth3();
            obj.meth4();
    }
}