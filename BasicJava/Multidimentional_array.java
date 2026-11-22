package java_learnings.BasicJava;

import java.util.Arrays;
import java.util.Scanner;

public class Multidimentional_array{
    public static void main(String[] args) {
        
        //2D Arrays

        //   int[][] a ={{55,61,43},{10,11,13}};
        //      System.out.println(a[1][2]);
           
        //          String[][] section ={{"Rohan","Mohan","Robert"},{"Ram","Shyam","Rohit"}};
        //              for(int i=0;i<section.length;i++){
        //                 for(int j=0;j<section[i].length;j++){
        //                 System.out.print(section[i] [j]);
        //              System.out.print(" ");
        //     }
        //          System.out.println(" ");
        // }
        
        // How to take Inputs in 2D arrays
        System.out.print("Add your element : ");
        Scanner sc = new Scanner(System.in);
       
        int [][] arr2d = new int [3][3];

            // For Input

        for (int row = 0; row < arr2d.length; row++) {
        for (int col = 0; col < arr2d[row].length; col++) {
                arr2d [row] [col] = sc.nextInt();
           }
        }

            // For Output
        // for (int row = 0; row < arr2d.length; row++) {
        // for (int col = 0; col < arr2d[row].length; col++) {
        //        System.out.print( arr2d [row] [col] + " "); 
        //    }
        //    System.out.println( );
        // }
            // Other way taking Output
         for (int row = 0; row < arr2d.length; row++) {
            // for (int[] a : arr2d) {
            //  System.out.println(Arrays.toString(a) );   
             System.out.println(Arrays.toString(arr2d[row]));
        }
    }
}