package com.example.todos.data.tools;

/**
 * Data class that captures information for a specific singleToDo
 */
public class ToDo {

    private int id;
    private String title;
    private boolean isActive;
    private String calendar;
    private String userName;

    /**
     * Creates a singleToDo object
     * @param id
     * @param title
     * @param isActive
     * @param calendar
     * @param userName
     */
    public ToDo(int id, String title, boolean isActive, String calendar, String userName) {
        this.id = id;
        this.title = title;
        this.isActive = isActive;
        this.calendar = calendar;
        this.userName = userName;
    }

    /**
     * ID Get
     * @return ID of the singleToDo
     */
    public int getId() {
        return id;
    }

    /**
     * Title Get
     * @return Title of the singleToDo
     */
    public String getTitle() {
        return title;
    }

    /**
     * isActive Get
     * @return current status of the singleToDo
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Calendar Get
     * @return Calendar string (stored in unix mils)
     */
    public String getCalendar() {
        return calendar;
    }

    /**
     * Username Get
     * @return Username associated with the singleToDo
     */
    public String getUsername() {
        return userName;
    }
}