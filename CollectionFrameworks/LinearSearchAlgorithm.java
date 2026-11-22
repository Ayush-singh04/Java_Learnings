package java_learnings.CollectionFrameworks;

import java.util.Arrays;

public class LinearSearchAlgorithm {

        // Search in the Array and return the index if found otherwise item not found return -1

    static int linearSearch(int []arr , int target){
        if(arr.length == 0){
            return -1;
        }
        // searching part
        for (int i = 0; i < arr.length; i++) {
            // Checking element 
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

        // Funtion for searching an element in the Range
        static boolean inRange(int [] arr , int target , int a , int b){
            if (arr.length == 0) {
               return false; 
            }
            // Searching Part
            for(int i = a ; i <+ b ; i++){
                if(arr[i] == target){
                    return true;
                }
            }
            return false;
        }
        static int[] twoDSearch(int [][] arr , int find){
             if (arr.length == 0) {
                  return new int[] {-1 , 1}; 
            }
            // Searching Part
            for (int row = 0; row < arr.length; row++) {
                for (int col = 0; col < arr[row].length; col++) {
                    if(arr[row][col] == find){
                       
                       return new int [] {row , col};
                    }
                }
            }
            return new int[] {-1 , -1}; 
        }
    public static void main(String[] args) {
        int [] nums = {23, 45, 1, 2, 8, 19, -3, 16, -11, 28};
        int target = 19;
        // System.out.println(linearSearch(nums, target));

    // Q - Search In Range--- {From index 1 to 4}
//    System.out.println(inRange(nums, target, 1, 4));

    // Q - Search in the 2D Array---

        int [] [] arr2D = {

            {2, 4, 6} , 
            {10, 11, 16},
            {99, 12, 45, 66},
            {11, 30, 32, 69}
            };
    int find = 69 ;
    int[] ans = twoDSearch(arr2D, find);
   System.out.println(Arrays.toString(ans));
    }
}
