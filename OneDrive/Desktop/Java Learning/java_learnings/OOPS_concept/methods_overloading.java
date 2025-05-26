package java_learnings.OOPS_concept;

public class methods_overloading{

    // Method Overloading - it says two or more methods have same name but different use of it.

        static void wish(){
            System.out.println("Happy birthday! ");
        }
                                                                    // HERE both method have same name but diff. parameters.
        static void wish(String ayu){
            System.out.println(" Happy birthday ! "+ ayu + " bro");
        
        }

        static void wish(int a , int b){
            System.out.println(" Happy birthday ! "+ a + " bro");
            System.out.println(" Happy birthday ! "+ b + " bro");
        }

    static void jokes(){ // static method is used to associate the code within all the class...it is acceseble in whole class.
        System.out.println("Lappu sa sachin h ! "); // Here we wont type return because we used void in method.
    }

        public static void main(String[] args) {
             jokes();

        
        wish();
            wish("ayush");
              wish(10,20);
    }
}