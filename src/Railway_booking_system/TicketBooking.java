package Railway_booking_system;

import java.util.*;

public class TicketBooking {

    public static void main(String[] args) {
        TicketSystem ticketSystem = new TicketSystem();
        Scanner in = new Scanner(System.in);

        while (true){
            System.out.println("\nRailway Booking System: ");
            System.out.println("1. Book Ticket: ");
            System.out.println("2. Cancel Ticket: ");
            System.out.println("3. View confirmed Tickets: ");
            System.out.println("4. View Available Tickets: ");
            System.out.println("5. View RAC Tickets: ");
            System.out.println("6. View WaitingList Tickets: ");
            System.out.println("7. Exit");
            System.out.print("Enter your Choice : ");
            int choice = in.nextInt();
            in.nextLine();

            switch (choice){
                case 1:
                    System.out.print("Enter Name : ");
                    String name = in.nextLine();
                    System.out.print("Enter Age : ");
                    int age = in.nextInt();
                    in.nextLine();
                    System.out.print("Enter Gender (Male/female) : ");
                    String gender = in.nextLine();
                    System.out.print("Enter Berth Perference (L/U/M) : ");
                    String berthPreference = in.nextLine();
                    ticketSystem.bookTicket(name, age, gender, berthPreference);
                    break;
                case 2:
                    System.out.print("Enter Ticket Id to cancel : ");
                    String tickeId = in.nextLine();
                    ticketSystem.cancelTicket(tickeId);
                    break;
                case 3:
                    ticketSystem.printBookedTicket();
                    break;
                case 4:
                    ticketSystem.printAvailableTickets();
                    break;
                case 5:
                    ticketSystem.viewRACtickets();
                    break;
                case 6:
                    ticketSystem.viewWaitingListtickets();
                    break;
                case 7:
                    System.out.println("Exiting...........");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice Try again ! ");
            }
        }
    }

}
