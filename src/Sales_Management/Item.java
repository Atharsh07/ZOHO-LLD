package Sales_Management;
import java.util.*;

enum ItemType {
    FOOD, BEVERAGE
}

class Item {
    private String name;
    private ItemType type;

    public Item(String name, ItemType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Item other) {
            return name.equalsIgnoreCase(other.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}



class PriceCatalog {
    private Map<String, Map<Item, Integer>> cityPrices = new HashMap<>();

    public void setPrice(String city, Item item, int price) {
        cityPrices.computeIfAbsent(city, k -> new HashMap<>()).put(item, price);
    }

    public int getPrice(String city, Item item) {
        return cityPrices.getOrDefault(city, Collections.emptyMap()).getOrDefault(item, 0);
    }
}
