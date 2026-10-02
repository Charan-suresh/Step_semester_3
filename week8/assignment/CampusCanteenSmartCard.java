import java.util.*;

interface PricingPlan {
    String getPlanName();
    double calculatePrice(double basePrice);
}

class DayScholarPlan implements PricingPlan {
    @Override
    public String getPlanName() {
        return "Day Scholar";
    }

    @Override
    public double calculatePrice(double basePrice) {
        return basePrice;
    }
}

class HostellerPlan implements PricingPlan {
    @Override
    public String getPlanName() {
        return "Hosteller";
    }

    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 0.90; // 10% off
    }
}

class StaffPlan implements PricingPlan {
    @Override
    public String getPlanName() {
        return "Staff";
    }

    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 0.80; // 20% off
    }
}

enum TransactionType {
    TOP_UP,
    PURCHASE,
    REFUND
}

class Transaction {
    private final String id;
    private final TransactionType type;
    private final double amount; // positive for credit, negative for debit
    private final String description;
    private final String refPurchaseItem;

    public Transaction(String id, TransactionType type, double amount, String description, String refPurchaseItem) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.refPurchaseItem = refPurchaseItem;
    }

    public String getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getRefPurchaseItem() {
        return refPurchaseItem;
    }
}

class SmartCard {
    private static final double MIN_TOPUP = 100.00;
    private static final double MAX_BALANCE = 5000.00;

    private final String cardId;
    private final PricingPlan plan;
    private boolean isBlocked;
    private final List<Transaction> transactions;
    private final Set<String> refundedItems;
    private int txCounter = 1;

    public SmartCard(String cardId, PricingPlan plan) {
        this.cardId = cardId;
        this.plan = plan;
        this.isBlocked = false;
        this.transactions = new ArrayList<>();
        this.refundedItems = new HashSet<>();
    }

    public String getCardId() {
        return cardId;
    }

    public PricingPlan getPlan() {
        return plan;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public double getBalance() {
        double balance = 0.0;
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public boolean topUp(double amount) {
        if (isBlocked) {
            System.out.println("Top-up rejected: Card " + cardId + " is blocked.");
            return false;
        }
        if (amount < MIN_TOPUP) {
            System.out.printf("Top-up rejected: Minimum top-up amount is ₹%.2f.%n", MIN_TOPUP);
            return false;
        }
        if (getBalance() + amount > MAX_BALANCE) {
            System.out.printf("Top-up rejected: Maximum card balance cannot exceed ₹%.2f.%n", MAX_BALANCE);
            return false;
        }

        transactions.add(new Transaction("TX-" + (txCounter++), TransactionType.TOP_UP, amount, "Top-up", null));
        System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f.%n", cardId, amount, getBalance());
        return true;
    }

    public boolean purchase(String item, double basePrice) {
        if (isBlocked) {
            System.out.println("Purchase rejected: Card " + cardId + " is blocked.");
            return false;
        }

        double finalPrice = plan.calculatePrice(basePrice);
        double currentBalance = getBalance();

        if (currentBalance < finalPrice) {
            System.out.printf("Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                    finalPrice, currentBalance);
            return false;
        }

        transactions.add(new Transaction("TX-" + (txCounter++), TransactionType.PURCHASE, -finalPrice, item, item));
        System.out.printf("%s purchased for ₹%.2f. Balance: ₹%.2f.%n", item, finalPrice, getBalance());
        return true;
    }

    public boolean refund(String item) {
        if (isBlocked) {
            System.out.println("Refund rejected: Card " + cardId + " is blocked.");
            return false;
        }

        if (refundedItems.contains(item)) {
            System.out.println("Refund rejected: " + item + " has already been refunded.");
            return false;
        }

        // Find the last purchase of this item
        Transaction purchaseTx = null;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getType() == TransactionType.PURCHASE && item.equals(t.getRefPurchaseItem())) {
                purchaseTx = t;
                break;
            }
        }

        if (purchaseTx == null) {
            System.out.println("Refund rejected: No purchase record found for " + item + ".");
            return false;
        }

        double refundAmount = Math.abs(purchaseTx.getAmount());
        refundedItems.add(item);
        transactions.add(new Transaction("TX-" + (txCounter++), TransactionType.REFUND, refundAmount, "Refund: " + item, item));
        System.out.printf("Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n", refundAmount, item, getBalance());
        return true;
    }

    public void printMiniStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mini-statement for ").append(cardId).append(": ");
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            if (t.getAmount() >= 0) {
                sb.append(String.format("+%.2f", t.getAmount()));
            } else {
                sb.append(String.format("%.2f", t.getAmount()));
            }
            if (i < transactions.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(String.format(" = ₹%.2f.", getBalance()));
        System.out.println(sb.toString());
    }
}

public class CampusCanteenSmartCard {
    public static void main(String[] args) {
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());

        // 1. Card 'C-2045' (Hosteller plan) is topped up with ₹500
        card.topUp(500.0);

        // 2. Holder buys 'Veg Thali' priced at ₹120
        card.purchase("Veg Thali", 120.0);

        // 3. Holder buys 'Cold Coffee' priced at ₹60
        card.purchase("Cold Coffee", 60.0);

        // 4. Holder attempts to buy items worth ₹400
        card.purchase("Snacks Combo", 400.0);

        // 5. Canteen refunds the 'Veg Thali' purchase
        card.refund("Veg Thali");

        // 6. Canteen attempts to refund the 'Veg Thali' purchase again
        card.refund("Veg Thali");

        // 7. Holder requests a mini-statement
        card.printMiniStatement();
    }
}
