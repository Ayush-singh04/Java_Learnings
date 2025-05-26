package java_learnings.BasicJava;

//import java.util.Scanner;
import java.util.Arrays;
import java.util.Scanner;   

public class Practice_set{

      //x and y are the numbers to find the GCF  
          public static int divisor(int x,int y){
               int gcd=1; //gcd- Greatest common divisor
            //running loop form 1 to the smallest of both numbers  
                for(int i = 1; i <= x && i <= y; i++) {  
            //returns true if both conditions are satisfied   
                if(x%i==0 && y%i==0) { 
            //storing the variable i in the variable gcd 
                  gcd=i;
                }   
            }  
               return gcd; 
      }
    public static void main(String[] args) {
        
      /*   // Q1- Write a programm for the sum of first N even nos. using while loop.
        int sum=0;
        int n=4;  
        int i=0;
        while(i<n){
             
           sum =sum+(2*i);// For even numbers..
          // sum=sum+ (2*i+1);// For odd numbers..
           i++;
           
        }
           System.out.print("The sum of first "+n+ " even nos. are "+sum);
        */

             /* // Q2- Write the table of 5 using loop..

            int n = 5;

            for(int i=1;i<=10;i++){
       
               System.out.println(n+" X "+i+" = "+n*i); 

            }*/

           /*  //Q3- create an array of 5 float elements and make sum of them..
               float sum =0;
               float [] numb={10.11f,29.5f,30.06f,40.6f,50.5f};
                  for (float element : numb) {
                      sum = sum + element;
                  }
               System.out.println("The sum of all the numbers is : "+sum);
           */
            /*  //Q4- Write a program to find a given no. is present in an array or not..
               float [] marks={10.11f,29.5f,30.6f,40.6f,50.5f};
                float Numb=50.5f;
                boolean found = false;
               for (float element : marks){
               
               if(Numb==element){
                  found = true;            
                  break;                   
              }
              }
               if(found){  
                   System.out.println("Given no. is present ");
            }
               else{
                  System.out.println("Given no. is not present ");
        }
          

               // Q5- Add two matrices of 2x3...

               int[][] matr1={{2,5,8} ,
                              {10,9,8}};

               int[][] matr2={{5,7,3} ,
                              {11,0,1}};

               int[][] result={{0,0,0} ,
                               {0,0,0}};
               
               for(int i=0;i<matr1.length;i++){ // row no. of times..
                  for(int j=0;j<matr1[i].length;j++){  // column no. of times..
                     result[i][j]= matr1[i][j]+matr2[i][j]; // Mathematical formulae of sum of matrix..
                     System.out.print(result[i][j] +" ");
                  }
                     System.out.println("");
               }
               
        
         // Q6- Write a java programm which reverse the array..

               int [] arr={1,2,3,4,5,6,7,};
                int l =arr.length;
                int n = Math.floorDiv(l, 2); // this method is used for division withoud returning the decimal number.
                int temp;
                for(int i=0;i<n;i++){
                     //We will swap the numbers with each other.(arr[i] with arr[l-1-i]) ,i- index of elements
                       temp = arr[i];
                       arr[i]=arr[l-1-i];
                       arr[l-1-i]=temp;
                  }
                     for (int element : arr) {
                        System.out.print(element +" ");
                     }
                  

          // Q7- Find the max./min. no. in array..
              int [] arr={10,2,3,4,5,6,-77,};
              int max=Integer.MIN_VALUE;
              
              for (int element : arr) {
                  if(element>max){
                     max=element; 
                  }  
              }
               System.out.println("The max. element is :"+max);
                int min=Integer.MAX_VALUE;
                for (int element : arr) {
                  if(element<min){
                     min=element;
                  }
               }
               System.out.println("The min. element is :"+min); 
               */
            
         // Accending order for Array..
          
          int [] arr={10,2,3,4,5,6,-77,};
          Arrays.sort(arr); // for this condtion we have to import array function
          for (int i = 0; i < arr.length; i++)   
          {       
          System.out.println(arr[i]);   
          }  
            // gcd---
            
           try (Scanner sc = new Scanner(System.in)) {
                            System.out.print("Enter the 1st no: ");  
                            int x=sc.nextInt();  
                             System.out.print("Enter the 2nd no: ");  
                             int y=sc.nextInt(); 
                      int  div =divisor(x, y);
                     System.out.println("Greatest Common Divisor of "+x+" & "+y+" is "+div);
           }
    }
}