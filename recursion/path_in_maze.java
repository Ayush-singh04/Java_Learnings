package java_learnings.Recursion;

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

    // Other way to do the same
    static int countpath2(int row , int col){
        if(row == 1 || col == 1){
            return 1 ;
        }
        //For moving right 
        int right = countpath2(row, col-1);
        //For moving Down 
        int down = countpath2(row-1, col);

        return right + down ;

    }

    // Printing all the paths found
    static void printPath(String empty , int row , int col){
        if(row == 1 && col == 1){ // if we'll reach the end just print the path.
            System.out.println(empty);
            return ;
        }

        if(row > 1){
            // we'll move down and add the path in the empty string.
            printPath(empty + 'D' , row - 1, col);
        }
        if(col > 1){
            // we'll move right and add the path in the empty string.
            printPath(empty + 'R' , row , col - 1);
        }

    }

    // What if we also move diagonally 
     static void diagonallyPath(String empty , int row , int col){
        if(row == 1 && col == 1){ // if we'll reach the end just print the path.
            System.out.println(empty);
            return ;
        }
        if(row > 1 && col > 1){
            // We'll move diagonally here and add the path in empty string.
            diagonallyPath(empty + 'D', row-1, col-1);
        }
        if(row > 1){
            // we'll move down and add the path in the empty string.
            diagonallyPath(empty + 'V' , row - 1, col); // V = vertical
        }
        if(col > 1){
            // we'll move right and add the path in the empty string.
            diagonallyPath(empty + 'H' , row , col - 1); // H = horizontal
        }

    }

    public static void main(String[] args) {
        // int n=3 , m=3;
        // int totalPath = countPath(0, 0, m, n);
        // System.out.println(totalPath);

        // System.out.println(countpath2(3, 3));

        // Print all the paths of the Maze 
        // printPath("", 3, 3);

        // Print with diagonal path 
        diagonallyPath("", 3, 3);
    }
}
