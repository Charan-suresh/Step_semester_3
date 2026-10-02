import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

enum RoomCategory {
    STANDARD(150.00),
    DELUXE(200.00),
    SUITE(300.00);

    private final double ratePerNight;

    RoomCategory(double ratePerNight) {
        this.ratePerNight = ratePerNight;
    }

    public double getRatePerNight() {
        return ratePerNight;
    }
}

class Customer {
    private final String id;
    private final String name;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Reservation {
    private final String reservationId;
    private final Customer customer;
    private final Room room;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final double totalPrice;
    private final LocalDate cancellationDeadline;
    private boolean isCancelled;

    public Reservation(String reservationId, Customer customer, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        this.totalPrice = nights * room.getCategory().getRatePerNight();
        // Deadline is 24 hours before check-in date
        this.cancellationDeadline = checkIn.minusDays(1);
        this.isCancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public boolean overlapsWith(LocalDate otherCheckIn, LocalDate otherCheckOut) {
        if (isCancelled) {
            return false;
        }
        return !(checkOut.isEqual(otherCheckIn) || checkOut.isBefore(otherCheckIn) ||
                 checkIn.isEqual(otherCheckOut) || checkIn.isAfter(otherCheckOut));
    }

    public boolean cancel(LocalDate requestDate) {
        if (isCancelled) {
            System.out.println("Reservation already cancelled.");
            return false;
        }
        if (requestDate.isAfter(cancellationDeadline)) {
            System.out.println("Cancellation failed: Deadline has passed for " + room.getName() + ".");
            return false;
        }
        this.isCancelled = true;
        System.out.println("Reservation for " + room.getName() + " cancelled successfully.");
        return true;
    }
}

class Room {
    private final String roomNumber;
    private final String name;
    private final RoomCategory category;
    private final List<Reservation> reservations;

    public Room(String roomNumber, String name, RoomCategory category) {
        this.roomNumber = roomNumber;
        this.name = name;
        this.category = category;
        this.reservations = new ArrayList<>();
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getName() {
        return name;
    }

    public RoomCategory getCategory() {
        return category;
    }

    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        for (Reservation r : reservations) {
            if (r.overlapsWith(checkIn, checkOut)) {
                return false;
            }
        }
        return true;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}

class BookingManager {
    private int counter = 1;

    public Reservation bookRoom(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut) {
        if (!room.isAvailable(checkIn, checkOut)) {
            System.out.println("Booking failed: " + room.getName() + " is not available for " + checkIn + " to " + checkOut + ".");
            return null;
        }
        Reservation reservation = new Reservation("RES-" + (counter++), customer, room, checkIn, checkOut);
        room.addReservation(reservation);
        System.out.printf("%s booked from %s to %s. Total price: $%.2f%n",
                room.getName(), checkIn, checkOut, reservation.getTotalPrice());
        return reservation;
    }

    public void cancelReservation(Reservation reservation, LocalDate currentDate) {
        if (reservation != null) {
            reservation.cancel(currentDate);
        }
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();
        Customer customer = new Customer("C101", "John");

        Room deluxe101 = new Room("101", "Deluxe Room 101", RoomCategory.DELUXE);
        Room standard205 = new Room("205", "Standard Room 205", RoomCategory.STANDARD);

        Reservation res1 = manager.bookRoom(customer, deluxe101, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 5));
        Reservation res2 = manager.bookRoom(customer, standard205, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));

        // Overlapping attempt
        manager.bookRoom(customer, deluxe101, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));

        // Cancel before deadline
        manager.cancelReservation(res1, LocalDate.of(2024, 11, 25));
    }
}
