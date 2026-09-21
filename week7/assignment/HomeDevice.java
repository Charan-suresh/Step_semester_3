public abstract class HomeDevice {
    private static int counter = 1000;
    private final String serialNumber;

    public HomeDevice() {
        counter++;
        this.serialNumber = "HD-" + counter;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public abstract String activate();

    public static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable) {
            EnergyTrackable et = (EnergyTrackable) d;
            return et.getConsumptionWatts();
        }
        return 0.0;
    }

    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine(500.0);
        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));

        Refrigerator fridge = new Refrigerator(150.0);
        System.out.println(getConsumptionIfTrackable(fridge));

        MobileApp app = new MobileApp("HomeConnect App");
        System.out.println(app.connect("HomeConnect"));

        // upcasting: WashingMachine stored as its parent type
        HomeDevice ref = wm;
        System.out.println(getConsumptionIfTrackable(ref));

        RemoteControllable.connectAll(new RemoteControllable[]{wm, app}, "HomeConnect");
    }
}
