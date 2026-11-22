package java_learnings.BasicJava;

import java.util.Scanner;
public class Unitconvertor_2{
    public static void main(String[] args) {
      //System.out.println("Choose the required :");
        System.out.println("Type 1 for CM-M :");
           System.out.println("Type 2 for M-CM  :");
               System.out.println("Type 3 for CM-KM :");
               System.out.println("Type 4 for M-KM  :");
           System.out.println("Type 5 for KM-M  :");
       System.out.println("Type 6 for KM-CM :");
      
        try (Scanner num = new Scanner(System.in)) {
          int Option = num.nextInt();

        switch(Option){
     
            // converting length from cm to m.
        case 1:  System.out.println("Enter the length in Cm :");
           Scanner sc = new Scanner(System.in);
            double CM = sc.nextDouble();
            double M = CM/100;
          System.out.println(CM + "cm have "+ M + " meters in it ! ");
          break;

          // converting length from m to cm.
        case 2:  System.out.println("Enter the length in M :");
           Scanner sc2 = new Scanner(System.in);
            double M2 = sc2.nextDouble();
            double CM2 = M2*100;
          System.out.println(M2 + "m have "+ CM2 + " cm in it ! ");
          break;

          // converting length from cm to km.
        case 3:  System.out.println("Enter the length in CM :");
           Scanner sc3 = new Scanner(System.in);
            double CM3 = sc3.nextDouble();
            double KM3 = CM3/10000;
          System.out.println(CM3 + "cm have "+ KM3 + " km in it ! ");
          break;

          // converting length from m to km.
        case 4:  System.out.println("Enter the length in M :");
           Scanner sc4 = new Scanner(System.in);
            double M4 = sc4.nextDouble();
            double KM4 = M4/1000;
          System.out.println(M4 + "m have "+ KM4 + " km in it ! ");
          break;

          // converting length from km to m.
        case 5:  System.out.println("Enter the length in Km :");
           Scanner sc5 = new Scanner(System.in);
              double KM5 = sc5.nextDouble();
              double M5 = KM5*1000;
          System.out.println(KM5 + "km have "+ M5 + " meters in it ! ");
        break;

         // converting length from km to cm.
         case 6:  System.out.println("Enter the length in Km :");
           Scanner sc6 = new Scanner(System.in);
              double KM6 = sc6.nextDouble();
              double CM6 = KM6*10000;
           System.out.println(KM6 + "km have "+ CM6 + " cm in it ! ");
         break;
     }
        }
      
    }
}