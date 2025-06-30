// -------------------------
// BANK/GIFT CARD SYSTEM
// -------------------------
import java.util.*;

class Customer {
    int id;
    String name;
    String password;
    double balance;
    List<GiftCard> giftCards = new ArrayList<>();

    public Customer(int id, String name, String password, double balance) {
        this.id = id;
        this.name = name;
        this.password = encrypt(password);
        this.balance = balance;
    }

    private String encrypt(String pw) {
        StringBuilder sb = new StringBuilder();
        for (char ch : pw.toCharArray()) {
            if (Character.isDigit(ch)) sb.append((char) (((ch - '0' + 1) % 10) + '0'));
            else if (Character.isLowerCase(ch)) sb.append((char) (((ch - 'a' + 1) % 26) + 'a'));
            else if (Character.isUpperCase(ch)) sb.append((char) (((ch - 'A' + 1) % 26) + 'A'));
            else sb.append(ch);
        }
        return sb.toString();
    }

    public boolean login(String pw) {
        return this.password.equals(encrypt(pw));
    }
}

class GiftCard {
    String cardNo;
    String pin;
    double balance = 0;
    int rewardPoints = 0;
    boolean blocked = false;
    List<String> history = new ArrayList<>();

    public GiftCard(String cardNo, String pin) {
        this.cardNo = cardNo;
        this.pin = pin;
    }

    public void addTransaction(String msg) {
        history.add(msg);
    }
}

class BankSystem {
    static Scanner sc = new Scanner(System.in);
    static Map<Integer, Customer> customers = new HashMap<>();
    static int custIdCounter = 1;

    public static void main(String[] args) {
        Customer c1 = new Customer(1, "Alice", "Abc123", 1000);
        customers.put(1, c1);
        run();
    }

    static void run() {
        while (true) {
            System.out.println("1. Login to Account\n2. Exit");
            int ch = sc.nextInt();
            if (ch == 1) accountLogin();
            else break;
        }
    }

    static void accountLogin() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        System.out.print("Enter Password: ");
        String pw = sc.next();
        Customer cust = customers.get(id);
        if (cust != null && cust.login(pw)) {
            System.out.println("Login successful! Balance: Rs. " + cust.balance);
            accountMenu(cust);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    static void accountMenu(Customer cust) {
        while (true) {
            System.out.println("1.Create Gift Card\n2.TopUp\n3.Transaction History\n4.Block\n5.Logout");
            int ch = sc.nextInt();
            if (ch == 1) createGiftCard(cust);
            else if (ch == 2) topUp(cust);
            else if (ch == 3) showHistory(cust);
            else if (ch == 4) blockCard(cust);
            else break;
        }
    }

    static void createGiftCard(Customer cust) {
        String cardNo = "GC" + new Random().nextInt(99999);
        String pin = String.valueOf(1000 + new Random().nextInt(9000));
        GiftCard gc = new GiftCard(cardNo, pin);
        cust.giftCards.add(gc);
        System.out.println("Gift Card Created. Card No: " + cardNo + " PIN: " + pin);
    }

    static void topUp(Customer cust) {
        System.out.print("Enter card number: ");
        String cn = sc.next();
        for (GiftCard gc : cust.giftCards) {
            if (gc.cardNo.equals(cn) && !gc.blocked) {
                System.out.print("Enter amount to top-up: ");
                double amt = sc.nextDouble();
                if (cust.balance >= amt) {
                    cust.balance -= amt;
                    gc.balance += amt;
                    gc.addTransaction("Topped up Rs. " + amt);
                    System.out.println("Top up successful.");
                } else {
                    System.out.println("Insufficient balance.");
                }
                return;
            }
        }
        System.out.println("Gift card not found or blocked.");
    }

    static void showHistory(Customer cust) {
        for (GiftCard gc : cust.giftCards) {
            System.out.println("Card: " + gc.cardNo);
            for (String h : gc.history)
                System.out.println("- " + h);
        }
    }

    static void blockCard(Customer cust) {
        System.out.print("Enter card to block: ");
        String cn = sc.next();
        for (GiftCard gc : cust.giftCards) {
            if (gc.cardNo.equals(cn)) {
                if (!gc.blocked) {
                    cust.balance += gc.balance;
                    gc.blocked = true;
                    gc.addTransaction("Card blocked, Rs. " + gc.balance + " returned to account.");
                    gc.balance = 0;
                    System.out.println("Card blocked and funds returned.");
                } else {
                    System.out.println("Card already blocked.");
                }
                return;
            }
        }
        System.out.println("Card not found.");
    }
}
