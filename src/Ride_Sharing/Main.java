package Ride_Sharing;

class RideSharingApp {
    public static void main(String[] args) {
        RideManager manager = new RideManager();

        // Sample Data
        manager.addDriver("John");
        manager.addDriver("Mike");
        manager.addRider("Alice");

        manager.createOrUpdateRide("R1", 50, 60, 1, "Alice"); // Normal rider, 1 seat
        manager.closeRide("R1"); // Expect 200

        manager.createOrUpdateRide("R2", 50, 60, 2, "Alice"); // Normal rider, 2 seats
        manager.closeRide("R2"); // Expect 300

        // Simulate 10 rides to make Alice preferred
        for (int i = 3; i <= 12; i++) {
            manager.createOrUpdateRide("R" + i, 0, 10, 1, "Alice");
            manager.closeRide("R" + i);
        }

        manager.createOrUpdateRide("R13", 10, 20, 2, "Alice"); // Preferred rider, 2 seats
        manager.closeRide("R13"); // Expect 200

        manager.createOrUpdateRide("R14", 10, 20, 1, "Alice"); // Preferred rider, 1 seat
        manager.closeRide("R14"); // Expect 150
    }
}
