package java_learnings.pattern_Question;
public class Hollow_rec_Pattern {
    public static void main(String[]args){
            // Making a Hollow rectangle pattern-
            System.out.println("Hollow Rectangle-");
            for (int Rows = 1; Rows <=4 ; Rows++) {
                for (int colms =1; colms<=5; colms++) {
                  // To Indicate the particular rows and colms to print "*"
                  if(Rows==1 || colms==1 || Rows ==4 ||  colms==5 ){ 
                     System.out.print(" * ");
                  }else{
                     System.out.print("   "); // to print space between stars
                  }
                }
                  System.out.println();
            }
    }
}