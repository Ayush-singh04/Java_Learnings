package java_learnings.Recursion;

public class KeyPad_combination {
        // print String combination of "23" in a keypad mobile---
    public static String[] keypad ={".","abc","def","ghi","jkl","mno","pqrs","tu","vwx","yz"};
      public static void keyComb(String str,int idx, String combination){
        if (idx==str.length()) {
            System.out.println(combination);
            return;
        }
        char currentchar = str.charAt(idx);
         String mapping = keypad[currentchar-'0'];
        for (int i = 0; i < mapping.length(); i++) {
         keyComb(str, idx+1, combination+mapping.charAt(i));  
        } 
      }  
    public static void main(String[] args) {
        String str ="23";
        keyComb(str, 0, "");
    }
}
