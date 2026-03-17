package java_learnings.JAVASessional;
class EvenThread extends Thread {
    private int limit;

    public EvenThread(int limit) {
        this.limit = limit;
    }

    public void run() {
        System.out.println("Even Numbers:");
        for (int i = 2; i <= limit; i += 2) {
            System.out.println(i);
        }
    }
}

class OddThread extends Thread {
    private int limit;

    public OddThread(int limit) {
        this.limit = limit;
    }

    public void run() {
        System.out.println("Odd Numbers:");
        for (int i = 1; i <= limit; i += 2) {
            System.out.println(i);
        }
    }
}

public class Q5 {
    public static void main(String[] args) {
        int N = 10;

        EvenThread even = new EvenThread(N);
        OddThread odd = new OddThread(N);

        even.start(); 
        odd.start();  
    }
}