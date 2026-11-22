package java_learnings.CollectionFrameworks;

// In Java, a doubly linked list is a type of linked list in which each node contains a reference to the next node and the previous node in the sequence. It allows for traversal of the list in both forward and backward directions, making it different from a singly linked list that only allows traversal in one direction.


public class DoublyLinkedListFromScratch {

    Node head ;

    public void insertFirst(int val){
        Node node = new Node(val);
        node.next = head;
        node.prev = null;
        if (head != null) {
            head.prev = node;
        }
        head = node;
    }
    public void insertAtLast(int val){ 
     Node node = new Node(val);
        if (head == null) {
            node.prev = null;
            head = node;
            return;
        }
     Node tail = head;
          
        while (tail.next != null) {
            tail = tail.next; // here we will reach the tail after the loop will end.
        }
        node.prev = tail;
        node.next = null;
        tail.next = node;
        

    }
      public void insertAtIndex(int val , int index){
            if (index == 0 ) {
                insertFirst(val);
                return;
            }
           
            Node current = head;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            if (current.next == null) {
                insertAtLast(val);
                return;
            }
            Node node = new Node(val, current.next, current);
                node.prev = current;
                current.next.prev = node;
                node.next = current.next;
                current.next = node;
            
        }
    public void display(){
        Node temp = head;

        while (temp != null) {
            System.out.print(temp.value + " -> ");
            temp = temp.next;
        }
        System.out.println("NULL");
    }
    public void displayRev(){ // Because we don't know the tail element we have to reach there first and then print backwards
        Node temp = head;
        Node tail = null; // Assume tail is null at the start.

        while (temp != null) {
            tail = temp; // when this loop will end our "last" node will reach the end element
            temp = temp.next;
        }
        while (tail != null) {
              System.out.print(tail.value + " -> ");
            tail = tail.prev;
        }
        System.out.println("NULL");
    }

    private class Node{
        private int value;
        private Node next;
        private Node prev;

        public Node(int value) {
            this.value = value;
        }
        public Node(int value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

    }
    public static void main(String[] args) {
        DoublyLinkedListFromScratch list = new DoublyLinkedListFromScratch();
    
        list.insertFirst(15);
        list.insertFirst(10);
        list.insertFirst(5);
        list.insertAtLast(20);
        list.insertAtIndex(25, 4);
        list.insertAtIndex(30, 5);
        list.insertAtIndex(2, 1);

        list.display();
        list.displayRev();
    }
}
