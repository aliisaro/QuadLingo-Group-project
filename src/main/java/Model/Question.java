package Model;

public class Question {

    public String type;

    public String question;

    public Question(String type, String question) {
        this.type = type;
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }
}
