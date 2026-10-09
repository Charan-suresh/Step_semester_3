import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Assignment Problem 3: Net Balance Period Counter
 *
 * TASK:
 * A financial audit requires counting how many contiguous periods (subarrays)
 * in an account ledger have a net balance change equal to a target amount k.
 * The ledger contains both positive and negative transaction values.
 *
 * COMPLEXITY:
 * - Time Complexity: O(n)
 *   A single linear pass processes all n transactions. Hash map lookups and inserts
 *   operate in O(1) average time.
 * - Auxiliary Space Complexity: O(n)
 *   In the worst case (all prefix sums distinct), the hash map stores up to n + 1 entries.
 *
 * WHY SLIDING WINDOW FAILS WITH NEGATIVE NUMBERS:
 * - The two-pointer sliding window technique requires monotonicity: expanding right must
 *   increase (or maintain) the sum, while shrinking left must decrease it.
 * - When negative numbers are present, expanding right can decrease the sum, and shrinking left
 *   can increase the sum. Thus, the window condition cannot be greedily maintained.
 * - By using the mathematical identity:
 *     sum(i...j) = prefixSum[j] - prefixSum[i - 1] = k
 *     => prefixSum[i - 1] = prefixSum[j] - k
 *   a hash map of prefix sum frequencies provides an optimal O(n) solution.
 */
public class NetBalancePeriodCounter {

    /**
     * Counts contiguous subarrays whose sum equals target k.
     *
     * @param transactions array of net balance changes (can be positive, zero, negative)
     * @param k            target reconciliation balance
     * @return count of contiguous periods summing exactly to k
     */
    public static int countPeriodsWithNetBalance(int[] transactions, long k) {
        if (transactions == null || transactions.length == 0) {
            return 0;
        }

        Map<Long, Integer> prefixSumCounts = new HashMap<>();
        // Base case: prefix sum 0 has occurred once before processing any elements
        prefixSumCounts.put(0L, 1);

        long runningSum = 0;
        int count = 0;

        for (int transaction : transactions) {
            runningSum += transaction;

            long requiredPrefix = runningSum - k;
            if (prefixSumCounts.containsKey(requiredPrefix)) {
                count += prefixSumCounts.get(requiredPrefix);
            }

            prefixSumCounts.put(runningSum, prefixSumCounts.getOrDefault(runningSum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        // Sample 1: transactions = [3, 4, -7, 1, 3, 3, 1, -4], k = 7
        int[] transactions1 = {3, 4, -7, 1, 3, 3, 1, -4};
        long k1 = 7;
        int result1 = countPeriodsWithNetBalance(transactions1, k1);
        System.out.println("Sample 1 Transactions: " + Arrays.toString(transactions1) + ", Target k: " + k1);
        System.out.println("Expected Output: 4");
        System.out.println("Actual Output:   " + result1);

        // Sample 2: transactions = [1, 2, 3], k = 10
        int[] transactions2 = {1, 2, 3};
        long k2 = 10;
        int result2 = countPeriodsWithNetBalance(transactions2, k2);
        System.out.println("\nSample 2 Transactions: " + Arrays.toString(transactions2) + ", Target k: " + k2);
        System.out.println("Expected Output: 0");
        System.out.println("Actual Output:   " + result2);

        // Additional Test: All zeros, k = 0
        int[] transactions3 = {0, 0, 0};
        long k3 = 0;
        int result3 = countPeriodsWithNetBalance(transactions3, k3);
        System.out.println("\nSample 3 (Zeros) Transactions: " + Arrays.toString(transactions3) + ", Target k: " + k3);
        System.out.println("Expected Output: 6");
        System.out.println("Actual Output:   " + result3);
    }
}
