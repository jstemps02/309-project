package  com.example.todos.ui.login;

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
import com.example.todos.data.tools.Group;
import com.example.todos.data.tools.OtherUser;
import com.example.todos.data.tools.Stack;
import com.example.todos.data.tools.ToDo;
import com.example.todos.data.tools.User;
import com.example.todos.databinding.ActivityLoginBinding;
import com.example.todos.lists.CalendarActivity;
import com.example.todos.lists.NewUserActivity;

/**
 * Specific activity regarding logging in and out a User. Extends AppCombatActivity which is a class
 * that handles much of this functionality
 *
 * This is the first page a user sees when opening this app. It is the login screen
 */
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    public String response = null;

    static public User user =  new User();

    // Parsed json responses stored here
    static public OtherUser[] gOthers;
    static public Stack[] gStacks;
    static public ToDo[] gtodos;
    static public Group[] gGroups;

    static public TextView msg;

private ActivityLoginBinding binding;

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

     binding = ActivityLoginBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        msg = (TextView) findViewById(R.id.msg);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button toRegisterButton = binding.Register;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                if (response.equals("success")) {
                    user.setUsername(usernameEditText.getText().toString());
                    user.setPassword(passwordEditText.getText().toString());

                    Intent intent = new Intent(LoginActivity.this, CalendarActivity.class);
                    startActivity(intent);
                }
                else {}
            }
        });

        toRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
                startActivity(intent);
                setContentView(R.layout.activity_newuser);
            }
        });
    }

    /**
     * Displays a welcome message for specific user
     * @param model Current View of the login screen
     */
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    /**
     * If the login fails, displays an error code so the user knows what happened
     * @param errorString error code
     */
    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}