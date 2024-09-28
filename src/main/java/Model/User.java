package Model;

public class User {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private int quizzesCompleted;
    private boolean passwordChanged;

    // Constructor with ID (for existing users)
    public User(int userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Constructor without ID (for new users)
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userId = null;  // ID will be assigned by the database
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getQuizzesCompleted() {
        return quizzesCompleted;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password, boolean passwordChanged) {
        this.password = password;
        this.passwordChanged = passwordChanged;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setQuizzesCompleted(int quizzesCompleted) {
        this.quizzesCompleted = quizzesCompleted;
    }
}
