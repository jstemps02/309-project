package com.example.todos.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Patterns;

import com.example.todos.data.tools.Result;
import com.example.todos.data.model.LoggedInUser;
import com.example.todos.R;

/**
 * Class that is the model for the current loginview. Contains its data
 */
public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    public String response = null;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    /**
     * Creates a string that says whether the login attempt was a success or failure so it
     * can be easily parsed
     * @param username Users username
     * @param password Users password
     * @return String that tells if login attempt succeeded
     */
    public String login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
            response = "success";
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
            response = "failure";
        }

        return response;
    }

    /**
     * Determines if login data has been changed
     * @param username Users username
     * @param password Users password
     */
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    /**
     * Determines is sign up data has been changed and is valid
     * @param username Users username
     * @param password Users password
     * @param confirmation Users password confirmation
     */
    public void signUpDataChanged(String username, String password, String confirmation) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    /**
     * Determines if the user is attempting to use a valid username or email address
     * @param username Users username
     * @return Boolean that describes if username is valid
     */
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    /**
     * Determines if password is valid. It must be at least 6 characters
     * @param password Users username
     * @return Boolean that describes if password is valid
     */
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isConfirmationValid(String password, String confirmation) {
        if (password == confirmation) return true;
        else return false;
    }
}