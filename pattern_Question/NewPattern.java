package java_learnings.Pattern_Question;

public class NewPattern {
    static void pattern(int n){
        
        // First part.
        for (int row = 1; row <= n; row++) { // So the Rows will be increasing in the first part
                for (int col = 1; col <=row; col++) {
                    System.out.print("* ");
                }
                System.out.println( );
        }
        // Second part. (we'll do opposite of first part)
        for (int row = n-1; row >=1 ; row--) {
                for (int col = 1; col <= row; col++) {
                    System.out.print("* ");
                }
                System.out.println( );
        }
    }
    static void numPattern(int n){
        n = 2*n ;
    for(int row = 0 ; row <= n ; row++){
            for (int col = 0; col <= n; col++) {
                int atEveryIndex = Math.min(Math.min(row, col) , Math.min(n-row, n-col));
                    System.out.print(atEveryIndex + " ");
            }
            System.out.println();
    }
    }
    public static void main(String[] args) {
    //    pattern(5);
       numPattern(4);

    }

    
}
