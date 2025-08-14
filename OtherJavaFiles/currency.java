package java_learnings.OtherJavaFiles;
import java.util.Scanner;
class currency{
    public static void main(String[] args) {
        System.out.println("Convert From: ");
            System.out.println(" 1: USD\n 2: EURO\n 3: AED\n 4: Singapore$\n 5: Australian$\n 6: Yen");
            Scanner curr = new Scanner(System.in);
            System.out.println("Enter currency Serial");
            int a = curr.nextInt();
            if (a==1) {
               Scanner amount = new Scanner(System.in);
               System.out.println("Enter amount in USD:");
               float newAmount =  amount.nextFloat();
               System.out.println("Total amount in INR is:" + newAmount*(83.96));
            } 
            else if (a==2) {
                Scanner amount = new Scanner(System.in);
                System.out.println("Enter amount in EURO:");
                float newAmount =  amount.nextFloat();
                System.out.println("Total amount in INR is:" + newAmount*(91.71));
             } 
             else if (a==3) {
                Scanner amount = new Scanner(System.in);
                System.out.println("Enter amount in AED:");
                float newAmount =  amount.nextFloat();
                System.out.println("Total amount in INR is:" + newAmount*(22.86));
             }
             else if (a==4) {
                Scanner amount = new Scanner(System.in);
                System.out.println("Enter amount in Singapore$:");
                float newAmount =  amount.nextFloat();
                System.out.println("Total amount in INR is:" + newAmount*(63.391524));
             }
             else if (a==5) {
                Scanner amount = new Scanner(System.in);
                System.out.println("Enter amount in Australian$:");
                float newAmount =  amount.nextFloat();
                System.out.println("Total amount in INR is:" + newAmount*(55.39));
             }
             else if (a==6) {
                Scanner amount = new Scanner(System.in);
                System.out.println("Enter amount in Yen:");
                float newAmount =  amount.nextFloat();
                System.out.println("Total amount in INR is:" + newAmount*(0.57 ));
             }

    }

}