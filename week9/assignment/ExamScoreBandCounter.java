import java.util.Arrays;

/**
 * Assignment Problem 4: Exam Score Band Counter
 *
 * TASK:
 * An academic department holds an already-sorted array of student examination scores
 * (with possible duplicate scores). Given a score range [low, high] inclusive, count the
 * number of students who scored within that range without performing a linear scan.
 *
 * COMPLEXITY:
 * - Time Complexity: O(log n)
 *   Two binary searches are executed:
 *   1. firstGE(low): finds the lower boundary index (first element >= low) in O(log n).
 *   2. firstGT(high): finds the upper boundary index (first element > high) in O(log n).
 *   Total time is O(log n) compared to O(n) for a linear scan.
 * - Auxiliary Space Complexity: O(1)
 *   Only a constant number of pointer variables are used.
 *
 * WHY DUAL BINARY SEARCH HANDLES DUPLICATES:
 * - Standard binary search might land on any duplicate arbitrarily.
 * - Lower bound search (firstGE) biases left on matches, finding the first occurrence >= low.
 * - Upper bound search (firstGT) biases right on matches, finding the first occurrence > high.
 * - The count is simply: firstGT(high) - firstGE(low).
 */
public class ExamScoreBandCounter {

    /**
     * Finds the index of the first element in sorted array that is >= target.
     * Returns nums.length if all elements are < target.
     */
    public static int firstGE(int[] scores, int target) {
        int low = 0;
        int high = scores.length - 1;
        int result = scores.length;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (scores[mid] >= target) {
                result = mid;
                high = mid - 1; // Search left half for earlier occurrence
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    /**
     * Finds the index of the first element in sorted array that is > target.
     * Returns nums.length if all elements are <= target.
     */
    public static int firstGT(int[] scores, int target) {
        int low = 0;
        int high = scores.length - 1;
        int result = scores.length;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (scores[mid] > target) {
                result = mid;
                high = mid - 1; // Search left half for earlier occurrence
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    /**
     * Counts the number of scores in the inclusive range [low, high].
     *
     * @param scores sorted array of exam scores (ascending)
     * @param low    minimum score threshold (inclusive)
     * @param high   maximum score threshold (inclusive)
     * @return number of students scoring between low and high
     */
    public static int countScoresInBand(int[] scores, int low, int high) {
        if (scores == null || scores.length == 0 || low > high) {
            return 0;
        }

        int lowerIndex = firstGE(scores, low);
        int upperIndex = firstGT(scores, high);

        return Math.max(0, upperIndex - lowerIndex);
    }

    public static void main(String[] args) {
        int[] scores = {35, 42, 42, 50, 58, 58, 58, 63, 71, 88};

        // Sample 1: Band [42, 58]
        int low1 = 42, high1 = 58;
        int count1 = countScoresInBand(scores, low1, high1);
        System.out.println("Scores: " + Arrays.toString(scores));
        System.out.println("Sample 1 Band: [" + low1 + ", " + high1 + "]");
        System.out.println("Expected Output: 6");
        System.out.println("Actual Output:   " + count1);

        // Sample 2: Band [90, 100]
        int low2 = 90, high2 = 100;
        int count2 = countScoresInBand(scores, low2, high2);
        System.out.println("\nSample 2 Band: [" + low2 + ", " + high2 + "]");
        System.out.println("Expected Output: 0");
        System.out.println("Actual Output:   " + count2);

        // Additional Test: Band covering entire array [30, 90]
        int low3 = 30, high3 = 90;
        int count3 = countScoresInBand(scores, low3, high3);
        System.out.println("\nSample 3 (Full range) Band: [" + low3 + ", " + high3 + "]");
        System.out.println("Expected Output: 10");
        System.out.println("Actual Output:   " + count3);

        // Additional Test: Single value band [50, 50]
        int low4 = 50, high4 = 50;
        int count4 = countScoresInBand(scores, low4, high4);
        System.out.println("\nSample 4 (Exact match) Band: [" + low4 + ", " + high4 + "]");
        System.out.println("Expected Output: 1");
        System.out.println("Actual Output:   " + count4);
    }
}
