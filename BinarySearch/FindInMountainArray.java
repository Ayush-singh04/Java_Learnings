package java_learnings.BinarySearch;

public class FindInMountainArray {
    // First we have to find the peak element of the mountain array then we will perform binary search in both the halves of the array.
    static int findPeak(int [] arr){
        int start = 0;
        int end = arr.length-1;

        while(start < end){
            int mid = start + (end - start) / 2;
            if(arr[mid] > arr[mid + 1]){
                end = mid; // This means we are in the decreasing part of the array so the peak element is at mid or before mid.
            } else {
                start = mid + 1; // This means we are in the increasing part of the array so the peak element is at mid or after mid.

            }
        }
        return start; // or return end; both will be same
    }
    // Now we have to perform binary search in both the halves of the array.
    static int searchBothSide(int [] arr , int target , int start , int end){ 

        // Check wheater the target is in the increasing part of the array or in the decreasing part of the array.

        boolean isAsc = arr[start] < arr[end]; // If the first element is less than the last element then it's an increasing array otherwise it's a decreasing array.
        
        while(start <= end){
            int mid = start + (end - start)/2;
            if(arr[mid] == target){
                return mid;
            }
            // Checking the target is in the increasing part of the array or in the decreasing part of the array.
            if (isAsc) {
               if (arr[mid] < target) {
                   start = mid + 1;
               } else {
                   end = mid - 1;
               }
            } else {
                if (arr[mid] < target) {
                    end = mid -1;
                }else{
                    start = mid + 1;
                }
            }

        }
        return -1; // Target not found
    }

    // This Function will call the above two functions to find the target in the mountain array.
    static int findInMountainArray(int [] arr , int target){
        int peak = findPeak(arr);

        int leftSide = searchBothSide(arr, target, 0, peak);
        if( leftSide != -1){ // Means element is found in the left side of the array.
            return leftSide;
        }
        // Else we'll search in  the right side of the array and return it.
        
        return searchBothSide(arr, target, peak + 1, arr.length - 1);
    }
    public static void main(String[] args) {
        int arr [] = {1, 2, 2, 3, 4, 5, 3, 1};
        int target = 3;
        System.out.println(findInMountainArray(arr, target));
    }
}
