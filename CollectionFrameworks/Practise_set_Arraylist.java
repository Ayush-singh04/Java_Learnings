package java_learnings.CollectionFrameworks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class Book{
    String author, name;

    public Book(String author, String name) {
        this.author = author;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book [name=" + name + ", author=" + author + "]";
    }
     
}
class library1{
   
                    
   public ArrayList<Book> books;
    public library1(ArrayList<Book> books){
        this.books = books;
    }
    public void addbook(Book book){
        System.out.println(book +" book has been Added! to the Library");
            this.books.add(book);
    }
    public void returnbook(Book book){
        System.out.println(book +" book has been returned!");
            this.books.add(book);
    }
     LocalDateTime dt = LocalDateTime.now();
    DateTimeFormatter df2 = DateTimeFormatter.ISO_DATE_TIME;
           String myDate2 = dt.format(df2);
    public void issuebook(Book book, String issued_to){
        System.out.println(book +" book has been issued! to "+ issued_to );
            System.out.println("Issued on-: "+ myDate2);
            this.books.remove(book);
    }
}
public class Practise_set_Arraylist {
public static void main(String[] args) {
    ArrayList<Book> bk = new ArrayList<>();
    Book b1 = new Book("abcd", "Word-war");
    Book b2 = new Book("austin", "air-space");
    Book b3 = new Book("vivian", "computer nation");
        bk.add(b1);
         bk.add(b2);
          bk.add(b3);
    
       library1 lb = new library1(bk);
           System.out.println(lb.books);
        lb.issuebook(b3, "Rahul");
            System.out.println(lb.books);
      
     
   }
}
