package com.example.todos.data.tools;

import com.example.todos.data.tools.ToDo;

/**
 * Data class that captures information for a specific stack
 */
public class Stack {

    private int id;
    private String title;
    private boolean isActive;
    private ToDo[] todos;

    /**
     * Creates a stack object
     * @param id
     * @param title
     * @param isActive
     * @param todos
     */
    public Stack(int id, String title, boolean isActive, ToDo[] todos) {
        this.id = id;
        this.title = title;
        this.isActive = isActive;
        this.todos = todos;
    }

    /**
     * ID Get
     * @return ID of the stack
     */
    public int getId() {
        return id;
    }

    /**
     * Title Get
     * @return Title of the stack
     */
    public String getTitle() {
        return title;
    }

    /**
     * isActive Get
     * @return Current status of the stack
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * ToDos Get
     * @return Array of ToDos in the stack
     */
    public ToDo[] getToDos() {
        return todos;
    }
}
