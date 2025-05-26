package java_learnings.OOPS_concept;

public class Methods{

     static int logic(int a , int b){ // Calling a new method..
        int c;
            if(a>b){
                c = a+b;
            }
            else{
                c = a*b;
            }
            return c;
    }
    public static void main(String[] args) { 
        // Here we have removed the static from above method so, we have to make a new obj.
       //  Methods obj = new Methods();
        int x=10 , y=100 , z;
           z = logic(x,y); // Here we applying the logic made above. 
            
        int x1=11, y1=10 , z1;
         z1 = logic(x1,y1)  ;

         System.out.println(z);
         System.out.println(z1);
            
      }
}