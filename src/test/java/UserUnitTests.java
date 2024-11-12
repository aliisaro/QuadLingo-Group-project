import DAO.UserDaoImpl;
import Model.User;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;

// User tests that use mockito instead of actually interacting with the database

class UserUnitTests {

    @Mock
    private UserDaoImpl userDao; // Mocking the UserDaoImpl

    private final String testEmail = "testuser@example.com";
    private final String testUsername = "testUser";
    private final int testUserId = 1;

    private User validUser;
    private User invalidEmailUser;
    private User invalidPasswordUserNoUppercase;
    private User invalidPasswordUserNoDigit;
    private User invalidPasswordUserTooShort;
    private User existingEmailUser;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);  // This ensures the mocks are properly initialized

        // Valid user with userId
        validUser = new User(testUserId, testUsername, "Password123", testEmail);

        // Invalid email format
        invalidEmailUser = new User(testUserId, testUsername, "Password123", "invalidemailformat");

        // Invalid password users with userId
        invalidPasswordUserNoUppercase = new User(testUserId, testUsername, "password", testEmail);  // No uppercase letter
        invalidPasswordUserNoDigit = new User(testUserId, testUsername, "Password", testEmail);  // No digit
        invalidPasswordUserTooShort = new User(testUserId, testUsername, "Pass", testEmail);  // Too short (less than 8 characters)

        // Existing email user for email conflict test
        existingEmailUser = new User(testUserId, testUsername, "Password123", testEmail);
    }

    @Test
    void testCreateUser_Success() {
        when(userDao.createUser(validUser)).thenReturn(1); // Mock valid creation
        int userId = userDao.createUser(validUser);
        assertTrue(userId > 0, "User should be created successfully.");
    }

    @Test
    void testCreateUser_WithExistingEmail() {
        when(userDao.createUser(existingEmailUser)).thenReturn(-1);  // Simulate failure with existing email
        int userId = userDao.createUser(existingEmailUser);
        assertTrue(userId < 0, "User creation should fail with existing email.");
    }

    @Test
    void testCreateUser_WithInvalidEmailFormat() {
        when(userDao.createUser(invalidEmailUser)).thenReturn(-1);  // Simulate invalid email format
        int userId = userDao.createUser(invalidEmailUser);
        assertTrue(userId < 0, "User creation should fail with invalid email format.");
    }

    @Test
    void testCreateUser_WithInvalidPassword_NoUppercase() {
        when(userDao.createUser(invalidPasswordUserNoUppercase)).thenReturn(-1);  // Simulate invalid password (no uppercase)
        int userId = userDao.createUser(invalidPasswordUserNoUppercase);
        assertTrue(userId < 0, "User creation should fail with password missing uppercase letter.");
    }

    @Test
    void testCreateUser_WithInvalidPassword_NoDigit() {
        when(userDao.createUser(invalidPasswordUserNoDigit)).thenReturn(-1);  // Simulate password missing digit
        int userId = userDao.createUser(invalidPasswordUserNoDigit);
        assertTrue(userId < 0, "User creation should fail with password missing digit.");
    }

    @Test
    void testCreateUser_WithInvalidPassword_TooShort() {
        when(userDao.createUser(invalidPasswordUserTooShort)).thenReturn(-1);  // Simulate password too short
        int userId = userDao.createUser(invalidPasswordUserTooShort);
        assertTrue(userId < 0, "User creation should fail with a password that is too short.");
    }

    @Test
    void testLoginUser_Success() {
        when(userDao.loginUser(testUsername, "Password123")).thenReturn(validUser);
        User loggedInUser = userDao.loginUser(testUsername, "Password123");
        assertNotNull(loggedInUser, "Login should succeed with valid credentials.");
        assertEquals(testUsername, loggedInUser.getUsername(), "Username should match.");
        assertEquals(testEmail, loggedInUser.getEmail(), "Email should match.");
    }

    @Test
    void testLoginWithInvalidPassword() {
        when(userDao.loginUser(testUsername, "WrongPassword")).thenReturn(null);
        User loggedInUser = userDao.loginUser(testUsername, "WrongPassword");
        assertNull(loggedInUser, "Login should fail with incorrect password.");
    }

    @Test
    void testLoginWithInvalidUsername() {
        when(userDao.loginUser("nonexistent", "Password123")).thenReturn(null);
        User loggedInUser = userDao.loginUser("nonexistent", "Password123");
        assertNull(loggedInUser, "Login should fail with nonexistent username.");
    }

    @Test
    void testUpdateUser_Success() {
        User existingUser = new User(1, "existingUser", "Password123", "existing@example.com");
        when(userDao.getUserById(1)).thenReturn(existingUser);

        User updatedUser = new User(1, "updatedUser", "Password123", "existing@example.com");
        when(userDao.updateUser(updatedUser)).thenReturn(true);

        boolean result = userDao.updateUser(updatedUser);
        assertTrue(result, "Update should be successful.");
    }

    @Test
    void testDeleteUserByEmail_Success() {
        // Mock behavior for deleting a user by email
        when(userDao.deleteUserByEmail(testEmail)).thenReturn(true);  // Simulate successful deletion
        boolean result = userDao.deleteUserByEmail(testEmail);
        assertTrue(result, "User should be deleted successfully.");
    }

    @Test
    void testDeleteUserByEmail_Failure_UserNotFound() {
        // Simulate failure for non-existing user
        when(userDao.deleteUserByEmail("nonexistent@example.com")).thenReturn(false);  // Simulate failure
        boolean result = userDao.deleteUserByEmail("nonexistent@example.com");
        assertFalse(result, "Deletion should fail for non-existing user.");
    }

    @Test
    void testGetUserById_Success() {
        // Mocking the retrieval of a user by ID
        when(userDao.getUserById(testUserId)).thenReturn(validUser);

        User retrievedUser = userDao.getUserById(testUserId);

        assertNotNull(retrievedUser, "User should be retrieved successfully.");
        assertEquals(testUserId, retrievedUser.getUserId(), "User ID should match.");
        assertEquals(testUsername, retrievedUser.getUsername(), "Username should match.");
        assertEquals(testEmail, retrievedUser.getEmail(), "Email should match.");
    }

    @Test
    void testGetUserById_Failure() {
        // Simulating the case where no user is found for the given ID
        when(userDao.getUserById(testUserId)).thenReturn(null);

        User retrievedUser = userDao.getUserById(testUserId);

        assertNull(retrievedUser, "User should not be found for invalid ID.");
    }

    @Test
    void testDoesEmailExist_Exists() {
        when(userDao.doesEmailExist(testEmail)).thenReturn(true);
        boolean exists = userDao.doesEmailExist(testEmail);
        assertTrue(exists, "Email should exist in the database.");
    }

    @Test
    void testDoesEmailExist_NotExists() {
        when(userDao.doesEmailExist("nonexistent@example.com")).thenReturn(false);
        boolean exists = userDao.doesEmailExist("nonexistent@example.com");
        assertFalse(exists, "Email should not exist in the database.");
    }

    @Test
    void testGetQuizzesCompleted() {
        int expectedQuizzesCompleted = 5;
        when(userDao.getQuizzesCompleted(testUserId, "en")).thenReturn(expectedQuizzesCompleted);
        int quizzesCompleted = userDao.getQuizzesCompleted(testUserId, "en");
        assertEquals(expectedQuizzesCompleted, quizzesCompleted, "Retrieved quizzes completed should match.");
    }

    @Test
    void testGetFlashcardsMastered() {
        int expectedFlashcardsMastered = 10;
        when(userDao.getFlashcardsMastered(testUserId, "en")).thenReturn(expectedFlashcardsMastered);
        int flashcardsMastered = userDao.getFlashcardsMastered(testUserId, "en");
        assertEquals(expectedFlashcardsMastered, flashcardsMastered, "Retrieved mastered flashcards should match.");
    }

    @Test
    void testGetEmail() {
        when(userDao.getEmail()).thenReturn(testEmail);
        String retrievedEmail = userDao.getEmail();
        assertEquals(testEmail, retrievedEmail, "Retrieved email should match the user's email.");
    }

    @AfterEach
    void tearDown() {
        reset(userDao);  // Reset mocks after each test to ensure they are fresh for the next one
    }
}
