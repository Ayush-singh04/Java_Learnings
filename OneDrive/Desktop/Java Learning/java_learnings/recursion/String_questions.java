package java_learnings.recursion;

public class String_questions {
       // Write a stirng(abcd) into reversive order(dcba)--
       public static void reversiveStr(String str, int index){
        if (index==0) {
            System.out.println(str.charAt(index));
            return;
        }
        System.out.print(str.charAt(index));
            reversiveStr(str, index-1);
     }
        // Find the first and last  occurence of letter "a" in (abaacdaefaah)--
        public static int first =-1; // intialising  with -1 because positive index already been taken
        public static int last =-1;

    public static void occurence(String str2, int index, char element){
        if (index==str2.length()) {
            System.out.println(first);
            System.out.println(last);
             return;
        }
        char currentchar = str2.charAt(index);
        if (currentchar == element) {
            if (first==-1) {
                first=index;
            }else{
                last=index;
            }
        }
            occurence(str2, index+1, element);
    }
        // Move all "x" to the end of the string(axbcxxd)---
     public static void moveAllX(String str3, int idx, int count, String newString){
        if (idx == str3.length()) {
            for (int i = 1; i <= count; i++) {
                newString += 'x';
            }
            System.out.println(newString);
            return;
        }
        char currentchar= str3.charAt(idx);
        if (currentchar == 'x') {
            count++;
            moveAllX(str3, idx+1, count, newString);
        }else{
            newString +=currentchar; // if the char. is not= 'x' then it will be added to newString
            moveAllX(str3, idx+1, count, newString);
        }
     }
        // Remove the duplicates from the string (abbccda)---
        public static boolean []map = new boolean[26];
     public static void remDupli(String str4, int idx, String newString){
         if (idx == str4.length()){
            System.out.println(newString);
             return;
         }
        char currentchar = str4.charAt(idx);
        if (map[currentchar -'a']) { // true
            remDupli(str4, idx+1, newString);
        }else{
            newString +=currentchar;
            map[currentchar - 'a'] = true;
            remDupli(str4, idx+1, newString);
        }

     }

    public static void main(String[] args) {
       // String str ="abcd";
       // reversiveStr(str, str.length()-1);

      // String str2 ="abaacdaefaah";
      // occurence(str2, 0, 'a');

      //String str3 = "axbcxxd";
     // moveAllX(str3, 0, 0, "");

      //  String str4 = "abbccda";
      //  remDupli(str4, 0, "");

      
       
    }
}
