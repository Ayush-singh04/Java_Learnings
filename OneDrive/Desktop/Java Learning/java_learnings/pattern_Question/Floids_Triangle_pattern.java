package java_learnings.pattern_Question;
public class Floids_Triangle_pattern {
    public static void main(String[]args){
             System.out.println(" Floid's Triangle ");
           /*
             1 
             2 3 
             4 5 6  // Printing this pattern
             7 8 9 10
             11 12 13 14 15
            */
            int num =1;
            for (int row = 1; row <=5; row++) {
              for (int colm = 1; colm<=row ; colm++) {
                System.out.print(num+  " ");
                  num++;
              }
              System.out.println();
            }
             System.out.println("-----(0 1) Triangle-----");
         /*
             1 
             0 1 
             1 0 1  // Printing this pattern
             0 1 0 1
             1 0 1 0 1
            */  
             for (int row = 1; row <=5; row++) {
              for (int colm = 1; colm<=row ; colm++) {
                int sum=row+colm;
                if (sum%2==0) {//even condition
                  System.out.print("1 ");
                }else{ //odd
                  System.out.print("0 ");
                }
             }
             System.out.println();
        }
    }
 }