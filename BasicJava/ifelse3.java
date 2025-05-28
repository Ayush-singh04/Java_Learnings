package java_learnings.BasicJava;

// Test.
public class ifelse3 {
    public static void main(String[]args){

    int A=12;
 
    if(A>=1){
        System.out.println(A + " is a Natural number.");
    }
    else{
        System.out.println(A + " is not a Natural number.");
    }

    if(A>=0){
        System.out.println(A+ " is a POSITIVE number.");
         System.out.println(A + " is a WHOLE number.");
    }
    else{
        System.out.println(A+ " is a NEGATIVE number.");
          System.out.println(A + " is not a WHOLE number.");
    }

    if(A>=50){
        System.out.println(A+ " is GREATER than 50.");    
    }
    else{
        System.out.println(A+" is LESS than 50."); 
    }

    }
    
}
