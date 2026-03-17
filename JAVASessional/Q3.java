package java_learnings.JAVASessional;

import java.util.ArrayList;

public class Q3 {

   
    public ArrayList<Integer> storeEvenNumbers(int N) {
        
        ArrayList<Integer> A1 = new ArrayList<>();

        for (int i = 2; i <= N; i++) {
            if (i % 2 == 0) {
                A1.add(i);
            }
        }

        return A1;
    }

    
    public static void main(String[] args) {
        Q3 obj = new Q3();
        
        ArrayList<Integer> result = obj.storeEvenNumbers(10);
        
        System.out.println(result);
    }
}
