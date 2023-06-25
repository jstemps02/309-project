package com.example.todos.data.tools;

// Current users:
// bababooey
// dea99b7488effac3b2c33fa280302e04
//
//
//

/**
 * Data class that captures information for a specific user
 */
public class OtherUser {

    private int id;
    private String username;
    private String password; // This probably is not remotely needed and would be very insecure if used... :(
    private boolean isActive;
    private Stack[] stacks;
    private boolean loggedIn;
    private ToDo[] todos;

    /**
     * Creates a singleToDo object
     * @param id
     * @param password
     * @param isActive
     * @param stacks
     * @param username
     * @param isActive
     * @param todos
     */
    public OtherUser(int id, String username, String password, boolean isActive, Stack[] stacks, boolean loggedIn, ToDo[] todos) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.stacks = stacks;
        this.loggedIn = loggedIn;
        this.todos = todos;
    }

    /**
     * ID Get
     * @return ID of the singleToDo
     */
    public int getId() {
        return id;
    }

    /**
     * Title Get
     * @return Users Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Stacks Get
     * @return Array of Stacks associated with the user
     */
    public Stack[] getStacks() {
        return stacks;
    }

    /**
     * IsActive Get
     * @return Current account status of the user
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * LoggedIn Get
     * @return Current login status of the user
     */
    public boolean getLoggedIn() {
        return loggedIn;
    }


    /**
     * ToDos Get
     * @return Array of ToDos in the stack
     */
    public ToDo[] getToDos() {
        return todos;
    }
}
