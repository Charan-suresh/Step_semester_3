import java.util.*;

enum ParcelStatus {
    BOOKED,
    PICKED_UP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}

interface ShippingType {
    String getTypeName();
    double calculateCharge(double weightKg);
}

class StandardShipping implements ShippingType {
    @Override
    public String getTypeName() {
        return "Standard";
    }

    @Override
    public double calculateCharge(double weightKg) {
        return 40.0 + (10.0 * weightKg);
    }
}

class ExpressShipping implements ShippingType {
    @Override
    public String getTypeName() {
        return "Express";
    }

    @Override
    public double calculateCharge(double weightKg) {
        return 80.0 + (15.0 * weightKg);
    }
}

class FragileShipping implements ShippingType {
    private final StandardShipping standard = new StandardShipping();

    @Override
    public String getTypeName() {
        return "Fragile";
    }

    @Override
    public double calculateCharge(double weightKg) {
        return standard.calculateCharge(weightKg) + 50.0;
    }
}

interface NotificationChannel {
    void sendNotification(String parcelId, ParcelStatus status);
}

class SmsChannel implements NotificationChannel {
    @Override
    public void sendNotification(String parcelId, ParcelStatus status) {
        System.out.println("[SMS] " + parcelId + " is now " + status + ".");
    }
}

class EmailChannel implements NotificationChannel {
    @Override
    public void sendNotification(String parcelId, ParcelStatus status) {
        System.out.println("[Email] " + parcelId + " is now " + status + ".");
    }
}

class Parcel {
    private final String parcelId;
    private final double weightKg;
    private final ShippingType shippingType;
    private final double charge;
    private final List<NotificationChannel> channels;
    private ParcelStatus status;

    public Parcel(String parcelId, double weightKg, ShippingType shippingType, List<NotificationChannel> channels) {
        this.parcelId = parcelId;
        this.weightKg = weightKg;
        this.shippingType = shippingType;
        this.charge = shippingType.calculateCharge(weightKg);
        this.channels = new ArrayList<>(channels != null ? channels : Collections.emptyList());
        this.status = ParcelStatus.BOOKED;

        System.out.printf("Parcel %s booked (%s, %.0f kg). Charge: ₹%.2f.%n",
                parcelId, shippingType.getTypeName(), weightKg, charge);
        notifyChannels();
    }

    public String getParcelId() {
        return parcelId;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public double getCharge() {
        return charge;
    }

    private void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.sendNotification(parcelId, status);
        }
    }

    public boolean updateStatus(ParcelStatus newStatus) {
        if (!isValidTransition(this.status, newStatus)) {
            System.out.println("Invalid transition: " + this.status + " → " + newStatus + " is not allowed.");
            return false;
        }
        this.status = newStatus;
        notifyChannels();
        return true;
    }

    public boolean cancel() {
        if (this.status != ParcelStatus.BOOKED) {
            System.out.println("Cancellation failed: " + parcelId + " can be cancelled only while BOOKED.");
            return false;
        }
        this.status = ParcelStatus.CANCELLED;
        System.out.println("Parcel " + parcelId + " cancelled.");
        notifyChannels();
        return true;
    }

    private boolean isValidTransition(ParcelStatus current, ParcelStatus next) {
        switch (current) {
            case BOOKED:
                return next == ParcelStatus.PICKED_UP || next == ParcelStatus.CANCELLED;
            case PICKED_UP:
                return next == ParcelStatus.IN_TRANSIT;
            case IN_TRANSIT:
                return next == ParcelStatus.OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY:
                return next == ParcelStatus.DELIVERED;
            default:
                return false;
        }
    }
}

public class SwiftShipParcelTracker {
    public static void main(String[] args) {
        List<NotificationChannel> channels = Arrays.asList(new SmsChannel(), new EmailChannel());

        // 1. Customer books Express parcel 'P101' weighing 2 kg and subscribes to SMS and Email updates
        Parcel p101 = new Parcel("P101", 2.0, new ExpressShipping(), channels);

        // 2. Courier marks 'P101' as picked up
        p101.updateStatus(ParcelStatus.PICKED_UP);

        // 3. Customer attempts to cancel 'P101'
        p101.cancel();

        // 4. Courier marks 'P101' as in transit
        p101.updateStatus(ParcelStatus.IN_TRANSIT);

        // 5. Courier attempts to mark 'P101' as delivered (skipping OUT_FOR_DELIVERY)
        p101.updateStatus(ParcelStatus.DELIVERED);
    }
}
