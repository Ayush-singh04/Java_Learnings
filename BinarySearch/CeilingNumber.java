package java_learnings.BinarySearch;

public class CeilingNumber {
    static int ceiling(int arr[], int target){
        int s = 0;
        int e = arr.length-1;

        while(s<=e){
            int mid = s + (e-s)/2;
            if(target < arr[mid]){
                return mid;
            }else if (target > arr[mid]){
                s = mid + 1;
            }else{
                e = mid - 1;
            }
        }
        return s; // If we don't find the target, then s will be the index of the smallest number greater than the target because we are moving the start pointer to the right when the target is greater than the mid element.
    }
    public static void main(String[] args) {
        
        // Ceiling of a number is the smallest number in the array which is greater than or equal to the target number.

        int arr[] = {2,3,5,9,14,16,18};
        int target = 15;
        int ans = ceiling(arr, target);
        System.out.println("The ceiling of " + target + " is at index: " + ans);
    }
}
