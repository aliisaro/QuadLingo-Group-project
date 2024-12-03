import Dao.QuizDaoImpl;
import Model.Question;
import Model.Quiz;
import Database.MariaDbConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizIntegrationTests {

    private QuizDaoImpl quizDaoImpl;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        // Obtain a real connection using MariaDbConnection
        connection = MariaDbConnection.getConnection();
        quizDaoImpl = new QuizDaoImpl(connection);

        // Clear relevant data to avoid interference from previous tests
        clearSampleData();

        // Insert sample data needed for testing
        insertSampleData();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Clear sample data after each test to reset the state
        clearSampleData();
        MariaDbConnection.terminate(connection);
    }

    private void insertSampleData() throws SQLException {
        // Insert a test user in LINGOUSER with the column order: UserID, Email, Username, Password
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO LINGOUSER (UserID, Email, Username, UserPassword) VALUES (100, 'testuser@example.com', 'TestUser', 'TestPassword123') " +
                        "ON DUPLICATE KEY UPDATE Email='testuser@example.com', Username='TestUser', UserPassword='TestPassword123'")) {
            stmt.executeUpdate();
        }

        // Insert sample data into QUIZ table
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO QUIZ (QuizID, QuizTitle, QuizScore, language_code) VALUES (100, 'Sample Quiz', 1, 'en')")) {
            stmt.executeUpdate();
        }

        // Insert sample question data
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO QUESTION (QuestionID, QuizID, Question) VALUES (100, 100, 'What is 2 + 2?')")) {
            stmt.executeUpdate();
        }

        // Insert sample answer data
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO ANSWER (AnswerID, Answer) VALUES (100, '4'), (101, '5'), (102, '6')")) {
            stmt.executeUpdate();
        }

        // Link correct answer to the question
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO CORRECTANSWER (QuestionID, AnswerID) VALUES (100, 100)")) {
            stmt.executeUpdate();
        }
    }


    private void clearSampleData() throws SQLException {
        // Delete data related to the tests to avoid database pollution
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM ISCOMPLETED WHERE UserID = 100 AND QuizID = 100")) {
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM CORRECTANSWER WHERE QuestionID = 100")) {
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM ANSWER WHERE AnswerID IN (100, 101, 102)")) {
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM QUESTION WHERE QuestionID = 100")) {
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM QUIZ WHERE QuizID = 100")) {
            stmt.executeUpdate();
        }
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM LINGOUSER WHERE UserID = 100")) {
            stmt.executeUpdate();
        }
    }

    @Test
    public void testGetAllQuizzes() throws SQLException {
        List<Quiz> quizzes = quizDaoImpl.getAllQuizzes("en");
        assertNotNull(quizzes, "Quizzes list should not be null");
        assertTrue(quizzes.size() > 0, "Quizzes list should contain at least one quiz");
    }


    @Test
    public void testGetQuestionsForQuiz() {
        List<Question> questions = quizDaoImpl.getQuestionsForQuiz(100);
        assertNotNull(questions, "Questions list should not be null");
        assertEquals(1, questions.size(), "Questions list should contain one question");

        Question firstQuestion = questions.get(0);
        assertEquals("What is 2 + 2?", firstQuestion.getQuestionText(), "First question text should match");
        assertTrue(firstQuestion.getAnswerOptions().contains("4"), "First question should have '4' as an answer option");
    }

    @Test
    public void testCheckAnswer_Correct() {
        boolean result = quizDaoImpl.checkAnswer(100, "4");
        assertTrue(result, "Correct answer should return true");
    }

    @Test
    public void testCheckAnswer_Incorrect() {
        boolean result = quizDaoImpl.checkAnswer(100, "5");
        assertFalse(result, "Incorrect answer should return false");
    }

    @Test
    public void testRecordQuizCompletion() throws SQLException {
        int userId = 100;
        int quizId = 100;
        int score = 1;

        quizDaoImpl.recordQuizCompletion(userId, quizId, score);

        String query = "SELECT Score FROM ISCOMPLETED WHERE UserID = ? AND QuizID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, quizId);
            ResultSet rs = pstmt.executeQuery();

            assertTrue(rs.next(), "The record should exist in ISCOMPLETED");
            assertEquals(score, rs.getInt("Score"), "The recorded score should match the expected score");
        }
    }
}
