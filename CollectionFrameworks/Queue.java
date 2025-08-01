package java_learnings.CollectionFrameworks;
 
 
public class Queue {
    // Queue using array--
    static class Array_queue {
    static int arr[];
    int size;
    static int rear = -1;
        Array_queue(int n){
            arr = new int[n];
            this.size=n;
        }
        public boolean isEmpty(){
              return rear == -1 ;
        }
        // Adding data into queue
        public void add(int data){
            if (rear == size-1) {
                System.out.println("Full Queue");
                return;
            }
            rear++;
            arr[ rear] = data;
        }
        // Removing data from the queue
        public int remove(){
            if (isEmpty()) {
                System.out.println("Empty Queue");
                return -1;
            }
                int front = arr[0];
                for (int i = 0; i <rear; i++) {
                    arr [i] = arr[i+1];
                }
                 rear--;
                 return front;
        }
         // peek
         public int peek(){
            if (isEmpty()) {
                System.out.println("Empty Queue");
                return -1;
            }
            return arr[0];
         }
 }
    public static void main(String[] args) {
        Array_queue q = new Array_queue(3);
        q.add(1);
        q.add(2);
        q.add(3);
        while (!q.isEmpty()) {
            System.out.println(q.peek());
            q.remove();
        }

    }
}
