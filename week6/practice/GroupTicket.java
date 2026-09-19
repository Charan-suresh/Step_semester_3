public class GroupTicket extends EventTicket {
    private int groupSize;

    public GroupTicket(double basePrice, int groupSize) {
        super(basePrice);
        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }
}
