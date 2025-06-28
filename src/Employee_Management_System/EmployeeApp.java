package Employee_Management_System;
import java.util.*;

public class EmployeeApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Employee_Directory directory = new Employee_Directory();

        // Sample data
        directory.addEmployee(1, "Alice", null);           // CEO
        directory.addEmployee(2, "Bob", 1);
        directory.addEmployee(3, "Charlie", 2);
        directory.addEmployee(4, "Dave", 2);
        directory.addEmployee(5, "Eve", 1);
        directory.addEmployee(6, "Frank", 5);

        while (true) {
            System.out.println("\n=== Employee Manager System ===");
            System.out.println("1. Get Employee by ID");
            System.out.println("2. List all Subordinates by Employee ID");
            System.out.println("3. Search by Name Prefix");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Employee emp = directory.getEmployeeById(id);
                    if (emp != null) {
                        System.out.println(emp);
                    } else {
                        System.out.println("❌ Employee not found.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Manager ID: ");
                    int managerId = Integer.parseInt(scanner.nextLine());
                    List<Employee> subs = directory.getSubordinates(managerId);
                    if (!subs.isEmpty()) {
                        System.out.println("📋 Subordinates:");
                        for (Employee e : subs) {
                            System.out.println("  - " + e);
                        }
                    } else {
                        System.out.println("No subordinates found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter name prefix: ");
                    String prefix = scanner.nextLine();
                    List<Employee> matches = directory.searchByPrefix(prefix);
                    if (!matches.isEmpty()) {
                        System.out.println("🔍 Matches:");
                        for (Employee e : matches) {
                            System.out.println("  - " + e);
                        }
                    } else {
                        System.out.println("No employees found with that prefix.");
                    }
                    break;

                case 4:
                    System.out.println("Exiting... 👋");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
