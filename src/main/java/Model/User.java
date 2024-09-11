package Model;

public class User {
    //TODO: According to Copilot: "This class would represent a user of your application. It could contain fields such as username, password, email, progress, etc"

    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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
}
