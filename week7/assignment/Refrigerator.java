public class Refrigerator extends HomeDevice implements EnergyTrackable {
    private final double consumptionWatts;

    public Refrigerator(double consumptionWatts) {
        super();
        if (consumptionWatts <= 0) {
            throw new IllegalArgumentException("Consumption watts must be positive");
        }
        this.consumptionWatts = consumptionWatts;
    }

    @Override
    public String activate() {
        return "Refrigerator " + getSerialNumber() + " cooling activated";
    }

    @Override
    public double getConsumptionWatts() {
        return consumptionWatts;
    }
}
