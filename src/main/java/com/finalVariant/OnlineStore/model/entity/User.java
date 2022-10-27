package com.finalVariant.OnlineStore.model.entity;

public class User {
    private long id;
    private String login;
    private String password;
    private Role role;
    private UserStatus status;

    private User(String login, String password, Role role, UserStatus status){
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public static User createUser(String login, String password, Role role, UserStatus status){
        return new User(login, password, role, status);
    }

    public static User createUser(long id, String login, String password, Role role, UserStatus status){
        User user = new User(login, password, role, status);
        user.setId(id);
        return user;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    public UserStatus getStatus() { return status; }

    public void setStatus(UserStatus status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if(o instanceof User){
            return login.equals(((User) o).login) && password.equals(((User) o).password);
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password is PROTECTED"  +
                ", role=" + role +
                '}';
    }

    public enum Role{
        Admin, User, Guest
    }

    public enum UserStatus{
        Blocked, Unblocked
    }
}
