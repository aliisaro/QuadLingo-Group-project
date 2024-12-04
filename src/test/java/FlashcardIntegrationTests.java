import dao.FCImplement;
import dao.FlashcardDao;
import dao.UserDaoImpl;
import database.MariaDbConnection;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FlashcardIntegrationTests {
    Connection connection = MariaDbConnection.getConnection();
    private FlashcardDao flashcardDao = new FCImplement(connection);
    private UserDaoImpl userDao = UserDaoImpl.getInstance();
    private final String testFlashcardTopic = "animals"; // Use an existing flashcard topic
    private int userId;
    private final String languageCode = "en"; // Set to match the flashcards' language
    private final String testEmail = "flashcardtest@test.com"; // Create a new email

    @BeforeEach
    public void setUp() {

        Boolean user = userDao.getUserByEmail(testEmail);
        if (user) {
            userDao.deleteUserByEmail(testEmail);
        }

        // Create a new user for testing
        userDao = UserDaoImpl.getInstance();
        User validUser = new User("flashcardTester", "Password123", testEmail);
        userId = userDao.createUser(validUser); // Save the created user's ID

        // Log in new user
        userDao.loginUser("flashcardTester", "Password123");
    }

    @Test
    void testRetrieveAllTopics() {

        // Retrieve all topics
        flashcardDao.getTopics(languageCode);

        // Check if the topics are not null
        assertNotNull(flashcardDao.getTopics(languageCode));

        // Check if there are 8 topics
        Assertions.assertEquals(8, flashcardDao.getTopics(languageCode).size());
    }

    @Test
    void testRetrieveAllTopicFlashcards() {

        // Retrieve all flashcards related to example topic
        flashcardDao.getFlashcardsByTopic(testFlashcardTopic, userId, languageCode);

        // Check if the flashcards are not null
        assertNotNull(flashcardDao.getFlashcardsByTopic(testFlashcardTopic, userId, languageCode));

        // Check if there are 2 flashcards
        Assertions.assertEquals(2, flashcardDao.getFlashcardsByTopic(testFlashcardTopic, userId, languageCode).size());

    }

    @Test
    void testMastering() {

        // Master a flashcard
        flashcardDao.masterFlashcard(1, 1);

        // Check if the flashcard is mastered
        Assertions.assertTrue(flashcardDao.hasUserMasteredSpecificFlashcard(1, 1));

    }

    @Test
    void testUnmastering() {

        // Unmaster a flashcard
        flashcardDao.unmasterFlashcard(1, 1);

        // Check if the flashcard is unmastered
        Assertions.assertFalse(flashcardDao.hasUserMasteredSpecificFlashcard(1, 1));
    }

    @Test
    void testUnmasteringAll() {

        // Master flashcards to unmaster
        flashcardDao.masterFlashcard(1, 1);
        flashcardDao.masterFlashcard(2, 1);

        // Unmaster all flashcards
        flashcardDao.unmasterAllFlashcards(1, languageCode);

        // Check if all flashcards are unmastered
        Assertions.assertFalse(flashcardDao.hasUserMasteredSpecificFlashcard(1, 1));
        Assertions.assertFalse(flashcardDao.hasUserMasteredSpecificFlashcard(2, 1));

    }

    @AfterEach
    void tearDown() {
        // Only attempt to delete the user if they exist in the database
        User user = userDao.getUserById(userId);
        if (user != null) {
            userDao.deleteUserByEmail(testEmail);
        }

    }

}
