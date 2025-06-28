package Toll_Management_System;
import java.util.*;

public class Highway {

    List<Toll> tollPoints;
    Map<String, Vehicle> vehicleRecords;

    public Highway(List<Toll> tollPoints) {
        this.tollPoints = tollPoints;
        this.vehicleRecords = new HashMap<>();
    }

    public void processJourney(String vehicleNumber, String vehicleType, boolean isVIP, String startPoint, String endPoint, List<Integer> tollRoutes){
        Vehicle vehicle = vehicleRecords.computeIfAbsent(vehicleNumber, vn -> new Vehicle(vn, vehicleType, isVIP));
        
    }
}
