package java_learnings.BasicJava;
import java.util.*;
public class Exponential_power{
    public static int exponentialPower(int x,int y){
        int power =1;
          for(int i=1;i<=y;i++){
            power = power*x;
           
      }
        return power;  
          
                }
                public static void main(String[] args){
                        try (Scanner sc = new Scanner(System.in)) {
                            System.out.print("Enter the base: ");  
                            int x=sc.nextInt();  
                             System.out.print("Enter the exponent: ");  
                             int y=sc.nextInt();  
                            //calling function  
                              int pow=exponentialPower(x, y);  
                            //prints the result  
                              System.out.println(x +" to the power " +y + " is: "+pow);
                        }  
 
    }  
}