// -------------------------
// BALL BRICK GAME (MULTI-LEVEL)
// -------------------------
import java.util.*;

class BallBrickGame {
    static final Scanner sc = new Scanner(System.in);
    static int N;
    static String[][] grid;
    static int ballCount;
    static Set<String> powerBricks = new HashSet<>(Arrays.asList("B", "P"));
    static int baseLeft, baseRight;
    static int togglePower = 0;

    public static void main(String[] args) {
        setupGame();
        while (ballCount > 0 && hasBricks()) {
            display();
            System.out.print("Enter the direction in which the ball need to traverse (ST, LD, RD): ");
            String dir = sc.next().toUpperCase();
            shoot(dir);
        }
        System.out.println(hasBricks() ? "GAME OVER..!!" : "HURRAY..!! YOU WIN");
    }

    static void setupGame() {
        System.out.print("Enter size of the NxN matrix : ");
        N = sc.nextInt();
        grid = new String[N][N];

        for (int i = 0; i < N; i++) Arrays.fill(grid[i], " ");

        for (int i = 0; i < N; i++) {
            grid[0][i] = "W";
            grid[N - 1][i] = (i == N / 2) ? "o" : "G";
        }
        grid[0][0] = grid[0][N - 1] = "W";
        grid[N - 1][0] = grid[N - 1][N - 1] = "W";
        baseLeft = baseRight = N / 2;

        sc.nextLine();
        while (true) {
            System.out.print("Enter the brick's position and the brick type : ");
            int r = sc.nextInt(), c = sc.nextInt();
            String val = sc.next();
            grid[r][c] = val;
            System.out.print("Do you want to continue(Y or N)?");
            if (sc.next().equalsIgnoreCase("N")) break;
        }

        System.out.print("Enter ball Count : ");
        ballCount = sc.nextInt();
    }

    static void display() {
        for (String[] row : grid) {
            for (String cell : row) {
                System.out.print((cell == null ? " " : cell) + "\t");
            }
            System.out.println();
        }
        System.out.println("Ball count is " + ballCount);
    }

    static void shoot(String dir) {
        int row = N - 1, col = (baseLeft + baseRight) / 2;
        grid[row][col] = "G";

        int dr = -1, dc = 0;
        if (dir.equals("LD")) dc = -1;
        else if (dir.equals("RD")) dc = 1;

        boolean wallHit = false;
        while (row >= 0 && col >= 0 && col < N) {
            if (grid[row][col].equals("W")) {
                if (wallHit) {
                    resetBall();
                    return;
                }
                wallHit = true;
                dc = -dc;
                col += dc;
                continue;
            }

            if (!grid[row][col].equals(" ") && !grid[row][col].equals("G")) {
                String brick = grid[row][col];
                if (brick.equals("DE")) {
                    Arrays.fill(grid[row], " ");
                } else if (brick.equals("DS")) {
                    for (int i = Math.max(0, row - 1); i <= Math.min(N - 1, row + 1); i++)
                        for (int j = Math.max(0, col - 1); j <= Math.min(N - 1, col + 1); j++)
                            grid[i][j] = grid[i][j].matches("[1-9]+|DS|DE|B|P") ? " " : grid[i][j];
                } else if (powerBricks.contains(brick)) {
                    extendBase();
                } else if (brick.matches("[1-9]+")) {
                    int val = Integer.parseInt(brick) - 1;
                    grid[row][col] = (val == 0) ? " " : Integer.toString(val);
                } else {
                    grid[row][col] = " ";
                }
                placeBall(row + 1, col);
                return;
            }

            row += dr;
            col += dc;
        }

        placeBall(N - 1, col);
        if (col < baseLeft || col > baseRight) ballCount--;
    }

    static void placeBall(int r, int c) {
        if (r >= 0 && r < N && c >= 0 && c < N) grid[r][c] = "o";
    }

    static void resetBall() {
        grid[N - 1][(baseLeft + baseRight) / 2] = "o";
        ballCount--;
    }

    static boolean hasBricks() {
        for (int i = 0; i < N - 1; i++)
            for (int j = 0; j < N; j++)
                if (!grid[i][j].equals(" ") && !grid[i][j].equals("W")) return true;
        return false;
    }

    static void extendBase() {
        if (togglePower % 2 == 0 && baseRight + 1 < N - 1) baseRight++;
        else if (togglePower % 2 == 1 && baseLeft - 1 > 0) baseLeft--;
        togglePower++;
    }
}