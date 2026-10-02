import java.util.*;

interface IPaymentMethod {
    String getMethodName();
    boolean processPayment(double amount);
}

class CreditCardPayment implements IPaymentMethod {
    private final boolean shouldSucceed;

    public CreditCardPayment(boolean shouldSucceed) {
        this.shouldSucceed = shouldSucceed;
    }

    public CreditCardPayment() {
        this(true);
    }

    @Override
    public String getMethodName() {
        return "Credit Card";
    }

    @Override
    public boolean processPayment(double amount) {
        return shouldSucceed;
    }
}

class DigitalWalletPayment implements IPaymentMethod {
    private final boolean shouldSucceed;

    public DigitalWalletPayment(boolean shouldSucceed) {
        this.shouldSucceed = shouldSucceed;
    }

    public DigitalWalletPayment() {
        this(true);
    }

    @Override
    public String getMethodName() {
        return "Digital Wallet";
    }

    @Override
    public boolean processPayment(double amount) {
        return shouldSucceed;
    }
}

class FoodItem {
    private final String name;
    private final double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class LineItem {
    private final FoodItem item;
    private final int quantity;

    public LineItem(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public FoodItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return item.getPrice() * quantity;
    }
}

enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PAID
}

class Customer {
    private final String id;
    private final String name;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void notify(String message) {
        System.out.println("Notification: " + message);
    }
}

class Order {
    private final String orderId;
    private final Customer customer;
    private final List<LineItem> lineItems;
    private OrderStatus status;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.lineItems = new ArrayList<>();
        this.status = OrderStatus.CREATED;
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void addItem(FoodItem item, int quantity) {
        lineItems.add(new LineItem(item, quantity));
    }

    public double calculateTotal() {
        double total = 0.0;
        for (LineItem li : lineItems) {
            total += li.getSubtotal();
        }
        return total;
    }

    public boolean placeOrder(IPaymentMethod paymentMethod) {
        if (lineItems.isEmpty()) {
            System.out.println("Cannot place order: Order must contain at least one item.");
            return false;
        }

        double total = calculateTotal();
        boolean paymentSuccess = paymentMethod.processPayment(total);

        if (paymentSuccess) {
            this.status = OrderStatus.PAID;
            System.out.println("Order placed successfully. Payment via " + paymentMethod.getMethodName() + " successful. Order status: " + status + ".");
            customer.notify("Order #" + orderId + " placed and paid.");
            return true;
        } else {
            this.status = OrderStatus.PENDING_PAYMENT;
            System.out.println("Order placed. Payment via " + paymentMethod.getMethodName() + " failed. Order status: " + status + ".");
            customer.notify("Order #" + orderId + " placed, awaiting payment.");
            return false;
        }
    }
}

public class FoodOrderSystem {
    public static void main(String[] args) {
        Customer customer = new Customer("C1", "Sam");

        FoodItem pizza = new FoodItem("Pizza", 12.0);
        FoodItem soda = new FoodItem("Soda", 3.0);
        FoodItem burger = new FoodItem("Burger", 8.0);

        // 1. Customer creates an order, adds 'Pizza (Qty 2)' and 'Soda (Qty 1)'
        Order order1 = new Order("123", customer);
        order1.addItem(pizza, 2);
        order1.addItem(soda, 1);
        System.out.println("Order created. Added Pizza (Qty 2), Soda (Qty 1).");

        // 2. Customer attempts to place the order with an empty cart (should fail)
        Order emptyOrder = new Order("120", customer);
        emptyOrder.placeOrder(new CreditCardPayment(true));

        // 3. Customer places order1, attempts payment via Credit Card (simulate success)
        order1.placeOrder(new CreditCardPayment(true));

        // 4. Customer creates another order, adds 'Burger (Qty 1)', attempts payment via Digital Wallet (simulate failure)
        Order order2 = new Order("124", customer);
        order2.addItem(burger, 1);
        order2.placeOrder(new DigitalWalletPayment(false));
    }
}
