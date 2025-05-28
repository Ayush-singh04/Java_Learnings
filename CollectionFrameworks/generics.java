package java_learnings.CollectionFrameworks;

//import java.util.ArrayList;

class myGeneric<T1>{ // Generic class uses "<>""
    int val;
    private T1 t1;
        public myGeneric(int val, T1 t1) {
        this.val = val;
        this.t1 = t1;
    }
        public int getVal() {
            return val;
        }
        public void setVal(int val) {
            this.val = val;
        }
        public T1 getT1() {
            return t1;
        }
        public void setT1(T1 t1) {
            this.t1 = t1;
        }
}
public class generics {
    public static void main(String[] args) {
     //   ArrayList arrayList = new ArrayList();
      //      arrayList.add("one");
       //     arrayList.add(24);
        //    arrayList.add(555);
        
      // int a = (int) arrayList.get(1);
        //  System.out.println(a);

       //  myGeneric<String> str = new myGeneric(240, "It's MyString!");
         //   String gen = str.getT1();
          //      System.out.println(gen);
          //  int value = str.getVal();
           //     System.out.println(value);
    }
}
