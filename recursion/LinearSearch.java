package java_learnings.Recursion;

public class LinearSearch {
    static boolean search(int [] arr , int target , int index){

        if (index > arr.length-1) {
            return false;
        }
         
        return arr[index] == target || search(arr, target, index + 1) ;
    }
    static int findIndex(int [] arr , int target , int index){

        if (index > arr.length-1) {
            return -1;
        }
        if (arr[index] == target ) {
            return index ;
        }
          return  findIndex(arr, target, index + 1) ;
    }
    public static void main(String[] args) {
        // Doing LinearSearch Using Recursion---

        int[] nums = {12, 14, 9, 16, 18, 22} ;
        int target = 18 ;
        // System.out.println(search(nums, target, 0));
        System.out.println(findIndex(nums, target, 0));
    }
}
