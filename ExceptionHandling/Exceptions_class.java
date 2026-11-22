package java_learnings.ExceptionHandling;

import java.util.Scanner;

class myException extends Exception{

    @Override
    public String toString() {
        return  " I am toString() ";
    }

    @Override
    public String getMessage() {
         return "Upps...enter the no. bigger than 100";
    }
}
public class Exceptions_class {
    public static void main(String[] args) {
        System.out.println("Enter the number");
       try ( Scanner sc = new Scanner(System.in)){
             int a = sc.nextInt();
        if(a<100) {
            try{
              //  throw new myException();
                throw new ArithmeticException("This is an Exception ");
        } catch (Exception e) {
            System.out.println(e.getMessage()); // it will print getMessage (execption)
         //   System.out.println(e.toString()); // it will print toString (execption)
         //   System.out.println(e);   // it will print toString execption by [By Default]
            e.printStackTrace();
           }
         }
       }
    }
}

