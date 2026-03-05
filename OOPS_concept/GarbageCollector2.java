package java_learnings.OOPS_concept;

public class GarbageCollector2 {
    public void finalize() { // finalize method is called by the garbage collector before it destroys the object.
        System.out.println("Finalize thread ended");
        System.out.println(1000/0);
       
    }
    public static void main(String[] args) {
        GarbageCollector2 Gc = new GarbageCollector2();
                // Gc.finalize(); 
                Gc = null;
                System.gc();
                System.out.println("Main thread ended");

    }
}
