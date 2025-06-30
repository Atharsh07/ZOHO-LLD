package Sales_Management;
import  java.util.*;

class SalesManager {
    private PriceCatalog priceCatalog = new PriceCatalog();
    private Map<String, List<Store>> cityStores = new HashMap<>();
    private Map<String, String> cityToState = new HashMap<>();

    public void addCityToState(String city, String state) {
        cityToState.put(city, state);
    }

    public void addStore(Store store) {
        cityStores.computeIfAbsent(store.getCity(), k -> new ArrayList<>()).add(store);
    }

    public void setItemPrice(String city, Item item, int price) {
        priceCatalog.setPrice(city, item, price);
    }

    public void printStoreSales(String storeName) {
        for (List<Store> stores : cityStores.values()) {
            for (Store store : stores) {
                if (store.getName().equals(storeName)) {
                    System.out.println("Store: " + store.getName() + " Revenue: " + store.getRevenue(priceCatalog));
                }
            }
        }
    }

    public void printCitySales(String city) {
        int total = 0;
        for (Store store : cityStores.getOrDefault(city, Collections.emptyList())) {
            total += store.getRevenue(priceCatalog);
        }
        System.out.println("City: " + city + " Revenue: " + total);
    }

    public void printStateSales(String state) {
        int total = 0;
        for (String city : cityStores.keySet()) {
            if (cityToState.get(city).equals(state)) {
                for (Store store : cityStores.get(city)) {
                    total += store.getRevenue(priceCatalog);
                }
            }
        }
        System.out.println("State: " + state + " Revenue: " + total);
    }

    public void printAllIndiaSales() {
        int total = 0;
        for (List<Store> stores : cityStores.values()) {
            for (Store store : stores) {
                total += store.getRevenue(priceCatalog);
            }
        }
        System.out.println("All India Revenue: " + total);
    }
}
class SalesSystemApp {
    public static void main(String[] args) {
        SalesManager manager = new SalesManager();

        // Items
        Item tea = new Item("Tea", ItemType.BEVERAGE);
        Item sandwich = new Item("Sandwich", ItemType.FOOD);

        // State and City Mapping
        manager.addCityToState("Mumbai", "Maharashtra");
        manager.addCityToState("Pune", "Maharashtra");
        manager.addCityToState("Chennai", "Tamil Nadu");

        // Prices
        manager.setItemPrice("Mumbai", tea, 10);
        manager.setItemPrice("Pune", tea, 12);
        manager.setItemPrice("Chennai", tea, 15);
        manager.setItemPrice("Mumbai", sandwich, 30);
        manager.setItemPrice("Pune", sandwich, 35);
        manager.setItemPrice("Chennai", sandwich, 40);

        // Stores
        Store mumbaiStore1 = new Store("Mumbai1", "Mumbai");
        Store puneStore1 = new Store("Pune1", "Pune");
        Store chennaiStore1 = new Store("Chennai1", "Chennai");

        // Add Stock
        mumbaiStore1.addStock(tea, 100);
        mumbaiStore1.addStock(sandwich, 50);
        puneStore1.addStock(tea, 80);
        chennaiStore1.addStock(sandwich, 60);

        // Add stores to manager
        manager.addStore(mumbaiStore1);
        manager.addStore(puneStore1);
        manager.addStore(chennaiStore1);

        // Sell Items
        mumbaiStore1.sellItem(tea, 10);         // 10 * 10 = 100
        mumbaiStore1.sellItem(sandwich, 5);     // 5 * 30 = 150
        puneStore1.sellItem(tea, 20);           // 20 * 12 = 240
        chennaiStore1.sellItem(sandwich, 10);   // 10 * 40 = 400

        // Reporting
        manager.printStoreSales("Mumbai1");      // 250
        manager.printCitySales("Mumbai");        // 250
        manager.printStateSales("Maharashtra");  // 250 + 240 = 490
        manager.printAllIndiaSales();            // 250 + 240 + 400 = 890
    }
}
