package java_learnings.sorting;

public class merge_sort {
    /*In mergeSort divide and rule technique work---
     * we first divide the big list of data and then sort from last part of the divisoin--
     */
    public static void sorted(int arr[], int Si, int mid, int Ei){
        int merged[] = new int[Ei-Si+1];
        int idx1 = Si;
        int idx2 = mid+1;
        int x =0;
    while (idx1 <= mid && idx2 <= Ei) {
        if (arr[idx1]<= arr[idx2]) {
            merged[x++] = arr[idx1++];
        }else{
            merged[x++] = arr[idx2++];
        }
    }
    while (idx1 <= mid) {
         merged[x++] = arr[idx1++];
    }
    while (idx2 <= Ei) {
         merged[x++] = arr[idx2++];
    }
    for (int i = 0 , j=Si; i< merged.length; i++, j++) {
        arr[j] = merged[i];
    }
    }
    public static void divide( int arr[], int Si, int Ei){ // si- Starting index , ei- ending index
        if (Si>=Ei) {
            return; 
        }
        int mid = Si +(Ei-Si)/2; // mid- middle
        divide(arr, Si, mid);
        divide(arr, mid+1, Ei);
        sorted(arr, Si, mid, Ei);
    }
    public static void main(String[] args) {
        int [] arr = {6,3,9,5,2,8};
        int n = arr.length;
       divide(arr, 0, n-1);
        //print
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i]+ " ");
        }
        System.out.println();
    }
}
