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
                left++;
            } else {
                right--;
            }
        }

        return new PairResult(null, null);
    }

    public static void main(String[] args) {
        int[] nums1 = {-4, -1, 0, 3, 5, 9};
        int target1 = 4;
        PairResult result1 = pairSumSorted(nums1, target1);
        System.out.println("Sample 1 Output: " + result1);

        int[] nums2 = {1, 2, 3};
        int target2 = 100;
        PairResult result2 = pairSumSorted(nums2, target2);
        System.out.println("Sample 2 Output: " + result2);
    }
}
