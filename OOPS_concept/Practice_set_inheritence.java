package java_learnings.OOPS_concept;

class circle{
    public void radius(){
        System.out.println(" Circle have radius ");
    }
    public void circum(){
        System.out.println(" Circle have circumstance");
    }
    public void area(){
        System.out.println(" Circle has its area ");
        
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

   


public class Practice_set_inheritence{
    public static void main(String [] args){
        
        // Q1- make a class circle and use inheritence to create a cylinder class from it..
            circle C= new circle();
            C.radius();
            C.area();
            cyldr CL= new cyldr();
            CL.radius();
            CL.area();
       
    }       
}
