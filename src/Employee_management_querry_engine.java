// -------------------------
// EMPLOYEE MANAGEMENT / QUERY ENGINE
// -------------------------
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Employee {
    String name, designation, department, reportingTo;
    int age;

    public Employee(String name, int age, String designation, String department, String reportingTo) {
        this.name = name;
        this.age = age;
        this.designation = designation;
        this.department = department;
        this.reportingTo = reportingTo;
    }

    public String toString() {
        return String.format("%-10s %-3d %-15s %-15s %-10s", name, age, designation, department, reportingTo);
    }
}

class EmployeeManager {
    static Scanner sc = new Scanner(System.in);
    static List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        seedData();
        while (true) {
            System.out.println("1. Movie_booking.Show All\n2. Query\n3. Reporting Tree\n4. Employees under Manager\n5. Summary\n6. Exit");
            int ch = sc.nextInt(); sc.nextLine();
            switch (ch) {
                case 1 -> showAll();
                case 2 -> processQuery();
                case 3 -> showReportingTree();
                case 4 -> employeesUnderManager();
                case 5 -> showSummary();
                case 6 -> System.exit(0);
            }
        }
    }

    static void seedData() {
        employees.add(new Employee("A", 55, "CEO", "Management", ""));
        employees.add(new Employee("B", 50, "VP", "Finance", "A"));
        employees.add(new Employee("C", 45, "Manager", "Finance", "B"));
        employees.add(new Employee("D", 40, "Lead", "Finance", "C"));
        employees.add(new Employee("E", 42, "Lead", "HR", "C"));
        employees.add(new Employee("F", 35, "Executive", "Finance", "D"));
        employees.add(new Employee("G", 30, "Executive", "Finance", "D"));
        employees.add(new Employee("H", 25, "Executive", "HR", "E"));
        employees.add(new Employee("I", 28, "Executive", "HR", "E"));
        employees.add(new Employee("J", 24, "Intern", "Finance", "I"));
    }

    static void showAll() {
        System.out.println("Name       Age Designation     Department      ReportingTo");
        for (Employee e : employees) System.out.println(e);
    }

    static void processQuery() {
        System.out.println("Enter query (e.g., age > 30 and department contains Finance):");
        String input = sc.nextLine().toLowerCase();
        Predicate<Employee> filter = e -> true;

        for (String condition : input.split(" and ")) {
            String[] parts = condition.trim().split(" ", 3);
            if (parts.length < 3) continue;
            String field = parts[0];
            String op = parts[1];
            String val = parts[2];

            String finalVal = val;
            Predicate<Employee> cond = switch (field) {
                case "age" -> switch (op) {
                    case ">" -> e -> e.age > Integer.parseInt(finalVal);
                    case "<" -> e -> e.age < Integer.parseInt(finalVal);
                    case "==" -> e -> e.age == Integer.parseInt(finalVal);
                    case "!=" -> e -> e.age != Integer.parseInt(finalVal);
                    default -> e -> true;
                };
                case "name", "designation", "department", "reportingto" -> {
                    val = val.toLowerCase();
                    yield switch (op) {
                        case "contains" -> e -> getField(e, field).contains(finalVal);
                        case "startswith" -> e -> getField(e, field).startsWith(finalVal);
                        case "endswith" -> e -> getField(e, field).endsWith(finalVal);
                        case "equals" -> e -> getField(e, field).equals(finalVal);
                        case "notequals" -> e -> !getField(e, field).equals(finalVal);
                        case "notcontains" -> e -> !getField(e, field).contains(finalVal);
                        default -> e -> true;
                    };
                }
                default -> e -> true;
            };
            filter = filter.and(cond);
        }

        System.out.println("Filtered Results:");
        showAll();
        employees.stream().filter(filter).forEach(System.out::println);
    }

    static String getField(Employee e, String field) {
        return switch (field) {
            case "name" -> e.name.toLowerCase();
            case "designation" -> e.designation.toLowerCase();
            case "department" -> e.department.toLowerCase();
            case "reportingto" -> e.reportingTo.toLowerCase();
            default -> "";
        };
    }

    static void showReportingTree() {
        System.out.print("Enter employee name: ");
        String name = sc.nextLine();
        List<String> chain = new ArrayList<>();
        String current = name;
        while (true) {
            String finalCurrent = current;
            Optional<Employee> e = employees.stream().filter(emp -> emp.name.equalsIgnoreCase(finalCurrent)).findFirst();
            if (e.isPresent() && !e.get().reportingTo.isEmpty()) {
                current = e.get().reportingTo;
                chain.add(current);
            } else break;
        }
        System.out.println("Reporting chain: " + String.join(" -> ", chain));
    }

    static void employeesUnderManager() {
        System.out.print("Enter manager name: ");
        String name = sc.nextLine().toLowerCase();
        System.out.println("Employees under " + name + ":");
        employees.stream().filter(e -> e.reportingTo.equalsIgnoreCase(name)).forEach(System.out::println);
    }

    static void showSummary() {
        Map<String, Long> deptSummary = employees.stream().collect(Collectors.groupingBy(e -> e.department, Collectors.counting()));
        Map<String, Long> desgSummary = employees.stream().collect(Collectors.groupingBy(e -> e.designation, Collectors.counting()));
        Map<String, Long> mgrSummary = employees.stream().collect(Collectors.groupingBy(e -> e.reportingTo, Collectors.counting()));

        System.out.println("\nDepartment Summary:");
        deptSummary.forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("\nDesignation Summary:");
        desgSummary.forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("\nReporting To Summary:");
        mgrSummary.forEach((k, v) -> System.out.println((k.isEmpty() ? "None" : k) + ": " + v));
    }
}
