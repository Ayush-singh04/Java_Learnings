package java_learnings.recursion;

public class path_in_maze {
    // Find the total no. of paths in a maze to move from (0,0) to (n,m)
        // n=3,m=3 or you can only moove right or downwards.
    public static int countPath( int i,int j,int m, int n){
        if (i ==n || j==m) {
            return 0;
        }
        if (i==n-1 && j==m-1) {
            return 1;
        }
        //Moving down
        int downPath = countPath(i+1, j, m, n);
        // Moving right
        int rightpath = countPath(i, j+1, m, n);
        return rightpath + downPath;
    }
    public static void main(String[] args) {
        int n=3 , m=3;
        int totalPath = countPath(0, 0, m, n);
        System.out.println(totalPath);
    }
}
