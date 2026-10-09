import java.util.*;

/**
 * Problem 4: Library Catalog Lookup (Detailed Specification)
 *
 * TASK:
 * A library maintains its catalog as a list of book records, where each record
 * contains a unique ISBN (a numeric string) and a title. The catalog is pre-sorted
 * by ISBN in ascending order.
 *
 * INPUT / OUTPUT:
 * - Input: catalog — list of (ISBN, Title) records pre-sorted by ISBN; targetIsbn — string to search.
 * - Output: The title string if found, or "Not Found".
 *
 * CONSTRAINTS:
 * - The catalog is sorted by ISBN in ascending order.
 * - Queries are far more frequent than updates.
 *
 * COMPLEXITY:
 * - Time Complexity: O(log n)
 *   Binary search repeatedly bisects the range. With n records, at most ceil(log2(n))
 *   comparisons are executed.
 * - Space Complexity: O(1)
 *   In-place binary search requiring no auxiliary buffers.
 */
public class LibraryCatalogLookupDetailed {

    public static class Entry {
        public final String isbn;
        public final String title;

        public Entry(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }
    }

    /**
     * Binary search lookup enforcing exact constraints.
     *
     * @param catalog    pre-sorted list of catalog entries
     * @param targetIsbn target ISBN to search
     * @return Title if found, else "Not Found"
     */
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

        // Example 1
        String query1 = "0003334445";
        System.out.println("Example 1 Output: " + findBook(catalog, query1));
        // Expected: Classic Mythology

        // Example 2
        String query2 = "0009998887";
        System.out.println("Example 2 Output: " + findBook(catalog, query2));
        // Expected: Not Found
    }
}
