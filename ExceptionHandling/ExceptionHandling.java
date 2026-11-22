package java_learnings.ExceptionHandling;

public class ExceptionHandling {

   static void divded(int a , int b){
        try{
            int div = a/b ;
            System.out.println(div);
        }
        catch(Exception e ){
            System.out.println("Can't divide by Zero.");
        }
        
    }
    public static void main(String[] args) {
        
        divded(5, 0);
    }
}
