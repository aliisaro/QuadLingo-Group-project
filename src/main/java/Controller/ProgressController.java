package Controller;

import DAO.ProgressDao;

public class ProgressController {

    private static ProgressController instance;
    private ProgressDao progressDao;

    private ProgressController(ProgressDao progressDao) {
        this.progressDao = progressDao;
    }

    public static ProgressController getInstance(ProgressDao progressDao) {
        if (instance == null) {
            instance = new ProgressController(progressDao);
        }
        return instance;
    }

    public void createOverallProgress(String username, int score) {
        progressDao.createOverallProgress(username, score);
    }

    // Other methods related to Progress database operations
}
