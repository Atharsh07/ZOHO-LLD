package Toll_Management_System;
import java.util.*;


class Vehicle {

    String vehicleNumber;
    String vehicleType;
    boolean isVIP;
    List<Journey> journeys;

    public Vehicle(String vehicleNumber, String vehicleType, boolean isVIP) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.isVIP = isVIP;
        this.journeys = new ArrayList<>();
    }

    public void addJourney(Journey journey){
        journeys.add(journey);
    }

    public void displayDetails(){
        System.out.println("Vehicle Number : " + vehicleNumber);
        System.out.println("Type : " + vehicleType + " , VIP : " + isVIP);
        for(Journey journey : journeys){
            System.out.println("Journey: " + journey.startPoint + " -> " + journey.endPoint);
            System.out.println("Tolls passed : " + journey.tollsPassed);
            System.out.println("Amount paid : " + journey.amountPaid);
        }

        int totalPaid = 0;
        for(Journey journey : journeys){
            totalPaid += journey.amountPaid;
        }
        System.out.println("Total amount paid : " + totalPaid);
    }

}
