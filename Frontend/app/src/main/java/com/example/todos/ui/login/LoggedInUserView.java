package  com.example.todos.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    /**
     * Shows the logged in user their name when logging in
     * @param displayName name to be displayed
     */
    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Simply returns name to be displayed
     * @return name to be displayed
     */
    String getDisplayName() {
        return displayName;
    }
}