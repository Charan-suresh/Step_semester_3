public class Trap implements Defendable {
    private final String trapType;

    public Trap(String trapType) {
        if (trapType == null || trapType.trim().isEmpty()) {
            throw new IllegalArgumentException("Trap type cannot be blank");
        }
        this.trapType = trapType.trim();
    }

    public String getTrapType() {
        return trapType;
    }

    @Override
    public String defend() {
        return trapType + " triggers automatically";
    }
}
