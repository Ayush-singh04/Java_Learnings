package java_learnings.CollectionFrameworks;

import java.util.ArrayDeque;

public class arrayDeque {
    public static void main(String[] args) {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
            ad1.add(34);
            ad1.add(56);
            ad1.add(9);
            ad1.addFirst(5);

        System.out.println(ad1.getFirst());
         System.out.println(ad1.getLast());

    }
}
