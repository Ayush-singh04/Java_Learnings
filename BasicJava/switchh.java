package java_learnings.BasicJava;

import java.util.Scanner;
public class switchh {

    public static void main(String[]args){
         System.out.println("Please enter the month number:");
       try( Scanner M = new Scanner(System.in)){
        int Month=M.nextInt();
        String monthString="";  
        switch(Month){
        
            case 1: monthString="1 - January";  
            break;    

            case 2: monthString="2 - February";
            break;

            case 3: monthString="3 - March";  
            break;    

            case 4: monthString="4 - April";  
            break; 
            
            case 5: monthString="5 - May";  
            break;    

            case 6: monthString="6 - June";  
            break;    

            case 7: monthString="7 - July";  
            break;    

            case 8: monthString="8 - August";  
            break;    

            case 9: monthString="9 - September";  
            break;    

            case 10: monthString="10 - October";  
            break;    

            case 11: monthString="11 - November";  
            break;    

            case 12: monthString="12 - December";  
            break;    
        
           default:System.out.println("No month exists");

         }
                System.out.println(monthString);
            
        }     
        
    }
}
