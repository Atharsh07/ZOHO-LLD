// -------------------------
// MINESWEEPER (Console Version)
// -------------------------
import java.io.IOException;
import java.util.*;

class Cell {
    boolean hasMine = false;
    boolean revealed = false;
    int adjacentMines = 0;
}

class Minesweeper {
    private final int rows, cols, mines;
    private final Cell[][] board;
    private boolean gameOver = false;

    public Minesweeper(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        board = new Cell[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                board[i][j] = new Cell();
        placeMines();
        calculateAdjacency();
    }

    private void placeMines() {
        Random rand = new Random();
        int count = 0;
        while (count < mines) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (!board[r][c].hasMine) {
                board[r][c].hasMine = true;
                count++;
            }
        }
    }

    private void calculateAdjacency() {
        int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int count = 0;
                for (int d = 0; d < 8; d++) {
                    int ni = i + dx[d];
                    int nj = j + dy[d];
                    if (ni >= 0 && nj >= 0 && ni < rows && nj < cols && board[ni][nj].hasMine)
                        count++;
                }
                board[i][j].adjacentMines = count;
            }
        }
    }

    public void click(int r, int c) {
        if (r < 0 || c < 0 || r >= rows || c >= cols || board[r][c].revealed)
            return;
        board[r][c].revealed = true;
        if (board[r][c].hasMine) {
            gameOver = true;
            printBoard(true);
            System.out.println("Game Over! You hit a mine.");
            return;
        }
        if (board[r][c].adjacentMines == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0)
                        click(r + i, c + j);
                }
            }
        }
    }

    public void printBoard(boolean revealAll) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = board[i][j];
                if (revealAll || cell.revealed) {
                    if (cell.hasMine) System.out.print(" * ");
                    else System.out.print(" " + cell.adjacentMines + " ");
                } else {
                    System.out.print(" # ");
                }
            }
            System.out.println();
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Minesweeper game = new Minesweeper(5, 5, 5);
        while (!game.isGameOver()) {
            game.printBoard(false);
            System.out.print("Enter row and col to reveal: ");
            int r = sc.nextInt();
            int c = sc.nextInt();
            game.click(r, c);
        }
    }
}

// -------------------------
// BRICK BREAKER (Console Logic Skeleton)
// -------------------------

class BrickBreaker {
    static final int WIDTH = 20;
    static final int HEIGHT = 10;
    static char[][] screen = new char[HEIGHT][WIDTH];
    static int ballX = 10, ballY = 8, ballDX = 1, ballDY = -1;
    static int paddleX = 8, paddleWidth = 4;

    static void initBricks() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < WIDTH; j++)
                screen[i][j] = '#';
    }

    static void render() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (i == ballY && j == ballX) System.out.print("o");
                else if (i == HEIGHT - 1 && j >= paddleX && j < paddleX + paddleWidth)
                    System.out.print("=");
                else if (screen[i][j] == '#') System.out.print("#");
                else System.out.print(" ");
            }
            System.out.println();
        }
    }

    static void update() {
        int nextX = ballX + ballDX;
        int nextY = ballY + ballDY;

        // Wall bounce
        if (nextX < 0 || nextX >= WIDTH) ballDX *= -1;
        if (nextY < 0) ballDY *= -1;

        // Brick collision
        if (nextY >= 0 && nextY < HEIGHT && screen[nextY][nextX] == '#') {
            screen[nextY][nextX] = ' ';
            ballDY *= -1;
        }

        // Paddle collision
        if (nextY == HEIGHT - 1 && nextX >= paddleX && nextX < paddleX + paddleWidth) {
            ballDY *= -1;
        }

        // Bottom check
        if (nextY >= HEIGHT) {
            System.out.println("Game Over!");
            System.exit(0);
        }

        ballX += ballDX;
        ballY += ballDY;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);
        initBricks();
        while (true) {
            System.out.print("\033[H\033[2J"); // Clear screen
            System.out.flush();
            render();
            System.out.print("Move paddle (a/d): ");
            if (System.in.available() > 0) {
                char move = sc.next().charAt(0);
                if (move == 'a' && paddleX > 0) paddleX--;
                if (move == 'd' && paddleX + paddleWidth < WIDTH) paddleX++;
            }
            update();
            Thread.sleep(200);
        }
    }
}
