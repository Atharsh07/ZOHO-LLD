

import java.util.*;

enum SplitType {
    EQUAL, EXACT, PERCENT
}


class User {
    private final String id;
    private final String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }

    public String getName() { return name; }
}



abstract class Split {
    private final User user;
    private double amount;

    public Split(User user) {
        this.user = user;
    }

    public User getUser() { return user; }

    public double getAmount() { return amount; }

    public void setAmount(double amount) {
        this.amount = Math.round(amount * 100.0) / 100.0;
    }
}

class EqualSplit extends Split {
    public EqualSplit(User user) { super(user); }
}

class ExactSplit extends Split {
    public ExactSplit(User user, double amount) {
        super(user);
        setAmount(amount);
    }
}

class PercentSplit extends Split {
    private final double percent;

    public PercentSplit(User user, double percent) {
        super(user);
        this.percent = percent;
    }

    public double getPercent() { return percent; }
}



class Expense {
    private final double total;
    private final User paidBy;
    private final SplitType type;
    private final List<Split> splits;

    public Expense(double total, User paidBy, SplitType type, List<Split> splits) {
        this.total = total;
        this.paidBy = paidBy;
        this.type = type;
        this.splits = splits;

        validate();
        calculateSplits();
    }

    private void validate() {
        if (type == SplitType.EXACT) {
            double sum = splits.stream().mapToDouble(Split::getAmount).sum();
            if (Math.round(sum * 100.0) / 100.0 != Math.round(total * 100.0) / 100.0) {
                throw new IllegalArgumentException("Exact split doesn't match total");
            }
        }

        if (type == SplitType.PERCENT) {
            double sum = splits.stream()
                    .mapToDouble(s -> ((PercentSplit) s).getPercent())
                    .sum();
            if (sum != 100.0) {
                throw new IllegalArgumentException("Percent split must add to 100%");
            }
        }
    }

    private void calculateSplits() {
        switch (type) {
            case EQUAL -> {
                double share = total / splits.size();
                for (Split s : splits) {
                    s.setAmount(share);
                }
            }
            case PERCENT -> {
                for (Split s : splits) {
                    PercentSplit ps = (PercentSplit) s;
                    double amount = total * ps.getPercent() / 100.0;
                    ps.setAmount(amount);
                }
            }
        }
    }

    public User getPaidBy() { return paidBy; }

    public List<Split> getSplits() { return splits; }
}




class BalanceSheet {
    private final Map<String, Map<String, Double>> balances = new HashMap<>();

    public void updateBalance(String payer, String borrower, double amount) {
        balances.putIfAbsent(payer, new HashMap<>());
        balances.get(payer).put(borrower,
                round(balances.get(payer).getOrDefault(borrower, 0.0) + amount));

        balances.putIfAbsent(borrower, new HashMap<>());
        balances.get(borrower).put(payer,
                round(balances.get(borrower).getOrDefault(payer, 0.0) - amount));
    }

    public void showBalances() {
        for (String user1 : balances.keySet()) {
            for (Map.Entry<String, Double> entry : balances.get(user1).entrySet()) {
                String user2 = entry.getKey();
                double amount = entry.getValue();
                if (amount > 0) {
                    System.out.println(user2 + " owes " + user1 + ": ₹" + amount);
                }
            }
        }
    }

    private double round(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }
}

class ExpenseManager {
    private final Map<String, User> users = new HashMap<>();
    private final BalanceSheet balanceSheet = new BalanceSheet();

    public void registerUser(String id, String name) {
        users.put(id, new User(id, name));
    }

    public void addExpense(double total, String paidById, SplitType type, List<Split> splits) {
        User paidBy = users.get(paidById);
        if (paidBy == null) throw new IllegalArgumentException("User not registered");

        Expense expense = new Expense(total, paidBy, type, splits);

        for (Split split : expense.getSplits()) {
            if (!split.getUser().getId().equals(paidById)) {
                balanceSheet.updateBalance(paidById, split.getUser().getId(), split.getAmount());
            }
        }
    }

    public void showBalances() {
        balanceSheet.showBalances();
    }

    public User getUser(String id) {
        return users.get(id);
    }
}



class SplitwiseApp {
    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();

        // Register Users
        manager.registerUser("u1", "Alice");
        manager.registerUser("u2", "Bob");
        manager.registerUser("u3", "Charlie");

        // Equal Split: Alice pays 300 split among 3
        List<Split> equalSplits = List.of(
                new EqualSplit(manager.getUser("u1")),
                new EqualSplit(manager.getUser("u2")),
                new EqualSplit(manager.getUser("u3"))
        );
        manager.addExpense(300, "u1", SplitType.EQUAL, equalSplits);

        // Exact Split: Bob pays 400 split as 100, 300
        List<Split> exactSplits = List.of(
                new ExactSplit(manager.getUser("u2"), 100),
                new ExactSplit(manager.getUser("u3"), 300)
        );
        manager.addExpense(400, "u2", SplitType.EXACT, exactSplits);

        // Percent Split: Charlie pays 500 split as 20%, 30%, 50%
        List<Split> percentSplits = List.of(
                new PercentSplit(manager.getUser("u1"), 20),
                new PercentSplit(manager.getUser("u2"), 30),
                new PercentSplit(manager.getUser("u3"), 50)
        );
        manager.addExpense(500, "u3", SplitType.PERCENT, percentSplits);

        // Final balances
        System.out.println("Balances:");
        manager.showBalances();
    }
}
