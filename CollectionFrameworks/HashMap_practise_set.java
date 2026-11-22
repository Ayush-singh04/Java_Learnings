package java_learnings.CollectionFrameworks;
import java.util.HashMap;
import java.util.HashSet;
public class HashMap_practise_set {
    public static void majorityElement(int nums[]){
        HashMap<Integer,Integer> map = new HashMap<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (map.containsKey(nums[i])) {  // true
                map.put(nums[i], map.get(nums[i])+1);
            }else{  //false
                map.put(nums[i], 1);
            }
        }

        for (int key : map.keySet()) {
            if (map.get(key)>n/3) {
                System.out.println(key);
            }
        }
    }
    public static void union(int arr1[],int arr2[]){
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < arr1.length; i++) {
            set.add(arr1[i]);
        }
        for (int j = 0; j < arr2.length; j++) {
            set.add(arr2[j]);
        }

        System.out.println(set);
        System.out.println("Size of the set is "+ set.size());   
    }
    public static void intersection(int arr1[],int arr2[]){
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < arr2.length; i++) {
            set.add(arr2[i]);   
            }
            int count =0;
            for (int j = 0; j < arr1.length; j++) {
                
                if (set.contains(arr1[j])) {
                    count++;
                    System.out.print(arr1[j]+" ");
               }
            }
            System.out.println();
            System.out.println("Total element in Intersection are: "+count);
        }
        public static String getStart( HashMap<String , String> tick){
             HashMap<String , String> revMap = new HashMap<>(); // Here all the vlaues of from and destination will be interchanged.

             for (String key : tick.keySet()) {
                revMap.put(tick.get(key), key); // Here Interchanging will be performed
             }
             for (String key : tick.keySet()){
               if (!revMap.containsKey(key)) {
                    return key;
               } 
             }
             return null;
        }
    
    public static void main(String[] args) {
        // Q1- Given an integer array of size n, find all elements that appear more than [n/3] times.
        //    int nums[] ={1,3,2,5,1,3,1,5,1};
        // int nums[] ={1,2};
         
            // majorityElement(nums);
        // Q2- Find the union of two arrays--
        // int arr1[] = {7,3,9};
        // int arr2[] = {6,3,9,2,9,4};
        // union(arr1, arr2);  
        // Q3- Find the intersection of two arrays--
        // intersection(arr1, arr2);

        // Q4- Find ITINERARY from Tickets---
            /*"Chennai"-> "Bengaluru"
             * "Mumbai"-> "Delhi"
             * "Goa"-> "chennai"
             * "Delhi"-> "Goa"
             */

            HashMap<String , String> tickets = new HashMap<>();
            tickets.put("Chennai", "Bengaluru");
            tickets.put("Mumbai", "Delhi");
            tickets.put("Goa", "Chennai");
            tickets.put("Delhi", "Goa");
        
                String start = getStart(tickets);
            while (tickets.containsKey(start)) {
                    System.out.print(start+"->");
                    start = tickets.get(start);
            }
            System.out.println(start); 
    }
}
