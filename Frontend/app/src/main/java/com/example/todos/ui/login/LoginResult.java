package  com.example.todos.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    /**
     * Error of this class becomes the error passed in
     * @param error Error that will be placed in this class
     */
    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    /**
     * Sets this class to be a success
     * @param success Success message that is saved in this class
     */
    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    /**
     * Returns whether this login attempt was a success
     * @return Success state
     */
    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }
    /**
     * Returns whether this login attempt was a failure
     * @return Error state
     */
    @Nullable
    Integer getError() {
        return error;
    }
}