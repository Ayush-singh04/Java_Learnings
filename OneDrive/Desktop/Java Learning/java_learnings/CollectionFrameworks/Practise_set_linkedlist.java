package java_learnings.CollectionFrameworks;

import java.util.LinkedList;

public class Practise_set_linkedlist {
    public static void main(String[] args) {
        //Q1- Find the nth Node of a linkedlist from last and delete it.
                    // sol-1
         LinkedList<Integer> list = new LinkedList<>();
         
            list.add(1);
             list.add(2);
              list.add(3);
               list.add(4);
                list.add(5);
            System.out.println(list);
            // Delete 2nd element from last.
            int n = 3;
           list.remove( list.size()-n);
           System.out.println(list);
    }
}
