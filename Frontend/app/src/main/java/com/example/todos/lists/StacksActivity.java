package com.example.todos.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todos.R;
import com.example.todos.data.tools.JSONUtility;
import com.example.todos.data.tools.Stack;
import com.example.todos.databinding.ActivityStacksBinding;

import java.util.ArrayList;

/**
 * Activity that displays a ToDo Stack
 */
public class StacksActivity extends AppCompatActivity {

private ActivityStacksBinding binding;

Button goToCalendar;
Button goToNewToDo;
ListView list;


        /**
         * Creates page for viewing a single ToDoS
         * @param savedInstanceState Current Instance State
         */
        @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStacksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        goToCalendar = (Button) findViewById(R.id.goToCalendar);
        goToNewToDo = (Button) findViewById(R.id.goToNewStack);
        list = (ListView) findViewById(R.id.listStacks);

        ArrayList listArray = new ArrayList();
        String Item = "";
        int index;

        // Access the parsed Java objects
        if (JSONUtility.myStacks != null) {
                for (Stack myStack : JSONUtility.myStacks) {
                        Item = myStack.getTitle();
                        listArray.add(Item);
                }
        }

        // Create an ArrayAdapter and pass the ArrayList to it
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listArray);

        // Set the adapter to the ListView
        list.setAdapter(adapter);

        // Make the buttons clickable
        list.setClickable(true);

        /*
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                **
                 * Goes to a singleStack page by clicking a singleStack button in the list
                 *
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(StacksActivity.this, SingleStackActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_singlestack);
                }
        });
        */

        goToCalendar.setOnClickListener(new View.OnClickListener() {
                /**
                 * Goes to the calendar page on clicking the calendar button
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(StacksActivity.this, CalendarActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_calendar);
                }
        });

        goToNewToDo.setOnClickListener(new View.OnClickListener() {
                /**
                 * Goes to the newToDo page on clicking the newToDo button
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(StacksActivity.this, NewStackActivity.class);
                        startActivity(intent);
                }
        });
        }
}
