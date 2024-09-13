package Model;

public class Progress {
    //TODO: According to Copilot: "This class would represent a user's progress in learning a language. It could contain fields such as user, language, currentLesson, score, etc."

    private User user;

    private Language language;

    private int completedQuizzes;

    public Progress(User user, Language language) {
        this.user = user;
        this.language = language;
        this.completedQuizzes = 0;
    }

    public User getUser() {
        return user;
    }

    public Language getLanguage() {
        return language;
    }

    public int getCompletedQuizzes() {
        return completedQuizzes;
    }

    public int completeQuiz() {
        return ++completedQuizzes;
    }
}
