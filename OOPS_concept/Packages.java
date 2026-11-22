package java_learnings.OOPS_concept;

//These are the inbuild Packages---:
// import java.util.Scanner; // It imports particular one Package...
   import java.util.*;   // It Imports all the available inbuild Packages in java... 

public class Packages{
     public static void main(String[] args){
           try (Scanner sc = new Scanner(System.in)) {
            //java.util.Scanner sc = new java.util.Scanner(System.in); //This is also a Method to impliment a Scanner Package
            int a = sc.nextInt();
            System.out.println("I'm an inbuild package! " +a);
         }
     }
}