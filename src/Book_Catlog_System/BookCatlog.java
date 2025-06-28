package Book_Catlog_System;
import java.util.*;
import java.util.stream.Collectors;

public class BookCatlog {

    private List<Book> books;

    public BookCatlog(){
        this.books = new ArrayList<>();
    }

    public void addBook(Book book){
        books.add(book);
    }

    public List<Book> searchBook(String keyword){
        return books.stream()
                .filter(book -> book.getName().toLowerCase().contains(keyword.toLowerCase()) || book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }


    public List<Book> getTopSoldBoolByAuthor(String author, int limit){
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .sorted((a,b) -> b.getCount() - a.getCount())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Book> getTopSoldBookByCat(String cat, int limit){
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(cat))
                .sorted((a,b)->b.getCount()-a.getCount())
                .limit(limit)
                .collect(Collectors.toList());
    }



}
