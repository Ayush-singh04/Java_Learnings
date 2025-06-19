package java_learnings.sorting;

import java.util.Arrays;

public class Sorting_insertion {
    static void insertionSort(int arr[]){
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i+1; j > 0; j--){ // j moves backward here comparing to the previous element 
                if (arr[j] < arr[j-1]) {
                   swap(arr, j, j-1);
                }else{
                    break;
                }
            }
        }
    }
     static void swap(int [] arr , int first , int second){
            int temp = arr[first];
            arr[first] = arr[second];
            arr[second] = temp;
          }
    public static void main(String[] args) {
        int arr[] = {7,8,3,1,2};
        // insertion sort
         
           insertionSort(arr);
           System.out.println(Arrays.toString(arr));
        }
}
