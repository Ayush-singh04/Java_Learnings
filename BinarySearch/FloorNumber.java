package java_learnings.BinarySearch;

public class FloorNumber {
    static int floor(int arr[], int target){
        int s = 0;
        int e = arr.length-1;

        while(s<=e){
            int mid = s + (e-s)/2;
            if(target < arr[mid]){
                e = mid - 1;
            }else if (target > arr[mid]){
                s = mid + 1;
            }else{
                return mid;
            }
        }
        return e; // If we don't find the target, then e will be the index of the greatest number less than the target because we are moving the end pointer to the left when the target is less than the mid element.
    }
    public static void main(String[] args) {
        // Floor of a number is the greatest number in the array which is less than or equal to the target number.

        int arr[] = {2,3,5,9,14,16,18};
        int target = 15;
        int ans = floor(arr, target);
        System.out.println("The floor of " + target + " is at index: " + ans);
    }
}
