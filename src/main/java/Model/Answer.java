package Model;

public class Answer {

    public Boolean isCorrect;

    public String answer;

    public Answer(Boolean isCorrect, String answer) {
        this.isCorrect = isCorrect;
        this.answer = answer;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public String getAnswer() {
        return answer;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
