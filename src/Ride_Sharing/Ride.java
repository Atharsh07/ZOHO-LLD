package Ride_Sharing;

enum RideStatus {
    CREATED, WITHDRAWN, CLOSED
}

class Ride {
    private static final int AMOUNT_PER_KM = 20;

    private String id;
    private int origin, destination, seats;
    private RideStatus status;
    private Rider rider;

    public Ride(String id, int origin, int destination, int seats, Rider rider) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.seats = seats;
        this.status = RideStatus.CREATED;
        this.rider = rider;
    }

    public void updateRide(int origin, int destination, int seats) {
        if (status == RideStatus.CREATED) {
            this.origin = origin;
            this.destination = destination;
            this.seats = seats;
        }
    }

    public void withdrawRide() {
        if (status == RideStatus.CREATED) {
            status = RideStatus.WITHDRAWN;
        }
    }

    public double closeRide() {
        if (status != RideStatus.CREATED) {
            throw new IllegalStateException("Ride not in progress.");
        }
        status = RideStatus.CLOSED;
        rider.incrementRideCount();

        int distance = destination - origin;
        if (distance < 0) throw new IllegalArgumentException("Invalid ride: destination < origin");

        boolean preferred = rider.isPreferred();

        if (seats >= 2) {
            return distance * seats * AMOUNT_PER_KM * (preferred ? 0.5 : 0.75);
        } else {
            return distance * AMOUNT_PER_KM * (preferred ? 0.75 : 1.0);
        }
    }

    public RideStatus getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}
