package java_learnings.OOPS_concept;

public class Var_args{

    static  int sum(int ...arr){
        int addition=0;
        for(int a: arr){
            addition+=a;
        }
        
        return addition;
    }

    public static void main(String[] args) {
        
        System.out.println(" The sum is "+sum(  3, 4, 6,8,9,0,5) );
         System.out.println(" The sum is "+sum(  13, 14, 16,18,19,10,15) );
    }
}