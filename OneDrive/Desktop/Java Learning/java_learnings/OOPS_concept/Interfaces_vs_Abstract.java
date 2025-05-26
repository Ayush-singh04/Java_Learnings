package java_learnings.OOPS_concept;

// Interface Syntax..
    interface Bycycle{  // we write "interface" not "class"..

        public void speedUp(int increment);
         public void applyBreak(int decrement);
    }
    class AvonCycle implements Bycycle{
      public void speedUp(int increment){
        System.out.println("Accelerating UP....!");
      } 
      public void applyBreak(int decrement){
        System.out.println("Applying Brakes....!");
      }
      public void Horn(){
        System.out.println("Pee....Peeep!");
      } 
    }


public class Interfaces_vs_Abstract{
    public static void main(String[] args){
        AvonCycle avc = new AvonCycle();
        avc.applyBreak(1);
        avc.Horn();
        avc.speedUp(12);
    }
}