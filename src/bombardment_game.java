// -------------------------
// BOMBARDMENT MAN GAME (In-Memory DB with Transactions)
// -------------------------
import java.util.*;

class BombardedManGame {
    static Map<String, String> db = new HashMap<>();
    static Deque<Map<String, String>> transactionStack = new ArrayDeque<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("END")) break;

            String[] parts = line.split(" ");
            String command = parts[0].toUpperCase();

            switch (command) {
                case "SET":
                    set(parts[1], parts[2]);
                    break;
                case "GET":
                    get(parts[1]);
                    break;
                case "UNSET":
                    unset(parts[1]);
                    break;
                case "COUNT":
                    count(parts[1]);
                    break;
                case "BEGIN":
                    begin();
                    break;
                case "ROLLBACK":
                    rollback();
                    break;
                case "COMMIT":
                    commit();
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }
    }

    static void set(String key, String value) {
        if (!transactionStack.isEmpty()) {
            transactionStack.peek().put(key, db.getOrDefault(key, null));
        }
        db.put(key, value);
    }

    static void get(String key) {
        String value = db.getOrDefault(key, null);
        System.out.println(value == null ? "null" : value);
    }

    static void unset(String key) {
        if (!transactionStack.isEmpty()) {
            transactionStack.peek().put(key, db.getOrDefault(key, null));
        }
        db.remove(key);
    }

    static void count(String value) {
        int cnt = 0;
        for (String v : db.values()) {
            if (v.equals(value)) cnt++;
        }
        System.out.println(cnt);
    }

    static void begin() {
        transactionStack.push(new HashMap<>());
    }

    static void rollback() {
        if (transactionStack.isEmpty()) {
            System.out.println("NO TRANSACTION");
            return;
        }
        Map<String, String> lastTransaction = transactionStack.pop();
        for (Map.Entry<String, String> entry : lastTransaction.entrySet()) {
            if (entry.getValue() == null) db.remove(entry.getKey());
            else db.put(entry.getKey(), entry.getValue());
        }
    }

    static void commit() {
        if (transactionStack.isEmpty()) {
            System.out.println("NO TRANSACTION");
            return;
        }
        transactionStack.clear();
    }
}
