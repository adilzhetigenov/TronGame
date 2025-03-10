package tron.db;

public class Score {
    private final String name;
    private final int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public long getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Score[" + "name = " + name + ", score = " + score + ']';
    }

}
