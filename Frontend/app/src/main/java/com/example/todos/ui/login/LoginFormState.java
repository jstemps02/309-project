package com.example.todos.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
public class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    /**
     * Constructor that creates a logingformstate
     * @param usernameError Error of username if their is an issue
     * @param passwordError Error of password if their is an issue
     */
    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    /**
     * Sets the errors to null and the data to the boolean passed to the function
     * @param isDataValid Boolean that describes the validity of the data
     */
    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    /**
     *
     * @return What the usernames error is
     */
    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    /**
     *
     * @return What the passwords error is
     */
    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    /**
     *
     * @return Whether the data is valid
     */
    public boolean isDataValid() {
        return isDataValid;
    }
}