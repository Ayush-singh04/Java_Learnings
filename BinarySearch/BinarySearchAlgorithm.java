package java_learnings.BinarySearch;

public class BinarySearchAlgorithm {
    // The use BinarySearch Algorithm 
    //  Return the index of target element
    //  If target not exist return -1
    static int binarySearch(int []arr , int target){
    if (arr.length == 0) {
        return -1;
    }
        int start = 0 ;
        int end = arr.length -1 ;   
    while (start <= end) {
        // Find the middle element 
        // int mid = (start + end) / 2 ; // This is the Basic formula to find the middle.
        int mid = start + (end-start) /2 ; // It does the same as above but helps when number gets bigger than the limit of integer in java.

        // Comparing target with mid then deciding whether to search in left or right.
            if (target < arr[mid] ) {
                end = mid-1;
            }else if (target > arr[mid]){
                start = mid+1;
            }else{
                return mid;
            }
    }
    return -1 ;
    }
    static int orderAgnosticBS(int arr [] , int target){
         if (arr.length == 0) {
        return -1;
    }
        int start = 0 ;
        int end = arr.length -1 ;   
        // finding whether the array is Ascending or Descending
         boolean isAsc = arr[start] < arr[end] ; // In any sorted array we can use this to check the (Asce. or Desce.)
            // In the above line the if first element is samller than the last then its ascending otherwise vice-versa.
    while (start <= end) {
         int mid = start + (end-start) /2 ;

         if (arr[mid] == target) {
            return mid;
         }
         // For Ascending----
         if (isAsc) {
            if (target < arr[mid] ) {
                end = mid-1;
            }else {
                start = mid+1;
            }
         }else{
             if (target > arr[mid] ) {
                end = mid-1;
            }else{
                start = mid+1;
            }
        }     
    }
    return -1 ;
    }
    public static void main(String[] args) {
        int nums [] = {2, 6, 10, 15, 19, 26};
        int target = 15;
        // System.out.println(binarySearch(nums, target));

    //  what if we dont know the array is sorted in which way (Ascending or Descending)
        int descNums [] = {79, 69, 34,26, 19, 15, 12, 9}; // Descending order array
           System.out.println(orderAgnosticBS(descNums, target));
    }
}
