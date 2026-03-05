package java_learnings.OOPS_concept;

public class GarbageCollector {
    static int count = 0;

    public void finalize() { // finalize method is called by the garbage collector before it destroys the object.
        count++;
        // System.out.println("Jersy Changed " + count + " times");
    }
    public static void main(String[] args) {
        for(int i = 0; i < 10000000; i++) {
            GarbageCollector obj = new GarbageCollector();
            obj = null; // we are making the reference variable null so that it can be eligible for garbage collection.


        }
    }
}
