package java_learnings.recursion;

public class tower_of_hanoi {
    public static void towerOfHanoi(int n ,String src, String helper,String desti ){
        if (n==1) {
            System.out.println("Transfer disk "+n+" from "+src+" to "+desti);
            return;
        }
        towerOfHanoi(n-1, src, desti, helper);
        System.out.println("Transfer disk "+n+" from "+src+" to "+desti);
        towerOfHanoi(n-1, helper, src, desti);
    }
    public static void main(String[] args) {
        int n=3;
        towerOfHanoi(n, "S-tower", "H-tower", "D-tower");
    }
}
