package Model;

public class Answer {
    private int questionId;
    private String userAnswer;
    private boolean isCorrect;

    public Answer(int questionId, String userAnswer, boolean isCorrect) {
        this.questionId = questionId;
        this.userAnswer = userAnswer;
        this.isCorrect = isCorrect;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
