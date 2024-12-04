package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class MariaDbConnection {
  private static final Dotenv dotenv = Dotenv.load();
  private static final String URL = "jdbc:mariadb://10.120.32.98/QuadLingo";
  private static final String USER = dotenv.get("DB_USER");
  private static final String PASSWORD = dotenv.get("DB_PASSWORD");

  public static Connection getConnection() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (SQLException e) {
      System.out.println("Connection failed.");
      e.printStackTrace();
    }
    return conn;
  }

  public static void terminate(Connection conn) {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}