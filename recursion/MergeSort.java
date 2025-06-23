package java_learnings.recursion;

import java.util.Arrays;

public class MergeSort {

    static int [] mergeSort(int [] arr ){
        if (arr.length == 1) {
            return arr ;
        }

        int mid = arr.length / 2 ;

    int [] left = mergeSort(Arrays.copyOfRange(arr,0, mid)); // This function copies array from given range
    int [] right = mergeSort(Arrays.copyOfRange(arr,mid, arr.length));
        
        return merge(left , right);
    }

    static int[] merge(int [] first , int[] second){

        int [] mixed = new int[first.length + second.length] ;

        int i = 0 , j = 0 , k = 0 ;
    while (i < first.length && j < second.length) { // If one of the array gets out of bound then this loop will end.
        if (first[i] < second[j]) {
            mixed[k] = first[i] ;
            i++ ;
        }else{
            mixed[k] = second[j] ;
            j++ ;
        }
            k++ ;    
   }
   
    // It may be possible that one of the arrays is not complete in that case add all the remaining elements from other array as it is.
    while (i < first.length) {
         mixed[k] = first[i] ;
            i++ ;
            k++ ;
    }
    while (j < second.length) {
         mixed[k] = second[j] ;
            j++ ;
            k++ ;
    }
   return mixed ;
}
    public static void main(String[] args) {
        int [] arr = {7, 2, 3, 9, 0, 1} ;
        arr =  mergeSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
