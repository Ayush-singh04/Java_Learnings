package java_learnings.OOPS_concept;



class Employee1{

        int salary;
        String name;
    
    public  int getSalary(){
                                                         // for Q1 
    return salary;
    }
    public String getName(){
        return name;
    }
    public void setName(String n){
        name=n  ;
    }
    }
     class Cellphone{
        public void Ring(){
           System.out.println("Ringing....");
        }                                              // for Q2
        public void vibrate(){
            System.out.println("Vibrating....");
        }
    }
     class square{
        int side;
        public int area(){
            return side*side;
        }                                             // for Q3
        public int parimeter(){
            return 4*side;
        }
    }
     class rectangle{
        int length;
        int breadth;
        public int area(){
            return length*breadth;
        }
        public int parimeter(){
            return 2*(length+breadth);
        }
    }

    public class Practice_set_oops{

    public static void main(String[] args) {
        //Q1- make employee class and set properties and use them.
      /*   Employee1 jack = new Employee1();
        jack.setName("Jackson");
        jack.salary=100;
        System.out.println(jack.getSalary());
        System.out.println(jack.getName());

        //Q2- Make a cellphone class and use properties in it.

        Cellphone nokia = new Cellphone();
            nokia.Ring();
            nokia.vibrate();
       */
      // Q3- Make a square class to find area and paremeter of a square..
        
        square sq = new square();
            sq.side=6;
            System.out.println("Area of square is " + sq.area());
            System.out.println("Parimeter of square is "+ sq.parimeter());
            // we can also use these methods for rectangle and circles etc....

        rectangle rec = new rectangle();
            rec.length=16;
            rec.breadth=6;
            System.out.println("Area of rectangle is " + rec.area());
            System.out.println("Parimeter of rectangle is "+ rec.parimeter());

           
        
    }
    
}