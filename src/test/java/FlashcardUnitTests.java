import dao.FlashcardDao;
import model.Flashcard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FlashcardUnitTests {

    @Mock
    private FlashcardDao flashcardDao;

    private final String testFlashcardTopic = "animals";
    private final String languageCode = "en";
    private final int userId = 1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testRetrieveAllTopics() {
        // Arrange
        List<Flashcard> mockTopics = Arrays.asList(
                new Flashcard("Lion", "Leijona", "animals"),
                new Flashcard("Soccer", "Jalkapallo", "sports"),
                new Flashcard("Physics", "Fysiikka", "science")
        );
        when(flashcardDao.getTopics(languageCode)).thenReturn(mockTopics);

        // Act
        List<Flashcard> topics = flashcardDao.getTopics(languageCode);

        // Assert
        assertNotNull(topics);
        assertEquals(3, topics.size());
        assertEquals("animals", topics.get(0).getTopic());
        verify(flashcardDao, times(1)).getTopics(languageCode);
    }

    @Test
    void testRetrieveAllTopicFlashcards() {
        // Arrange
        List<Flashcard> mockFlashcards = Arrays.asList(
                new Flashcard("Lion", "Leijona", "animals"),
                new Flashcard("Elephant", "Norsu", "animals")
        );
        when(flashcardDao.getFlashcardsByTopic(testFlashcardTopic, userId, languageCode)).thenReturn(mockFlashcards);

        // Act
        List<Flashcard> flashcards = flashcardDao.getFlashcardsByTopic(testFlashcardTopic, userId, languageCode);

        // Assert
        assertNotNull(flashcards);
        assertEquals(2, flashcards.size());
        assertEquals("Lion", flashcards.get(0).getTerm());
        assertEquals("Leijona", flashcards.get(0).getTranslation());
        verify(flashcardDao, times(1)).getFlashcardsByTopic(testFlashcardTopic, userId, languageCode);
    }

    @Test
    void testMastering() {
        // Arrange
        int flashcardId = 1;
        when(flashcardDao.hasUserMasteredSpecificFlashcard(flashcardId, userId)).thenReturn(true);

        // Act
        flashcardDao.masterFlashcard(flashcardId, userId);
        boolean isMastered = flashcardDao.hasUserMasteredSpecificFlashcard(flashcardId, userId);

        // Assert
        assertTrue(isMastered);
        verify(flashcardDao, times(1)).masterFlashcard(flashcardId, userId);
        verify(flashcardDao, times(1)).hasUserMasteredSpecificFlashcard(flashcardId, userId);
    }

    @Test
    void testUnmastering() {
        // Arrange
        int flashcardId = 1;
        when(flashcardDao.hasUserMasteredSpecificFlashcard(flashcardId, userId)).thenReturn(false);

        // Act
        flashcardDao.unmasterFlashcard(flashcardId, userId);
        boolean isMastered = flashcardDao.hasUserMasteredSpecificFlashcard(flashcardId, userId);

        // Assert
        assertFalse(isMastered);
        verify(flashcardDao, times(1)).unmasterFlashcard(flashcardId, userId);
        verify(flashcardDao, times(1)).hasUserMasteredSpecificFlashcard(flashcardId, userId);
    }

    @Test
    void testUnmasteringAll() {
        // Arrange
        int flashcardId1 = 1;
        int flashcardId2 = 2;
        when(flashcardDao.hasUserMasteredSpecificFlashcard(flashcardId1, userId)).thenReturn(false);
        when(flashcardDao.hasUserMasteredSpecificFlashcard(flashcardId2, userId)).thenReturn(false);

        // Act
        flashcardDao.unmasterAllFlashcards(userId, languageCode);
        boolean isUnmastered1 = flashcardDao.hasUserMasteredSpecificFlashcard(flashcardId1, userId);
        boolean isUnmastered2 = flashcardDao.hasUserMasteredSpecificFlashcard(flashcardId2, userId);

        // Assert
        assertFalse(isUnmastered1);
        assertFalse(isUnmastered2);
        verify(flashcardDao, times(1)).unmasterAllFlashcards(userId, languageCode);
        verify(flashcardDao, times(1)).hasUserMasteredSpecificFlashcard(flashcardId1, userId);
        verify(flashcardDao, times(1)).hasUserMasteredSpecificFlashcard(flashcardId2, userId);
    }
}
