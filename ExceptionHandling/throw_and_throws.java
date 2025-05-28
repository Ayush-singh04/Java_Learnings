package java_learnings.ExceptionHandling;
class myException1 extends Exception{

    @Override
    public String toString() {
        return "Radius Can't be NEGATIVE!";
    }
    public String getMessage() {
        return "Radius Can't be NEGATIVE!";
    }
    
}
public class throw_and_throws {
    public static double areaCircle(float r) throws myException1{
        if(r<=0){
            throw new myException1();
        }
    double result = Math.PI*r*r;
    return result;
        
}

   public static int divide(int a, int b)throws ArithmeticException{ // Its reminded the programmer that it can throw an exception.
        int result = a/b;
        return result;
    }
    public static void main(String[] args) {
      try{ int c = divide(8,0);
       System.out.println(c);
      }catch( Exception e){
        System.out.println("exception occured: "+e);
      }try{
        double area = areaCircle(-4);
        System.out.println("The area of circle is: "+area);
      }
      catch(Exception e){
        System.out.println("Exception: " +e);
      }
     
       

       
  
    }
}
