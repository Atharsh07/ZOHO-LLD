package Book_Catlog_System;

public class Book {

    private String Name;
    private String Author;
    private String Publisher;
    private int Publish_year;
    private String Category;
    private double Price;
    private int Count;

    public Book(String name, String author, String publisher, int publish_year, String category, double price, int count) {
        Name = name;
        Author = author;
        Publisher = publisher;
        Publish_year = publish_year;
        Category = category;
        Price = price;
        Count = count;
    }

    public String getName() {
        return Name;
    }

    public String getAuthor() {
        return Author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public int getPublish_year() {
        return Publish_year;
    }

    public String getCategory() {
        return Category;
    }

    public double getPrice() {
        return Price;
    }

    public int getCount() {
        return Count;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public void setPublish_year(int publish_year) {
        Publish_year = publish_year;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setCount(int count) {
        Count = count;
    }


    @Override
    public String toString() {
        return "Book{" +
                "Name='" + Name + '\'' +
                ", Author='" + Author + '\'' +
                ", Publisher='" + Publisher + '\'' +
                ", Publish_year=" + Publish_year +
                ", Category='" + Category + '\'' +
                ", Price=" + Price +
                ", Count=" + Count +
                '}';
    }
}
