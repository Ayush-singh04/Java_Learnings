package java_learnings.BasicJava;

public class opperators
 {
   public static void main(String[]args)
   {
     int X=15;
   System.out.println(X++); // '++,--' are arithmatic operator's
     System.out.println(X--);
   System.out.println(++X);
     System.out.println(--X);
      
       X +=4; 
     System.out.println(X);
    
     int Y=X%3; // This is called modulo operator
        System.out.println(Y); // modulo gives the remainder of division.

    System.out.println(7==9); // "==","<'>,<=,>=" is a comparision operator
                              // "=,+=,*=,-="  is an assingment operator 
        System.out.println(18>9 && 18>7); // '&&(and),||(or)' is a logical operator

        // 'and' operator search's for false only,'or' operator search's for true only.
    
    System.out.println(18<9 || 18<7);

    // Math operator....
    System.out.println(Math.max(1,3)); // It finds greater no. between two nos.
    System.out.println(Math.min(5,6)); // It finds smaller no. between two nos.

    // Random function
   //   System.out.println(Math.random()); // Gives random value between (0-1).
   //   System.out.println((int)Math.random()); // It will give integer value b/w (0-1).
   // System.out.println((int)(Math.random()*100)); // It will give random values b/w (0-100).




   }
 }
