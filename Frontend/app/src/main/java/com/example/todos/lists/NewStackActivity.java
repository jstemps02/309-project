package com.example.todos.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todos.R;
import com.example.todos.data.tools.JSONUtility;
import com.example.todos.databinding.ActivityNewstackBinding;
import com.example.todos.ui.login.LoginActivity;

import org.json.JSONObject;

/**
 * Class that is the page for that allows the user to create a new Group
 */
public class NewStackActivity extends AppCompatActivity {

private ActivityNewstackBinding binding;

Button MakeStack, Back;
EditText Title, Group;

        /**
         * Creates page for New Group creation
         * @param savedInstanceState Current Instance State
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                binding = ActivityNewstackBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                MakeStack = (Button)findViewById(R.id.create_new_group);
                Title = (EditText)findViewById(R.id.groupName);
                Group = (EditText)findViewById(R.id.groupOwner);
                Back = (Button)findViewById(R.id.buttonback2);

                Back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(NewStackActivity.this, StacksActivity.class);
                                startActivity(intent);
                        }
                });

                MakeStack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                // Add new group
                                String newStack = "stacks/groups/" + Group.getText().toString();
                                JSONObject cred = JSONUtility.createNewStackObject(Title.getText().toString());
                                JSONUtility.makeAPIPost(cred, newStack);

                                // Get users stacks
                                String getStacks = "users/stacks/" + LoginActivity.user.username;
                                cred = JSONUtility.createAPIObject(LoginActivity.user.username);
                                JSONUtility.makeAPIGet(cred, getStacks);

                                // Get user's todos
                                String getTodos = "todos/user/" + LoginActivity.user.username;
                                cred = JSONUtility.createAPIObject(LoginActivity.user.username);
                                JSONUtility.makeAPIGet(cred, getTodos);

                                Intent intent = new Intent(NewStackActivity.this, GroupsActivity.class);
                                startActivity(intent);
                        }
                });
        }
}
