package TakiBooking;

import java.util.*;

class Taxi{
    int id ;
    char currPoint = 'A';
    int totalEarning = 0;
    List<Booking> bookings = new ArrayList<>();

    public Taxi(int id){
        this.id = id;
    }

    public boolean isAvailable(int requestTime){
        if(bookings.isEmpty()) return true;
        Booking lastBooking = bookings.getLast();
        return lastBooking.dropTime <= requestTime;
    }

    public int calculateEarning(char from , char to){
        int distance = Math.abs(to - from) * 15;
        return 100 + Math.max(0,(distance-5) * 10);
    }

    public void addBooking(Booking booking){
        bookings.add(booking);
        totalEarning += booking.amount; //udpdating the state
        currPoint = booking.to; // udpate the currPoint of the taxi
    }

}