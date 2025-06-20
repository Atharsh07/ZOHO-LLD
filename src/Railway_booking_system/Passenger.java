package Railway_booking_system;
import java.util.*;
public class Passenger {

    String name;
    int age;
    String gender;
    String berthPreference; // lower berth upper and middle
    String allottedBerth;
    String tickeId;   // for cancelation

    public Passenger(String name, int age, String gender, String berthPreference, String allottedBerth, String tickeId) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.berthPreference = berthPreference;
        this.allottedBerth = allottedBerth;
        this.tickeId = tickeId;
    }


    @Override
    public String toString() {
        return "Passenger{" +
                "Age=" + age +
                ", Gender='" + gender + '\'' +
                ", Berth = '" + allottedBerth + '\'' +
                ", Ticket Id ='" + tickeId + '\'' +
                ", Name = '" + name + '\'' +
                '}';
    }
}
