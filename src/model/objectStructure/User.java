package model.objectStructure;

public class User {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String priority;
    private int theme;

    public User(String username, String password, String name, String surname, String email, String priority) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.priority = priority;
        this.theme = 1;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public String getPriority() {
        return priority;
    }

    public int getTheme() {
        return theme;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return username+","+password+","+name+","+surname+","+email+","+priority+","+ theme;
    }
}
