package Model;

public class Progress {
    //TODO: According to Copilot: "This class would represent a user's progress in learning a language. It could contain fields such as user, language, currentLesson, score, etc."

    private User user;

    private Language language;

    private int currentLesson;

    private int score;

    public Progress(User user, Language language, int currentLesson, int score) {
        this.user = user;
        this.language = language;
        this.currentLesson = currentLesson;
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public Language getLanguage() {
        return language;
    }

    public int getCurrentLesson() {
        return currentLesson;
    }

    public int getScore() {
        return score;
    }
}
