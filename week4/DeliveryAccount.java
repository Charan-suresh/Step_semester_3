public class DeliveryAccount {
    private static double minimumSurgePercent;

    static {
        minimumSurgePercent = 1.0;
    }

    private String studentId;
    private double orderValue;

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public final double calculateSurgeFee(int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Negative values not allowed");
        }
        if (delayMinutes == 0) {
            return 0.0;
        }

        double fee = 0.0;
        int m1 = Math.min(delayMinutes, 5);
        fee += m1 * 0.005 * orderValue;

        if (delayMinutes > 5) {
            int m2 = Math.min(delayMinutes - 5, 10);
            fee += m2 * 0.01 * orderValue;
        }

        if (delayMinutes > 15) {
            int m3 = delayMinutes - 15;
            fee += m3 * 0.02 * orderValue;
        }

        double floorFee = (minimumSurgePercent / 100.0) * orderValue;
        return Math.max(fee, floorFee);
    }

    public void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
        if (account == null) {
            return;
        }
        account.orderValue = amount;
    }

    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        if (accounts == null || amounts == null || delayMinutesArray == null) {
            System.out.println("Invalid batch input");
            return;
        }

        int n = Math.min(accounts.length, Math.min(amounts.length, delayMinutesArray.length));
        int processedCount = 0;
        int nullSkippedCount = accounts.length - n;
        int premiumCount = 0;
        int regularCount = 0;
        double grandTotal = 0.0;

        for (int i = 0; i < n; i++) {
            DeliveryAccount acc = accounts[i];
            if (acc == null) {
                nullSkippedCount++;
                continue;
            }

            acc.orderValue = amounts[i];
            double fee = acc.calculateSurgeFee(delayMinutesArray[i]);

            if (acc instanceof PremiumDeliveryAccount) {
                premiumCount++;
                fee = fee * 0.5;
            } else {
                regularCount++;
            }

            grandTotal += fee;
            processedCount++;
        }

        System.out.println(processedCount + " processed | " + nullSkippedCount + " null skipped | " +
                           premiumCount + " premium | " + regularCount + " regular | grand total surge fees = " + grandTotal);
    }

    public String getStudentId() {
        return studentId;
    }

    public double getOrderValue() {
        return orderValue;
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = new DeliveryAccount[] {
            new PremiumDeliveryAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};

        processBatch(accounts, amounts, delayMinutesArray);
    }
}
