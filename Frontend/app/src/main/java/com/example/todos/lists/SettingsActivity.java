package com.example.todos.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todos.R;
import com.example.todos.data.tools.JSONUtility;
import com.example.todos.databinding.ActivitySettingsBinding;
import com.example.todos.ui.login.LoginActivity;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Activity that displays a Users Settings page
 */
public class SettingsActivity extends AppCompatActivity {

private ActivitySettingsBinding binding;
private WebSocket webSocket;

Button goToCalendar;

Button goToUser;

ListView list;

ArrayList listArray;

        /**
         * Creates page for displaying Users Settings
         * @param savedInstanceState Current Instance State
         *
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                binding = ActivitySettingsBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                goToCalendar = (Button) findViewById(R.id.goToCalendar);
                goToUser = (Button) findViewById(R.id.goToUser);
                list = (ListView) findViewById(R.id.listOnlineStatus);

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("ws://coms-309-032.class.las.iastate.edu:8080/status/" + LoginActivity.user.username)
                        .build();

                WebSocketListener webSocketListener = new WebSocketListener() {

                        public void onOpen(WebSocket webSocket, Response response) {
                                // WebSocket connection is successfully established
                                // You can send messages or perform other actions here
                                System.out.println("Websocket Opened");
                                webSocket.close(1000, "Close");
                        }

                        public void onMessage(WebSocket webSocket, String text) {
                                // Called when a message is received
                                System.out.println("Websocket Success");
                                System.out.println(text);
                                JSONUtility.convertWStoOnline(text);

                                listHelper();
                        }

                        public void onClosed(WebSocket webSocket, int code, String reason) {
                                // WebSocket connection is closed
                                System.out.println("Websocket Closed");
                        }

                        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                                // WebSocket connection failure
                                System.out.println("Websocket Error");
                                System.out.println(response);
                        }
                };

                webSocket = client.newWebSocket(request, webSocketListener);





        goToCalendar.setOnClickListener(new View.OnClickListener() {
                /**
                 * Goes to the calendar page on clicking the calendar button
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(SettingsActivity.this, CalendarActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_calendar);
                }
                });

        goToUser.setOnClickListener(new View.OnClickListener() {
                /**
                 * Goes to the user profile page on clicking the profile button
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(SettingsActivity.this, UserActivity.class);
                        startActivity(intent);
                        setContentView(R.layout.activity_user);
                }
        });
        }

        private void listHelper() {
                listArray = new ArrayList();

                listArray.add(JSONUtility.WSUser);

                // Create an ArrayAdapter and pass the ArrayList to it
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SettingsActivity.this, android.R.layout.simple_list_item_1, listArray);

                // Set the adapter to the ListView
                list.setAdapter(adapter);
        }
}
