package com.example.todos.data.tools;

/**
 * Class that contains a user object
 */
public class User {
    public String username;
    private String password;
    public boolean isLoggedIn;

    /**
     *Creates a logged in user
     * @param username Users username
     * @param password Users password
     *
     */
    public void User(String username, String password) {
        this.username = username;
        this.password = password;
        isLoggedIn = true;
    }

    /**
     * Sets a users username
     * @param username Users username
     */
    public void setUsername(String username) {this.username = username;};

    /**
     *                 Sets a users password
     * @param password Users password
     */
    public void setPassword(String password) {this.password = password;};
}
