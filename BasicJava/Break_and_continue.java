package java_learnings.BasicJava;

public class Break_and_continue{ 
    public static void main(String[] args) {
        // Using Break and continue methods with loops....

                 for(int i=0; i<=4; i++){
                    System.out.println(i);
                    if(i==3){
              
                 System.out.println("Process Bounded");
                    break; // Here after checking if condition the progrm. will break-sdown....
                
               } 
           } 
                // Here we will check-out Continue statement....

                    for(int x=1;x<5;x++){
                   
                    if(x==3){
                    System.out.println("Skipped...");
                    continue; // when the  condition is matched...it will skip the condition and will print other statements...
                 }
                    System.out.println(x);       
                
           }
                // Here we will check-out Continue statement using while loop....
                     System.out.println("Using while loop");
                     int x2=0;
              
                     while(x2<=8){ // imp step.

                     x2=x2+2;

                     if(x2==4){
                     System.out.println("Skipped Again..");
                     continue; // Here 4 will be skipped.
                }
                     System.out.println(x2);
           }
        }
    }
