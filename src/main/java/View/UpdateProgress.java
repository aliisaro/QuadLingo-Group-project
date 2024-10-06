package View;

import javafx.scene.control.ProgressBar;

public interface UpdateProgress {

    void updateQuizProgress(ProgressBar progressBar);

    void updateScoreProgress(ProgressBar progressBar);

    void updateFlashcardProgress(ProgressBar progressBar);

}
