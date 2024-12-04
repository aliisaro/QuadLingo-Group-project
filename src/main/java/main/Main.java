package main;

import controller.UserController;
import dao.UserDaoImpl;
import view.QuadLingoGui;

// This launches the whole program
public class Main {
  public static void main(String[] args) throws Exception {
    UserDaoImpl userDao = new UserDaoImpl();
    UserController.getInstance(userDao);
    QuadLingoGui.launch(QuadLingoGui.class, args);
  }
}