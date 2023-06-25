package com.example.todos.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todos.R;
import com.example.todos.data.tools.JSONUtility;
import com.example.todos.databinding.ActivityNewgroupBinding;
import com.example.todos.ui.login.LoginActivity;

import org.json.JSONObject;

/**
 * Class that is the page for that allows the user to create a new Group
 */
public class NewGroupActivity extends AppCompatActivity {

private ActivityNewgroupBinding binding;

Button MakeGroup, Back;
EditText Title, Owner;

        /**
         * Creates page for New Group creation
         * @param savedInstanceState Current Instance State
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                binding = ActivityNewgroupBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                MakeGroup = (Button)findViewById(R.id.create_new_group);
                Title = (EditText)findViewById(R.id.groupName);
                Owner = (EditText)findViewById(R.id.groupOwner);
                Back = (Button)findViewById(R.id.buttonback3);

                Back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(NewGroupActivity.this, GroupsActivity.class);
                                startActivity(intent);
                        }
                });

                MakeGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                // Add new group
                                String newTodo = "groups/user/" + Owner.getText().toString();
                                JSONObject cred = JSONUtility.createNewGroupObject(Title.getText().toString(), Owner.getText().toString());
                                JSONUtility.makeAPIPost(cred, newTodo);

                                // Get users stacks
                                String getStacks = "users/stacks/" + LoginActivity.user.username;
                                cred = JSONUtility.createAPIObject(LoginActivity.user.username);
                                JSONUtility.makeAPIGet(cred, getStacks);
                                /*
                                if (SKs != null) {
                                        System.out.println(SKs);
                                }
                                */

                                // Get user's todos
                                String getTodos = "todos/user/" + LoginActivity.user.username;
                                cred = JSONUtility.createAPIObject(LoginActivity.user.username);
                                JSONUtility.makeAPIGet(cred, getTodos);
                                /*
                                if (TDs != null) {
                                        System.out.println(TDs);
                                }
                                */

                                Intent intent = new Intent(NewGroupActivity.this, GroupsActivity.class);
                                startActivity(intent);
                        }
                });
        }
}
