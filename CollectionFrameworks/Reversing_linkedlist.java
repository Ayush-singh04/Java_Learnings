package java_learnings.CollectionFrameworks;

import java.util.LinkedList;

public class Reversing_linkedlist {
    public static void main(String[] args) {
        /* (head)[1]->[2]->[3]->[4]->NULL
         *      after reversing
         * NULL<-[1]<-[2]<-[3]<-[4](head)
         * No EXTRA memory should be used---
         */
          LinkedList<Integer> list = new LinkedList<>();
         
            list.add(1);
             list.add(2);
              list.add(3);
               list.add(4);
                
           for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i)+"->");
           }
           System.out.println("NULL");
           // reversing the list--
        
           for (int i=list.size()-1; i >=0; i--) {
                     System.out.print(list.get(i)+"->");
           }
           System.out.println("NULL");
           
    }
}
