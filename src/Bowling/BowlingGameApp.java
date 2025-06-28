package Bowling;

import java.util.*;

public class BowlingGameApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Get player names
        System.out.println("🎳 Enter number of players: ");
        int n = Integer.parseInt(scanner.nextLine());
        List<String> playerNames = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            playerNames.add(scanner.nextLine());
        }

        // 2. Initialize Game
        Game game = new Game(playerNames);
        System.out.println("\n🎮 Game started!\n");

        // 3. Game Loop
        while (!game.isGameOver()) {
            Player currentPlayer = game.getCurrentPlayer();
            Frame currentFrame = currentPlayer.getCurrentFrame();

            System.out.println("It's " + currentPlayer.getName() + "'s turn.");
            System.out.print("Enter pins knocked down: ");
            int pins = Integer.parseInt(scanner.nextLine());

            if (pins < 0 || pins > 10) {
                System.out.println("❌ Invalid input! Enter between 0 and 10.");
                continue;
            }

            game.roll(pins);
            game.printScoreboard();
        }

        // 4. Game Over
        System.out.println("\n✅ Game Over!");
        game.printScoreboard();
        game.printWinner();

        scanner.close();
    }
}

