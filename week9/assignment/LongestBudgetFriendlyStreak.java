import java.util.Arrays;

/**
 * Assignment Problem 2: Longest Budget-Friendly Streak
 *
 * TASK:
 * Given an array of daily non-negative operational costs and a maximum budget,
 * find the longest contiguous streak of days whose total cost does not exceed the budget.
 * Returns the pair (length, startIndex).
 * If multiple streaks achieve the maximum length, return the first one encountered
 * (smallest start index). If no valid streak of length >= 1 exists, return (0, -1).
 *
 * COMPLEXITY:
 * - Time Complexity: O(n)
 *   Both pointers (left and right) only move forward from 0 to n - 1.
 *   Each element enters the window once and leaves the window at most once.
 * - Auxiliary Space Complexity: O(1)
 *   Only a few primitive tracking variables (left, right, windowSum, maxLen, bestStart) are used.
 *
 * WHY SLIDING WINDOW WORKS:
 * - Since all daily costs are non-negative, the window sum is monotonic:
 *   expanding the window to the right never decreases the sum, and shrinking from
 *   the left never increases the sum. This guarantees the two-pointer invariant.
 */
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

    /**
     * Finds the longest contiguous streak of days with total cost <= budget.
     *
     * @param costs  array of non-negative daily costs
     * @param budget maximum allowable cumulative cost
     * @return StreakResult representing (length, startIndex) or (0, -1) if none
     */
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

            // Shrink window from the left until sum is within budget
            while (windowSum > budget && left <= right) {
                windowSum -= costs[left];
                left++;
            }

            // Valid window found
            if (windowSum <= budget && left <= right) {
                int currentLen = right - left + 1;
                // Strict greater-than ensures the first encounter of maximum length is preserved
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
        // Sample 1: costs = [4, 2, 1, 7, 3, 1, 2, 1, 5], budget = 8
        int[] costs1 = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        long budget1 = 8;
        StreakResult res1 = findLongestStreak(costs1, budget1);
        System.out.println("Sample 1 Costs: " + Arrays.toString(costs1) + ", Budget: " + budget1);
        System.out.println("Expected Output: (4, 4)");
        System.out.println("Actual Output:   " + res1);

        // Sample 2: costs = [9, 10], budget = 8
        int[] costs2 = {9, 10};
        long budget2 = 8;
        StreakResult res2 = findLongestStreak(costs2, budget2);
        System.out.println("\nSample 2 Costs: " + Arrays.toString(costs2) + ", Budget: " + budget2);
        System.out.println("Expected Output: (0, -1)");
        System.out.println("Actual Output:   " + res2);

        // Edge Case: Tie-breaker keeps earliest start index
        int[] costs3 = {2, 2, 5, 2, 2};
        long budget3 = 4;
        StreakResult res3 = findLongestStreak(costs3, budget3);
        System.out.println("\nSample 3 (Tie breaker) Costs: " + Arrays.toString(costs3) + ", Budget: " + budget3);
        System.out.println("Expected Output: (2, 0)");
        System.out.println("Actual Output:   " + res3);
    }
}
