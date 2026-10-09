import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpiralStockAuditRoute {

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
            for (int col = left; col <= right; col++) {
                route.add(grid[top][col]);
            }
            top++;

            for (int row = top; row <= bottom; row++) {
                route.add(grid[row][right]);
            }
            right--;

            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    route.add(grid[bottom][col]);
                }
                bottom--;
            }

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

        int[][] grid2 = {
            {10, 20, 30, 40}
        };
        List<Integer> route2 = spiralOrder(grid2);
        System.out.println("\nSample 2 Grid (1x4): [10, 20, 30, 40]");
        System.out.println("Expected Output: [10, 20, 30, 40]");
        System.out.println("Actual Output:   " + route2);

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
