package com.example.todos.lists;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todos.R;
import com.example.todos.data.tools.Const;
import com.example.todos.data.tools.JSONUtility;
import com.example.todos.ui.login.LoginActivity;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Activity that displays a Users Calendar
 */
public class CalendarActivity extends AppCompatActivity {

    Button goTodos;
    TextView title;
    CalendarView cale;
    Button groups;
    Button logout;
    Button stacks;
    Button settings;

    String username = null;

    public void setUsername(String name) {
        username = name;
    }

    /**
     * Creates all the views that the user can go to
     */
    private void initializeViews() {
        title = (TextView) findViewById(R.id.title_view);
        cale = (CalendarView) findViewById(R.id.calendar_view);
        groups = (Button) findViewById(R.id.my_groups);
        goTodos = (Button) findViewById(R.id.todosButton);
        stacks = (Button) findViewById(R.id.stacksButton);
        logout = (Button) findViewById(R.id.logout);
        settings = (Button) findViewById(R.id.settings);
    }

    /**
     * Creates Calendar page to be displayed on android device. Also creates a listener
     * that listens for clicks
     * @param savedInstanceState Current Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Get users stacks
        String getStacks = "users/stacks/" + LoginActivity.user.username;
        JSONObject cred = JSONUtility.createAPIObject(LoginActivity.user.username);
        JSONUtility.makeAPIGet(cred, getStacks);

        // Get user's todos
        String getTodos = "todos/user/" + LoginActivity.user.username;
        cred = JSONUtility.createAPIObject(LoginActivity.user.username);
        JSONUtility.makeAPIGet(cred, getTodos);

        // Get online users
        //WebSocketActivity ws = new WebSocketActivity();

        initializeViews();
        String date_text = DateToString(cale.getDate());
        title.setText(date_text);

        cale.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView cal_view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);
                cal_view.setDate(cal.getTimeInMillis());
                Log.d("calendar", String.valueOf(cal_view.getDate()));
                String date_text = DateToString(cale.getDate());
                title.setText(date_text);
            }
        });

        goTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CalendarActivity.this, TodosActivity.class);
                startActivity(intent);
            }
        });

        stacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CalendarActivity.this, StacksActivity.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CalendarActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CalendarActivity.this, GroupsActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                JSONObject cred = JSONUtility.createLogoutObject(LoginActivity.user.username);
                JSONUtility.makeObjectPost(cred, Const.URL_JSON_LOGOUT);
                Intent intent = new Intent(CalendarActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public static String CalendarToString(Calendar cal){
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        return format.format(cal.getTime());
    }

    public Calendar StringtoCalendar(String str) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        cal.setTime(sdf.parse(str));// all done
        return cal;
    }

    public static String DateToString(long date){
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyy", Locale.ENGLISH);
        return format.format(date);
    }
}
