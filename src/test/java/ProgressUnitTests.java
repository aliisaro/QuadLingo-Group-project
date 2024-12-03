import Dao.ProgressDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProgressUnitTests {
    @Mock
    private ProgressDao progressDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testGetUserScore() {
        // Arrange
        int userId = 1;
        String language = "en";
        int expectedScore = 10;

        when(progressDao.getUserScore(userId, language)).thenReturn(expectedScore);

        // Act
        int actualScore = progressDao.getUserScore(userId, language);

        // Assert
        assertEquals(expectedScore, actualScore);
        verify(progressDao, times(1)).getUserScore(userId, language);
    }

    @Test
    void testGetAllCompletedQuizzes() {
        // Arrange
        int userId = 1;
        String language = "en";
        int expectedCount = 10;

        when(progressDao.getAllCompletedQuizzes(userId, language)).thenReturn(expectedCount);

        // Act
        int actualCount = progressDao.getAllCompletedQuizzes(userId, language);

        // Assert
        assertEquals(expectedCount, actualCount);
        verify(progressDao, times(1)).getAllCompletedQuizzes(userId, language);
    }

    @Test
    void testGetQuizAmount() {
        // Arrange
        String language = "en";
        int expectedQuizCount = 10;

        when(progressDao.getQuizAmount(language)).thenReturn(expectedQuizCount);

        // Act
        int actualQuizCount = progressDao.getQuizAmount(language);

        // Assert
        assertEquals(expectedQuizCount, actualQuizCount);
        verify(progressDao, times(1)).getQuizAmount(language);
    }

    @Test
    void testGetMaxScore() {
        // Arrange
        String language = "en";
        int expectedMaxScore = 50;

        when(progressDao.getMaxScore(language)).thenReturn(expectedMaxScore);

        // Act
        int actualMaxScore = progressDao.getMaxScore(language);

        // Assert
        assertEquals(expectedMaxScore, actualMaxScore);
        verify(progressDao, times(1)).getMaxScore(language);
    }

    @Test
    void testGetMasteredFlashcards() {
        // Arrange
        int userId = 1;
        String language = "en";
        int expectedMasteredCount = 10;

        when(progressDao.getMasteredFlashcards(userId, language)).thenReturn(expectedMasteredCount);

        // Act
        int actualMasteredCount = progressDao.getMasteredFlashcards(userId, language);

        // Assert
        assertEquals(expectedMasteredCount, actualMasteredCount);
        verify(progressDao, times(1)).getMasteredFlashcards(userId, language);
    }

    @Test
    void testGetFlashcardAmount() {
        // Arrange
        String language = "en";
        int expectedFlashcardCount = 33;

        when(progressDao.getFlashcardAmount(language)).thenReturn(expectedFlashcardCount);

        // Act
        int actualFlashcardCount = progressDao.getFlashcardAmount(language);

        // Assert
        assertEquals(expectedFlashcardCount, actualFlashcardCount);
        verify(progressDao, times(1)).getFlashcardAmount(language);
    }
}
