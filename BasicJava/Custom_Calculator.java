package java_learnings.BasicJava;

class InvalidInputException extends Exception{
    public String toString(){
        return "Can't add 8 and 9";
    }
}
class maxValueException extends Exception{
    public String toString(){
         return "Exceeded the value limit!";
      }
}
class maxMultipleException extends Exception{
    public String toString(){
         return "Can't Multiply over 7000";
      }
}


class Calculator {
    
    double add(int a , int b) throws InvalidInputException,maxValueException,maxMultipleException{
         if(a>10000 || b>10000){
            throw new maxValueException();
         }
        if (a==8 || b==9) { 
        throw new InvalidInputException();
        }
        return a+b;
    }
    double substract(int a , int b)throws maxValueException{
         if(a>10000 || b>10000){
            throw new maxValueException();
         }
        return a-b;
    }
    double multiply(int a , int b)throws maxValueException,maxMultipleException {
         if(a>7000 || b>7000){
            throw new maxMultipleException();
         }
         if(a>10000 || b>10000){
            throw new maxValueException();
         }
        return a*b;
    }
     double divide(int a , int b)throws maxValueException{
         if(a>10000 || b>10000){
            throw new maxValueException();
         }
        return a/b;
    }
}
 


public class Custom_Calculator {
   
    
    public static void main(String[] args) throws InvalidInputException ,maxValueException,maxMultipleException {
       Calculator cal = new Calculator();
        //   System.out.println(cal.add(8, 9));
        // System.out.println(cal.divide(100000, 2));
            System.out.println(cal.multiply(7001, 3));


    }
}
