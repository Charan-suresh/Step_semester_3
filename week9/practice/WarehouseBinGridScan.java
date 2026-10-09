/**
 * Problem 2: Warehouse Bin Grid Scan
 *
 * TASK:
 * A warehouse floor layout is represented as a 2D grid, where each cell (row, col)
 * contains a non-negative integer representing the count of items in a storage bin.
 * Return a summary containing the total items across all bins and the coordinates (row, col)
 * of the bin holding the maximum items. If there are ties, return the first one encountered
 * in row-major order (left to right, top to bottom).
 *
 * COMPLEXITY:
 * - Time Complexity: O(m * n)
 *   Where m is the number of rows and n is the number of columns.
 *   Every cell must be inspected to compute the sum and locate the global maximum.
 * - Auxiliary Space Complexity: O(1)
 *   Only variables for running total, maximum value, and coordinate tracking are used.
 */
public class WarehouseBinGridScan {

    public static class Summary {
        private final long total;
        private final int maxRow;
        private final int maxCol;

        public Summary(long total, int maxRow, int maxCol) {
            this.total = total;
            this.maxRow = maxRow;
            this.maxCol = maxCol;
        }

        public long getTotal() {
            return total;
        }

        public int getMaxRow() {
            return maxRow;
        }

        public int getMaxCol() {
            return maxCol;
        }

        @Override
        public String toString() {
            return "total = " + total + ", maxCoordinate = (" + maxRow + ", " + maxCol + ")";
        }
    }

    /**
     * Scans the 2D grid in row-major order to compute total items and find the max bin.
     *
     * @param grid 2D array of non-negative integers
     * @return Summary object containing total and coordinates of maximum bin
     */
    public static Summary warehouseSummary(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return new Summary(0, -1, -1);
        }

        long total = 0;
        int maxVal = -1;
        int maxRow = 0;
        int maxCol = 0;

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                total += grid[r][c];

                // Strict '>' ensures the first encountered maximum is preserved during ties
                if (grid[r][c] > maxVal) {
                    maxVal = grid[r][c];
                    maxRow = r;
                    maxCol = c;
                }
            }
        }

        return new Summary(total, maxRow, maxCol);
    }

    public static void main(String[] args) {
        int[][] grid = {
            {4, 9, 2},
            {7, 1, 6},
            {3, 12, 5}
        };

        Summary summary = warehouseSummary(grid);
        System.out.println("Warehouse Summary Output: " + summary);
        // Expected: total = 49, maxCoordinate = (2, 1)
    }
}
