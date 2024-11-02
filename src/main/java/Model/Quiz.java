package Model;

public class Quiz {
    private int quizId;
    private String quizTitle;
    private int quizScore;
    private String languageCode;

    // Constructor
    public Quiz(int quizId, String quizTitle, int quizScore, String languageCode) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.quizScore = quizScore;
        this.languageCode = languageCode;
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

    public String getLanguageCode() {
        return languageCode; // Getter for language code
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

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
