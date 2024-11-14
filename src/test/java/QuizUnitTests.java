import DAO.QuizDaoImpl;
import Model.Question;
import Model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizUnitTests {

    private QuizDaoImpl quizDaoImpl;
    private Connection mockConnection;
    private Quiz sampleQuiz;

    @BeforeEach
    public void setUp() {
        // Mock the Connection and QuizDaoImpl
        mockConnection = mock(Connection.class);
        quizDaoImpl = new QuizDaoImpl(mockConnection);

        // Create a mock Quiz object with sample data
        sampleQuiz = new Quiz(1, "Sample Quiz", 0, "en");
    }

    @Test
    public void testGetAllQuizzes() {
        // Mock the behavior of getAllQuizzes
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

        // Mock the behavior of checkAnswer to return true for the correct answer
        when(quizDaoImpl.checkAnswer(questionId, correctAnswer)).thenReturn(true);

        boolean result = quizDaoImpl.checkAnswer(questionId, correctAnswer);
        assertTrue(result, "Correct answer should return true");
    }

    @Test
    public void testCheckAnswer_Incorrect() {
        int questionId = 1;
        String incorrectAnswer = "5";

        // Mock the behavior of checkAnswer to return false for an incorrect answer
        when(quizDaoImpl.checkAnswer(questionId, incorrectAnswer)).thenReturn(false);

        boolean result = quizDaoImpl.checkAnswer(questionId, incorrectAnswer);
        assertFalse(result, "Incorrect answer should return false");
    }

    @Test
    public void testRecordQuizCompletion() {
        int userId = 123;
        int quizId = 1;
        int score = 5;

        // Use Mockito to simulate recordQuizCompletion behavior
        doNothing().when(quizDaoImpl).recordQuizCompletion(userId, quizId, score);

        // Call the method to check that it executes without errors
        quizDaoImpl.recordQuizCompletion(userId, quizId, score);

        // Verify that recordQuizCompletion was called with the correct parameters
        verify(quizDaoImpl, times(1)).recordQuizCompletion(userId, quizId, score);
    }
}
