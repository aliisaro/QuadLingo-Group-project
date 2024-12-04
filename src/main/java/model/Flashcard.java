package model;

public class Flashcard {

  private final String term;

  private final String translation;

  private final String topic;

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