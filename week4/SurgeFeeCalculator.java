public final class SurgeFeeCalculator {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
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

    public static void main(String[] args) {
        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0);
        System.out.println("Rs " + calc.calculateSurgeFee(500, 0));
        System.out.println("Rs " + calc.calculateSurgeFee(500, 1));
        System.out.println("Rs " + calc.calculateSurgeFee(500, 16));
    }
}
