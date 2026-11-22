package java_learnings.OtherJavaFiles;

public class NewTest {

    static int bigIdxName(String details ,  int bIdx){
         for (int i = 0 ; i  < details.length(); i++) {
            if (details.charAt(i) == '-') {
            bIdx = i+1;
            }  
        }
        return bIdx ;
    }
    static int endIdxName(String details ,  int eIdx){
         for (int i = 0 ; i  < details.length(); i++) {
            if (details.charAt(i) == ',') {
            eIdx = i;
            }  
        }
        return eIdx ;
    }
    static int bigIdxRol(String details ,  int bIdx){
         for (int i = 0 ; i  < details.length(); i++) {
            if (details.charAt(i) == '(') {
            bIdx = i+1;
            }  
        }
        return bIdx ;
    }
    static int endIdxRol(String details ,  int eIdx){
         for (int i = 0 ; i  < details.length(); i++) {
            if (details.charAt(i) == ')') {
            eIdx = i;
            }  
        }
        return eIdx ;
    }
    static int bigIdxage(String details ,  int bIdx){
         for (int i = 0 ; i  < details.length(); i++) {
            if (details.charAt(i) == ';') {
            bIdx = i+1;
            }  
        }
        return bIdx ;
    }
    static int endIdxage(String details ,  int eIdx){
        eIdx = details.length();
        return eIdx ;
    }
     static int bigIdxClean(String RoughRole ,  int bIdx){
         for (int i = 0 ; i  < RoughRole.length(); i++) {
            if (RoughRole.charAt(i) == 'D') {
            bIdx = i;
            }  
        }
        return bIdx ;
    }
    public static void main(String[] args) {
        
        String details = "968-Maria,(Ab@cD@t@ Engineer);;27y";

      String name =  details.substring(bigIdxName(details, 0), endIdxName(details, 0));
        // System.out.println("Name: " +name);

      String RoughRole =  details.substring(bigIdxRol(details, 0), endIdxRol(details, 0));
      String Role = RoughRole.substring(bigIdxClean(RoughRole, 0));
             Role =  Role.replace('@','a' );
          
      String Age =  details.substring(bigIdxage(details, 0), endIdxage(details, 0));
        // System.out.println("Age: " +Age);
    
        System.out.println("Name: " + name + " | " + "Role: " + Role + " | " + "Age: " + Age);

    }
}
