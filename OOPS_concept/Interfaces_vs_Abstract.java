package java_learnings.OOPS_concept;

// Interface Syntax..
    interface Bycycle{  // we write "interface" not "class"..

        public void speedUp(int increment);
         public void applyBreak(int decrement);
         void tyreStart();
    }
     interface tools { // Defining tools of cycle
      void tyreStart();
      void seat();
      
      
    }
    class AvonCycle implements Bycycle , tools{
     
      public void speedUp(int increment){
        System.out.println("Accelerating UP....!");
      } 
      public void applyBreak(int decrement){
        System.out.println("Applying Brakes....!");
      }
      public void Horn(){
        System.out.println("Pee....Peeep!");
      } 
      public void tyreStart(){
        System.out.println("My Cycle has MRF tyres moving.");
      }
      public void seat(){
        System.out.println("It has two seats.");
      }
    }


public class Interfaces_vs_Abstract{
    public static void main(String[] args){
        AvonCycle avc = new AvonCycle();
        tools too = new AvonCycle();
        avc.applyBreak(1);
        avc.Horn();
        avc.speedUp(12);
        avc.tyreStart();
        too.tyreStart();
    }
}