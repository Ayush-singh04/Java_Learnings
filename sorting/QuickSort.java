package java_learnings.Sorting;

public class QuickSort {
     public static int partition(int arr[],int first , int last){
        int pivot = arr[last]; // Pivot is the element around which sorting is done.
        int i = first-1; // Here we assumed that no element is smaller than pivot till now.
        for (int j = first; j < last; j++) {
            if (arr[j] < pivot) {
                i++;
                // Swap
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        i++;
            // Swap for pivot
            int temp = arr[i];
            arr[i] = pivot;
            arr[last] = temp; 
            return i; // Here i will be the index of pivot.
     }

     public static void quickSorting(int arr[],int first , int last  ){
        if (first<last) {
            int pivotIdx = partition(arr, first, last);

            quickSorting(arr, first, pivotIdx-1);
            quickSorting(arr,  pivotIdx+1,last);
        }
     }  
    public static void main(String[] args) {
        int [] arr = {6,3,9,5,2,8};
        int n = arr.length;
        quickSorting(arr, 0, n-1);
        // Print
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i]+ " ");
        }
        System.out.println();
    }
}
