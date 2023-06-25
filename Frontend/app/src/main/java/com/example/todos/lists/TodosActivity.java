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
import com.example.todos.data.tools.ToDo;
import com.example.todos.databinding.ActivityTodosBinding;
import com.example.todos.ui.login.LoginActivity;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Activity that displays a ToDo Stack
 */
public class TodosActivity extends AppCompatActivity {

private ActivityTodosBinding binding;

Button goToCalendar;
Button goToGroups;
Button goToNewToDo;
ListView list;


        /**
         * Creates page for viewing a single ToDoS
         * @param savedInstanceState Current Instance State
         *
         */
        @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTodosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        goToCalendar = (Button) findViewById(R.id.goToCalendar);
        goToGroups = (Button) findViewById(R.id.goToGroups);
        goToNewToDo = (Button) findViewById(R.id.goToNewToDo);
        list = (ListView) findViewById(R.id.listToDos);

        ArrayList listArray = new ArrayList();
        String Item = "";
        int index;

        if (JSONUtility.myToDos != null) {
                // Access the parsed Java objects
                for (ToDo myToDo : JSONUtility.myToDos) {
                        Item = myToDo.getCalendar();
                        index = Item.indexOf(".");
                        Item = Item.substring(0, index - 3);
                        Item = Item.replace('T', ' ');
                        Item = myToDo.getTitle() + "\n" + Item;
                        listArray.add(Item);
                }
        }

        // Create an ArrayAdapter and pass the ArrayList to it
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listArray);

        // Set the adapter to the ListView
        list.setAdapter(adapter);

        // Make the buttons clickable
        list.setClickable(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                /**
                 * Goes to a singleToDo page by clicking a singleToDo button in the list
                 */
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Delete from the backend
                        String[] lines = listArray.get(position).toString().split("\\r?\\n", 2);
                        String delToDo = "delete/todo/" + lines[0];
                        JSONObject cred = new JSONObject();
                        JSONUtility.makeAPIPost(cred, delToDo);

                        // Delete from the page
                        listArray.remove(position);
                        adapter.notifyDataSetChanged();
                }
        });

        goToCalendar.setOnClickListener(new View.OnClickListener() {
                /**
                 * Goes to the calendar page on clicking the calendar button
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(TodosActivity.this, CalendarActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_calendar);
                }
        });

        goToGroups.setOnClickListener(new View.OnClickListener() {
                /**
                 * Goes to the groups list page on clicking the groups button
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(TodosActivity.this, GroupsActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_groups);
                }
        });

        goToNewToDo.setOnClickListener(new View.OnClickListener() {
                /**
                 * Goes to the newToDo page on clicking the newToDo button
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(TodosActivity.this, NewToDoActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_newtodo);
                }
        });
        }
}
