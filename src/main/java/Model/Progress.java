package Model;

public class Progress {
    //TODO: According to Copilot: "This class would represent a user's progress in learning a language. It could contain fields such as user, language, currentLesson, score, etc."

    private User user;

    private int completedQuizzes;

    public Progress(User user) {
        this.user = user;
        this.completedQuizzes = 0;
    }

    public User getUser() {
        return user;
    }

    public int getCompletedQuizzes() {
        return completedQuizzes;
    }

    public int completeQuiz() {
        return ++completedQuizzes;
    }
}
