interface Renewable {
    String renew();
}

interface Reservable {
    String reserve();
}

abstract class LibraryItem {
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
}

class Textbook extends LibraryItem implements Renewable, Reservable {

    public Textbook(String title) {
        super(title);
    }

    @Override
    public int getLoanPeriodDays() {
        return 14;
    }

    @Override
    public String renew() {
        return getTitle() + " renewed";
    }

    @Override
    public String reserve() {
        return getTitle() + " reserved";
    }
}

class Magazine extends LibraryItem implements Renewable {

    public Magazine(String title) {
        super(title);
    }

    @Override
    public int getLoanPeriodDays() {
        return 7;
    }

    @Override
    public String renew() {
        return getTitle() + " renewed";
    }
}

class DigitalPass implements Renewable {
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

public class CommunityLibraryCheckoutSystem {
    public static void processCheckouts(LibraryItem[] items) {
        LibraryItem.processCheckouts(items);
    }

    public static String reserveIfSupported(Object o) {
        return LibraryItem.reserveIfSupported(o);
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
