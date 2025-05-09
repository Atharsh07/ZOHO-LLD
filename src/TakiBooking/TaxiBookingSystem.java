package TakiBooking;
import java.util.*;


public class TaxiBookingSystem {

    static List<Taxi> taxis = new ArrayList<>();

    static Scanner in = new Scanner(System.in);

    static int customerCounter = 1;

    public static void main(String[] args) {
        System.out.println("Enter the Number of Taxi's");
        int numTaxis = in.nextInt();
        initializeTaxis(numTaxis);
    }


    public static void initializeTaxis(int n ){
        for(int i = 1; i<=n; i++){
            taxis.add(new Taxi(i));
        }
    }


}
