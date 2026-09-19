public class WorkshopTicket extends EventTicket {
    private String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public WorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    public WorkshopTicket(double basePrice) {
        this(basePrice, "General");
    }

    public String getTrack() {
        return track;
    }

    @Override
    public void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }

    @Override
    public String printTicket() {
        return "Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue();
    }
}
