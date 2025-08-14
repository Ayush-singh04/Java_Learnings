package java_learnings.CollectionFrameworks;

public class CircularLinkedListFromScratch {

   private Node head;
   private Node tail;

   public void insert(int val){
    Node node = new Node(val);
        if (head == null) {
            head = node;
            tail = node;
            return;
        }

       tail.next = node;
        node.next = head;
        tail = node;
   }
   public void display(){
    Node curr = head;
    if (head != null) { // We have used do while loop here because we have to print the last element when we reach head again.
      do{
        System.out.print(curr.value + " -> ");
        curr = curr.next;
      }
      while (curr != head);
    }
   }

    private class Node {
    
      private int value;
      private Node next;

      public Node(int value){
        this.value = value;
      }
      public Node(int value , Node next){
        this.value = value;
        this.next = next;
      }
        
    }
    public static void main(String[] args) {
        CircularLinkedListFromScratch list = new CircularLinkedListFromScratch();
                list.insert(10);
                list.insert(20);
                list.insert(30);
                list.insert(40);
                list.insert(50);

            list.display();
    }
}
