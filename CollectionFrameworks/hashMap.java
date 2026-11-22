package java_learnings.CollectionFrameworks;

import java.util.HashMap;
import java.util.Map;

public class hashMap {
    public static void main(String[] args) {
        // country(key), population (Value)
        HashMap<String , Integer> map = new HashMap<>();

        // Insertion
        map.put("India",120);
        map.put("USA",30);
        map.put("China",150);
         System.out.println(map);
        // System.out.println(map.containsKey("India"));
        // System.out.println(map.get("China"));
        // System.out.println(map.get("Japan"));

        // Creating loop for printing data of HashMap---

        for (Map.Entry<String , Integer> e : map.entrySet()) {
             System.out.println(e.getKey());
             System.out.println(e.getValue());
            
        }
        map.remove("China");
         System.out.println(map);
    }
}
