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
    }
}
