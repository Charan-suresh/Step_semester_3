import java.util.Arrays;
import java.util.List;

public class LibraryCatalogLookupDetailed {

    public static class Entry {
        public final String isbn;
        public final String title;

        public Entry(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }
    }

    public static String findBook(List<Entry> catalog, String targetIsbn) {
        if (catalog == null || targetIsbn == null || catalog.isEmpty()) {
            return "Not Found";
        }

        int left = 0;
        int right = catalog.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Entry midEntry = catalog.get(mid);
            int cmp = midEntry.isbn.compareTo(targetIsbn);

            if (cmp == 0) {
                return midEntry.title;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return "Not Found";
    }

    public static void main(String[] args) {
        List<Entry> catalog = Arrays.asList(
            new Entry("0001112223", "Introduction to Algebra"),
            new Entry("0002223334", "Beginning Python"),
            new Entry("0003334445", "Classic Mythology"),
            new Entry("0004445556", "Data and Society"),
            new Entry("0005556667", "European History")
        );

        String query1 = "0003334445";
        System.out.println("Example 1 Output: " + findBook(catalog, query1));

        String query2 = "0009998887";
        System.out.println("Example 2 Output: " + findBook(catalog, query2));
    }
}
