package java_learnings.BasicJava;

public class Arrays{
    public static void main(String[] args) {
        //Arrays....

        // We can declare Arrays using three methods.
        /*1st-
                int[]marks;
                marks=new int[4];
                                    */
        /*2nd-
              int[] marks = new int [4];
                  marks[0]=55;
                  marks[1]=56;
                  marks[2]=71;
                  marks[3]=27;
                  System.out.println(marks[1]);
                                                */
        //3rd-
            int[]marks={55,56,71,27};
              System.out.println(marks[0]);
                 System.out.println(marks.length);
          
            // We can print the all elements of array using for loop...in only one line of code .
               // System.out.println("Printing using for loop :");
               // for(int i=0;i<=marks.length;i++){
               //     System.out.println(marks[i]);
               // }
      
            // We will use for each loop to make code easier...and simple.
                System.out.println("Printing using for each loop :");
                 String[]students={"Rohan","Jack","Robert"};
                     for(String element : students){
                          System.out.println(element);
        }
    }

    public static Object asList(int[] arr) {
        return null;
    }
} 