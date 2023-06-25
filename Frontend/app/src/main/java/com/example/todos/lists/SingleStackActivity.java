/*
package com.example.todos.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.todos.R;
import com.example.todos.databinding.ActivitySinglestackBinding;
import com.example.todos.lists.CalendarActivity;
import com.example.todos.lists.StacksActivity;
import com.google.android.material.tabs.TabLayout;

**
 * Activity that displays a single ToDo
 *
public class SingleStackActivity extends AppCompatActivity {

private ActivitySinglestackBinding binding;

Button goToCalendar;
Button goToStacks;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySinglestackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TabLayout tabs = binding.tabs;

        goToCalendar = (Button) findViewById(R.id.goToCalendar);
        goToStacks = (Button) findViewById(R.id.goToStacks);


        goToCalendar.setOnClickListener(new View.OnClickListener() {
                **
                 * Goes to the calendar page on clicking the calendar button
                 * @param v
                 *
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(SingleStackActivity.this, CalendarActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_calendar);
                }
        });

        goToStacks.setOnClickListener(new View.OnClickListener() {
                **
                 * Goes to the Stacks list page on clicking the ToDos button
                 * @param v
                 *
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(SingleStackActivity.this, StacksActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_stacks);
                }
        });
        }
}

 */
