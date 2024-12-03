import Dao.QuizDao;
import Model.Question;
import Model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class QuizUnitTests {
    @Mock
    private QuizDao quizDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testGetAllQuizzes() {
        // Arrange
        String language = "English";
        List<Quiz> mockQuizzes = Arrays.asList(
                new Quiz(1, "Quiz 1", 5, "en"),
                new Quiz(2, "Quiz 2", 5, "en"),
                new Quiz(3, "Quiz 3", 5, "en")
        );
        when(quizDao.getAllQuizzes(language)).thenReturn(mockQuizzes);

        // Act
        List<Quiz> quizzes = quizDao.getAllQuizzes(language);

        // Assert
        assertNotNull(quizzes);
        assertEquals(3, quizzes.size());
        assertEquals("Quiz 1", quizzes.get(0).getQuizTitle());
        verify(quizDao, times(1)).getAllQuizzes(language);
    }

    @Test
    void testGetQuestionsForQuiz() {
        // Arrange
        int quizId = 1;
        List<String> answers = Arrays.asList("Option A", "Option B", "Option C", "Option D");
        List<Question> mockQuestions = Arrays.asList(
                new Question(1, "Question 1", answers, "Option A"),
                new Question(2, "Question 2", answers, "Option B")
        );
        when(quizDao.getQuestionsForQuiz(quizId)).thenReturn(mockQuestions);

        // Act
        List<Question> questions = quizDao.getQuestionsForQuiz(quizId);

        // Assert
        assertNotNull(questions);
        assertEquals(2, questions.size());
        assertEquals("Question 1", questions.get(0).getQuestionText());
        verify(quizDao, times(1)).getQuestionsForQuiz(quizId);
    }

    @Test
    void testCheckAnswer_Correct() {
        // Arrange
        int questionId = 1;
        String correctAnswer = "Option A";

        // Mock the behavior for the correct answer
        when(quizDao.checkAnswer(questionId, correctAnswer)).thenReturn(true);

        // Act
        boolean isCorrect = quizDao.checkAnswer(questionId, correctAnswer);

        // Assert
        assertTrue(isCorrect, "The correct answer should return true.");
        verify(quizDao, times(1)).checkAnswer(questionId, correctAnswer);
    }

    @Test
    void testCheckAnswer_Incorrect() {
        // Arrange
        int questionId = 1;
        String wrongAnswer = "Option B";

        // Mock the behavior for the wrong answer
        when(quizDao.checkAnswer(questionId, wrongAnswer)).thenReturn(false);

        // Act
        boolean isIncorrect = quizDao.checkAnswer(questionId, wrongAnswer);

        // Assert
        assertFalse(isIncorrect, "The incorrect answer should return false.");
        verify(quizDao, times(1)).checkAnswer(questionId, wrongAnswer);
    }

    @Test
    void testRecordQuizCompletion() {
        // Arrange
        int userId = 1;
        int quizId = 1;
        int score = 5;

        // Act
        quizDao.recordQuizCompletion(userId, quizId, score);

        // Assert
        verify(quizDao, times(1)).recordQuizCompletion(userId, quizId, score);
    }
}
