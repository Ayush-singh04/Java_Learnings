package java_learnings.BinarySearch;

public class FindPerfectSquare {
      static boolean isPerfectSquare(int num) {
        long start = 1;
        long end = num ;

        while(start <= end){
            long mid = start + (end-start)/2;
               long sqr = mid * mid;
            if(sqr == num){
                return true;
            }
            else if (sqr > num){
                 end = mid - 1;
            }else{
                 start = mid + 1;
            }
        }
        return false;
   }
   public static void main(String[] args) {
    int num = 16;
    if (isPerfectSquare(num)) {
       System.out.println(num + " is a perfect square.");
    } else {
       System.out.println(num + " is not a perfect square.");
    }
    
   }
}
