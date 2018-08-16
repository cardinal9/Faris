package Model;

public class Word {

    private int id;
    private String word;
    private String word_topic;

    public Word() {

    }

    public Word(int id, String word, String word_topic) {
        this.id = id;
        this.word = word;
        this.word_topic = word_topic;
    }

    public Word(String word) {
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord_topic() {
        return word_topic;
    }

    public void setWord_topic(String word_topic) {
        this.word_topic = word_topic;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
