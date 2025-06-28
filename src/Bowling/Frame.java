package Bowling;
import java.util.*;


public class Frame {

    private List<Integer> rolls;
    private boolean isFinalFrame;

    public Frame(boolean isFinalFrame) {
        this.isFinalFrame = isFinalFrame;
        this.rolls = new ArrayList<>();
    }

    public void addRoll(int pins) {
        if (isOver()) return;
        rolls.add(pins);
    }

    public boolean isStrike() {
        return rolls.size() > 0 && rolls.get(0) == 10;
    }

    public boolean isSpare() {
        return rolls.size() >= 2 && (rolls.get(0) + rolls.get(1) == 10) && !isStrike();
    }

    public boolean isOver() {
        if (isFinalFrame) {
            if (rolls.size() == 3) return true;
            if (rolls.size() == 2 && (rolls.get(0) + rolls.get(1) < 10)) return true;
            return false;
        } else {
            return isStrike() || rolls.size() == 2;
        }
    }


    public int getScore(){
        return rolls.stream().mapToInt(Integer::intValue).sum();
    }

    public List<Integer> getRolls(){
        return rolls;
    }

    public boolean isFinalFrame(){
        return isFinalFrame;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "rolls=" + rolls +
                ", isFinalFrame=" + isFinalFrame +
                '}';
    }
}
