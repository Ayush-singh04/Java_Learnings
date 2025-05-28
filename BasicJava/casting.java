package java_learnings.BasicJava;

public class casting 
{
    public static void main( String []args){
      
        // Casting

        double price = 100.00;
         double finalPrice = price + 18.4;
          System.out.println(finalPrice);

        float pr = 1.2f;
          int fpr = (int)pr + (int)18.4;
            System.out.println(fpr);  

        int age = 30;
          age = 55; // Here we have changed the value of age.
            age = 18; // The value of age is not constant and can be changed again.

             System.out.println(age);

    // If we want to put a no. as constant so we have to write final before the keyword.

       final float PI =3.14f;
         System.out.println(PI);

    // Now we can't change the value of PI again in this program.


    
    }
}