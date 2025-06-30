package Ride_Sharing;
import java.util.*;

class RideManager {
    private Map<String, Ride> rides = new HashMap<>();
    private Map<String, Rider> riders = new HashMap<>();
    private Map<String, Driver> drivers = new HashMap<>();

    public void addDriver(String name) {
        drivers.put(name, new Driver(name));
    }

    public void addRider(String name) {
        riders.put(name, new Rider(name));
    }

    public void createOrUpdateRide(String rideId, int origin, int destination, int seats, String riderName) {
        Rider rider = riders.get(riderName);
        if (rider == null) throw new IllegalArgumentException("Rider not found");

        Ride ride = rides.get(rideId);
        if (ride == null) {
            rides.put(rideId, new Ride(rideId, origin, destination, seats, rider));
        } else {
            ride.updateRide(origin, destination, seats);
        }
    }

    public void withdrawRide(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride != null) ride.withdrawRide();
    }

    public void closeRide(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride != null) {
            double amount = ride.closeRide();
            System.out.println("Ride " + rideId + " closed. Amount charged: " + amount);
        }
    }
}
