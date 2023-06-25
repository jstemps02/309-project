package com.example.todos.data.tools;

import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.todos.lists.SettingsActivity;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.todos.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

/**
 * Class that contains methods for using JSON
 */
public class JSONUtility {

    public static List<ToDo> myToDos;
    public static List<Stack> myStacks;
    public static List<OtherUser> myUsers;
    public static List<ToDo> myGroups;

    public static String WSUser;

    private String TAG = JSONUtility.class.getSimpleName();
    private String tag_string_req = "string_req";
    private static boolean loginResponse = false;

    /**
     * Creates object for logging in
     *
     * @param username Username of User
     * @param password Password of User
     * @return JSONObject that can be sent to server for login confirmation. Contains username
     * and password
     */
    public static JSONObject createLoginObject(String username, String password) {
        JSONObject cred = new JSONObject();
        try {
            cred.put("username", username);
            cred.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cred;
    }

    /**
     * Creates object for logging out
     *
     * @param username Users username
     * @return JSONObject that can be sent to backend for logout confirmation. Only contains
     * username
     */
    public static JSONObject createLogoutObject(String username) {
        JSONObject cred = new JSONObject();
        try {
            cred.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cred;
    }

    /**
     * Creates object for a group
     *
     * @param username Users username
     * @return Gets groups a user is in
     */
    public static JSONObject createAPIObject(String username) {
        JSONObject cred = new JSONObject();
        try {
            cred.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cred;
    }

    public static JSONObject createNewTodoObject(String title, String calendar, String username) {
        JSONObject cred = new JSONObject();
        try {
            cred.put("id", 0);
            cred.put("title", title);
            cred.put("isActive", true);
            cred.put("calendar", calendar);
            cred.put("username", username);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cred;
    }

    public static JSONObject createNewStackObject(String title) {
        JSONObject cred = new JSONObject();
        try {
            //User[] users = {}; // Empty array

            cred.put("id", 0);
            cred.put("title", title);
            cred.put("isActive", true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cred;
    }

    public static JSONObject createNewGroupObject(String title, String owner) {
        JSONObject cred = new JSONObject();
        try {
            //User[] users = {}; // Empty array

            cred.put("id", 0);
            cred.put("groupname", title);
            cred.put("owner", owner);
            cred.put("isActive", true);
            //cred.put("users", users);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cred;
    }

    /**
     * Makes a get request for a JSONObject from the backend
     */
    public static void makeObjectGet(String url) {
        String TAG = JSONUtility.class.getSimpleName();
        TextView msgResponse = null;
        String tag_string_req = "string_req";
        // Create the JSON Object Request
        JsonObjectRequest jReq = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    // Listens for a JSON response from the backend
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
            // Error Lister for errors involving Volley
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            // Get any extra HTTP Headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hdrs = new HashMap<String, String>();
                hdrs.put("Content-Type", "application/json; charset=utf-8");
                return hdrs;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jReq, tag_string_req);
    }

    /**
     * Attempts to make a post to the backend
     *
     * @param cred The JSONObject to be sent to the backend
     * @param url The url to which the object will be posted
     * @return A string containing success state of the post attempt
     */
    public static void makeObjectPost(JSONObject cred, String url) {
        String TAG = JSONUtility.class.getSimpleName();
        String tag_string_req = "string_req";

        // Create the JSON Object Request
        JsonObjectRequest jReq = new JsonObjectRequest(
                Request.Method.POST, url, cred,
                new Response.Listener<JSONObject>() {
                    // Listens for a JSON response from the backend
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
            // Error Lister for errors involving Volley
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            // Get any extra HTTP Headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hdrs = new HashMap<String, String>();
                hdrs.put("Content-Type", "application/json; charset=utf-8");
                return hdrs;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jReq, tag_string_req);
    }

    /**
     * Sends a logout post request to the backend
     *
     * @param cred JSONObject containt users username
     */
//    public static void makeLogoutPost(JSONObject cred) {
//        String TAG = JSONUtility.class.getSimpleName();
//        TextView msgResponse = null;
//        String tag_string_req = "string_req";
//        // Create the JSON Object Request
//        JsonObjectRequest jReq = new JsonObjectRequest(
//                Request.Method.POST, Const.URL_JSON_LOGOUT, cred,
//                new Response.Listener<JSONObject>() {
//
//                    // Listens for a JSON response from the backend
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
//                    }
//                }, new Response.ErrorListener() {
//
//            // Error Lister for errors involving Volley
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//            }
//        }) {
//            // Get any extra HTTP Headers
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> hdrs = new HashMap<String, String>();
//                hdrs.put("Content-Type", "application/json; charset=utf-8");
//                return hdrs;
//            }
//        };
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jReq, tag_string_req);
//    }

    // TODO: Tailor to specific needs
    /**
     *Posts a JSONObject containing groups the user has made
     * @param cred JSONObject containing users username
     *
     */
    public static void makeGetAPIPost(JSONObject cred) {
        String TAG = JSONUtility.class.getSimpleName();
        TextView msgResponse = null;
        String tag_string_req = "string_req";
        // Create the JSON Object Request
        JsonObjectRequest jReq = new JsonObjectRequest(
                Request.Method.GET, Const.URL_JSON_STUFF, cred,
                new Response.Listener<JSONObject>() {

                    // Listens for a JSON response from the backend
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

            // Error Lister for errors involving Volley
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            // Get any extra HTTP Headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hdrs = new HashMap<String, String>();
                hdrs.put("Content-Type", "application/json; charset=utf-8");
                return hdrs;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jReq, tag_string_req);
    }


    public static void makeAPIGet(JSONObject cred, String url) {
        // Create an instance of the OnResponseListener interface
        OnResponseListener responseListener = new OnResponseListener() {
            @Override
            public void onSuccess(String response) {
                // Handle successful response
                // The response string is available here
                System.out.println("Get Response: ");
                System.out.println(response);
                if (url.length() >= 13 && url.substring(0, 13).equals("users/stacks/")) { // Get stacks
                    System.out.println("Stacking Stacks");
                    convertToStacks(response);

                    // Sort the list based on the calendar string
                    if (myStacks != null) {
                        Collections.sort(myStacks, Comparator.comparing(Stack::getTitle));
                    }

                } else if (url.length() >= 11 && url.substring(0, 11).equals("todos/user/")) { // Get todos
                    System.out.println("Doing ToDos");
                    convertToTodos(response);

                    // Sort the list based on the calendar string
                    if (myToDos != null) {
                        Collections.sort(myToDos, Comparator.comparing(ToDo::getCalendar));
                    }
                }
            }

            @Override
            public void onError(String error) {
                // Handle error response
                // The error message is available here
                System.out.println(error);
            }
        };

        // Call the makeAPIGet function with the JSONObject and URL, along with the response listener
        makeAPIGet2(cred, url, responseListener);

    }

    private static void makeAPIGet2(JSONObject cred, String url, final OnResponseListener listener) {
        String TAG = JSONUtility.class.getSimpleName();

        // Create the JSON Object Request
        JsonArrayRequest jReq = new JsonArrayRequest(
                Request.Method.GET, Const.URL_JSON_STUFF + url, null,
                new Response.Listener<JSONArray>() {

                    // Listens for a JSON response from the backend
                    @Override
                    public void onResponse(JSONArray response) {
                        if (listener != null) {
                            listener.onSuccess(response.toString());
                        }
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

            // Error Listener for errors involving Volley
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onError(error.getMessage());
                }
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            // Get any extra HTTP Headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hdrs = new HashMap<String, String>();
                hdrs.put("Content-Type", "application/json; charset=utf-8");
                return hdrs;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jReq);
    }

    // Define an interface for response callbacks
    interface OnResponseListener {
        void onSuccess(String response);

        void onError(String error);
    }

    public static void makeAPIPost(JSONObject cred, String url) {
        // Create an instance of the OnResponseListener interface
        OnResponseListener responseListener = new OnResponseListener() {
            @Override
            public void onSuccess(String response) {
                // Handle successful response
                // The response string is available here
                System.out.println(response);
            }

            @Override
            public void onError(String error) {
                // Handle error response
                // The error message is available here
                System.out.println(error);

            }
        };

        // Call the makeAPIGet function with the JSONObject and URL, along with the response listener
        makeAPIPost2(cred, url, responseListener);

    }

    private static void makeAPIPost2(JSONObject cred, String url, final OnResponseListener listener) {
        String TAG = JSONUtility.class.getSimpleName();
        TextView msgResponse = null;
        String tag_string_req = "string_req";
        // Create the JSON Object Request
        JsonObjectRequest jReq = new JsonObjectRequest(
                Request.Method.POST, Const.URL_JSON_STUFF + url, cred,
                new Response.Listener<JSONObject>() {

                    // Listens for a JSON response from the backend
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {

            // Error Lister for errors involving Volley
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            // Get any extra HTTP Headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hdrs = new HashMap<String, String>();
                hdrs.put("Content-Type", "application/json; charset=utf-8");
                return hdrs;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jReq, tag_string_req);
    }

    public static void convertToConfirmation(String json) {

        Gson gson = new Gson();

        // Parse JSON array into a list of ToDos
        // myToDos = gson.fromJson(json, new TypeToken<List<ToDo>>(){}.getType());

    }

    public static void convertToTodos(String json) {

        Gson gson = new Gson();

        // Parse JSON array into a list of ToDos
        myToDos = gson.fromJson(json, new TypeToken<List<ToDo>>(){}.getType());

    }

    public static void convertToStacks(String json) {

        Gson gson = new Gson();

        // Parse JSON array into a list of ToDos
        myStacks = gson.fromJson(json, new TypeToken<List<Stack>>() {
        }.getType());

    }

    public static void convertToGroups(String json) {

        Gson gson = new Gson();

        // Parse JSON array into a list of Groups
        myGroups = gson.fromJson(json, new TypeToken<List<Group>>() {
        }.getType());

    }

    public static void convertWStoOnline(String json) {
        json = json.replace(String.valueOf(':'), " - ");
        json = json.replace(String.valueOf('{'), "");
        json = json.replace(String.valueOf('}'), "");
        json = json.replace(String.valueOf(','), "\n");
        json = json.replace("false", "Offline");
        json = json.replace("true", "Online");
        json = json.concat("\n");
        WSUser = json;

    }
}
