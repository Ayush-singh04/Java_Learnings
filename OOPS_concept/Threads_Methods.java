package java_learnings.OOPS_concept;
class myNewThr extends Thread{
    public  myNewThr(String name){
        super(name);
    }
    public void run(){
     int i=0;
     while (i<=10) {
       System.out.println("Hello .."+ this.getName()); 
       i++; 
        }
     }
 }
 class myNewThr2 extends Thread{
        public void run(){
            int i=0;
            while (i<=10) {
            System.out.println("Welcome"); 
            try {
                Thread.sleep(432);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++; 
        }
     }
 }

public class Threads_Methods {
    public static void main(String[] args) {
        myNewThr t1= new myNewThr("uppsss..");
        myNewThr t2= new myNewThr("wow...");
        t1.start();
     /*    try {
            t1.join();          // It will run second object only when the first one gets complelty executed..
        } catch (Exception e) {
            
           System.out.println(e);;
        }
    */
        t2.start();
    }
}
    