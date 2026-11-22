package java_learnings.OOPS_concept;

class MyEmployee{
   private int id;
   private String name;

public String getName(){
    return name;
}

public void setName(String n){
    name=n;
}
public int getId(){
    return id;
}   
public void setId(int i){
  id=i;
}

}
public class modifiers_getters_setters{
    public static void main(String[] args) {
        MyEmployee ayush = new MyEmployee();
        //ayush.id=7;           // these command will throw error due to private access modifiers,
       // ayush.name="Ayush Singh";
       ayush.setName("Ayush Singh");
       System.out.println(ayush.getName());
       ayush.setId(7);
       System.out.println(ayush.getId());
    }
}