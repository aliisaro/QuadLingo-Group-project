import DAO.*;
import Database.MariaDbConnection;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgressIntegrationTest {
    Connection connection = MariaDbConnection.getConnection();
    private FlashcardDao flashcardDao = new FCImplement(connection);
    private QuizDao quizDao = new QuizDaoImpl(connection);
    private UserDaoImpl userDao = UserDaoImpl.getInstance();
    private ProgressDao progressDao = new ProgressDaoImpl();
    private int userId;
    private final String languageCode = "en"; // Set to match the quiz's language
    private final String testEmail = "progresstest@test.com"; // Create a new email

    @BeforeEach
    public void setUp() {
        Boolean user = userDao.getUserByEmail(testEmail);
        if (user) {
            quizDao.deleteUserRecord(userId);
            flashcardDao.unmasterFlashcard(1, userId);
            userDao.deleteUserByEmail(testEmail);
        }

        // Create a new user for testing
        userDao = UserDaoImpl.getInstance();
        User validUser = new User("progressTester", "Password123", testEmail);
        userId = userDao.createUser(validUser); // Save the created user's ID

        // Log in new user
        userDao.loginUser("progressTester", "Password123");

        // User completes a quiz and masters a flashcards for testing other methods
        quizDao.recordQuizCompletion(userId, 1,5);
        flashcardDao.masterFlashcard(1, userId);

    }

    @Test
    public void testGetUserScore() {
        // Retrieve the user's score for a specific language
        int score = progressDao.getUserScore(userId, languageCode);
        assertEquals(5, score);

    }

    @Test
    public void testGetAllCompletedQuizzes() {
        // Retrieve the number of quizzes completed by the user for a specific language
        int completedQuizzes = progressDao.getAllCompletedQuizzes(userId, languageCode);
        assertEquals(1, completedQuizzes);
    }

    @Test
    public void testGetQuizAmount() {
        // Retrieve the total number of quizzes available for a specific language
        int quizAmount = progressDao.getQuizAmount(languageCode);
        assertEquals(10, quizAmount);

    }

    @Test
    public void testGetMaxScore() {
        // Retrieve the maximum score possible for a specific language
        int maxScore = progressDao.getMaxScore(languageCode);
        assertEquals(50, maxScore);

    }

    @Test
    public void testGetMasteredFlashcards() {
        // Retrieve the number of mastered flashcards for a specific language
        int masteredFlashcards = progressDao.getMasteredFlashcards(userId, languageCode);
        assertEquals(1, masteredFlashcards);

    }

    @Test
    public void testGetFlashcardAmount() {
        // Retrieve the total number of flashcards available for a specific language
        int flashcardAmount = progressDao.getFlashcardAmount(languageCode);
        assertEquals(33, flashcardAmount);

    }

    @AfterEach
    public void tearDown() {
        // Delete the user created for testing
        User user = userDao.getUserById(userId);
        if (user != null) {
            quizDao.deleteUserRecord(userId);
            flashcardDao.unmasterFlashcard(1, userId);
            userDao.deleteUserByEmail(testEmail);
        }

    }
}

