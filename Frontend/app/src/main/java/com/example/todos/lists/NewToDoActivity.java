package com.example.todos.lists;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todos.R;
import com.example.todos.data.tools.JSONUtility;
import com.example.todos.databinding.ActivityNewtodoBinding;
import com.example.todos.ui.login.LoginActivity;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * Class that is the page for creation of a new ToDoS
 */
public class NewToDoActivity extends AppCompatActivity implements View.OnClickListener{

private ActivityNewtodoBinding binding;

Button DatePicker, TimePicker, Back, Create;
EditText Date, Time, ToDo;

//Used internally by time and date picker, represents actual time
private int Year, Month, Day, Hour, Minute;

//Passed to object, represents selected time
private int sYear, sMonth, sDay, sHour, sMinute;


private Calendar calendar;
private String calstring;

        /**
         * Creates page for creating a new To Do
         * @param savedInstanceState Current Instance State
         *
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                binding = ActivityNewtodoBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                DatePicker = (Button)findViewById(R.id.button_date);
                TimePicker = (Button)findViewById(R.id.button_time);
                Back = (Button)findViewById(R.id.buttonback);
                Create = (Button)findViewById(R.id.create_new_todo);
                Date = (EditText)findViewById(R.id.input_date);
                Time = (EditText)findViewById(R.id.input_time);
                ToDo = (EditText)findViewById(R.id.todoName);

                calendar = Calendar.getInstance();

                sYear = 0;
                sMonth = 0;
                sDay = 0;
                sHour = 0;
                sMinute = 0;

                DatePicker.setOnClickListener(this);
                TimePicker.setOnClickListener(this);

                Back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(NewToDoActivity.this, TodosActivity.class);
                                startActivity(intent);
                        }
                });

                Create.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                calendar.set(sYear, sMonth, sDay, sHour, sMinute, 0);
                                calstring = CalendarToString(calendar);



                                // Add new singleTodo
                                String newTodo = "todos/user/" + LoginActivity.user.username;
                                JSONObject cred = JSONUtility.createNewTodoObject(ToDo.getText().toString(), calstring, LoginActivity.user.username);
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

                                Intent intent = new Intent(NewToDoActivity.this, TodosActivity.class);
                                startActivity(intent);
                        }
                });

        }

        @Override
        public void onClick(View v) {

                if (v == DatePicker) {

                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        Year = c.get(Calendar.YEAR);
                        Month = c.get(Calendar.MONTH);
                        Day = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                                new DatePickerDialog.OnDateSetListener() {

                                        @Override
                                        public void onDateSet(android.widget.DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {

                                                Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                                sYear = year;
                                                sMonth = monthOfYear;
                                                sDay = dayOfMonth;
                                        }
                                }, Year, Month, Day);
                        datePickerDialog.show();
                }
                if (v == TimePicker) {

                        // Get Current Time
                        final Calendar c = Calendar.getInstance();
                        Hour = c.get(Calendar.HOUR_OF_DAY);
                        Minute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                                new TimePickerDialog.OnTimeSetListener() {

                                        @Override
                                        public void onTimeSet(android.widget.TimePicker view, int hourOfDay,
                                                              int minute) {

                                                Time.setText(hourOfDay + ":" + minute);

                                                sHour = hourOfDay;
                                                sMinute = minute;
                                        }
                                }, Hour, Minute, false);
                        timePickerDialog.show();
                }
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
}
