import java.util.Arrays;

public class ExamScoreBandCounter {

    public static int firstGE(int[] scores, int target) {
        int low = 0;
        int high = scores.length - 1;
        int result = scores.length;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (scores[mid] >= target) {
                result = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    public static int firstGT(int[] scores, int target) {
        int low = 0;
        int high = scores.length - 1;
        int result = scores.length;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (scores[mid] > target) {
                result = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

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

        int low1 = 42, high1 = 58;
        int count1 = countScoresInBand(scores, low1, high1);
        System.out.println("Scores: " + Arrays.toString(scores));
        System.out.println("Sample 1 Band: [" + low1 + ", " + high1 + "]");
        System.out.println("Expected Output: 6");
        System.out.println("Actual Output:   " + count1);

        int low2 = 90, high2 = 100;
        int count2 = countScoresInBand(scores, low2, high2);
        System.out.println("\nSample 2 Band: [" + low2 + ", " + high2 + "]");
        System.out.println("Expected Output: 0");
        System.out.println("Actual Output:   " + count2);

        int low3 = 30, high3 = 90;
        int count3 = countScoresInBand(scores, low3, high3);
        System.out.println("\nSample 3 (Full range) Band: [" + low3 + ", " + high3 + "]");
        System.out.println("Expected Output: 10");
        System.out.println("Actual Output:   " + count3);

        int low4 = 50, high4 = 50;
        int count4 = countScoresInBand(scores, low4, high4);
        System.out.println("\nSample 4 (Exact match) Band: [" + low4 + ", " + high4 + "]");
        System.out.println("Expected Output: 1");
        System.out.println("Actual Output:   " + count4);
    }
}
