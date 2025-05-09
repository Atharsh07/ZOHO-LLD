package TakiBooking;

public class Booking {
    int bookingId, customerId, pickupTime, dropTime, amount;
    char from, to;

    public Booking(int customerId, int bookingId, int pickupTime, int dropTime, int amount, char from, char to) {
        this.customerId = customerId;
        this.bookingId = bookingId;
        this.pickupTime = pickupTime;
        this.dropTime = dropTime;
        this.amount = amount;
        this.from = from;
        this.to = to;
    }


}
