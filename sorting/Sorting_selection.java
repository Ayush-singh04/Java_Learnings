package java_learnings.sorting;

import java.util.Arrays;

public class Sorting_selection {
   
        static void selectionSort(int[] arr){
            for (int i = 0; i < arr.length; i++) {
                // Find the Max element in the array and swap it with correct index
                int last = arr.length -i -1 ; // Max Element at every step will be sended to last ,then second last , then so on backwards
                int maxIndex = findMaxIndex(arr , 0 , last);
                swap(arr, maxIndex, last);
            }
        }
        static void swap(int [] arr , int first , int second){
            int temp = arr[first];
            arr[first] = arr[second];
            arr[second] = temp;
          }
        static int findMaxIndex(int[] arr , int start , int end){
            int max = start;

            for (int i = start; i <= end; i++) {
                if (arr[max] < arr[i]) {
                    max = i ;
                }
            }
            return max ;
          }
    public static void main(String[] args) {
        int arr[] = {7,8,3,1,2};
        selectionSort(arr);
        System.out.println(Arrays.toString(arr));
            
        }
}
