package java_learnings.sorting;

import java.util.Arrays;

public class CyclicSort {
    static void sort(int [] arr){
        int i = 0 ; // Here we check the element at the first index then send it to the correct position if. If it's correct then we move forward.
        while (i < arr.length) {
            int correct = arr[i] - 1 ; // from this we'll check the correct position of element at i
            if (arr[i] != arr[correct]) { // If the element at i is not in the correct position then we'll send it to correct positon by swapping.
                swap(arr, i, correct);
            }else{
                i++ ;
            }
        }
    }
    static void swap(int [] arr , int first , int second){
            int temp = arr[first];
            arr[first] = arr[second];
            arr[second] = temp;
          }
    public static void main(String[] args) {
        // One thing to remember in cyclic sort is that array should be in continous form.
        int [] arr = {3, 5, 2, 1, 4};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
