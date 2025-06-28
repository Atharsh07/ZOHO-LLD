package Book_Catlog_System;
import java.util.*;
public class BookCatalogApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookCatlog catalog = new BookCatlog();
        BookService service = new BookService(catalog);

        while (true) {
            System.out.println("\n=== Book Catalog Menu ===");
            System.out.println("1. Add a Book");
            System.out.println("2. Search Books");
            System.out.println("3. Get Most Sold Books by Author");
            System.out.println("4. Get Most Sold Books by Category");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }
            switch (choice) {
                case 1:
                    System.out.print("Book Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Author: ");
                    String author = scanner.nextLine();

                    System.out.print("Publisher: ");
                    String publisher = scanner.nextLine();

                    System.out.print("Publish Year: ");
                    int year = Integer.parseInt(scanner.nextLine());

                    System.out.print("Category: ");
                    String category = scanner.nextLine();

                    System.out.print("Price: ");
                    double price = Double.parseDouble(scanner.nextLine());

                    System.out.print("Count Sold: ");
                    int countSold = Integer.parseInt(scanner.nextLine());

                    service.addNewBook(name, author, publisher, year, category, price, countSold);
                    System.out.println("✅ Book added successfully!");
                    break;
                case 2:
                    System.out.print("Enter keyword (book/author): ");
                    String keyword = scanner.nextLine();
                    List<Book> results = service.searchBooks(keyword);
                    results.forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Enter author name: ");
                    String searchAuthor = scanner.nextLine();

                    System.out.print("Limit: ");
                    int limitAuthor = Integer.parseInt(scanner.nextLine());

                    service.getTopSoldByAuthor(searchAuthor, limitAuthor)
                            .forEach(System.out::println);
                    break;
                case 4:
                    System.out.print("Enter category: ");
                    String searchCategory = scanner.nextLine();

                    System.out.print("Limit: ");
                    int limitCategory = Integer.parseInt(scanner.nextLine());

                    service.getTopSoldByCategory(searchCategory, limitCategory)
                            .forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("Exiting... 👋");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }
}
