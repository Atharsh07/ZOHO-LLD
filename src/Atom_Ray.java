// -------------------------
// MATRIX RAY GAME (5 Rule Implementation)
// -------------------------
import java.util.*;

 class MatrixRayGame {
    static char[][] grid;
    static int n = 3, m = 3;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        grid = new char[n][m];
        for (char[] row : grid) Arrays.fill(row, '-');

        int atomCount = sc.nextInt();
        for (int i = 0; i < atomCount; i++) {
            int r = n - sc.nextInt();
            int c = sc.nextInt() - 1;
            grid[r][c] = 'X';
        }

        int rayCount = sc.nextInt();
        String[] rays = new String[rayCount];
        for (int i = 0; i < rayCount; i++) rays[i] = sc.next();

        for (String ray : rays) processRay(ray);
        printGrid();
    }

    static void processRay(String ray) {
        if (ray.startsWith("R")) {
            int row = n - Integer.parseInt(ray.substring(1));
            for (int col = 0; col < m; col++) {
                if (grid[row][col] == 'X') {
                    grid[row][col] = 'H'; // Rule 1: Hit
                    return;
                }

                boolean reflect = false;
                if (isDiagonalHit(row - 1, col) && isDiagonalHit(row + 1, col)) reflect = true; // Rule 4
                if (reflect) {
                    // reflected: ray returns
                    return;
                }
                if (isDiagonalHit(row - 1, col) || isDiagonalHit(row + 1, col)) {
                    grid[row][col] = 'R'; // Rule 2 & 3: Refract
                    return;
                }
            }
        } else if (ray.startsWith("C")) {
            int col = Integer.parseInt(ray.substring(1)) - 1;
            for (int row = n - 1; row >= 0; row--) {
                if (grid[row][col] == 'X') {
                    grid[row][col] = 'H';
                    return;
                }

                boolean reflect = false;
                if (isDiagonalHit(row, col - 1) && isDiagonalHit(row, col + 1)) reflect = true;
                if (reflect) return;
                if (isDiagonalHit(row, col - 1) || isDiagonalHit(row, col + 1)) {
                    grid[row][col] = 'R';
                    return;
                }
            }
        }
    }

    static boolean isDiagonalHit(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m && grid[r][c] == 'X';
    }

    static void printGrid() {
        for (int i = 0; i < n; i++) {
            System.out.print("R" + (n - i) + " | ");
            for (int j = 0; j < m; j++) {
                System.out.print(grid[i][j] + "   ");
            }
            System.out.println("|");
        }
        System.out.print("     ");
        for (int j = 0; j < m; j++) System.out.print("C" + (j + 1) + "  ");
        System.out.println();
    }
}
