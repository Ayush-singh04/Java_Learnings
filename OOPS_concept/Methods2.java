package java_learnings.OOPS_concept;

public class Methods2 {
  static void greetuser(){
        System.out.println("hello user ! ");

    }

    static void greetuser(String name){
        System.out.println("hello "+ name + " ! ");

    }

    static String greetuser(String name , int age){
        return "hello "+ name + " ! your age is "+ age;
    }
    static int userage(int age){
        return age;
    }

    static int sum(int a , int b){
        return a+b;
    }

    static boolean isEven(int num){
        return num%2==0;
    }

    public static void main(String[] args) {
        
        // greetuser();
        // greetuser("ayush"); 
        // System.out.println(greetuser("ayush", 20));
       
        // System.out.println("your age is "+ userage(25));
        // System.out.println("the sum of 10 and 20 is "+ sum(10,20));

        
        if((isEven(15))){
            System.out.println("the number is even");
        }
        else{
            System.out.println("the number is odd");
        }
    }
}
