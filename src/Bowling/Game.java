package Bowling;
import java.util.*;
public class Game {

    List<Player> players;
    private int currentPlayerIndex;

    public Game(List<String> playerNames) {
        players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
        currentPlayerIndex = 0;
    }

    public void roll(int pins) {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.roll(pins);

        // Move to next player if their frame is done
        if (currentPlayer.getCurrentFrame() == null || currentPlayer.getCurrentFrame().isOver()) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    public boolean isGameOver() {
        for (Player player : players) {
            if (!player.isGameOver()) {
                return false;
            }
        }
        return true;
    }

    public void printScoreboard() {
        System.out.println("\n--- Scoreboard ---");
        for (Player player : players) {
            System.out.print(player.getName() + ": ");
            for (Frame frame : player.getFrames()) {
                System.out.print(frame.getRolls() + " ");
            }
            System.out.println("=> Total: " + player.getTotalScore());
        }
    }

    public void printWinner() {
        Player winner = null;
        int highScore = -1;
        for (Player p : players) {
            int score = p.getTotalScore();
            if (score > highScore) {
                highScore = score;
                winner = p;
            }
        }
        System.out.println("\n🏆 Winner: " + winner.getName() + " with " + highScore + " points!");
    }


    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }


}
