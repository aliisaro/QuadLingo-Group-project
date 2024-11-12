import DAO.QuizDaoImpl;
import DAO.UserDaoImpl;
import Database.MariaDbConnection;
import Model.Question;
import Model.Quiz;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizIntegrationTests {

    private QuizDaoImpl quizDaoImpl;
    private UserDaoImpl userDaoImpl;
    private Connection connection;
    private final int testQuizId = 1; // Use an existing quiz ID
    private final int sampleUserId = 1; // Use an existing user from the database
    private final String languageCode = "en"; // Set to match the quiz's language

    @BeforeEach
    void setUp() {
        connection = MariaDbConnection.getConnection();
        quizDaoImpl = new QuizDaoImpl(connection);
        userDaoImpl = UserDaoImpl.getInstance();

        // Remove any existing quiz completion record to start with a clean state
        quizDaoImpl.removeQuizCompletion(sampleUserId, testQuizId);
    }

    @Test
    void testRetrieveAllQuizzes() {
        List<Quiz> quizzes = quizDaoImpl.getAllQuizzes(languageCode);

        assertNotNull(quizzes, "Quizzes list should not be null.");
        assertTrue(quizzes.size() > 0, "Quizzes list should contain at least one quiz.");
        assertEquals("Basic Finnish Vocabulary", quizzes.get(0).getQuizTitle(), "The first quiz title should match the expected title.");
    }

    @Test
    void testQuestionLoading() {
        List<Question> questions = quizDaoImpl.getQuestionsForQuiz(testQuizId);
        assertNotNull(questions, "Questions list should not be null");
        assertTrue(questions.size() > 0, "Questions list should contain at least one question");

        for (Question question : questions) {
            assertTrue(question.getAnswerOptions().size() >= 2, "Each question should have at least two answer options");
            assertTrue(question.getAnswerOptions().contains(question.getCorrectAnswer()), "Correct answer should be among the options");
        }
    }

    @Test
    void testAnswerChecking() {
        int questionId = 1; // Example question ID
        String correctAnswer = "Hei"; // Known correct answer
        String incorrectAnswer = "Incorrect Answer"; // Incorrect answer

        assertTrue(quizDaoImpl.checkAnswer(questionId, correctAnswer), "Correct answer should return true");
        assertFalse(quizDaoImpl.checkAnswer(questionId, incorrectAnswer), "Incorrect answer should return false");
    }

    @Test
    void testQuizCompletion() {
        int expectedScore = 5;

        // Record quiz completion
        quizDaoImpl.recordQuizCompletion(sampleUserId, testQuizId, expectedScore);

        assertTrue(quizDaoImpl.hasUserCompletedQuiz(sampleUserId, testQuizId), "User should have completed the quiz");

        int actualScore = quizDaoImpl.getUserScoreForQuiz(sampleUserId, testQuizId);
        assertEquals(expectedScore, actualScore, "The recorded score should match the expected score based on correct answers");

        int anotherQuizId = 999;
        assertFalse(quizDaoImpl.hasUserCompletedQuiz(sampleUserId, anotherQuizId), "User should not have completed a different quiz");
    }

    @Test
    void testScoreCalculation() {
        int expectedScore = 5;

        quizDaoImpl.recordQuizCompletion(sampleUserId, testQuizId, expectedScore);

        int actualScore = quizDaoImpl.getUserScoreForQuiz(sampleUserId, testQuizId);
        assertEquals(expectedScore, actualScore, "The recorded score should match the expected score");
    }

    @Test
    void testHasUserCompletedSpecificQuiz() {
        quizDaoImpl.recordQuizCompletion(sampleUserId, testQuizId, 5);

        boolean hasCompleted = quizDaoImpl.hasUserCompletedQuiz(sampleUserId, testQuizId);
        assertTrue(hasCompleted, "User should have completed the specified quiz.");

        int anotherQuizId = 999;
        assertFalse(quizDaoImpl.hasUserCompletedQuiz(sampleUserId, anotherQuizId), "User should not have completed a different quiz.");
    }

    @Test
    void testRetrieveUserScoreForQuiz() {
        int expectedScore = 5;

        quizDaoImpl.recordQuizCompletion(sampleUserId, testQuizId, expectedScore);

        int actualScore = quizDaoImpl.getUserScoreForQuiz(sampleUserId, testQuizId);
        assertEquals(expectedScore, actualScore, "The retrieved score should match the expected score.");
    }

    @Test
    void testIncrementCompletedQuizzes() {
        // Step 1: Remove any existing completion records for a clean start
        quizDaoImpl.removeQuizCompletion(sampleUserId, testQuizId);

        // Step 2: Get the initial count of completed quizzes
        int initialCompletedCount = userDaoImpl.getQuizzesCompleted(sampleUserId, languageCode);

        // Step 3: Check if the user has already completed the quiz
        quizDaoImpl.incrementCompletedQuizzes(sampleUserId, testQuizId);

        // Step 4: Get the updated count of completed quizzes
        int updatedCompletedCount = userDaoImpl.getQuizzesCompleted(sampleUserId, languageCode);

        // Step 5: Verify the count incremented by 1 since the quiz was initially not completed
        assertEquals(initialCompletedCount + 1, updatedCompletedCount,
                "Completed quizzes count should increment if the quiz was not already completed.");

        // Step 6: Call increment again and verify the count does not increase further
        quizDaoImpl.incrementCompletedQuizzes(sampleUserId, testQuizId);
        int finalCompletedCount = userDaoImpl.getQuizzesCompleted(sampleUserId, languageCode);
        assertEquals(updatedCompletedCount, finalCompletedCount,
                "Completed quizzes count should not increment if the quiz was already completed.");
    }


    @AfterEach
    void tearDown() {
        // Clean up by removing quiz completion
        quizDaoImpl.removeQuizCompletion(sampleUserId, testQuizId);
        MariaDbConnection.terminate(connection);
    }
}
