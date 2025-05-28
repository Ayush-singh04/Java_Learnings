package java_learnings.OOPS_concept;
class Greet_gm extends Thread{
    public void run(){
        int i = 0;
       while (i<=10) {
         System.out.println("Good Morning"); 
     /*    try {
           Thread.sleep(200);
        } catch (InterruptedException e) {      // Sol.-2
            e.printStackTrace();
        }
     */ 
         i++;
       }
    }
}
 class Greet_wlc extends Thread{
    public void run(){
        int i = 0;
       while (i<=10) {
         System.out.println("Welcome"); 
         i++;
       }
    }
}
public class Practice_set_Threads {
   public static void main(String[] args) {
    // Q1- Write a programm to print "good Moring" and "welcome" continously using threads..
    // Q2- Apply Sleep command on good morning thread..
   //  Q3- Use priority command in Q1.
   //  Q4- Use the command getState to find the state of a method.
   //  Q5- How do you get refference of the current Thread in java..

            // Sol.-1
        Greet_gm gm = new Greet_gm();
        Greet_wlc wlc = new Greet_wlc();
            // sol.-3
        gm.setPriority(10); // Setting maximum priority
        System.out.println(gm.getPriority());   // This command will show the set priority.
        System.out.println(wlc.getPriority()); // It will show 5(normal priority) by default.
            // sol.- 4
        System.out.println(gm.getState());
            // Sol.- 5
        System.out.println(Thread.currentThread().getState()); // This command is used to reffer the current thread

         //  gm.start(); 
         //  wlc.start();
        
        
   } 
}
