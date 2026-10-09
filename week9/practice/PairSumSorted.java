/**
 * Problem 1: Pair Sum in a Sorted Array
 *
 * TASK:
 * A finance application needs to determine if any two distinct account balances
 * in an already-sorted list of integers sum up to a specific target reconciliation amount.
 *
 * COMPLEXITY:
 * - Time Complexity: O(n)
 *   Two pointers (left and right) traverse the sorted array from both ends inward.
 *   Each element is inspected at most once, reducing the search space in linear time.
 * - Auxiliary Space Complexity: O(1)
 *   Only two pointer indices and a running sum variable are maintained.
 *
 * COMPARISON TO BRUTE FORCE:
 * - A brute force approach checks all pairs (i, j) with i < j, requiring O(n^2) time.
 * - Leveraging the sorted property with two pointers cuts runtime from quadratic O(n^2)
 *   to linear O(n) without any additional memory overhead.
 */
public class PairSumSorted {

    public static class PairResult {
        private final Integer first;
        private final Integer second;

        public PairResult(Integer first, Integer second) {
            this.first = first;
            this.second = second;
        }

        public Integer getFirst() {
            return first;
        }

        public Integer getSecond() {
            return second;
        }

        @Override
        public String toString() {
            if (first == null || second == null) {
                return "Not Found";
            }
            return "(" + first + ", " + second + ")";
        }
    }

    /**
     * Finds two distinct values in a sorted array that sum to target.
     *
     * @param nums   sorted array of integers (ascending order)
     * @param target reconciliation target sum
     * @return PairResult containing the two numbers, or "Not Found" indicator
     */
    public static PairResult pairSumSorted(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new PairResult(null, null);
        }

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int currentSum = nums[left] + nums[right];

            if (currentSum == target) {
                return new PairResult(nums[left], nums[right]);
            } else if (currentSum < target) {
                left++; // Increase the sum by moving left pointer rightward
            } else {
                right--; // Decrease the sum by moving right pointer leftward
            }
        }

        return new PairResult(null, null);
    }

    public static void main(String[] args) {
        // Sample 1
        int[] nums1 = {-4, -1, 0, 3, 5, 9};
        int target1 = 4;
        PairResult result1 = pairSumSorted(nums1, target1);
        System.out.println("Sample 1 Output: " + result1); // Expected: (-1, 5)

        // Sample 2
        int[] nums2 = {1, 2, 3};
        int target2 = 100;
        PairResult result2 = pairSumSorted(nums2, target2);
        System.out.println("Sample 2 Output: " + result2); // Expected: Not Found
    }
}
