package java_learnings.BinarySearch;

import java.util.Arrays;

public class BinarySearch2DArray {
    static int [] search(int [][] matrix , int target){
        int row = 0; // we'll Start checking from  matrix[ 0 , 0] element
        int col = matrix.length -1 ; // And the col. will always be checked from last.

            while (row < matrix.length && col > 0) { // Row can't exceed the length and col can't be zero cause both are moving forward and backward respectively.
                if (matrix[row][col] == target) {
                    return new int [] {row , col} ;

                }else if(matrix[row][col] < target){
                    row ++ ;
                }else{
                    col -- ;
                }
            }
                 return new int [] {-1 , -1} ;

    }
     static int [] newSearch(int [][] matrix , int target){
        int row = 0 ;
        int col = matrix.length-1;
              while (row < matrix.length && col > 0) { // Row can't exceed the length and col can't be zero cause both are moving forward and backward respectively.
                if (matrix[row][col] == target) {
                    return new int [] {row , col} ;

                }else if(matrix[row][col] < target){
                    row ++ ;
                }else{
                    for (int i = 0; i < matrix[row].length; i++) {
                        if (target == matrix[row][i]) {
                            return new int[] {row , i };
                        }
                    }
                //   int Column = Arrays.toString(binarySearch(matrix[row], target , 0 , matrix[row].length ));
                //   return new int [] {row , Column};
                }

         }
         return new int [] {-1,-1};
     }
    //   static int [] binarySearch(int []arr , int target , int start , int end){  
    // while (start <= end) {
    //     // Find the middle element 
    //     // int mid = (start + end) / 2 ; // This is the Basic formula to find the middle.
    //     int mid = start + (end-start) /2 ; // It does the same as above but helps when number gets bigger than the limit of integer in java.

    //     // Comparing target with mid then deciding whether to search in left or right.
    //         if (target < arr[mid] ) {
    //             end = mid-1;
    //         }else if (target > arr[mid]){
    //             start = mid+1;
    //         }else{
    //             return new int [] {mid};
    //         }
    // }
    //  return new int [] {-1 , -1};
    // }
    public static void main(String[] args) {
        // Searching in Rows and Columns type sorted 2D array
        int [] [] arr = {
            {10, 20, 30, 40},
            {15, 25, 35, 45},
            {28, 29, 37, 49},
            {33, 34, 38, 50},
        } ;

        // System.out.println(Arrays.toString(search(arr, 37)));

        // Searching in complete sorted 2D array
         int [] [] arrNew = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
            {13, 14, 15, 16},
        } ;

        System.out.println(Arrays.toString(newSearch(arrNew, 9)));
    }
}
