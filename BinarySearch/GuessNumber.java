package java_learnings.BinarySearch;

public class GuessNumber {
        static int guess(int num){
            int pick = 6 ;
            if(num == pick){
                return 0;
            }else if(num < pick){
                return 1;
            }else{
                return -1;
            }
        }
        static int guessNumber(int n) {
        int start = 1 ;
        int end = n ; 
        while(start <= end){
            int mid = start + (end - start)/2 ;

            if(guess(mid) == 0){
                return mid;
            }else if(guess(mid) == 1){
                start = mid + 1;
            }else if(guess(mid) == -1){
                end = mid - 1 ;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        int n = 10 ;
        int ans = guessNumber(n);
        System.out.println(ans);
    }
}
