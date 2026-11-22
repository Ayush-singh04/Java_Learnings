package java_learnings.OOPS_concept;

class circle{
    public void radius(){
        System.out.println(" Circle have radius ");
    }
    public void circum(){
        System.out.println(" Circle have circumference");
    }
    public void area(){
        System.out.println(" Circle has its area ");
        
    }
    void shape(){
        System.out.println(" It's a Shape.");
    }
}
class cyldr extends circle{
    public void radius(){
        System.out.println(" cylinder also have radius ");
    } 
     public void paremeter(){
        System.out.println(" cylinder have paremeter. ");
    }
     public void area(){
        System.out.println(" cylinder have surface area and curved surface area ");
    }
}

   // New Example 

class Species {
    int age ;

        int ageof(int age){
            this.age = age;
                return age ;
        }
}

class Human extends Species{
    String name;

    void details(String name ){
        this.name = name ;

    System.out.println("Hello my name is " + name + " and my age is " + ageof(age));
    }
}


public class Practice_set_inheritence{
    public static void main(String [] args){
        
        // Q1- make a class circle and use inheritence to create a cylinder class from it..
            // circle C= new circle();
            // C.radius();
            // C.area();
            // cyldr CL= new cyldr();
            // CL.radius();
            // CL.area();
            // CL.shape(); // This function is made in circle class but used in cylinder class.

        // Q2 

        Human rahul = new Human();
        rahul.ageof(54);
        rahul.details("Rahul Mistry");
       
    }       
}
