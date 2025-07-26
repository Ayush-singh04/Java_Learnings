package java_learnings;

import java.util.Scanner;

public class New {

   static boolean ifExist(String str , String str2 ){

   int i = 0 , j = 0 ;
   while (i < str.length() && j < str2.length()) {
    if (str.charAt(i) == str2.charAt(j)) {
      i =  i + 1 ;
      j =  j + 1 ;
    }else{
         i =  i + 1 ;
         j = 0 ;
    }
    return true ;
   }
    return false ;
   }
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
        
       System.out.println("Enter the Sentence :");
        String str = sc.nextLine();
       System.out.println("Enter the word you want to find :");
        String str2 = sc.nextLine();

    ifExist(str, str2);
            
    }
}
