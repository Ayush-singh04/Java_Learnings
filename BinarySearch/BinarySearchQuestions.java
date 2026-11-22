package java_learnings.BinarySearch;

public class BinarySearchQuestions {
    // For Q1 (Finding the Smallest element Greater or Equal to Target )
     static int ceilingOfNumber(int arr [] , int target){
    // Base Cases
    if (arr.length == 0) {
        return -1;
    }
    // Base Cases
    if (target > arr [arr.length-1]) {
        return -1;
    }
        int start = 0 ;
        int end = arr.length -1 ;   
    while (start <= end) {
        int mid = start + (end-start) /2 ; 
            if (target < arr[mid] ) {
                end = mid-1;
            }else if (target > arr[mid]){
                start = mid+1;
            }else{
                return mid;
            }
    }
    return start ; // Atlast if target not found then we have to return the smallest element greater than target. So, we know "start" is moving forward or "end" is moving backwards so atlast when the loop will voilated "start" will be greater than "mid" so the answer will be "start" . 
    }


    // For Q2 (Finding the Greatest element less or Equal to Target )
     static int floorOfNumber(int arr [] , int target){
    // Base Cases
    if (arr.length == 0) {
        return -1;
    }
    // Base Cases
    if (target < arr [arr.length-1]) {
        return -1;
    }
        int start = 0 ;
        int end = arr.length -1 ;   
    while (start <= end) {
        int mid = start + (end-start) /2 ; 
            if (target < arr[mid] ) {
                end = mid-1;
            }else if (target > arr[mid]){
                start = mid+1;
            }else{
                return mid;
            }
    }
    return end ; // Atlast if target not found then we have to return the Greatest element lesser than target. So, we know "start" is moving forward or "end" is moving backwards so atlast when the loop will voilated "end" will be lesser than "mid" so the answer will be "end" . 
    }
    public static void main(String[] args) {
        // Q1- Ceiling of a Numer // Q2- Roof of a Number

        int nums [] = {2, 3, 5, 9, 14, 16, 18};
        int target = 15;
        System.out.println(ceilingOfNumber(nums, target));
        // System.out.println(floorOfNumber(nums, target));

    

    }
}
