package backend.Stacks;

import backend.Users.User;
import java.util.ArrayList;
import java.util.List;
import backend.Todos.Todo;
import backend.Groups.Group;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Calendar;

import javax.persistence.*;

@Entity
public class Stack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private boolean isActive;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "todo_id")
    @JsonIgnore
    private List<Todo> TodoList;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private Group group;
    public Stack() {

    }


    public Stack(String title, Boolean isActive, List<Todo> TodoList, Group group) {
        this.title = title;
        this.isActive = isActive;
        this.TodoList = new ArrayList<>();
        this.group = group;
    }

    public  int getId(){ return id; }

    public void setId(int id){ this.id = id;}

    public String getTitle(){ return title; }

    public void setTitle(String title){ this.title = title; }


    public boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }

    public  int nextAvailableID(){ return id; }

//    public User getUser(){
//        return user;
//    }
//
//    public void setUser(User user){
//        this.user = user;
//    }

    public Group getGroup(){ return group;}
    public void setGroup(Group group){  this.group = group;    }
    public List<Todo> getTodos() {    return TodoList;   }

    public void addTodos(Todo todo) { this.TodoList.add(todo);    }

    public void setTodoNull() { TodoList = null;    }
    public void setTodo(List<Todo> TodoList) { this.TodoList = TodoList;    }


}

