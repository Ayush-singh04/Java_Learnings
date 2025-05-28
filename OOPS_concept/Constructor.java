package java_learnings.OOPS_concept;

class MyMainEmployee{
    int id;
    String name;
public  MyMainEmployee(){
   id = 11; 
   name = "You-Know-Me";
}
public MyMainEmployee(String myName,int myId){
    this.id= myId;
   this.name= myName;

}
}
public class Constructor {
    public static void main(String[] args) {
        
      // MyMainEmployee ayush = new MyMainEmployee();
      MyMainEmployee ayush= new MyMainEmployee("You-Don't-know-Me",12);
          System.out.println(ayush.name);
          System.out.println(ayush.id);


    }
}