package backend.Todos;

import backend.Stacks.Stack;
import backend.Users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private boolean isActive;
    private String calendar;

    private String username;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    private List<Stack> Stacks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    public Todo() {

    }


    public Todo(String title, Boolean isActive, String calendar, String username) {
        this.title = title;
        this.isActive = isActive;
        this.calendar = calendar;
        this.username = username;
        this.Stacks = new ArrayList<>();
    }

    public  int getId(){ return id; }

    public void setId(int id){ this.id = id;}

    public String getTitle(){ return title; }

    public void setTitle(String title){ this.title = title; }

    public String getCalendar(){ return calendar; }

    public void setCalendar(String calendar){ this.calendar = calendar; }


    public boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }

    public  int nextAvailableID(){ return id; }
    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public void setStacksNull(){
        this.Stacks = null;
    }

}

