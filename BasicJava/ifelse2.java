package java_learnings.BasicJava;


public class ifelse2 {
    public static void main(String[]args) {
        
    

        int marks=199;
    
   if (marks>=60){
  
        System.out.println("you got first Division marks.");   
   }    
   else if (marks<=59){
        System.out.println("you got second Division marks.");
   }
   else if (marks<=26){
        System.out.println("you failed!");
   }
   else{
    System.out.println("Erorr!");
   } 
}
}