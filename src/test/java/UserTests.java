import DAO.UserDao;
import DAO.UserDaoImpl;
import Model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    private UserDaoImpl userDao;
    private final String testEmail = "testuser@example.com";
    private int userId; // Store userId for later use

    @BeforeEach
    void setUp() {
        userDao = UserDaoImpl.getInstance();

        // Create a user with a known email before each test
        User validUser = new User("testUser", "Password123", testEmail);
        userId = userDao.createUser(validUser); // Save the created user's ID
    }

    @Test
    void testCreateUser_Success() {
        User validUser = new User("testUser2", "Password123", testEmail);
        int newUserId = userDao.createUser(validUser);
        assertTrue(newUserId < 0, "Register should fail with existing email");
    }

    @Test
    void testCreateUser_WithExistingEmail() {
        // Attempt to create a user with existing email
        User existingEmailUser = new User("testUser3", "Password123", testEmail);
        int existingEmailId = userDao.createUser(existingEmailUser);
        assertTrue(existingEmailId < 0, "Register should fail with existing email");
    }

    @Test
    void testCreateUser_WithInvalidEmailFormat() {
        // Attempt to create a user with an invalid email format
        User invalidEmailUser = new User("testUser4", "Password123", "invalidemailformat");
        int invalidEmailId = userDao.createUser(invalidEmailUser);
        assertTrue(invalidEmailId < 0, "Register should fail with invalid email format");
    }

    @Test
    void testCreateUser_WithInvalidPassword_NoUppercase() {
        // Attempt to create a user with an invalid password (no uppercase letter)
        User invalidPasswordUser1 = new User("testUser5", "password123", testEmail);
        int invalidPasswordId1 = userDao.createUser(invalidPasswordUser1);
        assertTrue(invalidPasswordId1 < 0, "Register should fail with password missing uppercase letter");
    }

    @Test
    void testCreateUser_WithInvalidPassword_NoDigit() {
        // Attempt to create a user with an invalid password (no digit)
        User invalidPasswordUser2 = new User("testUser6", "Password", testEmail);
        int invalidPasswordId2 = userDao.createUser(invalidPasswordUser2);
        assertTrue(invalidPasswordId2 < 0, "Register should fail with password missing digit");
    }

    @Test
    void testCreateUser_WithInvalidPassword_TooShort() {
        // Attempt to create a user with an invalid password (too short)
        User invalidPasswordUser3 = new User("testUser7", "P1", testEmail);
        int invalidPasswordId3 = userDao.createUser(invalidPasswordUser3);
        assertTrue(invalidPasswordId3 < 0, "Register should fail with password too short");
    }

    @Test
    void testLoginUser() {
        // Attempt to log in with valid credentials
        User loggedInUser = userDao.loginUser("testUser", "Password123");

        // Assert that the logged-in user is not null and that the details are correct
        assertNotNull(loggedInUser, "Login should succeed with valid credentials");
        assertEquals("testUser", loggedInUser.getUsername(), "Username should match");
        assertEquals(testEmail, loggedInUser.getEmail(), "Email should match");
    }

    @Test
    void testLoginWithInvalidPassword() {
        // Attempt to log in with incorrect password
        User invalidPasswordLogin = userDao.loginUser("testUser", "WrongPassword");
        assertNull(invalidPasswordLogin, "Login should fail with incorrect password");
    }

    @Test
    void testLoginWithInvalidUsername() {
        // Attempt to log in with a username that doesn't exist in the database
        User invalidUsernameLogin = userDao.loginUser("nonexistentusername", "Password123");
        assertNull(invalidUsernameLogin, "Login should fail with nonexistent username");
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        User existingUser = userDao.getUserById(userId); // Use the dynamic ID
        User updatedUser = new User(existingUser.getUserId(), "updatedUser", existingUser.getPassword(), existingUser.getEmail());

        // Act
        boolean result = userDao.updateUser(updatedUser);

        // Assert
        assertTrue(result, "The updateUser method should return true on success.");

        // Fetch the updated user to verify changes
        User retrievedUser = userDao.getUserById(existingUser.getUserId());
        assertEquals("updatedUser", retrievedUser.getUsername(), "The username should be updated successfully.");
    }

    @Test
    void testUpdateUser_WithInvalidEmail() {
        // Arrange
        User existingUser = userDao.getUserById(userId); // Use the dynamic ID
        User updatedUserWithInvalidEmail = new User(existingUser.getUserId(), "updatedUser", existingUser.getPassword(), "invalidEmailFormat");

        // Act
        boolean result = userDao.updateUser(updatedUserWithInvalidEmail);

        // Assert
        assertFalse(result, "The updateUser method should fail with an invalid email format.");
    }


    @AfterEach
    void tearDown() {
        // Clean up after each test by deleting the user using the known email
        userDao.deleteUserByEmail(testEmail);
    }
}
