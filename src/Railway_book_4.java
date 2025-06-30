// -------------------------
// RAILWAY RESERVATION SYSTEM
// -------------------------
import java.util.*;

class Passenger {
    int id;
    String name;
    int age;
    String gender;
    String status; // Confirmed, RAC, Waiting

    public Passenger(int id, String name, int age, String gender, String status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.status = status;
    }

    public String toString() {
        return id + ". " + name + " | Age: " + age + " | Gender: " + gender + " | Status: " + status;
    }
}

class RailwaySystem {
    static Scanner sc = new Scanner(System.in);
    static final int TOTAL_SEATS = 5;
    static final int RAC_LIMIT = 2;
    static final int WL_LIMIT = 2;

    static List<Passenger> confirmedList = new ArrayList<>();
    static Queue<Passenger> racList = new LinkedList<>();
    static Queue<Passenger> waitingList = new LinkedList<>();
    static int idCounter = 1;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Book Movie_booking.Ticket\n2. Check Availability\n3. Cancel Movie_booking.Ticket\n4. Prepare Chart\n5. Exit");
            int choice = sc.nextInt(); sc.nextLine();
            switch (choice) {
                case 1 -> bookTicket();
                case 2 -> checkAvailability();
                case 3 -> cancelTicket();
                case 4 -> prepareChart();
                case 5 -> System.exit(0);
            }
        }
    }

    static void bookTicket() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Gender: ");
        String gender = sc.nextLine();

        Passenger p;
        if (confirmedList.size() < TOTAL_SEATS) {
            p = new Passenger(idCounter++, name, age, gender, "Confirmed");
            confirmedList.add(p);
        } else if (racList.size() < RAC_LIMIT) {
            p = new Passenger(idCounter++, name, age, gender, "RAC");
            racList.add(p);
        } else if (waitingList.size() < WL_LIMIT) {
            p = new Passenger(idCounter++, name, age, gender, "Waiting");
            waitingList.add(p);
        } else {
            System.out.println("All seats full. No booking possible.");
            return;
        }
        System.out.println("Booking successful. ID: " + p.id);
    }

    static void checkAvailability() {
        System.out.println("Confirmed: " + (TOTAL_SEATS - confirmedList.size()) + " left");
        System.out.println("RAC: " + (RAC_LIMIT - racList.size()) + " left");
        System.out.println("Waiting: " + (WL_LIMIT - waitingList.size()) + " left");
    }

    static void cancelTicket() {
        System.out.print("Enter Passenger ID to cancel: ");
        int id = sc.nextInt();
        for (Iterator<Passenger> it = confirmedList.iterator(); it.hasNext(); ) {
            Passenger p = it.next();
            if (p.id == id) {
                it.remove();
                promoteFromQueues();
                System.out.println("Cancelled and updated chart.");
                return;
            }
        }

        if (racList.removeIf(p -> p.id == id)) {
            promoteFromQueues();
            System.out.println("RAC passenger removed and updated chart.");
            return;
        }

        if (waitingList.removeIf(p -> p.id == id)) {
            System.out.println("Waiting list passenger removed.");
        } else {
            System.out.println("Passenger ID not found.");
        }
    }

    static void promoteFromQueues() {
        if (!racList.isEmpty()) {
            Passenger p = racList.poll();
            p.status = "Confirmed";
            confirmedList.add(p);
        }
        if (!waitingList.isEmpty()) {
            Passenger p = waitingList.poll();
            p.status = "RAC";
            racList.add(p);
        }
    }

    static void prepareChart() {
        System.out.println("\n--- Confirmed List ---");
        confirmedList.forEach(System.out::println);
        System.out.println("\n--- RAC List ---");
        racList.forEach(System.out::println);
        System.out.println("\n--- Waiting List ---");
        waitingList.forEach(System.out::println);
    }
}
