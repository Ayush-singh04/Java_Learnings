package java_learnings.JavaSessional;
class Animal {
    void eat() {
        System.out.println("Animal eats food.");
    }   
    
    public void finalize(){ 
        System.out.println("Animal objet removed.");
    }
}
class Dog extends Animal {
    void bark() {
        System.out.println("Dog barks.");
    }
     public void finalize(){  
        System.out.println("Dog objet removed.");
    }
}
class Q2 {
    public static void main(String[] args) {
        Dog dog = new Dog();
       
        dog = null; 
       
        System.gc();    
        
    }
}
