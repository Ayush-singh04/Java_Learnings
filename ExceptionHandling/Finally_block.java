package java_learnings.ExceptionHandling;

public class Finally_block {
    public static int div(){
        try{
         int a = 110;
         int b =10;
         int c = a/b;
        return c;
    }catch(Exception e){
        System.out.println(e);
    }
   //- Finally block will be executed in every condition whether code will throw exception or not. -\\
    finally{ 
        System.out.println("The process is over!");
    }
    return -1;
}
    
    public static void main(String[] args) {
      // int D =div(); 
      //System.out.println(D);

       int a = 10;
       int b = 5;
    while (true) {
        
      try {
        System.out.println(a/b);
      } catch (Exception e) {
        System.out.println(e);
        break;
      }
      finally{
        System.out.println("when 10 is divided by "+b );
      }
      b--;
    }
    }
}
