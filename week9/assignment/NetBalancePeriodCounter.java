import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NetBalancePeriodCounter {

    public static int countPeriodsWithNetBalance(int[] transactions, long k) {
        if (transactions == null || transactions.length == 0) {
            return 0;
        }

        Map<Long, Integer> prefixSumCounts = new HashMap<>();
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
        int[] transactions1 = {3, 4, -7, 1, 3, 3, 1, -4};
        long k1 = 7;
        int result1 = countPeriodsWithNetBalance(transactions1, k1);
        System.out.println("Sample 1 Transactions: " + Arrays.toString(transactions1) + ", Target k: " + k1);
        System.out.println("Expected Output: 4");
        System.out.println("Actual Output:   " + result1);

        int[] transactions2 = {1, 2, 3};
        long k2 = 10;
        int result2 = countPeriodsWithNetBalance(transactions2, k2);
        System.out.println("\nSample 2 Transactions: " + Arrays.toString(transactions2) + ", Target k: " + k2);
        System.out.println("Expected Output: 0");
        System.out.println("Actual Output:   " + result2);

        int[] transactions3 = {0, 0, 0};
        long k3 = 0;
        int result3 = countPeriodsWithNetBalance(transactions3, k3);
        System.out.println("\nSample 3 (Zeros) Transactions: " + Arrays.toString(transactions3) + ", Target k: " + k3);
        System.out.println("Expected Output: 6");
        System.out.println("Actual Output:   " + result3);
    }
}
