package java_learnings.OOPS_concept;

import java.io.FileWriter;
import java.io.IOException;

interface Bycycle{  // we write "interface" not "class"..

        public void speedUp();
    }
class myDepricate{
    @Deprecated
    void meth1(){
        System.out.println("I'm a depricated method");
    }
}
 
public class Practise_set_Advance2 {
            // sol-2
    @SuppressWarnings("deprication")
  public static void main(String[] args) {
    // Q1- Create a method using Deprecated annotation.
    // Q2- use suppress warning Annotation in Q1.
    // Q3- Make an interface and generate an instance from it.
    // Q4- Write a programm to generate multiplication table of a no. and write it into a file 
        // sol-1
    myDepricate dp = new myDepricate();
    dp.meth1(); // Its an deprecated method.

                // sol-3
       // Bycycle b1 = () -> System.out.println("moving fast");
   
                //sol-4
                int x=5;
            String table = "";
            
            for (int i = 1; i <=10; i++) {
               table += x+"X"+i+" = "+x*i; // Converting Table into String
               table += "\n";
            }
            
                 try {
                FileWriter fileWriter = new FileWriter("handlingfile.txt");
                fileWriter.write(table);
                    fileWriter.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                  
  }  
}
