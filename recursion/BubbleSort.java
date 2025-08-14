package java_learnings.Recursion;

import java.util.Arrays;

public class BubbleSort {

   static void bubble(int [] arr , int col , int row){
        // base case
        if (row == 0) {
            return;
        }
        if(col < row){
            if (arr[col] > arr[col+1]) {
                 // swap
            int temp = arr[col] ;
            arr[col] = arr[col+1] ;
            arr[col+1] = temp ;
                
        }
            bubble(arr, col+1 , row);
           
   }else{
        
            bubble(arr, 0 ,row-1);
            
        }
    }
           
    public static void main(String[] args) {
        int arr [] = {7, 5, 3, 8, 1, 9, 0};

            bubble(arr, 0 , arr.length-1);

        System.out.println(Arrays.toString(arr));

    }
}
