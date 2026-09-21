public abstract class ServiceableVehicle {
    private double mileage;

    public ServiceableVehicle() {
        this.mileage = 0.0;
    }

    public abstract String performMaintenance();

    public double getMileage() {
        return mileage;
    }

    public void addMileage(double km) {
        if (km >= 0) {
            this.mileage += km;
        }
    }

    public static String getInsuranceIfApplicable(ServiceableVehicle v) {
        if (v instanceof Insurable) {
            Insurable insurable = (Insurable) v;
            return insurable.getInsuranceInfo();
        }
        return "No insurance record exists";
    }

    public static void main(String[] args) {
        Forklift f = new Forklift("FL-22");
        f.addMileage(120);
        System.out.println("Mileage: " + f.getMileage());

        f.addMileage(-50); // rejected, mileage unchanged
        System.out.println("Mileage after negative addition: " + f.getMileage());

        System.out.println(f.performMaintenance());

        HeavyDutyForklift hd = new HeavyDutyForklift("HD-9");
        System.out.println(hd.performMaintenance());

        System.out.println(getInsuranceIfApplicable(f));
        System.out.println(getInsuranceIfApplicable(hd));
    }
}
