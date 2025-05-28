 package java_learnings.CollectionFrameworks;

import java.util.*;

public class linkedList { // Class name can't be exact same as "LinkedList" case sensitive.
    public static void main(String[] args) {
      
      LinkedList<Integer> l1 = new LinkedList<>();
      LinkedList<Integer> l2 = new LinkedList<>();
        l2.add(33);
        l2.add(19);
        l2.add(7);
         
        l1.add(6);
        l1.add(0,5);
        l1.add(9);
        l1.add(0,7);
        l1.addLast(11);
        l1.addFirst(15);
        
        
       l1.addAll( l2);
       System.out.println(l1);
       Collections.sort(l1);
       System.out.println(l1);

        // System.out.println(l1.contains(3));
        //  System.out.println(l1.indexOf(33));
        // System.out.println(l1.lastIndexOf(7));
        
      // for (int i = 0; i < l1.size(); i++) {
      //     System.out.print(l1.get(i));
      //       System.out.print(",");
      //   }
    }
}
