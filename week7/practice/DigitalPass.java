public class DigitalPass implements Renewable {
    private final String resourceName;

    public DigitalPass(String resourceName) {
        if (resourceName == null || resourceName.trim().isEmpty()) {
            throw new IllegalArgumentException("Resource name cannot be blank");
        }
        this.resourceName = resourceName.trim();
    }

    public String getResourceName() {
        return resourceName;
    }

    @Override
    public String renew() {
        return resourceName + " renewed";
    }
}
