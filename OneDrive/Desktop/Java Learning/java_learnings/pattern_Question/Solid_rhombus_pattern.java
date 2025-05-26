package java_learnings.pattern_Question;

public class Solid_rhombus_pattern {
    public static void main(String[] args) {
        for (int rows =1; rows <=5; rows++) {
            //Spaces
            int space = 5-rows;
                for (int colm = 1; colm <=space; colm++) {
                    System.out.print(" ");       
                }
            for (int colm =1; colm <=5; colm++) {
                System.out.print("*");
            }
                System.out.println();
        }
            System.out.println("-----Number Pryramid-----");
                // Number Pyramid
            for (int row =1; row <=5; row++) {
                //Spaces
            for (int colm =1; colm <=5-row; colm++) {
                System.out.print(" ");    
            }
            for (int colm =1; colm<=row; colm++) {
                System.out.print(row + " ");  
            }
                System.out.println();
            }
            
            System.out.println("-----Palindromic Pattern-----");
                 //Palindromic Pattern
            for (int row =1; row <=5; row++) {
                //Spaces
            for (int colm =1; colm <=5-row; colm++) {
                System.out.print(" ");    
            }
                // First half
            for (int colm =row; colm>=1; colm--) {
                System.out.print(colm);  
            }
              // second half
            for (int colm =2; colm<=row; colm++) {
                System.out.print(colm);  
            }
            
                System.out.println();
            }


    }
}
