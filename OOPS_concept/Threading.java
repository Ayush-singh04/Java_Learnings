package java_learnings.OOPS_concept;
class Thread1 extends Thread{
    public void run(){
        while (true) {
            System.out.println("Happy!"); 
        } 
    }
}
class Thread2 extends Thread{
    public void run(){
        while (true) {
            System.out.println("Sadd!"); 
        }
    }
}
class ThreadRunnable1 implements Runnable{      // Runnable Thread method
    public void run(){
        while (true) {
            System.out.println("walk....!");
        }
    }
}
class ThreadRunnable2 implements Runnable{     // Runnable Thread method
    public void run(){
        while (true) {
            System.out.println("Runn....!");
        }
    }
}
public class Threading {
    public static void main(String[] args) {    
        // Thread1 t1=new Thread1();
       // Thread2 t2=new Thread2();
      // t1.start();        // we will use start method to Run the code in threads.
     // t2.start(); 

            // Runnable Thread method....Testing.

      ThreadRunnable1 Bullet1 = new ThreadRunnable1();
      Thread gun1 = new Thread(Bullet1);                
      ThreadRunnable2 Bullet2 = new ThreadRunnable2();
      Thread gun2 = new Thread(Bullet2);
        gun1.start();  // we will use start method to Run the code in threads.
        gun2.start();
    }
}
