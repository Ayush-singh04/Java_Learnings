package java_learnings.BasicJava;

public class Multidimentional_array{
    public static void main(String[] args) {
        
        //2D Arrays

          int[][] a ={{55,61,43},{10,11,13}};
             System.out.println(a[1][2]);
           
                 String[][] section ={{"Rohan","Mohan","Robert"},{"Ram","Shyam","Rohit"}};
                     for(int i=0;i<section.length;i++){
                        for(int j=0;j<section[i].length;j++){
                        System.out.print(section[i] [j]);
                     System.out.print(" ");
            }
                 System.out.println(" ");
        }
    }
}