package java_learnings.OOPS_concept;
class mythr extends Thread{
    public  mythr(String name){
        super(name);
        
    System.out.println("Hello ..");
    }
}
public class Threads_constructor {
    public static void main(String[] args) {
        mythr t1 = new mythr("Ayush");
          //Thread Name= new Thread(t1,"Ayush");
         //Name.start();
        mythr t2 = new mythr("jhonn");
         t1.start();
         t2.start();
        System.out.println("Id-: "+ t1.threadId());
        System.out.println("Name:- "+ t1.getName());
        System.out.println("Id-: "+ t2.threadId());
        System.out.println("Name:- "+ t2.getName());
       
    }
}
