public abstract class PaymentMethod {
    private static int counter = 1000;
    private final String transactionId;

    public PaymentMethod() {
        counter++;
        this.transactionId = "TXN-" + counter;
    }

    public abstract String processPayment(double amount);

    public String processPayment(double amount, String note) {
        return processPayment(amount) + " (" + note + ")";
    }

    public String getTransactionId() {
        return transactionId;
    }

    public static void printConfirmation(PaymentMethod payment, double amount) {
        if (payment != null) {
            System.out.println(payment.processPayment(amount));
        }
    }

    public static void main(String[] args) {
        CreditCardPayment cc = new CreditCardPayment("4471");
        System.out.println(cc.processPayment(250.0));

        CashPayment cash = new CashPayment();
        System.out.println(cash.processPayment(40.0));

        System.out.println(cc.processPayment(250.0, "Birthday gift"));

        // upcasting: a CreditCardPayment reference stored as its parent type
        PaymentMethod ref = cc;
        printConfirmation(ref, 250.0);
    }
}
