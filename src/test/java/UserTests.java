// File: src/test/java/DAO/UserDaoImplTest.java

import DAO.UserDaoImpl;
import Model.User;
import Controller.UserController;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

/**
 * Test class for UserDaoImpl.
 * Tests user creation, login, and update operations.
 */

public class UserTests {

    private UserDaoImpl userDao;
    private UserController userController;

    @BeforeEach
    public void setUp() {
        userDao = new UserDaoImpl();
        userController = UserController.getInstance(userDao);
    }


}
