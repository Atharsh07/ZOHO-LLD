package Railway_booking_system;
import java.util.*;
public class TicketSystem {

   private final List<String> availableBerths = new ArrayList<>(Arrays.asList("L", "U", "M"));

   private final Queue<Passenger> racQueue = new LinkedList<>();

   private final Queue<Passenger> waitingList = new LinkedList<>();

   private final List<Passenger> confirmedPassengers = new ArrayList<>();

   private int tickerCounter = 1;


   public void bookTicket( String name, int age, String gender, String berthPerference ){
      String ticketId = "T" + tickerCounter++;
      Passenger passenger;
      if(!availableBerths.isEmpty()){
         String allocatedBerth = allocateBerth(age, gender, berthPerference);
         passenger = new Passenger(name, age, gender, berthPerference, allocatedBerth, ticketId);
         confirmedPassengers.add(passenger);
         availableBerths.remove(allocatedBerth);
         System.out.println("Ticket confirmed" + passenger);
      }else if(racQueue.size() < 1){
         passenger = new Passenger(name, age, gender, berthPerference, "RAC", ticketId);
         racQueue.offer(passenger);
         System.out.println("Ticket in RAC " + passenger);
      }else if(waitingList.size() < 1){
         passenger = new Passenger(name, age, gender, berthPerference, "WaitingList", ticketId);
         waitingList.offer(passenger);
         System.out.println("Ticket in Waiting List " + passenger);
      }else{
         System.out.println("No ticket's available");
      }
   }

   public void cancelTicket(String ticketId){
      Optional<Passenger> passengerOpt = confirmedPassengers.stream().filter(p -> p.tickeId.equals(ticketId)).findFirst();
      if (passengerOpt.isPresent()){
         Passenger passenger = passengerOpt.get();
         confirmedPassengers.remove(passenger);
         availableBerths.add(passenger.allottedBerth);
         if(!racQueue.isEmpty()){
            Passenger racPassenger = racQueue.poll();
            String allocatedBerth = allocateBerth(racPassenger.age, racPassenger.gender, racPassenger.berthPreference);
            racPassenger.allottedBerth = allocatedBerth;
            confirmedPassengers.add(racPassenger);
            availableBerths.remove(allocatedBerth);
            System.out.println("RAC ticket moved to confirmed : " + racPassenger);
         }
         if(!waitingList.isEmpty()){
            Passenger waitingPassenger = waitingList.poll();
            racQueue.offer(waitingPassenger);
            waitingPassenger.allottedBerth = "RAC";
            System.out.println("Waiting List ticket moved to RAC :  " + waitingPassenger);
         }
         System.out.println("Ticket cancelled successfully for ID : " + ticketId);
      }else{
         System.out.println("TicketId not found");
      }
   }

   public void printBookedTicket(){
      if(confirmedPassengers.isEmpty()){
         System.out.println("No confirmed tickets");
      }else{
         System.out.println("Confirmed tickets");
         for(Passenger passenger : confirmedPassengers){
            System.out.println(passenger);
         }
      }
   }

   public void printAvailableTickets(){
      System.out.println("Available Berts : " + availableBerths.size());
      System.out.println("Available RAC : " + (1-racQueue.size()));
      System.out.println("Waiting List : " + (1-waitingList.size()));
   }

   public void viewRACtickets(){
      if(racQueue.isEmpty()){
         System.out.println("NO RAC tickets");
      }else{
         System.out.println("RAC tickets : ");
         for(Passenger passenger : racQueue){
            System.out.println(passenger);
         }
      }
   }

   public void viewWaitingListtickets(){
      if(waitingList.isEmpty()){
         System.out.println("NO WaitingList tickets");
      }else{
         System.out.println("WaitingList tickets : ");
         for(Passenger passenger : waitingList){
            System.out.println(passenger);
         }
      }
   }


   private String allocateBerth(int age, String gender, String berthPerference) {
      if(age > 60 || gender.equalsIgnoreCase("female") && availableBerths.contains("L")){
         return "L";
      }
      if(availableBerths.contains(berthPerference)){
         return berthPerference;
      }
      return availableBerths.get(0);
   }

}
