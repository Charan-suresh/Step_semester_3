import java.util.*;

/**
 * Problem 3: Library Catalog Lookup
 *
 * TASK:
 * A library catalog is provided as a list of book records sorted by ISBN in ascending order.
 * Efficiently retrieve the book title given its ISBN, or return "Not Found".
 *
 * COMPLEXITY:
 * - Time Complexity: O(log n) per query
 *   Where n is the number of books in the catalog. Binary search divides the remaining
 *   search space in half at each step.
 * - Auxiliary Space Complexity: O(1)
 *   Only pointers (low, high, mid) are used during the search.
 *
 * JUSTIFICATION:
 * When queries vastly outnumber updates, keeping the catalog pre-sorted enables O(log n)
 * lookups without allocating extra memory for hash tables or secondary indices.
 * A linear scan would take O(n) per query, which degrades rapidly on large catalogs.
 */
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

    /**
     * Searches for a book by ISBN using binary search on a pre-sorted catalog.
     *
     * @param catalog    list of book records sorted ascending by ISBN
     * @param targetIsbn the ISBN string to look up
     * @return the book title if found, or "Not Found"
     */
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
                low = mid + 1; // target ISBN lies in the right half
            } else {
                high = mid - 1; // target ISBN lies in the left half
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

        // Sample 1
        String query1 = "0003334445";
        System.out.println("Query '" + query1 + "' Output: " + findBook(catalog, query1));
        // Expected: Classic Mythology

        // Sample 2
        String query2 = "0009998887";
        System.out.println("Query '" + query2 + "' Output: " + findBook(catalog, query2));
        // Expected: Not Found
    }
}
