import DAO.UserDaoImpl;
import Model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    private UserDaoImpl userDao;

    @BeforeEach
    void setUp() {
        userDao = UserDaoImpl.getInstance();
    }

    @Test
    void testCreateUser() {
        // Create a new user for testing
        User testUser = new User("testuser", "Password123", "testuser@example.com");

        // Call createUser and store the returned user ID
        int userId = userDao.createUser(testUser);

        // Check that the user ID returned is greater than 0, indicating success
        assertTrue(userId > 0, "User creation failed, user ID is not greater than 0");

        // Fetch the user from the database using the ID and check that it matches the inserted user
        User createdUser = userDao.getUserById(userId);

        assertNotNull(createdUser, "Failed to retrieve created user from the database");
        assertEquals(testUser.getUsername(), createdUser.getUsername(), "Usernames do not match");
        assertEquals(testUser.getEmail(), createdUser.getEmail(), "Emails do not match");
    }

    @AfterEach
    void tearDown() {
        // Clean up by deleting the user created during the test
        userDao.deleteUserByEmail("testuser@example.com");
    }
}
