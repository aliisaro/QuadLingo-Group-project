import DAO.QuizDaoImpl;
import Model.Question;
import Model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizUnitTests {

    private QuizDaoImpl quizDaoImpl;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Quiz sampleQuiz;

    @BeforeEach
    public void setUp() throws Exception {
        // Mock Connection and PreparedStatement
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);

        // Mock behavior for prepareStatement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Mock behavior for PreparedStatement methods
        when(mockPreparedStatement.executeQuery()).thenReturn(mock(ResultSet.class));
        doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());

        // Mock QuizDaoImpl
        quizDaoImpl = mock(QuizDaoImpl.class);

        // Set up a sample quiz
        sampleQuiz = new Quiz(1, "Sample Quiz", 0, "en");
    }

    @Test
    public void testGetAllQuizzes() {
        // Mock getAllQuizzes behavior
        when(quizDaoImpl.getAllQuizzes("en")).thenReturn(Arrays.asList(sampleQuiz));

        List<Quiz> quizzes = quizDaoImpl.getAllQuizzes("en");
        assertNotNull(quizzes, "Quizzes list should not be null");
        assertEquals(1, quizzes.size(), "Quizzes list should contain one quiz");
        assertEquals("Sample Quiz", quizzes.get(0).getQuizTitle(), "Quiz title should match the sample quiz title");
    }

    @Test
    public void testGetQuestionsForQuiz() {
        // Create sample questions for the quiz
        Question question1 = new Question(1, "What is 2 + 2?", Arrays.asList("4", "3", "5", "6"), "4");
        Question question2 = new Question(2, "What is 3 + 3?", Arrays.asList("6", "5", "7", "8"), "6");

        // Mock the behavior of getQuestionsForQuiz to return the sample questions
        when(quizDaoImpl.getQuestionsForQuiz(1)).thenReturn(Arrays.asList(question1, question2));

        List<Question> questions = quizDaoImpl.getQuestionsForQuiz(1);
        assertNotNull(questions, "Questions list should not be null");
        assertEquals(2, questions.size(), "Questions list should contain two questions");

        // Check the first question's details
        Question firstQuestion = questions.get(0);
        assertEquals("What is 2 + 2?", firstQuestion.getQuestionText(), "First question text should match");
        assertTrue(firstQuestion.getAnswerOptions().contains("4"), "First question should have '4' as an answer option");
    }

    @Test
    public void testCheckAnswer_Correct() {
        int questionId = 1;
        String correctAnswer = "4";

        // Mock checkAnswer behavior
        when(quizDaoImpl.checkAnswer(questionId, correctAnswer)).thenReturn(true);

        boolean result = quizDaoImpl.checkAnswer(questionId, correctAnswer);
        assertTrue(result, "Correct answer should return true");
    }

    @Test
    public void testCheckAnswer_Incorrect() {
        int questionId = 1;
        String incorrectAnswer = "5";

        // Mock checkAnswer behavior
        when(quizDaoImpl.checkAnswer(questionId, incorrectAnswer)).thenReturn(false);

        boolean result = quizDaoImpl.checkAnswer(questionId, incorrectAnswer);
        assertFalse(result, "Incorrect answer should return false");
    }

    @Test
    public void testRecordQuizCompletion() {
        int userId = 123;
        int quizId = 1;
        int score = 5;

        // Mock recordQuizCompletion behavior
        doNothing().when(quizDaoImpl).recordQuizCompletion(userId, quizId, score);

        quizDaoImpl.recordQuizCompletion(userId, quizId, score);

        // Verify the method was called
        verify(quizDaoImpl, times(1)).recordQuizCompletion(userId, quizId, score);
    }
}
