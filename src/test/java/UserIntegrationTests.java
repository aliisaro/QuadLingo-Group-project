import Controller.UserController;
import DAO.UserDaoImpl;
import Model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UserIntegrationTests {

    private UserController userController;
    private UserDaoImpl userDao;
    private final String testEmail = "testuser@example.com";
    private int userId; // Store userId for later use

    @BeforeEach
    void setUp() {
        // Initialize UserDaoImpl and UserController for direct database interaction
        userDao = UserDaoImpl.getInstance();
        userController = new UserController(userDao);

        // Create a user with a known email before each test
        User validUser = new User("testUser", "Password123", testEmail);
        userId = userController.createUser("testUser", "Password123", testEmail).getUserId();
    }

    @Test
    void testCreateUser_Success() {
        User createdUser = userController.createUser("testUser2", "Password123", "unique@example.com");

        assertNotNull(createdUser, "User creation should succeed with unique email");
        assertTrue(createdUser.getUserId() > 0, "User ID should be greater than 0 for successfully created user");
    }

    @Test
    void testCreateUser_WithExistingEmail() {
        // Attempt to create a user with an email that already exists
        User createdUser = userController.createUser("testUser3", "Password123", testEmail);
        assertNull(createdUser, "User creation should fail with existing email");
    }

    @Test
    void testCreateUser_WithInvalidEmailFormat() {
        User createdUser = userController.createUser("testUser7", "Password123", "invalidemail");
        assertNull(createdUser, "User creation should fail with an invalid email format");
    }

    @Test
    void testCreateUser_InvalidPassword_NoUppercase() {
        User createdUser = userController.createUser("testUser4", "password123", "noupcase@example.com");
        assertNull(createdUser, "User creation should fail with a password that has no uppercase letter");
    }

    @Test
    void testCreateUser_InvalidPassword_NoDigit() {
        User createdUser = userController.createUser("testUser5", "Password", "nodigit@example.com");
        assertNull(createdUser, "User creation should fail with a password that has no digit");
    }

    @Test
    void testCreateUser_InvalidPassword_TooShort() {
        User createdUser = userController.createUser("testUser6", "Pass1", "shortpwd@example.com");
        assertNull(createdUser, "User creation should fail with a password that is too short");
    }

    @Test
    void testLoginUser() {
        // Attempt to log in with valid credentials
        User loggedInUser = userController.loginUser(testEmail, "Password123");

        assertNotNull(loggedInUser, "Login should succeed with valid credentials");
        assertEquals("testUser", loggedInUser.getUsername(), "Username should match");
        assertEquals(testEmail, loggedInUser.getEmail(), "Email should match");
    }

    @Test
    void testLoginWithInvalidPassword() {
        // Attempt to log in with incorrect password
        User invalidPasswordLogin = userController.loginUser(testEmail, "WrongPassword");
        assertNull(invalidPasswordLogin, "Login should fail with incorrect password");
    }

    @Test
    void testLoginWithInvalidEmail() {
        User loggedInUser = userController.loginUser("nonexistent@example.com", "Password123");
        assertNull(loggedInUser, "Login should fail with a nonexistent username");
    }

    @Test
    void testUpdateUser_Success() {
        // Retrieve the user and update username
        User existingUser = userController.getUserById(userId);
        User updatedUser = new User(existingUser.getUserId(), "updatedUser", existingUser.getPassword(), existingUser.getEmail());

        boolean result = userController.updateUser(updatedUser);
        assertTrue(result, "The updateUser method should return true on success");

        User retrievedUser = userController.getUserById(existingUser.getUserId());
        assertEquals("updatedUser", retrievedUser.getUsername(), "The username should be updated successfully");
    }

    @Test
    void testDeleteUserByEmail() {
        // Attempt to delete a user with the known test email
        boolean deletionResult = userController.deleteUserByEmail(testEmail);

        assertTrue(deletionResult, "User should be deleted successfully with valid email");

        // Try to retrieve the deleted user to ensure they are no longer in the system
        User deletedUser = userController.getUserById(userId);
        assertNull(deletedUser, "Deleted user should not be found in the system");
    }

    @Test
    void testGetUserById() {
        User retrievedUser = userController.getUserById(userId);

        assertNotNull(retrievedUser, "User should be retrieved successfully");
        assertEquals(userId, retrievedUser.getUserId(), "User ID should match");
        assertEquals("testUser", retrievedUser.getUsername(), "Username should match");
        assertEquals(testEmail, retrievedUser.getEmail(), "Email should match");
    }

    @AfterEach
    void tearDown() {
        // Attempt to delete both the main test user and the "unique@example.com" user
        if (userController.getUserById(userId) != null) {
            assertTrue(userController.deleteUserByEmail(testEmail), "User should be deleted successfully in tearDown");
        }
        // Clean up any "unique@example.com" entries in case of failure to delete in test
        User uniqueUser = userController.loginUser("unique@example.com", "Password123");
        if (uniqueUser != null) {
            assertTrue(userController.deleteUserByEmail("unique@example.com"), "Unique user should be deleted in tearDown");
        }
    }
}
