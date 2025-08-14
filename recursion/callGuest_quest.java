package java_learnings.Recursion;

public class callGuest_quest {
    // Find the total no. of ways to call "n" guest in a party whether single or paired---
        // n=4
    public static int callGuest(int n){
        if (n<=1) {
            return 1;
        }
        // Single invite
        int way1 =callGuest(n-1);
        // paired invite
        int way2 = (n-1)*callGuest(n-2);
        return way1+way2;
    }
    public static void main(String[] args) {
        int n =4;
        System.out.println(callGuest(n));
    }
}
