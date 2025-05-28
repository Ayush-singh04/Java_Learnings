package java_learnings.pattern_Question;
public class Starpyramid{
    
   
    public static void main(String[]args){

                            // Making Star Pyramid using loops.
                            /*    *  > Here S.no. of row  = colms 
                                  * * > S.no. of row is 2 and colm =2
                                  * * * > The colms are increasing according to the S.no. of rows
                                  * * * *  
                            */
                                for(int rows=1; rows<=4; rows++){  
                        
                                  for(int colms=1; colms<=rows; colms++){ 
                          
                                    System.out.print("*");
                }
                                    System.out.println();
           }
                             System.out.println("making inverted of 1st pyramid.");
                                 for(int rows=1; rows<=4; rows++){  
                                    for(int colms=1; colms<=4-rows; colms++){ // printing spaces.
                                        System.out.print(" ");
                                    }
                                  for(int colms=1; colms<=rows; colms++){ 
                          
                                    System.out.print("*");
                }
                                    System.out.println();
           }

                              //Making the reverse pyramid pattern..
                              System.out.println("making reverse pyramid.");
                                    for( int rows2=4;rows2>=1; rows2--) {

                                    for(int colms2=1;colms2<=rows2;colms2++) {

                                      System.out.print(" # ");

           }
                                      System.out.println();
        } 
            // Making a solid rectangle pattern-
          /*    System.out.println("Sollid Rectangle-");
                for (int rows3 = 1; rows3 <=4 ; rows3 ++) {

                  for (int colm3 = 1; colm3<=5; colm3++) {
                    
                      System.out.print(" * ");
                  }
                      System.out.println();
                }
            */ 
            /*
             1
             12
             123   // Printing this pattern
             1234
             12345
             */
           
            for (int rows = 1; rows <=5; rows++) {
              
              for (int colms = 1; colms <=rows; colms++) {
                System.out.print(colms);
              }
                System.out.println(" ");
            }
            System.out.println("----------");
               /*
            12345
            1234
            123  // Printing this pattern
            12
            1
             */

               for (int rows =5; rows >=1; rows--) {
              
              for (int colms =1; colms <=rows; colms++) {
                System.out.print(colms);
              }
                System.out.println(" ");
            }
          
    }
}  
    
