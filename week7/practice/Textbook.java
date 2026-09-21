public class Textbook extends LibraryItem implements Renewable, Reservable {

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
