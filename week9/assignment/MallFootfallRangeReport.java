import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public long queryRange(int start, int end) {
        if (start < 0 || end >= size || start > end) {
            throw new IllegalArgumentException("Invalid range: [" + start + ", " + end + "] for array of length " + size);
        }
        return prefixSums[end + 1] - prefixSums[start];
    }

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

    public static List<Long> getFootfallReport(int[] visitors, List<RangeQuery> queries) {
        MallFootfallRangeReport reporter = new MallFootfallRangeReport(visitors);
        return reporter.processQueries(queries);
    }

    public static void main(String[] args) {
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};
        List<RangeQuery> queries = Arrays.asList(
            new RangeQuery(0, 2),
            new RangeQuery(2, 5),
            new RangeQuery(4, 6),
            new RangeQuery(3, 3)
        );

        MallFootfallRangeReport reporter = new MallFootfallRangeReport(visitors);
        List<Long> results = reporter.processQueries(queries);

        System.out.println("Visitors: " + Arrays.toString(visitors));
        System.out.println("Queries: " + queries);
        System.out.println("Expected Output: [22, 31, 27, 9]");
        System.out.println("Actual Output:   " + results);
    }
}
