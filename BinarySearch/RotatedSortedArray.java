package java_learnings.BinarySearch;

public class RotatedSortedArray {
      static int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while(start <= end){
            int mid = start + (end - start)/2;
            if(nums[mid] == target){
                return mid;
            }
            // Checking if the left part is sorted...
            if(nums[start] <= nums[mid]){
                if(target >= nums[start] && target < nums[mid]){
                    end = mid - 1 ;
                }else{
                    start = mid + 1;
                }
            }
            // Checking if the right part is sorted...
            else{
                if(target > nums[mid] && target <= nums[end]){
                    start = mid + 1;
                }else{
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {4, 5, 6, 0, 1, 2, 3};
        int target = 2;
        int result = search(nums, target);
        System.out.println("Index of target: " + result);
    }

}