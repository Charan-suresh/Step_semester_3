public class Magazine extends LibraryItem implements Renewable {

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
