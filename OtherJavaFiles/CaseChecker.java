package java_learnings.OtherJavaFiles;

import java.util.Scanner;

public class CaseChecker {
    public static void main(String[] args) {
    System.out.print("Enter the word we'll check the case of first letter : ");
        Scanner in = new Scanner(System.in);
            char word = in.next().charAt(0);
        
        if (word >= 'a' && word <= 'z') {       // This step checks the character case b/t (a to z)
            System.out.println("LowerCase character");  
        }else{
            System.out.println("UpperCase character");
        }

    }
}
