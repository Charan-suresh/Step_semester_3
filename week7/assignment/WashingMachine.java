public class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
    private final double consumptionWatts;

    public WashingMachine(double consumptionWatts) {
        super();
        if (consumptionWatts <= 0) {
            throw new IllegalArgumentException("Consumption watts must be positive");
        }
        this.consumptionWatts = consumptionWatts;
    }

    @Override
    public String activate() {
        return "Washing machine " + getSerialNumber() + " started a cycle";
    }

    @Override
    public String connect(String appId) {
        if (appId == null || appId.trim().isEmpty()) {
            throw new IllegalArgumentException("App ID cannot be blank");
        }
        return getSerialNumber() + " connected to " + appId.trim();
    }

    @Override
    public double getConsumptionWatts() {
        return consumptionWatts;
    }
}
