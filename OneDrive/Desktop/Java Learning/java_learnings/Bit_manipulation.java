package java_learnings;

import java.util.Scanner;

public class Bit_manipulation {
    public static void main(String[] args) {
        int n=5; // (0101) WILL check the bit of binary no. of 5
         //( Always use 1 to create bitmask by the given position)
            // Get bit operation--
     /*   if ((bitmask & n) ==0) { 
            System.out.println("Bit was Zero in pos. 2");
        }else{
            System.out.println("Bit was non-Zero in pos. 2");
        }
     */
           // Set Bit (It will change the desired bit no.  into 1)
           // int newnumber = bitmask | n; // (from 5's [0101] to set position 1 it changes to 7 [0111] )
           // System.out.println(newnumber);

          // Clear  bit (it will convert the desired bit into zero)
          //   int notbitmask = ~bitmask;
          //   int newnumber2= notbitmask & n; // (from 5's [0101] to clear position 2 it changes to 1 [0001] )
          //   System.out.println(newnumber2);

         // Update  bit (it will convert the desired bit from 0 to 1 or 1 to 0)
                    // update bit uses set or clear bit as required
            System.out.println("Choose: 0 or 1");
       try ( Scanner sc = new Scanner(System.in)) {
           try{ int operation =sc.nextInt(); //(1:set bit , 0:clear bit)
           
            if (operation==1) {
                    int pos =1;
                    int bitmask= 1<<pos;
                    int newnumber = bitmask | n; // set bit
                  System.out.println(newnumber);
             
                } else if (operation==0) {
                    int pos =2;
                    int bitmask= 1<<pos;
                    int notbitmask = ~bitmask;
                    int newnumber2= notbitmask & n; // clear bit
                   System.out.println(newnumber2);
               }
               else  {
                System.out.println("Choose only b/w 0 or 1"); 
              }
           }catch(Exception e){
                System.out.println("Entered invalid text ");
           }
              
          }  
        }
    }

