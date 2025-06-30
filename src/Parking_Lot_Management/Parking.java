package Parking_Lot_Management;

// -------------------------
// PARKING LOT MANAGEMENT SYSTEM (LLD FROM SAMPLE PAPER)
// -------------------------
import java.util.*;

enum VehicleType { CAR, BIKE, TRUCK }

class Vehicle {
    VehicleType type;
    String regNo;
    String color;

    public Vehicle(VehicleType type, String regNo, String color) {
        this.type = type;
        this.regNo = regNo;
        this.color = color;
    }
}

class Slot {
    int slotNo;
    VehicleType type;
    Vehicle parkedVehicle;

    public Slot(int slotNo, VehicleType type) {
        this.slotNo = slotNo;
        this.type = type;
    }

    public boolean isFree() {
        return parkedVehicle == null;
    }
}

class Floor {
    int floorNo;
    List<Slot> slots = new ArrayList<>();

    public Floor(int floorNo, int noOfSlots) {
        this.floorNo = floorNo;
        for (int i = 1; i <= noOfSlots; i++) {
            VehicleType type;
            if (i == 1) type = VehicleType.TRUCK;
            else if (i <= 3) type = VehicleType.BIKE;
            else type = VehicleType.CAR;
            slots.add(new Slot(i, type));
        }
    }

    public Slot getFirstFreeSlot(VehicleType vType) {
        for (Slot slot : slots) {
            if (slot.type == vType && slot.isFree()) return slot;
        }
        return null;
    }

    public List<Slot> getSlotsByType(VehicleType vType, boolean occupied) {
        List<Slot> result = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.type == vType && (occupied ? !slot.isFree() : slot.isFree())) {
                result.add(slot);
            }
        }
        return result;
    }
}

class Ticket {
    String id;
    Vehicle vehicle;

    public Ticket(String id, Vehicle vehicle) {
        this.id = id;
        this.vehicle = vehicle;
    }
}

class ParkingLot {
    String id;
    List<Floor> floors = new ArrayList<>();
    Map<String, Slot> ticketMap = new HashMap<>();

    public ParkingLot(String id, int noOfFloors, int slotsPerFloor) {
        this.id = id;
        for (int i = 1; i <= noOfFloors; i++) floors.add(new Floor(i, slotsPerFloor));
        System.out.printf("Created parking lot with %d floors and %d slots per floor\n", noOfFloors, slotsPerFloor);
    }

    public void parkVehicle(Vehicle vehicle) {
        for (Floor floor : floors) {
            Slot slot = floor.getFirstFreeSlot(vehicle.type);
            if (slot != null) {
                slot.parkedVehicle = vehicle;
                String ticketId = id + "_" + floor.floorNo + "_" + slot.slotNo;
                ticketMap.put(ticketId, slot);
                System.out.println("Parked vehicle. Ticket ID: " + ticketId);
                return;
            }
        }
        System.out.println("Parking Lot Full");
    }

    public void unparkVehicle(String ticketId) {
        Slot slot = ticketMap.remove(ticketId);
        if (slot != null && slot.parkedVehicle != null) {
            Vehicle v = slot.parkedVehicle;
            slot.parkedVehicle = null;
            System.out.printf("Unparked vehicle with Registration Number: %s and Color: %s\n", v.regNo, v.color);
        } else {
            System.out.println("Invalid Ticket");
        }
    }

    public void display(String type, VehicleType vType) {
        for (Floor floor : floors) {
            List<Slot> relevant = floor.getSlotsByType(vType, type.equals("occupied_slots"));
            if (type.equals("free_count")) {
                System.out.printf("No. of free slots for %s on Floor %d: %d\n", vType, floor.floorNo, relevant.size());
            } else {
                System.out.printf("%s slots for %s on Floor %d: ", capitalize(type), vType, floor.floorNo);
                System.out.println(String.join(",", relevant.stream().map(s -> s.slotNo + "").toList()));
            }
        }
    }

    private String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}

class ParkingSystemApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ParkingLot lot = null;

        while (true) {
            String input = sc.nextLine();
            if (input.equals("exit")) break;
            String[] tokens = input.split(" ");

            switch (tokens[0]) {
                case "create_parking_lot" -> lot = new ParkingLot(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                case "park_vehicle" -> lot.parkVehicle(new Vehicle(VehicleType.valueOf(tokens[1]), tokens[2], tokens[3]));
                case "unpark_vehicle" -> lot.unparkVehicle(tokens[1]);
                case "display" -> lot.display(tokens[1], VehicleType.valueOf(tokens[2]));
            }
        }
    }
}
