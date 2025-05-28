package java_learnings.OOPS_concept;

class Phone{
      String name;
      long numb;
      void  Dial(long numb){
          System.out.println("calling... +91"+numb);   
        }
        void PickUp(String name){
          System.out.println("your friend "+name+" is calling you");
        }
        
        
    }

interface camera{
    void vdo(int a);
    void pic();
    void snap();
}
interface GPS{
    void locate();
}
interface media{
    void music();
    void vdoplay();
}
class SmartPhone extends Phone implements camera,GPS,media{
    
    public void vdo(int a){
        System.out.println("Recording video in "+a +"k");
    }
    public void pic(){
        System.out.println("clicking picture... ");
    }
    public void snap(){
        System.out.println("taking snap... ");
    }
    public void locate(){
        System.out.println("Finding location... ");
    }
    public void music(){
        System.out.println("playing music... ");
    }
    public void vdoplay(){
        System.out.println("playing video... ");
    }
}

public class  Polymorphism{
    public static void main(String[] args){
        SmartPhone Iqoo = new SmartPhone();
          Iqoo.Dial(1008);
          Iqoo.PickUp("<Tony Stark>");
          Iqoo.vdo(4);

      // Polymorphism....allows to call a specific method by making new object

        GPS g = new SmartPhone(); // This method will only check methods of GPS and no other methods out of Gps will be checked
        g.locate();
       // GPS.vdoplay(); -- thorws error.
    }
}