package Main;

import Controller.UserController;
import DAO.UserDaoImpl;
import View.QuadLingoQui;


// This launches the whole program
public class  Main {
    public static void main(String[] args) throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        UserController userController = UserController.getInstance(userDao);
        QuadLingoQui.launch(QuadLingoQui.class, args);
    }
}