package Model;

public class Quiz {

    public String title;

    public Boolean isCompleted;

    public int score;

    public Quiz(String title, Boolean isCompleted) {
        this.title = title;
        this.isCompleted = isCompleted;
        this.score = 0;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
