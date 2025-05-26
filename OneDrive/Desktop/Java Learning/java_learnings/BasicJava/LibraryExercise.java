package java_learnings.BasicJava;

class Library{
    String[] book;
    int no_of_books;
    Library(){
      this.book = new String[100];
       // int no_of_books=0; 
    }
    void addBook(String book){
        this.book[no_of_books]=book;
        no_of_books++;
        System.out.println(book+" has been added!");
    }
    void showAvailableBooks(){
        System.out.println("Available books are:");
        for (String book : this.book) {
            if(book==null){
                continue;
            }
            System.out.println("* "+book);
        }
    }
    void issueBook(String book){
        for(int i=0;i<=this.book.length;i++) {
            if(this.book[i].equals(book)){
            System.out.println("The book has been issued!");
            this.book[i] = null;
            return;
            }  
        }
         System.out.print("This book isn't Available "); 
    }
    void returnBook(String book){
        addBook(book);
    }
}
public class LibraryExercise{
    public static void main(String[] args){
        Library CentralLibrary = new Library();
        // Adding the books
        CentralLibrary.addBook("God's Plan");
          CentralLibrary.addBook("Mindset");
           CentralLibrary.addBook("Free of cost");
            CentralLibrary.showAvailableBooks(); // Showing the Available books

        // Issuing a book...
        CentralLibrary.issueBook("Mindset");
            CentralLibrary.showAvailableBooks();

        // Returning a book...
        CentralLibrary.returnBook("Mindset");
            CentralLibrary.showAvailableBooks();
    }
}