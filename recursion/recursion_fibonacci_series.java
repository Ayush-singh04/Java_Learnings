package java_learnings.Recursion;

public class recursion_fibonacci_series {
    public static void Fibonacci(int a,int b, int n){
      //Fibonacci sequence - 0, 1, 1, 2, 3, 5, 8, 13, 21, 34
      if (n==0) {
        return;
      }
      int c =a+b;
      System.out.println(c);
      Fibonacci(b,c,n-1);
}
    public static void main(String[] args) {
       int a= 0 ,b= 1;
       System.out.println(a);
       System.out.println(b);
       int n =10;
       Fibonacci(a,b,n-2);
    }
}
