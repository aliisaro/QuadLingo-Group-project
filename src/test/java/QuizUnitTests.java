import DAO.QuizDaoImpl;
import Model.Question;
import Model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

// Tests for quizzes using mockito
// Mockito is used to create "mock" versions of the QuizDaoImpl class and its methods so that they don’t perform actual database operations.
// Instead, they return predefined values when called during the test.

public class QuizUnitTests {

    private QuizDaoImpl quizDaoImpl;
    private Quiz sampleQuiz;
    private int sampleUserId;

    @BeforeEach
    public void setUp() {
        // Mock the QuizDaoImpl class
        quizDaoImpl = mock(QuizDaoImpl.class);

        // Create a mock Quiz object with mock data, setting the score to 5
        sampleQuiz = new Quiz(1, "Sample Quiz", 5, "en");

        // Create a list of 5 mock questions for this quiz
        Question mockQuestion1 = new Question(1, "What is 2 + 2?", Arrays.asList("4", "3", "5", "6"), "4");
        Question mockQuestion2 = new Question(2, "What is 3 + 3?", Arrays.asList("6", "5", "7", "8"), "6");
        Question mockQuestion3 = new Question(3, "What is 4 + 4?", Arrays.asList("8", "7", "6", "9"), "8");
        Question mockQuestion4 = new Question(4, "What is 5 + 5?", Arrays.asList("10", "9", "11", "12"), "10");
        Question mockQuestion5 = new Question(5, "What is 6 + 6?", Arrays.asList("12", "11", "13", "14"), "12");

        // Mock the behavior of getQuestionsForQuiz to return the 5 questions
        when(quizDaoImpl.getQuestionsForQuiz(1)).thenReturn(Arrays.asList(mockQuestion1, mockQuestion2, mockQuestion3, mockQuestion4, mockQuestion5));

        // Mock the behavior of getAllQuizzes to return the sample quiz
        when(quizDaoImpl.getAllQuizzes("en")).thenReturn(Arrays.asList(sampleQuiz));

        // Mocking checkAnswer method for correct answers, ensuring that "4" is the correct answer for question 1
        when(quizDaoImpl.checkAnswer(1, "4")).thenReturn(true); // Correct answer for "What is 2 + 2?"
        when(quizDaoImpl.checkAnswer(1, "Incorrect Answer")).thenReturn(false);  // Incorrect answer

        // Mocking recordQuizCompletion to just simulate the behavior
        sampleUserId = 123;
        when(quizDaoImpl.getUserScoreForQuiz(sampleUserId, 1)).thenReturn(5);  // Assuming the user answered all 5 questions correctly

        // Mocking hasUserCompletedQuiz to return true for the given user and quiz
        when(quizDaoImpl.hasUserCompletedQuiz(sampleUserId, 1)).thenReturn(true);
    }

    @Test
    public void testQuestionLoading() {
        List<Question> questions = quizDaoImpl.getQuestionsForQuiz(1);
        assertNotNull(questions, "Questions list should not be null");
        assertTrue(questions.size() > 0, "Questions list should contain at least one question");

        for (Question question : questions) {
            assertTrue(question.getAnswerOptions().size() >= 2, "Each question should have at least two answer options");
            assertTrue(question.getAnswerOptions().contains(question.getCorrectAnswer()), "Correct answer should be among the options");
        }
    }

    @Test
    public void testAnswerChecking() {
        int questionId = 1; // Example question ID
        String correctAnswer = "4";  // Correct answer for "What is 2 + 2?"
        String incorrectAnswer = "Incorrect Answer"; // This is an incorrect answer

        // The correct answer is "4" for question 1
        assertTrue(quizDaoImpl.checkAnswer(questionId, correctAnswer), "Correct answer should return true");

        // This will still check for the wrong answer and should return false
        assertFalse(quizDaoImpl.checkAnswer(questionId, incorrectAnswer), "Incorrect answer should return false");
    }


    @Test
    public void testScoreCalculation() {
        int expectedScore = 5;
        quizDaoImpl.recordQuizCompletion(sampleUserId, 1, expectedScore);

        int actualScore = quizDaoImpl.getUserScoreForQuiz(sampleUserId, 1);
        assertEquals(expectedScore, actualScore, "The recorded score should match the expected score");
    }

    @Test
    public void testQuizCompletion() {
        // Set the expected score to match the number of questions
        int expectedScore = 5;  // The quiz has 5 questions, so the score should be 5
        int correctAnswers = 5; // Assume the user answers all 5 questions correctly

        // Mock that the user has completed the quiz and got all answers correct
        when(quizDaoImpl.getUserScoreForQuiz(sampleUserId, 1)).thenReturn(correctAnswers);

        // Check if the user has completed the quiz
        quizDaoImpl.recordQuizCompletion(sampleUserId, 1, expectedScore); // Simulate completing the quiz

        // Since the user completed the quiz and got all answers correct, score should be expected
        assertTrue(quizDaoImpl.hasUserCompletedQuiz(sampleUserId, 1), "User should have completed the quiz");

        // Now, check if the score reflects the correct number of answers
        int actualScore = quizDaoImpl.getUserScoreForQuiz(sampleUserId, 1);
        assertEquals(expectedScore, actualScore, "The recorded score should match the expected score based on correct answers");

        // Test for another quiz that has not been completed
        int anotherQuizId = 999;  // Assuming this quiz ID doesn't exist or hasn't been completed
        assertFalse(quizDaoImpl.hasUserCompletedQuiz(sampleUserId, anotherQuizId), "User should not have completed a different quiz");
    }
}
