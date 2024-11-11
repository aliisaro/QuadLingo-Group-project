package Model;

public class Flashcard {

    public String term;

    public String translation;

    public String topic;

    public Flashcard(String term, String translation, String topic) {
        this.term = term;
        this.translation = translation;
        this.topic = topic;
    }

    public String getTerm() {
        return term;
    }

    public String getTranslation() {
        return translation;
    }

    public String getTopic() {
        return topic;
    }

}