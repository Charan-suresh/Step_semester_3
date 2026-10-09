/**
 * Problem 5: Maximum Sum Subarray of Fixed Size K
 *
 * TASK:
 * Identify the highest total sales recorded across any k consecutive days
 * within a given sales record.
 *
 * COMPLEXITY REQUIREMENTS:
 * - Naive Approach:
 *   Recomputing the sum of each window of size k from scratch requires O(k)
 *   work per window across (n - k + 1) windows, resulting in O(n * k) time.
 * - Optimized Sliding Window Approach:
 *   - Time Complexity: O(n)
 *     Compute the initial sum of the first k elements in O(k).
 *     Slide the window across the remaining (n - k) elements in O(1) step each
 *     by adding the incoming element and subtracting the outgoing element.
 *   - Auxiliary Space Complexity: O(1)
 *     Only variables tracking windowSum and maxSum are retained.
 */
public class MaxSumSubarray {

    /**
     * Finds the maximum sum of any contiguous subarray of fixed size k.
     *
     * @param sales array of daily sales numbers
     * @param k     fixed window size (1 <= k <= sales.length)
     * @return maximum sum found
     */
    public static long maxSumSubarray(int[] sales, int k) {
        if (sales == null || k <= 0 || sales.length < k) {
            throw new IllegalArgumentException("Invalid input array or window size k.");
        }

        // Compute the sum of the first window of size k
        long windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += sales[i];
        }

        long maxSum = windowSum;

        // Slide the window across the rest of the array
        for (int i = k; i < sales.length; i++) {
            windowSum += sales[i] - sales[i - k]; // Add new element, remove oldest
            if (windowSum > maxSum) {
                maxSum = windowSum;
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] sales = {2, 1, 5, 1, 3, 2};
        int k = 3;

        long result = maxSumSubarray(sales, k);
        System.out.println("Maximum Sum Subarray (k = 3): " + result);
        // Expected: 9 (from subarray [5, 1, 3])
    }
}
