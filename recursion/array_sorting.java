package java_learnings.Recursion;

public class array_sorting {
    // Check whethere this array(1,2,3,4,5) is sorted[Strictly increasing] using recursion---
        
        public static boolean array(int arr[] , int idx){
            if (idx == arr.length-1) {
                return true;
            }
           if (arr[idx] < arr[idx+1]) {
                // Array is sorted
                return  array(arr, idx+1);
           }else{
            return false;
     }
        
 }
    public static void main(String[] args) {
       int arr[] ={1,3,3}; 
        System.out.println( array(arr, 0));
    }
}
