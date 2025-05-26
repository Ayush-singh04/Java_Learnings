package java_learnings.sorting;

public class Sorting_bubble {
    public static void printArr(int arr[]){
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i] + " ");
        }
            System.out.println();
    }
    public static void main(String[] args) {
        int arr[] = {7,8,3,1,2}; // {1,2,3,7,8}
        // bubble sort
            for (int i = 0; i <arr.length-1; i++) { //outer loop is used to run till (n-1) time
                for( int j=0;j<arr.length-i-1;j++){  //inner loop is used to check the next no.
                    if (arr[j] > arr[j+1]) {
                        //Swap
                        int temp =arr[j];
                        arr[j] = arr[j+1];  // Swapping is used to compare the no. with eachh other and to replace the greater no. in last
                        arr[j+1] = temp;
                    }
                }
            }
            printArr(arr);
    }
}
