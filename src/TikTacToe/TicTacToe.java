package TikTacToe;
import java.util.*;

public class TicTacToe {

    private static char[][] board;
    private static int n;
    private static char currentPlayer;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the size of the grid (n x n): ");
        n = in.nextInt();
        board = new char[n][n];

        initializeBoard(); // Initialize board with '-'

        currentPlayer = 'X';
        int moveCount = 0;
        boolean gameWon = false;

        // Game loop
        while (moveCount < n * n && !gameWon) {
            printBoard();
            System.out.printf("Player %c, enter your move (row and col): ", currentPlayer);
            int row = in.nextInt();
            int col = in.nextInt();

            if (validMove(row, col)) {
                board[row][col] = currentPlayer;
                moveCount++;

                if (checkWin(row, col)) {
                    printBoard();
                    System.out.printf("Player %c wins!%n", currentPlayer);
                    gameWon = true;
                } else {
                    // Switch player
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        if (!gameWon) {
            printBoard();
            System.out.println("It's a draw!");
        }

        in.close();
    }

    // Initialize board with '-'
    private static void initializeBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '-';
            }
        }
    }

    // Print board
    private static void printBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Check for win after last move
    private static boolean checkWin(int row, int col) {
        return checkRow(row) || checkCol(col) || checkDiagonal();
    }

    // Check entire row
    private static boolean checkRow(int row) {
        for (int col = 0; col < n; col++) {
            if (board[row][col] != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    // Check entire column
    private static boolean checkCol(int col) {
        for (int row = 0; row < n; row++) {
            if (board[row][col] != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    // Check both diagonals
    private static boolean checkDiagonal() {
        boolean diag1 = true, diag2 = true;
        for (int i = 0; i < n; i++) {
            if (board[i][i] != currentPlayer) {
                diag1 = false;
            }
            if (board[i][n - i - 1] != currentPlayer) {
                diag2 = false;
            }
        }
        return diag1 || diag2;
    }

    // Check if the move is valid
    private static boolean validMove(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n && board[row][col] == '-';
    }
}
