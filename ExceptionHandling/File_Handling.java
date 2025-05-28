package java_learnings.ExceptionHandling;

/*import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
*/

public class File_Handling {
    public static void main(String[] args) {
                       // --CODE TO CREATE A NEW FILE--
        /* 
                  File myFile = new File("handlingfile.txt");
                  try { 
                   myFile.createNewFile(); // A NEW FILE HAS BEEN CREATED BELOW THE PROJECTS.
                 } catch (IOException e) {
                      System.out.println("This file can't be created!");
                      e.printStackTrace();
                 }
           

                      // --CODE TO WRITE INTO A FILE--

                 try {
                FileWriter fileWriter = new FileWriter("handlingfile.txt");
                fileWriter.write("This is our first Manualy created file!\n Okay tested");
                    fileWriter.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
         

                      // --CODE TO READ A  FILE--
                 File myFile = new File("handlingfile.txt");
                try {
                    Scanner sc = new Scanner(myFile);
                    while (sc.hasNextLine()) {    // THIS LOOP IS USED TO CHANGE THE LINES AFTER READING ONE LINE.
                        String line = sc.nextLine();
                            System.out.println(line);
                    }
                        sc.close();
                } catch (FileNotFoundException e) {
                   
                    e.printStackTrace();
                }
           

                     // --CODE TO DELETE A  FILE--
                 File myFile = new File("handlingfile.txt");
                    if (myFile.delete()) { 
                System.out.println("I have deleted this file"); 
                }
                else{ 
                System.out.println("Some error occured");
                }
             */
    }
}
