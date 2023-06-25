package com.example.todos.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;

    /**
     * A user object. Contains a name and id for reference
     * @param userId Random 12 digit ID for unique reference
     * @param displayName Public Username
     */
    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    /**
     * ID Get
     * @return Users unique 12 digit ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Username Get
     * @return Users Username
     */
    public String getDisplayName() {
        return displayName;
    }
}