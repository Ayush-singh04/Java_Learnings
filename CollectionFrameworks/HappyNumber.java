package java_learnings.CollectionFrameworks;

public class HappyNumber {

    public static boolean isHappy(int n){
            int slow = n;
            int fast = n;
    
            do{
                slow = findSquare(slow);
                fast = findSquare(findSquare(fast)); // This step will move slow once and fast twice.
            }while(slow != fast);
    
            if (slow == 1) {
                return true; // Checking the Happy number
            }
            return false;
        }
    
        private static int findSquare(int num){
            int ans = 0;
    
            while (num > 0) {
                int rem = num % 10; // Taking the last digit out from the number.
                ans = rem * rem; // Made square of one digit and added it to the answer.
                num = num / 10;  // Now we made number one digit less from behind.
            }
            return ans;
        }
        public static void main(String[] args) {
           System.out.println(isHappy(6));
    }
}
