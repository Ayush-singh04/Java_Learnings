package java_learnings.recursion;

import java.util.ArrayList;
import java.util.HashSet;

public class String_questions_2 {
    
        // Print all the subsequences of a string "abc"---
        public static void subsequences(String str, int idx, String newString){
            if (idx == str.length()){
               System.out.println(newString);
                return;
            }
            char currentchar = str.charAt(idx);
           // if the character wants to get choosed
               subsequences(str, idx+1, newString+currentchar);
           // if the character does'nt wants to get choosed
               subsequences(str, idx+1, newString);
           }
            // Print all the subsequences of a string "aaa" with unique pattern---
        public static void subsequences2(String str, int idx, String newString, HashSet<String> set){
            if (idx == str.length()){
               if (set.contains(newString)) {
                  return;
               }else{
                System.out.println(newString);
                    set.add(newString);
                    return;
               }
              
            }
            char currentchar = str.charAt(idx);
           // if the character wants to get choosed
               subsequences2(str, idx+1, newString+currentchar,set);
           // if the character does'nt wants to get choosed
               subsequences2(str, idx+1, newString,set);
           }
            // Print all the permutations of the string "abc"
            public static void permutations(String str2, String permutation){
                if (str2.length()==0) {
                    System.out.println(permutation);
                    return;
                }
               for (int i = 0; i < str2.length(); i++) {
                   char currentchar = str2.charAt(i);
                   // "abc"-> "bc".
                   String newString = str2.substring(0, i) + str2.substring(i+1);  // "bc"
                   permutations(newString, permutation+currentchar);
               }
          
            }
            // Print the all subsets of the first n natural number (n=3)---
                public static void PrintSubset( ArrayList<Integer> subset){
                    for (int i = 0; i < subset.size(); i++) {
                            System.out.println(subset.get(i)+" ");
                    }
                        System.out.println();
                }
                public static void findSubset(int n, ArrayList<Integer> subset){
                    if (n == 0) {
                        System.out.println(subset);
                        return;
                    }
                    // willAdd
                    subset.add(n);
                    findSubset( n-1, subset);
                    // won't add
                    subset.remove(subset.size()-1);
                    findSubset(n-1, subset);
                }
    public static void main(String[] args) {
       // String str = "abc";
       // subsequences(str, 0, "");
       //  String str = "aaa";
       //  HashSet<String> set = new HashSet<>();
      //  subsequences2(str, 0, "",set);
        
      //  String str2 = "abc";
      //  permutations(str2, "");

      int n =3;
      ArrayList<Integer> subset = new ArrayList<>();
      findSubset(n, subset);
    }
}
