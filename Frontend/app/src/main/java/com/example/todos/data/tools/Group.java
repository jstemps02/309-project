package com.example.todos.data.tools;

import com.example.todos.data.tools.OtherUser;
import com.example.todos.data.tools.Stack;

/**
 * Data class that captures information for a specific group
 */
public class Group {

    private int id;
    private String groupName;
    private String owner;
    private boolean isActive;
    private Stack[] stacks;
    private OtherUser[] users;

    /**
     * Creates a singleToDo object
     * @param id
     * @param groupName
     * @param owner
     * @param stacks
     * @param users
     * @param isActive
     */
    public Group(int id, String groupName, String owner, boolean isActive, Stack[] stacks, OtherUser[] users) {
        this.id = id;
        this.groupName = groupName;
        this.owner = owner;
        this.isActive = isActive;
        this.stacks = stacks;
        this.users = users;
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
     * @return Users Username
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Owner Get
     * @return Group owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Stacks Get
     * @return Array of Stacks associated with the user
     */
    public Stack[] getStacks() {
        return stacks;
    }

    /**
     * IsActive Get
     * @return Current account status of the user
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Users Get
     * @return Array of Users in the stack
     */
    public OtherUser[] getUsers() {
        return users;
    }
}