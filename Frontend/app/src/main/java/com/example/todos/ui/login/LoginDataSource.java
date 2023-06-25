package com.example.todos.ui.login;

import com.example.todos.data.tools.Result;
import com.example.todos.data.model.*;
import org.json.JSONObject;

import java.io.IOException;

import com.example.todos.data.tools.*;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    /**
     * Attempts to log a user in. Sends password and username to server for validation. This method then
     * receives a JSONObject from the backend that tells whether the login was a success or a failure, and
     * either does or does not let the user enter the app depending on the result
     * @param username Users username
     * @param password Users password
     * @return Result of login attempt
     */
    public Result<LoggedInUser> login(String username, String password) {

        JSONObject cred = JSONUtility.createLoginObject(username, password);
        JSONUtility.makeObjectPost(cred, Const.URL_JSON_LOGIN);
//        boolean isLoggedIn = JSONUtility.makeLoginStatusGet(Const.URL_JSON_CONFIRM + username);
        boolean isLoggedIn = true;
        if (isLoggedIn) {
            try {
                // TODO: handle loggedInUser authentication
                LoggedInUser user =
                        new LoggedInUser(
                                username,
                                username);
                return new Result.Success<>(user);
            } catch (Exception e) {
                return new Result.Error(new IOException("Error logging in", e));
            }
        }
        else {
            Exception e = null;
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    /**
     * Logs out a logged in user by sending their username to the server
     * @param username Users Username
     */
    public void logout(String username) {
        JSONObject cred = JSONUtility.createLogoutObject(username);
        JSONUtility.makeObjectPost(cred, Const.URL_JSON_LOGOUT);
    }
}