package java_learnings.BasicJava;

public class switch2 {
    
  public static void main(String[]args){

   String level="Beginner";
      int rank=0;
        switch(level){  

           case "Beginner": rank=3;
           break;

           case "Intermediate": rank=2;
           break;  

           case "Expert": rank=1;
           break; 

           default: rank=0;
           break;
        }  
          System.out.println("Your Rank is: "+rank);

  }
}
