package java_learnings.BinarySearch;

public class FirstAndLastOccurence {

    static int[] Occurence(int[] arr, int target) {
        int ans[] = {-1, -1};
        // for first occurence
        ans[0] = search(arr, target, true);
        if (arr[0] != -1) {
            ans[1] = search(arr, target, false);
        }

        return ans;
    }
    static int search(int[] arr, int target, boolean firstOccurence){
        int ans = -1;
        int s = 0;
        int e = arr.length - 1;
        while(s <= e){
            int  mid = s + (e - s)/2;
            if(target < arr[mid]){
                e = mid - 1 ;
            }else if(target > arr[mid]){
                s = mid + 1;
            }else{
                ans = mid;
                 if (firstOccurence) {
                    e = mid - 1;
                }else{
                    s = mid + 1;
            }
            }
           
        }
        return ans;
    }
    public static void main(String[] args) {
        int arr[] = {1, 2, 2, 3, 4, 4, 4, 5, 6};
        int target = 4;
        int result[] = Occurence(arr, target);
        System.out.println("First and last occurrence of element " + target + " is " + result[0] + " and LastOccurence is " + result[1]);
    }
}
