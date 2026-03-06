package java_learnings.GarbageCollection;

class student {
public void finalize(){
    System.out.println("Student Object is Destroyed");
}
    
}

public class StudentRecord {
    public static void main(String[] args) {
        student s1 = new student();
        s1 = null;

        System.gc();
    }
}
