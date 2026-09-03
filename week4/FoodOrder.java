public class FoodOrder {
    private String studentName;
    private String dishName;
    private boolean delivered;

    public FoodOrder(String studentName, String dishName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid student name");
        }
        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid dish name");
        }
        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.delivered = false;
    }

    public void markDelivered() {
        if (delivered) {
            System.out.println("Warning: Order for " + studentName + " has already been delivered!");
        } else {
            delivered = true;
            System.out.println("Order for " + studentName + " (" + dishName + ") marked delivered.");
        }
    }

    public static void processBatch(String[][] rawOrders) {
        int accepted = 0;
        int rejected = 0;

        for (String[] order : rawOrders) {
            if (order == null || order.length < 2) {
                rejected++;
                continue;
            }
            try {
                new FoodOrder(order[0], order[1]);
                accepted++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + accepted + " | Rejected: " + rejected);
    }

    public static void main(String[] args) {
        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };
        processBatch(rawOrders);

        FoodOrder order = new FoodOrder("Ravi", "Paneer Butter Masala");
        order.markDelivered();
        order.markDelivered();
    }
}
