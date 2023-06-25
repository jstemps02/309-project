package com.example.todos.ui.login;

import com.example.todos.data.tools.Result;
import com.example.todos.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    /**
     * Private constructor
     * @param dataSource DataSoruce object
     */
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Returns instance of the current LoginRepository
     * @param dataSource datSource whos loginrepository is being returned
     * @return dataSources respective loginRepository
     */
    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if(instance == null){
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    /**
     *
     * @return Whether user is logged in
     */
    public boolean isLoggedIn() {
        return user != null;
    }

    /**
     * Sets current user to be the current logged in user
     * @param user User object being set as logged in
     */
    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    /**
     * Attempts to login the current user
     * @param username Users username
     * @param password User password
     * @return Result of login attempt
     */
    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}