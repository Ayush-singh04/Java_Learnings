package java_learnings.OOPS_concept;

class cylinder{
   private int radius;
   private int hieght;

public cylinder(int radius,int height){
     this.radius=radius;
     this.hieght=height;

}


public void setRadi(int r){
    radius=r;
}
public int getRadi(){
    return radius;
}
public void setHieght(int h){  // for Q1
    hieght=h;
}
public int getHieght(){
    return hieght;
}
public double area(){
    return 2 * 3.14* radius * hieght + 2 * 3.14* radius* radius;
}
public double volume(){
    return 3.14 * radius * radius * hieght; //Math.PI for calling pie.
}

}
public class Practice_set_constructor{
    public static void main(String[] args) {
        
        //Q1- Create a class cylinder and use getters and setters to find it's radius and hieght.
       // Q2- add volume and surface area of this cylinder..
           //  cylinder cldr = new cylinder();
            cylinder cldr = new cylinder(9,12); // for Q3-
              //  cldr.setRadi(9);
            System.out.println(cldr.getRadi());
             //   cldr.setHieght(12);
            System.out.println(cldr.getHieght());
           
        // for Q2- (3.14*r^2h) - vloume and 2*(3.14)r*h+2*(3.14)r^2 - area
         
                 System.out.println(cldr.area());
                 System.out.println(cldr.volume()); 
        
        //Q3- solve Q1 with the help of constructor..
       
    }
}