package java_learnings.OOPS_concept;
class C1{
    public int a = 10;  //Acesed from anywhere)
    protected int b = 20;
    int c = 30;         // Default Modifier.(Accesed within the same package)
    private int d = 40;

    public void meth1(){
        System.out.println(a);
        System.out.println(b);
        System.out.println(c); 
        System.out.println(d);
    }
}
public class Access_Modifiers{
    public static void main(String[] args) {
        C1 c = new C1();
        c.meth1(); // We can use all these modifiers in the same class
      /*  System.out.println(c.a);
          System.out.println(c.b);
          System.out.println(c.c);
          System.out.println(c.d); // we can't use Private modifiers in the same Package
        */
        
    }
}