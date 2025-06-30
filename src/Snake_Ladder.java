import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import java.util.*;



class Dice {
    private final Random rand = new Random();

    public int roll() {
        return rand.nextInt(6) + 1; // 1 to 6
    }
}

class Player {
    private final String name;
    private final String color;
    private int position = 0;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}




class Board {
    private final int SIZE = 100;
    private final Map<Integer, Integer> snakes;
    private final Map<Integer, Integer> ladders;

    public Board(Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
        this.snakes = snakes;
        this.ladders = ladders;
    }

    public int getNextPosition(int currentPos, int diceVal) {
        int next = currentPos + diceVal;
        if (next > SIZE) return currentPos;

        if (snakes.containsKey(next)) {
            System.out.println("Oops! Bitten by snake at " + next);
            return snakes.get(next);
        } else if (ladders.containsKey(next)) {
            System.out.println("Yay! Climbed ladder at " + next);
            return ladders.get(next);
        }
        return next;
    }

    public boolean isWinningPos(int pos) {
        return pos == SIZE;
    }
}


class Game {
    private final List<Player> players;
    private final Board board;
    private final Dice dice;
    private boolean gameOver = false;

    public Game(List<Player> players, Board board) {
        this.players = players;
        this.board = board;
        this.dice = new Dice();
    }

    public void play() {
        Queue<Player> queue = new LinkedList<>(players);

        while (!gameOver) {
            Player current = queue.poll();
            int roll = dice.roll();
            System.out.println(current.getName() + " rolls " + roll);

            int newPos = board.getNextPosition(current.getPosition(), roll);
            System.out.println(current.getName() + " moves from " + current.getPosition() + " to " + newPos);
            current.setPosition(newPos);

            if (board.isWinningPos(newPos)) {
                System.out.println("\n🎉 " + current.getName() + " wins the game!");
                gameOver = true;
            } else {
                queue.add(current);
            }
        }

        // Final positions
        System.out.println("\nFinal Positions:");
        for (Player p : players) {
            System.out.println(p.getName() + " (" + p.getColor() + ") is at " + p.getPosition());
        }
    }
}
