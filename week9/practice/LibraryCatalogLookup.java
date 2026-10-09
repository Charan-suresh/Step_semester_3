import java.util.Arrays;
import java.util.List;

public class LibraryCatalogLookup {

    public static class BookRecord {
        private final String isbn;
        private final String title;

        public BookRecord(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getTitle() {
            return title;
        }
    }

    public static String findBook(List<BookRecord> catalog, String targetIsbn) {
        if (catalog == null || targetIsbn == null || catalog.isEmpty()) {
            return "Not Found";
        }

        int low = 0;
        int high = catalog.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            BookRecord current = catalog.get(mid);
            int cmp = current.getIsbn().compareTo(targetIsbn);

            if (cmp == 0) {
                return current.getTitle();
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return "Not Found";
    }

    public static void main(String[] args) {
        List<BookRecord> catalog = Arrays.asList(
            new BookRecord("0001112223", "Introduction to Algebra"),
            new BookRecord("0002223334", "Beginning Python"),
            new BookRecord("0003334445", "Classic Mythology"),
            new BookRecord("0004445556", "Data and Society"),
            new BookRecord("0005556667", "European History")
        );

        String query1 = "0003334445";
        System.out.println("Query '" + query1 + "' Output: " + findBook(catalog, query1));

        String query2 = "0009998887";
        System.out.println("Query '" + query2 + "' Output: " + findBook(catalog, query2));
    }
}
