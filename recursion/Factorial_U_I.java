package java_learnings.recursion;
import java.util.Scanner;

public class Factorial_U_I{
  public static int fact(int num){ 
    if(num == 0 || num ==1){
      return 1;
    }
      return  num*fact(num-1);
}
public static void main(String[]args){
    System.out.println("Enter the number");
    Scanner sc = new Scanner(System.in);
          int num = sc.nextInt();
    System.out.println(fact(num));
  }
}
