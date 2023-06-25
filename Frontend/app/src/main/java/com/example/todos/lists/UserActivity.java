package com.example.todos.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.todos.R;
import com.example.todos.databinding.ActivityUserBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
/**
 * Activity that displays a Users profile
 */
public class UserActivity extends AppCompatActivity {

private ActivityUserBinding binding;

Button goToCalendar;

ImageView profile;

        /**
         * Creates page for viewing a User Profile
         * @param savedInstanceState Current Instance State
         *
         */
        @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPager viewPager = binding.viewPager;
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        goToCalendar = (Button) findViewById(R.id.goToCalendar);
        profile = (ImageView) findViewById(R.id.profile);

        goToCalendar.setOnClickListener(new View.OnClickListener() {
                /**
                 * Goes to the calendar page on clicking the calendar button
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(UserActivity.this, CalendarActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_calendar);
                }
                });
        }
}
