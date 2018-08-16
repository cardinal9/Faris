package Model;

import java.io.Serializable;

public class Score implements Serializable {

    private static final long serialVersionUID = 10L;
    private int id;
    private int score;
    private String scoreTopic;

    public Score() {

    }

    public Score(int id, int score, String scoreTopic) {
        this.id = id;
        this.score = score;
        this.scoreTopic = scoreTopic;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getScoreTopic() {
        return scoreTopic;
    }

    public void setScoreTopic(String scoreTopic) {
        this.scoreTopic = scoreTopic;
    }
}
