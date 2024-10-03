package Model;

import java.util.List;

public class Question {
    private int id; // Question ID
    private String questionText; // The text of the question
    private List<String> answerOptions; // List of answer options
    private String correctAnswer; // The correct answer

    // Constructor for all fields
    public Question(int id, String questionText, List<String> answerOptions, String correctAnswer) {
        this.id = id;
        this.questionText = questionText;
        this.answerOptions = answerOptions;
        this.correctAnswer = correctAnswer;
    }

    // Getters
    public int getQuestionId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // Optionally, you can add setters if you want to modify the properties later
}
