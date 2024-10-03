package Model;

public class Quiz {
    private int quizId;
    private String quizTitle;
    private int quizScore;

    // Constructor
    public Quiz(int quizId, String quizTitle, int quizScore) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.quizScore = quizScore;
    }

    // Getters
    public int getQuizId() {
        return quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public int getQuizScore() {
        return quizScore;
    }

    // Optionally, setters if needed
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public void setQuizScore(int quizScore) {
        this.quizScore = quizScore;
    }
}
