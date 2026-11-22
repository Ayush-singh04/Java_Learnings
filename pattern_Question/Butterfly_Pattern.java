package java_learnings.Pattern_Question;
public class Butterfly_Pattern{
    public static void main(String[]args){
        // FOR upper half of butterfly--
                
        for(int rows=1; rows<=5; rows++){  
                 // 1st Part--         
            for(int colms=1; colms<=rows; colms++){ 
    
              System.out.print("*");
         }
            // Spaces
            int spaces = 2*(5-rows);
        for (int colms = 1; colms <=spaces ; colms++) {
                System.out.print(" ");
        }
            // 2nd Part
        for (int colms = 1; colms<=rows; colms++) {
            System.out.print("*");
        }
          System.out.println();
    }
        
        // FOR lower half of butterfly--
             
        for(int rows=5; rows>=1; rows--){  
                     // 1st Part--        
            for(int colms=1; colms<=rows; colms++){ 
    
              System.out.print("*");
         }
            // Spaces
            int space = 2*(5-rows);
        for (int colms = 1; colms <=space ; colms++) {
                System.out.print(" ");
        }
            // 2nd Part
        for (int colms = 1; colms<=rows; colms++) {
            System.out.print("*");
        }
          System.out.println();
       }
    }
}