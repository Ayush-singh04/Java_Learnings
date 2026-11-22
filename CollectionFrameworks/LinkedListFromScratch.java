package java_learnings.CollectionFrameworks;
public class LinkedListFromScratch {

     private Node head ; // First element of Linklist is a head.
        private Node tail ; // Last element of Linklist is a tail which always points to the null.

        private int size ;
       
    public LinkedListFromScratch(int size) {
            this.size = size;
        }
    
    // Inserting The Element at the  First Position.
     
        public void insertFirst(int val){
            Node node = new Node(val);
            node.next = head;
            head = node;

            if (tail == null) {
                tail = head;
            }
            size += 1;  
        }
    // Inserting elements uisng Recursion.
    public void insertUsingRec(int val , int index){

        head = insertUsingRec(val, index, head);
    }
    private Node insertUsingRec(int val , int index , Node node){
        if (index == 0) {
            Node temp = new Node(val, node);
            size++;
            return temp;
        }

       node.next = insertUsingRec(val, index-1, node.next);
       return node;
    }
    // Inserting The Element at the  Last Position.
        public void insertAtLast(int val){
             if (tail == null) {
                insertAtLast(val);
                return;
            }
            Node node = new Node(val);
            tail.next = node;
            tail = node;
            size += 1;
        }
    // Inserting The Element at a particular Index.
        public void insertAtIndex(int val , int index){
            if (index == 0 ) {
                insertFirst(val);
                return;
            }
            if (index == size ) {
                insertAtLast(val);
                return;
            }
            Node current = head;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node node = new Node(val, current.next);
                current.next = node;
            size += 1;
        }
    // Deleting the first Element .
        public int deleteFirst(){
            int val = head.value; // cause we have to return the deleted value.
            head = head.next;
            if (head == null) {
                tail = null ;
            }
            size --;
            return val;
        }
    // Deleting the last Element .
        public int deleteLast(){
            Node curr = head;
            
           while (curr.next != tail) {
                    curr = curr.next;
           }
           int val = curr.next.value; // cause we have to return the deleted value.
           curr.next = null;
           if (tail == null) {
               return deleteFirst();
           }
            size --;
            return val;
        }
     // Deleting  the Element at particular index .
        public int deleteAtIndex(int idx){
            Node curr = head;
            if (idx == 0) {
              return deleteFirst();
            }
            if (idx == size) {
              return deleteLast();
            }
            for (int i = 1; i < idx; i++) {
                curr = curr.next;
            }
            int val = curr.next.value;
            curr.next = curr.next.next;

            return val;
        }
        public void display(){
            Node temp = head;

                while (temp != null) {
                    System.out.print(temp.value + " -> ");
                    temp = temp.next;
                }
            System.out.println("NULL");
        }


        // Q1 Remove Duplicates from sorted LinkedList

        public void removeDupli(){
            Node node = head;
        
            while (node.next != null) {
                if (node.value == node.next.value) {
                    node.next = node.next.next;
                    size--;
                }else{
                node = node.next;
                }
            }
            tail = node;
            tail.next = null;
        }

    
       
    public class Node {
       
        private int value ; // Each node in linklist has a value (here it is integer).
        private Node next ; // And the next element will be of Node Data type.

            public Node(int value) {
                this.value = value;
        }
          public Node(int value , Node next) {
            this.value = value;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LinkedListFromScratch list = new LinkedListFromScratch(0);
            list.insertFirst(10);
            list.insertFirst(20);
            list.insertFirst(30);
            list.insertFirst(40);
            list.insertAtLast(5);
            list.insertAtIndex(25 , 2);
            // System.out.println(list.deleteFirst()); // 40 has been removed.
            // System.out.println(list.deleteLast()); // 5 has been removed.
            System.out.println(list.deleteAtIndex(3));
            list.display();

            list.insertUsingRec(100, 3);
            list.display();

         // For Question1 

        // list.insertFirst(4);
        // list.insertFirst(4);
        // list.insertFirst(3);
        // list.insertFirst(2);
        // list.insertFirst(2);
        // list.insertFirst(1);

        // list.display();
        // list.removeDupli();
        // list.display();


    }
}
