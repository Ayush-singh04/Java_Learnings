package java_learnings.Pattern_Question;

public class Diamond_pattern {
    public static void main(String[] args) {
        
        //Upper Half
        for (int row =1; row <=4; row++) {
          // Spaces
         for (int colm =1; colm<=4-row; colm++) {
         System.out.print(" ");
        }
         for (int colm =1; colm <=2*row-1 ; colm++) {
         System.out.print("*");
        } 
         System.out.println(); 
        }

        //Lower Half
        for (int row =4; row >=1; row--) {
          // Spaces
          for (int colm =1; colm<=4-row; colm++) {
          System.out.print(" ");
        }
          for (int colm =1; colm <=2*row-1 ; colm++) {
          System.out.print("*");
        } 
          System.out.println(); 
        }
    }
}
