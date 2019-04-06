package Model;

public class User {
    private int id;
    private String fullName;
    private String email;
    private String Password;

    public User() {
    }

    public User(int id, String fullName, String email, String Password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.Password = Password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}
