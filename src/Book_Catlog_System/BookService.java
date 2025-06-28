package Book_Catlog_System;
import java.util.*;
public class BookService {

    private BookCatlog catalog;

    public BookService(BookCatlog catalog) {
        this.catalog = catalog;
    }

    public void addNewBook(String name, String author, String publisher, int year, String category, double price, int countSold){

        Book book = new Book(name, author, publisher, year, category,  price, countSold);
        catalog.addBook(book);

    }

    public List<Book> searchBooks(String keyword) {
        return catalog.searchBook(keyword);
    }

    public List<Book> getTopSoldByAuthor(String author, int limit) {
        return catalog.getTopSoldBoolByAuthor(author, limit);
    }

    public List<Book> getTopSoldByCategory(String category, int limit) {
        return catalog.getTopSoldBookByCat(category, limit);
    }

}
