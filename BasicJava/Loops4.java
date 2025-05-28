package java_learnings.BasicJava;

class Loopstesting {
    public static void main(String[]args){

    System.out.println("Odd venumbers till 10");
    // in dowhile loop we have to declare variable earlier
   /*     int i=2;
    do{
         System.out.println(i);
         i=i+2;
        }while(i<=10);*/
    
    //
    int j=1;
    while(j<=10){
        System.out.println(j);
        j=j+2;
    }

     int b = 1;
        while( b <5){
            System.out.println(b);
            b++;
        }




        int i= 1;
        do{
            System.out.println(i);
            i++;
        } while(i<5);
}

}