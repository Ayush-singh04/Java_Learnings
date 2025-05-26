package java_learnings.recursion;

public class place_tiles {
    // place  tiles of size 1xm in a floor of size nxm--
        // n=4,m=2
    public static int placeTiles(int n, int m){
        if (n==m) {
            return 2;
        }
        if (n<m) {
            return 1;
        }
        // vertically
      int virticlePlacing = placeTiles(n-m, m);
       // horizontally
      int horizontalPlacing = placeTiles(n-1, m);
      return virticlePlacing + horizontalPlacing;
    }
  public static void main(String[] args) {
    int n=4 , m=2;
   
    System.out.println(placeTiles(n, m));
  }  
}
