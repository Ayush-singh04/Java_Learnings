package java_learnings.OOPS_concept;
class mythr1 extends Thread{
    public  mythr1(String name){
        super(name);
    }
    public void run(){
     while (true) {
       System.out.println("Hello .."+ this.getName());  
         }
     }
}
public class Threads_Priorties {
    public static void main(String[] args) {
        mythr1 t1 = new mythr1("freind");
         mythr1 t2 = new mythr1("Brother");
          mythr1 t3 = new mythr1("mate");
           mythr1 t4 = new mythr1("Dude");

           //.. Setting Priority of an object...
            t2.setPriority(Thread.MAX_PRIORITY);     //this method can also be used
         // t2.setPriority(10);
            t1.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
