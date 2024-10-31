package Main;

import Controller.UserController;
import DAO.UserDaoImpl;
import View.QuadLingoQui;

import java.util.Locale;
import java.util.ResourceBundle;


// This launches the whole program
public class Main {
    public static void main(String[] args) throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        UserController.getInstance(userDao);
        QuadLingoQui.launch(QuadLingoQui.class, args);
    }
}