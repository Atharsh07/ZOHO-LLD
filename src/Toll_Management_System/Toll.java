package Toll_Management_System;
import java.util.*;

class Toll {

    int tollId;
    Map<String, Integer> chargesPerVehcileType;
    List<VehiclePayment> vehiclePassed;
    int totalRevenue;

    public Toll(int tollId, Map<String, Integer> chargesPerVehcileType) {
        this.tollId = tollId;
        this.chargesPerVehcileType = chargesPerVehcileType;
        this.vehiclePassed = new ArrayList<>();
        this.totalRevenue = 0;
    }

    public int calculateToll(String vehicleType , boolean isVIP){
        int charge = chargesPerVehcileType.getOrDefault(vehicleType, 0);
        if(isVIP){
            charge = charge - (charge / 5);
        }
        return charge;
    }

    public void recordVehicle(Vehicle vehicle, int charge){
        vehiclePassed.add(new VehiclePayment(vehicle.vehicleNumber, charge));
        totalRevenue += charge;
    }

    public void displayDetails(){
        System.out.println("TollID : " + tollId);
        System.out.println("Vehicle Passed : ");
        for(VehiclePayment vp : vehiclePassed){
            System.out.println("Vehicle: " + vp.vehicleNumber + " , paid " + vp.amountPaid);
        }
        System.out.println("Total Revenue : " + totalRevenue);
    }

}
