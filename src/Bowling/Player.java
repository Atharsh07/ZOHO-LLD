package Bowling;
import java.util.*;
public class Player {

    private String name;
    private List<Frame> frames;
    private int currFrameIdx;

    public Player(String name){
        this.name = name;
        this.frames = new ArrayList<>();
        for(int i = 0; i<10; i++){
            frames.add(new Frame(i==9));
        }
        currFrameIdx = 0;
    }

    public String getName(){
        return name;
    }

    public Frame getCurrentFrame(){
        if(currFrameIdx >= 10) return null;
        return frames.get(currFrameIdx);
    }

    public void roll(int pins){
        Frame frame = getCurrentFrame();
        if(frame == null || frame.isOver()) return;

        frame.addRoll(pins);

        if(frame.isOver() && currFrameIdx<9){
            currFrameIdx++;
        }
    }

    public boolean isGameOver(){
        return currFrameIdx == 9 && frames.get(9).isOver();
    }

    public int getTotalScore(){
        int score = 0;
        for(int i = 0; i<10; i++){
            Frame f = frames.get(i);
            score += f.getScore();
            if (f.isStrike()) {
                score += getStrikeBonus(i);
            } else if (f.isSpare()) {
                score += getSpareBonus(i);
            }
        }

        return score;
    }

    private int getStrikeBonus(int index) {
        if (index + 1 < frames.size()) {
            return 10;
        }
        return 0;
    }

    private int getSpareBonus(int index) {
        if (index + 1 < frames.size()) {
            Frame next = frames.get(index + 1);
            List<Integer> rolls = next.getRolls();
            return rolls.isEmpty() ? 0 : 5;
        }
        return 0;
    }

    public List<Frame> getFrames() {
        return frames;
    }


    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", frames=" + frames +
                ", currFrameIdx=" + currFrameIdx +
                '}';
    }
}
