package java_learnings.OOPS_concept;

   class Employee{  // Only one public class can be made in a java programm.
        int id;
        int Salary;
        String name;

        public void Details(){ // Here we wrote public because this method can access complete code to perform the task.
           System.out.println("Hello, My name is "+name);
           System.out.println("and my id is "+id);
        //  System.out.println( " my salary is "+Salary+"K"); 
        }
        public int getSalary(){
            return Salary;
        }
    }

   public class Custom_class{
    
    public static void main(String[] args) {
        System.out.println("Here we are making new custom class.");

        Employee Ayush = new Employee(); // here we instantiating a new Employee.
        Employee Robert = new Employee();
        // setting Attributes for Ayush.

         Ayush.id=10;
         Ayush.Salary = 30000;
         Ayush.name="Ayush Singh";

        // setting Attributes for Robert.
         Robert.id=15;
         Robert.Salary=20000;
         Robert.name="Robert Downey Aggrawal";
        // printing the Atributes.
        // System.out.println(Ayush.id);
        // System.out.println(Ayush.name );
        Ayush.Details();
        Robert.Details();

        int Salary= Ayush.getSalary();
        System.out.println(Salary);
         

    }
}