abstract class HomeDevice {
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
}

interface RemoteControllable {
    String connect(String appId);

    static void connectAll(RemoteControllable[] items, String appId) {
        if (items == null) {
            return;
        }
        for (RemoteControllable item : items) {
            if (item != null) {
                System.out.println(item.connect(appId));
            }
        }
    }
}

interface EnergyTrackable {
    double getConsumptionWatts();
}

class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
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

class Refrigerator extends HomeDevice implements EnergyTrackable {
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

class MobileApp implements RemoteControllable {
    private final String appName;

    public MobileApp(String appName) {
        if (appName == null || appName.trim().isEmpty()) {
            throw new IllegalArgumentException("App name cannot be blank");
        }
        this.appName = appName.trim();
    }

    public String getAppName() {
        return appName;
    }

    @Override
    public String connect(String appId) {
        if (appId == null || appId.trim().isEmpty()) {
            throw new IllegalArgumentException("App ID cannot be blank");
        }
        return appName + " connected to " + appId.trim();
    }
}

public class ConnectedHomeControlPanel {
    public static void connectAll(RemoteControllable[] items, String appId) {
        RemoteControllable.connectAll(items, appId);
    }

    public static double getConsumptionIfTrackable(HomeDevice d) {
        return HomeDevice.getConsumptionIfTrackable(d);
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

        connectAll(new RemoteControllable[]{wm, app}, "HomeConnect");
    }
}
