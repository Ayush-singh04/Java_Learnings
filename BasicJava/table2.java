package java_learnings.BasicJava;
import java.util.Scanner; 
public class table2 {
    
    public static void main(String [] args)
    {
   
    System.out.println("Enter the number that you want multiple of:");
       
        try (Scanner sc = new Scanner(System.in)) {
          int a =sc.nextInt();
          
          System.out.println("The table of "+ a + " is-:");
                
                System.out.println(a + "*1= "+(a*1));
                  System.out.println(a + "*2= "+(a*2));
                    System.out.println(a + "*3= "+(a*3));
                      System.out.println(a + "*4= "+(a*4));
                        System.out.println(a + "*5= "+(a*5));
                        System.out.println(a + "*6= "+(a*6));
                      System.out.println(a + "*7= "+(a*7));
                    System.out.println(a + "*8= "+(a*8));
                  System.out.println(a + "*9= "+(a*9));
                System.out.println(a + "*10= "+(a*10));
        }

    System.out.println("Thank you for using.");


    }
    
}

