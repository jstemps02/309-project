package com.example.todos.lists;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.todos.R;
import com.example.todos.data.tools.JSONUtility;
import com.example.todos.data.tools.User;
import com.example.todos.databinding.ActivityLoginBinding;
import com.example.todos.databinding.ActivityNewuserBinding;
import com.example.todos.ui.login.LoginFormState;
import com.example.todos.ui.login.LoginViewModel;
import com.example.todos.ui.login.LoginActivity;
import com.example.todos.ui.login.LoginViewModelFactory;
import com.example.todos.data.tools.*;

import org.json.JSONObject;

/**
 * Specific activity regarding logging in and out a User. Extends AppCombatActivity which is a class
 * that handles much of this functionality
 */
public class NewUserActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    public String response = null;

    static public User user =  new User();

    static public TextView msg;
    
    static public String answer;
private ActivityNewuserBinding binding;

    /**
     * Creates login page for the user to interact with. Contains a button to attempt to login,
     * a text box to input a username, and a text box to input a password. When attempting
     * to login this class will validate credentials with the backend and allow login if the username
     * and password are correct
     *
     * @param savedInstanceState Current Instance State
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityNewuserBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        msg = (TextView) findViewById(R.id.msg);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final EditText confirmPasswordEditText = binding.Confirm;
        final Button signUpButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                System.out.println(passwordEditText.getText().toString());
                System.out.println(confirmPasswordEditText.getText().toString());
                System.out.println(passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString()));

                if(passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString()) && passwordEditText.getText().toString() != "" && confirmPasswordEditText.getText().toString() != ""){
                    signUpButton.setEnabled(loginFormState.isDataValid());
                }
                else signUpButton.setEnabled(false);

                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.signUpDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject newUser = JSONUtility.createLoginObject(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                JSONUtility.makeObjectPost(newUser, Const.URL_JSON_USER);

                Intent intent = new Intent(NewUserActivity.this, LoginActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_login);

            }
        });
    }

    /**
     * If the login fails, displays an error code so the user knows what happened
     * @param errorString error code
     */
    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}