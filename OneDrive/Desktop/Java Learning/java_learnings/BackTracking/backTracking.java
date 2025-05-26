package java_learnings.BackTracking;

/* BackTracking is a function which finds  all the possible paths to solve the question 
    and uses the suitable one you want--
*/

public class backTracking {
    // Print all the permutations of given String "ABC"---
         public static void printPermutation( String str , String perm , int idx){
            if(str.length()==0) {
                System.out.println( perm);
                return;
            }
            for (int i = 0; i < str.length(); i++) {
                char currentChar = str.charAt(i);
                String newString = str.substring(0, i) + str.substring(i+1);
                printPermutation(newString, perm + currentChar, idx+1);
            }
         }
    public static void main(String[] args) {
        String str = "ABC";
        printPermutation(str, "", 0);
    }
}
