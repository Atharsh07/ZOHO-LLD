// -------------------------
// SHOOTING GAME (LEVEL-BASED DEVELOPMENT)
// -------------------------
import java.util.*;

class ShootingGame {
    static final int SIZE = 9;
    static final int EMPTY = 0;
    static final int GUN = 5;
    static final int PLAYER1 = 1;
    static final int PLAYER2 = 2;
    static final int SURVIVAL = 6;

    static int[][] grid = new int[SIZE][SIZE];
    static int[] gunPos = new int[2];
    static int[] p1 = new int[2];
    static int[] p2 = new int[2];
    static int p1Lives = 0, p2Lives = 0;
    static int gunHolder = 0;
    static int tries = 0;

    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    public static void main(String[] args) {
        placeSurvivals();
        placeGun();
        placePlayer(p1, PLAYER1);
        placePlayer(p2, PLAYER2);

        while (true) {
            display();
            System.out.println("Gun Holder : " + (gunHolder == 0 ? "-" : "Player " + gunHolder));
            System.out.println("Player 1 Survival Power : " + p1Lives);
            System.out.println("Player 2 Survival Power : " + p2Lives);

            if (playMove(1)) break;
            if (playMove(2)) break;
        }
    }

    static void placeGun() {
        gunPos = generateRandomPosition();
        grid[gunPos[0]][gunPos[1]] = GUN;
    }

    static void placeSurvivals() {
        for (int i = 0; i < 2; i++) {
            int[] pos;
            do {
                pos = generateRandomPosition();
            } while (grid[pos[0]][pos[1]] != 0);
            grid[pos[0]][pos[1]] = SURVIVAL;
        }
    }

    static void placePlayer(int[] player, int val) {
        int[] pos;
        do {
            pos = generateRandomPosition();
        } while (grid[pos[0]][pos[1]] != 0);
        player[0] = pos[0];
        player[1] = pos[1];
        grid[pos[0]][pos[1]] = val;
    }

    static boolean playMove(int playerId) {
        int[] player = playerId == 1 ? p1 : p2;
        int[] enemy = playerId == 1 ? p2 : p1;
        int enemyVal = playerId == 1 ? PLAYER2 : PLAYER1;

        System.out.println("Player " + playerId + " move:");
        System.out.print("Enter direction (1:left, 2:right, 3:up, 4:down, 5:shoot): ");
        int dir = sc.nextInt();

        if (dir == 5 && gunHolder == playerId && (player[0] == enemy[0] || player[1] == enemy[1])) {
            if ((playerId == 1 && p1Lives > 0) || (playerId == 2 && p2Lives > 0)) {
                if (playerId == 1) p1Lives--;
                else p2Lives--;
                System.out.println("Player " + enemyVal + " survived.");
            } else {
                System.out.println("Player " + playerId + " shoots Player " + enemyVal + " — He is the winner!");
                return true;
            }
            tries++;
            if (tries == 3) {
                grid[player[0]][player[1]] = playerId;
                gunHolder = 0;
                tries = 0;
                relocateGunNear(player);
            }
            return false;
        }

        System.out.print("Enter move count (0-3): ");
        int move = sc.nextInt();
        grid[player[0]][player[1]] = 0;

        switch (dir) {
            case 1 -> player[1] = Math.max(0, player[1] - move);
            case 2 -> player[1] = Math.min(SIZE - 1, player[1] + move);
            case 3 -> player[0] = Math.max(0, player[0] - move);
            case 4 -> player[0] = Math.min(SIZE - 1, player[0] + move);
        }

        if (grid[player[0]][player[1]] == GUN) {
            gunHolder = playerId;
            tries = 0;
        } else if (grid[player[0]][player[1]] == SURVIVAL) {
            if (playerId == 1) p1Lives++;
            else p2Lives++;
        } else if (grid[player[0]][player[1]] == enemyVal) {
            if (gunHolder == playerId) {
                System.out.println("Player " + playerId + " shoots Player " + enemyVal + " — He is the winner!");
                return true;
            }
        }

        grid[player[0]][player[1]] = playerId;
        return false;
    }

    static void relocateGunNear(int[] player) {
        List<int[]> possible = new ArrayList<>();
        for (int i = Math.max(0, player[0] - 2); i <= Math.min(SIZE - 1, player[0] + 2); i++) {
            for (int j = Math.max(0, player[1] - 2); j <= Math.min(SIZE - 1, player[1] + 2); j++) {
                if (grid[i][j] == 0) possible.add(new int[]{i, j});
            }
        }
        if (!possible.isEmpty()) {
            int[] newPos = possible.get(rand.nextInt(possible.size()));
            gunPos = newPos;
            grid[gunPos[0]][gunPos[1]] = GUN;
        }
    }

    static int[] generateRandomPosition() {
        return new int[]{rand.nextInt(SIZE), rand.nextInt(SIZE)};
    }

    static void display() {
        for (int[] row : grid) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}
