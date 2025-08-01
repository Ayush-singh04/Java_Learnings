package java_learnings.recursion;

public class PatternsRecursion {

    static void pattern(int row , int col){
        // base case
        if (row == 0) {
            return;
        }
        if(col < row){
            System.out.print("* ");
            pattern(row, col+1);
           
        }else{
            System.out.println();
            pattern(row-1, 0);
            
        }
    }
    static void pattern2(int row , int col){
        // base case
        if (row == 0) {
            return;
        }
        if(col < row){
            pattern2(row, col+1);
            System.out.print("* ");
        }else{
            pattern2(row-1, 0);
            System.out.println();
        }
    }
    public static void main(String[] args) {
        pattern(4, 0);
        pattern2(4, 0);
    }
}
