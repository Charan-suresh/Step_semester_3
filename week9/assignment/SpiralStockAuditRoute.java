import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Assignment Problem 5: Spiral Stock Audit Route
 *
 * TASK:
 * An automated inventory drone navigates a rectangular 2D warehouse layout of storage bins.
 * To optimize path continuity, the drone follows a clockwise spiral route starting from the
 * top-left corner (0, 0), moving right across the top edge, down the right edge, left across
 * the bottom edge, and up the left edge, repeating inward until all bins are visited.
 *
 * COMPLEXITY:
 * - Time Complexity: O(m * n)
 *   Every cell in the m x n grid is visited and appended to the result route exactly once.
 * - Auxiliary Space Complexity: O(1)
 *   Excluding the return list of size m * n, only four boundary pointers
 *   (top, bottom, left, right) and loop variables are maintained in memory.
 *
 * BOUNDARY SHRINKING MECHANISM:
 * - Four boundary markers represent the active rectangular sub-grid:
 *   top, bottom, left, and right.
 * - After traversing a row or column, the corresponding boundary is adjusted inward.
 * - Crucially, checks (top <= bottom) and (left <= right) before the bottom and left sweeps
 *   prevent duplicate visits on non-square (odd dimension) grids.
 */
public class SpiralStockAuditRoute {

    /**
     * Traverses the 2D grid in clockwise spiral order.
     *
     * @param grid 2D rectangular array of bin item identifiers or counts
     * @return list of elements in clockwise spiral order
     */
    public static List<Integer> spiralOrder(int[][] grid) {
        List<Integer> route = new ArrayList<>();
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return route;
        }

        int top = 0;
        int bottom = grid.length - 1;
        int left = 0;
        int right = grid[0].length - 1;

        while (top <= bottom && left <= right) {
            // 1. Traverse from left to right along top boundary
            for (int col = left; col <= right; col++) {
                route.add(grid[top][col]);
            }
            top++;

            // 2. Traverse from top to bottom along right boundary
            for (int row = top; row <= bottom; row++) {
                route.add(grid[row][right]);
            }
            right--;

            // 3. Traverse from right to left along bottom boundary (if row remains)
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    route.add(grid[bottom][col]);
                }
                bottom--;
            }

            // 4. Traverse from bottom to top along left boundary (if column remains)
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    route.add(grid[row][left]);
                }
                left++;
            }
        }

        return route;
    }

    public static void main(String[] args) {
        // Sample 1: 3 x 4 Grid
        int[][] grid1 = {
            { 1,  2,  3,  4},
            { 5,  6,  7,  8},
            { 9, 10, 11, 12}
        };

        System.out.println("Sample 1 Grid (3x4):");
        for (int[] row : grid1) {
            System.out.println(Arrays.toString(row));
        }
        List<Integer> route1 = spiralOrder(grid1);
        System.out.println("Expected Output: [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]");
        System.out.println("Actual Output:   " + route1);

        // Sample 2: Single Row (1 x 4 Grid)
        int[][] grid2 = {
            {10, 20, 30, 40}
        };
        List<Integer> route2 = spiralOrder(grid2);
        System.out.println("\nSample 2 Grid (1x4): [10, 20, 30, 40]");
        System.out.println("Expected Output: [10, 20, 30, 40]");
        System.out.println("Actual Output:   " + route2);

        // Sample 3: Single Column (4 x 1 Grid)
        int[][] grid3 = {
            {5},
            {6},
            {7},
            {8}
        };
        List<Integer> route3 = spiralOrder(grid3);
        System.out.println("\nSample 3 Grid (4x1):");
        System.out.println("Expected Output: [5, 6, 7, 8]");
        System.out.println("Actual Output:   " + route3);
    }
}
