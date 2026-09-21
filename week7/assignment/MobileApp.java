public class MobileApp implements RemoteControllable {
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
