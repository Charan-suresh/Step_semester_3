import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Assignment Problem 1: Mall Footfall Range Report
 *
 * TASK:
 * A mall management team tracks hourly visitor footfall. Given an array of hourly visitor
 * counts, answer multiple range queries (start, end) inclusive without re-computing the sum
 * from scratch for every query.
 *
 * COMPLEXITY:
 * - Preprocessing Time Complexity: O(n)
 *   Building the 1D prefix sum array takes a single linear pass over the n visitor counts.
 * - Query Time Complexity: O(1) per query
 *   Each range query [start, end] is answered in constant time via:
 *   prefix[end + 1] - prefix[start]
 * - Total Time Complexity for q queries: O(n + q)
 * - Auxiliary Space Complexity: O(n)
 *   Requires an array of size n + 1 to store cumulative prefix sums.
 *
 * COMPARISON TO NAIVE APPROACH:
 * - Naive approach loops from start to end for each query, taking O(end - start + 1) = O(n)
 *   time per query, leading to O(q * n) total runtime.
 * - Prefix sums reduce query time from O(n) to O(1), saving enormous compute when q is large.
 */
public class MallFootfallRangeReport {

    public static class RangeQuery {
        private final int start;
        private final int end;

        public RangeQuery(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        @Override
        public String toString() {
            return "(" + start + ", " + end + ")";
        }
    }

    private final long[] prefixSums;
    private final int size;

    /**
     * Constructs the range report helper by precomputing prefix sums.
     *
     * @param visitors array of hourly visitor counts
     */
    public MallFootfallRangeReport(int[] visitors) {
        if (visitors == null) {
            this.size = 0;
            this.prefixSums = new long[1];
        } else {
            this.size = visitors.length;
            this.prefixSums = new long[this.size + 1];
            for (int i = 0; i < this.size; i++) {
                this.prefixSums[i + 1] = this.prefixSums[i] + visitors[i];
            }
        }
    }

    /**
     * Computes the total visitor count in the range [start, end] inclusive.
     *
     * @param start start index (inclusive)
     * @param end   end index (inclusive)
     * @return sum of visitors between start and end
     * @throws IndexOutOfBoundsException if start or end are out of valid bounds
     */
    public long queryRange(int start, int end) {
        if (start < 0 || end >= size || start > end) {
            throw new IllegalArgumentException("Invalid range: [" + start + ", " + end + "] for array of length " + size);
        }
        return prefixSums[end + 1] - prefixSums[start];
    }

    /**
     * Processes a batch of range queries.
     *
     * @param queries list of range queries
     * @return list of results corresponding to each query
     */
    public List<Long> processQueries(List<RangeQuery> queries) {
        List<Long> results = new ArrayList<>();
        if (queries == null) {
            return results;
        }
        for (RangeQuery q : queries) {
            results.add(queryRange(q.getStart(), q.getEnd()));
        }
        return results;
    }

    /**
     * Static utility function to answer queries given visitor array and queries directly.
     */
    public static List<Long> getFootfallReport(int[] visitors, List<RangeQuery> queries) {
        MallFootfallRangeReport reporter = new MallFootfallRangeReport(visitors);
        return reporter.processQueries(queries);
    }

    public static void main(String[] args) {
        // Sample Data: visitors = [12, 7, 3, 9, 15, 4, 8]
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};
        List<RangeQuery> queries = Arrays.asList(
            new RangeQuery(0, 2), // 12 + 7 + 3 = 22
            new RangeQuery(2, 5), // 3 + 9 + 15 + 4 = 31
            new RangeQuery(4, 6), // 15 + 4 + 8 = 27
            new RangeQuery(3, 3)  // 9 = 9
        );

        MallFootfallRangeReport reporter = new MallFootfallRangeReport(visitors);
        List<Long> results = reporter.processQueries(queries);

        System.out.println("Visitors: " + Arrays.toString(visitors));
        System.out.println("Queries: " + queries);
        System.out.println("Expected Output: [22, 31, 27, 9]");
        System.out.println("Actual Output:   " + results);
    }
}
