import java.util.*;

abstract class Vehicle {
    private final String id;
    private final String model;
    private boolean isAvailable;

    public Vehicle(String id, String model) {
        this.id = id;
        this.model = model;
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public abstract double calculateRentalCharge(int days);
}

class LuxuryCar extends Vehicle {
    private static final double DAILY_RATE = 100.00;

    public LuxuryCar(String id, String model) {
        super(id, model);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return days * DAILY_RATE;
    }
}

class StandardCar extends Vehicle {
    private static final double DAILY_RATE = 50.00;

    public StandardCar(String id, String model) {
        super(id, model);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return days * DAILY_RATE;
    }
}

class SUVCar extends Vehicle {
    private static final double DAILY_RATE = 80.00;

    public SUVCar(String id, String model) {
        super(id, model);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return days * DAILY_RATE;
    }
}

class Customer {
    private final String customerId;
    private final String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private final String rentalId;
    private final Customer customer;
    private final Vehicle vehicle;
    private final int days;
    private final double totalCharge;
    private boolean isActive;

    public Rental(String rentalId, Customer customer, Vehicle vehicle, int days) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        this.totalCharge = vehicle.calculateRentalCharge(days);
        this.isActive = true;
        vehicle.setAvailable(false);
    }

    public String getRentalId() {
        return rentalId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getDays() {
        return days;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public boolean isActive() {
        return isActive;
    }

    public void returnVehicle() {
        if (isActive) {
            isActive = false;
            vehicle.setAvailable(true);
            System.out.println(vehicle.getModel() + " returned. Now available.");
        } else {
            System.out.println(vehicle.getModel() + " was not actively rented.");
        }
    }
}

class RentalService {
    private int rentalCounter = 1;

    public Rental rentVehicle(Customer customer, Vehicle vehicle, int days) {
        if (!vehicle.isAvailable()) {
            System.out.println("Rental failed: " + vehicle.getModel() + " is currently not available.");
            return null;
        }
        Rental rental = new Rental("R-" + (rentalCounter++), customer, vehicle, days);
        System.out.printf("%s rented for %d days. Total charge: $%.2f%n",
                vehicle.getModel(), days, rental.getTotalCharge());
        return rental;
    }

    public void returnVehicle(Rental rental) {
        if (rental != null) {
            rental.returnVehicle();
        }
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        RentalService rentalService = new RentalService();
        Customer customer = new Customer("C1", "Alice");

        Vehicle luxuryCarA = new LuxuryCar("V1", "Luxury Car A");
        Vehicle standardCarB = new StandardCar("V2", "Standard Car B");

        Rental rental1 = rentalService.rentVehicle(customer, luxuryCarA, 3);
        Rental rental2 = rentalService.rentVehicle(customer, standardCarB, 5);

        rentalService.returnVehicle(rental1);
    }
}
