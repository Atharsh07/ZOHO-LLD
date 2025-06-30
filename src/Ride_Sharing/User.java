package Ride_Sharing;

abstract class User {
    protected String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Driver extends User {
    public Driver(String name) {
        super(name);
    }
}

class Rider extends User {
    private int rideCount = 0;

    public Rider(String name) {
        super(name);
    }

    public void incrementRideCount() {
        rideCount++;
    }

    public boolean isPreferred() {
        return rideCount > 10;
    }

    public int getRideCount() {
        return rideCount;
    }
}
