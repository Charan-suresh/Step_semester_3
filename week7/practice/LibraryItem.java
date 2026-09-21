public abstract class LibraryItem {
    private static int counter = 1000;
    private final String itemId;
    private final String title;

    public LibraryItem(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        counter++;
        this.itemId = "LIB-" + counter;
        this.title = title.trim();
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public abstract int getLoanPeriodDays();

    public static void processCheckouts(LibraryItem[] items) {
        if (items == null) {
            return;
        }
        for (LibraryItem item : items) {
            if (item != null) {
                System.out.println(item.getTitle() + " (ID: " + item.getItemId() + ") - Loan Period: " + item.getLoanPeriodDays() + " days");
            }
        }
    }

    public static String reserveIfSupported(Object o) {
        if (o instanceof Reservable) {
            Reservable r = (Reservable) o;
            return r.reserve();
        }
        return "Reservation not supported";
    }

    public static void main(String[] args) {
        Textbook t = new Textbook("Java Fundamentals");
        System.out.println(t.getLoanPeriodDays());
        System.out.println(t.renew());
        System.out.println(t.reserve());

        Magazine m = new Magazine("Tech Monthly");
        System.out.println(reserveIfSupported(m));

        DigitalPass d = new DigitalPass("E-Journal Access");
        System.out.println(reserveIfSupported(d));

        // upcasting: Textbook stored as its parent type
        LibraryItem ref = t;
        System.out.println(reserveIfSupported(ref));

        processCheckouts(new LibraryItem[]{t, m});
    }
}
