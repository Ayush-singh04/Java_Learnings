package java_learnings.BasicJava;


public class Loops {
 
    public static void main(String[]args){

      
      String sec = "he" + "ll";
       sec = sec + "o";
       sec = sec.toUpperCase();
      System.out.println(sec);
 
    // It's for counting the numbers.     
 
       /*  for(int X = 1;X<=10;X++)
    {
        System.out.println(X);
    }*/
       
    // It runs for the 'i' first then for 'j'
            
             for(int i=1;i<=3;i++){ 
               
    // Here 'i' gets fixed till 'j' completes it's rotation.
             
             for(int j=1;j<=3;j++){  
               
            //  System.out.println(i+"  "+j);  

            } //end of i  
    
          }//end of j  
       

            // Example -: Count the number of occurence of a digit.

       /*      int num = 3431;
              int count = 0;
            while (num>0) {
              int rem = num%10;
                if (rem == 3) {
                  count++;
                }
                num = num/10;
            }
            System.out.println(count);
       */

            int num =123456;
              int revNum = 0;
            while (num>0) {
              int rem = num%10;
             num /= 10;
             revNum = revNum * 10 + rem;
            }
           System.out.println(revNum);
        }  
   
     }
    

     

    

