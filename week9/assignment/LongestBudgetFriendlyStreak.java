import java.util.Arrays;

public class LongestBudgetFriendlyStreak {

    public static class StreakResult {
        private final int length;
        private final int startIndex;

        public StreakResult(int length, int startIndex) {
            this.length = length;
            this.startIndex = startIndex;
        }

        public int getLength() {
            return length;
        }

        public int getStartIndex() {
            return startIndex;
        }

        @Override
        public String toString() {
            return "(" + length + ", " + startIndex + ")";
        }
    }

    public static StreakResult findLongestStreak(int[] costs, long budget) {
        if (costs == null || costs.length == 0 || budget < 0) {
            return new StreakResult(0, -1);
        }

        int maxLen = 0;
        int bestStart = -1;
        long windowSum = 0;
        int left = 0;

        for (int right = 0; right < costs.length; right++) {
            windowSum += costs[right];

            while (windowSum > budget && left <= right) {
                windowSum -= costs[left];
                left++;
            }

            if (windowSum <= budget && left <= right) {
                int currentLen = right - left + 1;
                if (currentLen > maxLen) {
                    maxLen = currentLen;
                    bestStart = left;
                }
            }
        }

        if (maxLen == 0) {
            return new StreakResult(0, -1);
        }
        return new StreakResult(maxLen, bestStart);
    }

    public static void main(String[] args) {
        int[] costs1 = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        long budget1 = 8;
        StreakResult res1 = findLongestStreak(costs1, budget1);
        System.out.println("Sample 1 Costs: " + Arrays.toString(costs1) + ", Budget: " + budget1);
        System.out.println("Expected Output: (4, 4)");
        System.out.println("Actual Output:   " + res1);

        int[] costs2 = {9, 10};
        long budget2 = 8;
        StreakResult res2 = findLongestStreak(costs2, budget2);
        System.out.println("\nSample 2 Costs: " + Arrays.toString(costs2) + ", Budget: " + budget2);
        System.out.println("Expected Output: (0, -1)");
        System.out.println("Actual Output:   " + res2);

        int[] costs3 = {2, 2, 5, 2, 2};
        long budget3 = 4;
        StreakResult res3 = findLongestStreak(costs3, budget3);
        System.out.println("\nSample 3 (Tie breaker) Costs: " + Arrays.toString(costs3) + ", Budget: " + budget3);
        System.out.println("Expected Output: (2, 0)");
        System.out.println("Actual Output:   " + res3);
    }
}
