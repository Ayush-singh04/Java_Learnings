package java_learnings.BasicJava;

public class String_Builder {
    public static void main(String[] args) {
       // String StringBuilder is a String type non-primitive data type which manages good memory.
       // and used for advanced uses.
    /*     StringBuilder Name = new StringBuilder("Tony");
            System.out.println(Name);
        
        // charAt
        //    System.out.println(Name.charAt(0));
        // setChar
        //    Name.setCharAt(0, 'S');
        //    System.out.println(Name);

        //insert a Char
            Name.insert(0,'S');
            System.out.println(Name);
        // Delete a char
            Name.delete(0, 1);
            System.out.println(Name); 
        // Add something after the string
            Name.append(" "+"Stark");
            System.out.println(Name);
        */

           StringBuilder greet = new StringBuilder("Hello");
            System.out.println(greet);
        
          for (int i = 0; i < greet.length()/2; i++) { // we used only half the string that's why we divided by 2
                int front =i;
                int back = greet.length()-1-i; // 5-1-0 =4. logic behind reversing.
            
                char frontChar = greet.charAt(front);
                char backChar =greet.charAt(back);

                greet.setCharAt(front, backChar);
                greet.setCharAt(back, frontChar);
          }
                System.out.println(greet);

       // Pretty Printing--

       float a = 3.257897f;
        // System.out.printf("Formated number is %.2f " , a); // This is printf function which is used to format the data 
       // Output for above command will be "3.26" As we want to format it till two digits.
       System.out.printf(" Pie: %.3f" , Math.PI);
       System.out.println();
       System.out.printf("Hello My name is %s and I'm %s", "Kunal" , "cool");
    }
}
