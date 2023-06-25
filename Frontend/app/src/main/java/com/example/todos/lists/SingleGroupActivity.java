/*

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todos.databinding.ActivitySingleGroupBinding;
import com.example.todos.lists.CalendarActivity;
import com.example.todos.lists.GroupsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


package com.example.todos.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.todos.R;
import com.example.todos.databinding.ActivitySingleGroupBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
**
 * Activity that displays a single group
 *
public class SingleGroupActivity extends AppCompatActivity {

private ActivitySingleGroupBinding binding;

Button goToCalendar;
Button goToGroups;
FloatingActionButton goToChat;

        **
         * Creates page for viewing a Single Group
         * @param savedInstanceState Current Instance State
         *
         *
        @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySingleGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TabLayout tabs = binding.tabs;

        goToCalendar = (Button) findViewById(R.id.goToCalendar);
        goToGroups = (Button) findViewById(R.id.goToGroupsIn);

        goToCalendar.setOnClickListener(new View.OnClickListener() {
                **
                 * Goes to the calendar page on clicking the calendar button
                 * @param v
                 *
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(SingleGroupActivity.this, CalendarActivity.class);
                        startActivity(intent);
                }
        });

        goToGroups.setOnClickListener(new View.OnClickListener() {
                **
                 * Goes to the groups list page on clicking the groups button
                 * @param v
                 *
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(SingleGroupActivity.this, GroupsActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_groups);
                }
        });

        }
}

 */
