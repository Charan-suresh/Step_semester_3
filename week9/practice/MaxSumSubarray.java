public class MaxSumSubarray {

    public static long maxSumSubarray(int[] sales, int k) {
        if (sales == null || k <= 0 || sales.length < k) {
            throw new IllegalArgumentException("Invalid input array or window size k.");
        }

        long windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += sales[i];
        }

        long maxSum = windowSum;

        for (int i = k; i < sales.length; i++) {
            windowSum += sales[i] - sales[i - k];
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
    }
}
