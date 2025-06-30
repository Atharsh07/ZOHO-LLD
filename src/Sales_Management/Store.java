package Sales_Management;
import java.util.*;

class Store {
    private String name;
    private String city;
    private Map<Item, Integer> stock = new HashMap<>();
    private Map<Item, Integer> unitsSold = new HashMap<>();

    public Store(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public void addStock(Item item, int quantity) {
        stock.put(item, stock.getOrDefault(item, 0) + quantity);
    }

    public boolean sellItem(Item item, int quantity) {
        int available = stock.getOrDefault(item, 0);
        if (available >= quantity) {
            stock.put(item, available - quantity);
            unitsSold.put(item, unitsSold.getOrDefault(item, 0) + quantity);
            return true;
        }
        return false;
    }

    public int getUnitsSold(Item item) {
        return unitsSold.getOrDefault(item, 0);
    }

    public int getRevenue(PriceCatalog priceCatalog) {
        int revenue = 0;
        for (Item item : unitsSold.keySet()) {
            int price = priceCatalog.getPrice(city, item);
            revenue += price * unitsSold.get(item);
        }
        return revenue;
    }

    public String getCity() {
        return city;
    }

    public Map<Item, Integer> getUnitsSoldMap() {
        return unitsSold;
    }

    public String getName() {
        return name;
    }
}
