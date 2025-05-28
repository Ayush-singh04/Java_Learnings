package java_learnings.BasicJava;

import java.util.Scanner;
   public class worldcup{

    
   public static void main(String[]args){

    System.out.println("Enter the ODI worldCup  year : ");

      try (Scanner sc = new Scanner(System.in)) {
          int Year = sc.nextInt();
               
               String winningteam ="";

    switch(Year){

             case 1975: winningteam ="West Indies";
             break;

                  case 1979: winningteam = "West Indies";
                  break;

             case 1983: winningteam = "India";
             break;

                  case 1987: winningteam = "Austrailia";
                  break;

             case 1992: winningteam = "Pakistan";
             break;

                  case 1996: winningteam = "Sri Lanka";
                  break;

             case 1999: winningteam = "Austrailia";
             break;

                   case 2003: winningteam = "Austrailia";
                   break;

             case 2007: winningteam = "Austrailia";
             break;

                  case 2011: winningteam = "India";
                  break;

             case 2015: winningteam = "Austrailia";
             break;

                  case 2019: winningteam = "England";
                  break;
               
                 default:System.out.println("No Odi WorldCup Happend that year till now.");

              }

                  System.out.println(winningteam);
     }
             
      } 


}