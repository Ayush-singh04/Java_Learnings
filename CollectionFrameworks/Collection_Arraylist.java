package java_learnings.CollectionFrameworks;
import java.util.ArrayList;
import java.util.Collections;
public class Collection_Arraylist {
    public static void main(String[] args) {
       
        ArrayList<Integer> l1= new ArrayList<>(); // This line shows that the programmer wants to make an array of integers.
        ArrayList<Integer> l2= new ArrayList<>();
        l2.add(33);
        l2.add(19);
        l2.add(7);
         
        l1.add(6);
        l1.add(0,5);
        l1.add(9);
        l1.add(0,7);
        
        l1.addAll( l2);
          System.out.println(l1.contains(3));
        System.out.println(l1.indexOf(33));
        Collections.sort(l1); // This method sorts the complete list automatically ,(we need to import collections package for it)-
       // System.out.println(l1);
      // System.out.println(l1.lastIndexOf(7));
     //  l1.set(6, 10); // It removes the element of given index and adds the given element     
       //     l1.clear();
      //    System.out.println( l1.equals(8)); //-- not sure

     //  for (int i = 0; i < l1.size(); i++) {
        //   System.out.print(l1.get(i));
        //    System.out.print(",");
      //  }
    }
}
