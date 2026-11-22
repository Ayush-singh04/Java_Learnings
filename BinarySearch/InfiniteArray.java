package java_learnings.BinarySearch;

public class InfiniteArray {
        static int ans(int [] arr , int target){
             // First find the range of array
            //  We'll move in the chunks of 2 then make it twice with every move
            int start = 0 ;
            int end = 1;
            // We'll check if the target lies in between this chunk.
            while (target > arr[end]) { // If target is greater than last element then it's definetly not gonna be in this chunk.
                int newStart = end+1; // Next chunk will start from here.

                // For the end of new chunk we have double the space of first chunk then assign the new end at last of it.
                end = end + (end -start + 1) *2; // This is very important step.
                start = newStart;
            }
            return binarySearch(arr, target, start, end);
        }

// We have to perform binarySearch in the chunks of arrays cause we don't know the exact length of main array.
        static int binarySearch(int []arr , int target ,int start ,int end){
    // if (arr.length == 0) {
    //     return -1;
    // } 
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
    public static void main(String[] args) {
        
    // Q3- Find the position of an element in the sorted array of infinite numbers.(we'll not use {array.length} function in this question's approach )
          int[] arr = {3, 5, 7, 9, 10, 90,
                100, 130, 140, 160, 170};
        int target = 10;
        System.out.println(ans(arr, target));
    

    }
}
