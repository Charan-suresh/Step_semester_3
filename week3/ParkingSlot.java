public class ParkingSlot {
    private String slotNo;
    private int capacity;
    private int occupiedCount;

    public ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    public boolean isAvailable() {
        return occupiedCount < capacity;
    }

    public boolean allot(String vehicleNo) {
        if (isAvailable()) {
            occupiedCount++;
            System.out.println(vehicleNo + " allotted to slot " + slotNo);
            return true;
        }
        return false;
    }

    public String getSlotNo() {
        return slotNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupiedCount() {
        return occupiedCount;
    }

    public static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        if (slots == null) {
            return null;
        }
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.isAvailable()) {
                return slot;
            }
        }
        return null;
    }

    public static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot freeSlot = findAvailableSlot(slots);
        if (freeSlot != null) {
            freeSlot.allot(vehicleNo);
        } else {
            System.out.println("No slots available for " + vehicleNo);
        }
    }

    public static void main(String[] args) {
        ParkingSlot[] slots1 = new ParkingSlot[] {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slots1, "TN09AB1234");

        ParkingSlot[] slots2 = new ParkingSlot[] {
            new ParkingSlot("A1", 4, 4),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slots2, "TN09AB1234");
    }
}
