package java_learnings.OOPS_concept;

public class ThisKeyword {
    long rollNum;
    String name;
    
    ThisKeyword( long rollNum, String name) {
        this.rollNum = rollNum;
        this.name = name;

        System.out.println("My Roll Number: " + rollNum);
        System.out.println("My name is: " + name);
    }
    public static void main(String[] args) {
        ThisKeyword student = new ThisKeyword(05, "Swayush Singh");
            
    }
}
