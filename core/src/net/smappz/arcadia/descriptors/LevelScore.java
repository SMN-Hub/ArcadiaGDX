package net.smappz.arcadia.descriptors;

public class LevelScore {
    int score;
    int damages;
    boolean completed;

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        this.score++;
    }

    public int getDamages() {
        return damages;
    }

    public void increaseDamages(int damages) {
        this.damages += damages;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
