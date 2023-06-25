package com.example.todos.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todos.R;
import com.example.todos.databinding.ActivityGroupsBinding;
import com.google.android.material.tabs.TabLayout;

/**
 * Activity that displays a user's various groups
 */
public class GroupsActivity extends AppCompatActivity {

private ActivityGroupsBinding binding;

Button goToCalendar;
Button goToTodos;
Button goToUser;
Button goToNewGroup;
ListView list;

        /**
         * Creates Groups page to be displayed on android device
         * @param savedInstanceState Current Instance State
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                binding = ActivityGroupsBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                TabLayout tabs = binding.tabs;

                // TODO: Tailor to specific needs
                //JSONObject cred = JSONUtility.createGroupsObject(LoginActivity.user.username);
                //JSONUtility.makeGroupsPost(cred);

                goToCalendar = (Button) findViewById(R.id.goToCalendar);
                goToTodos = (Button) findViewById(R.id.goToTodos);
                goToUser = (Button) findViewById(R.id.goToUser);
                goToNewGroup = (Button) findViewById(R.id.goToNewGroup);
                list = (ListView) findViewById(R.id.listStacks);

        goToCalendar.setOnClickListener(new View.OnClickListener() {

                /**
                 * Opens Calendar page on clicking the calendar button
                 * @param v Current View
                 */
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(GroupsActivity.this, CalendarActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_calendar);
                }
                });

        goToTodos.setOnClickListener(new View.OnClickListener() {
                /**
                 * Goes to ToDoS page on clicking the ToDoS page button
                 * @param v Current View
                 */
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(GroupsActivity.this, TodosActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_todos);
                }
        });

        goToUser.setOnClickListener(new View.OnClickListener() {
                /**
                 * Goes to Users Profile Page on clicking the profile button
                 * @param v Current View
                 */
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(GroupsActivity.this, UserActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_user);
                }
        });

        goToNewGroup.setOnClickListener(new View.OnClickListener() {
                /**
                 * Goes to a specific groups page; placeholder
                 * @param v Current View
                 */
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(GroupsActivity.this, NewGroupActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_newgroup);
                }
        });
        }
}
